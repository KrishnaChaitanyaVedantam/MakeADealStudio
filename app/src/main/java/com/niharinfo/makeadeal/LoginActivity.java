package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.niharinfo.makeadeal.RetrofitHelpers.gloginapi;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.GooglePlusData;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends Activity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String LOGIN_SHARED_PREF = "LoginCredentials";

    //LoginButton btnFbLogin;
    private SignInButton btnSignIn;

    ActionBar actionBar;

    private Button btnTwitterLogin,btnMakeADealLogin;
    private TextView txtLogin;

    private static final int RC_SIGN_IN = 0;
    ProgressDialog progressDialog;
    //private ConnectionResult mConnectionResult;


    String personName,email,res;
    SharedPreferences preferences;
    SharedPreferences sharedpreferences;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        sharedpreferences = getSharedPreferences(ServiceHandlers.MyPREFERENCES, Context.MODE_PRIVATE);
        actionBar = getActionBar();
        actionBar.hide();


        Globals.mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

        //btnFbLogin = (LoginButton)findViewById(R.id.authButton);
        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

       // btnTwitterLogin = (Button)findViewById(R.id.btnTwitter);
        btnMakeADealLogin = (Button)findViewById(R.id.btnMakeADealLogin);
        txtLogin = (TextView)findViewById(R.id.txtLogin);
        progressBar = (ProgressBar)findViewById(R.id.pbLogin);

        txtLogin.setOnClickListener(this);
        btnMakeADealLogin.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        String uid = sharedpreferences.getString(ServiceHandlers.USER_ID,"");
        if(sharedpreferences.contains(ServiceHandlers.USER_ID)){
            if(sharedpreferences.getString(ServiceHandlers.USER_ID,"").length()>0){
                ServiceHandlers.USERID=uid;
                Intent i = new Intent(LoginActivity.this,CategoriesActivity.class);
                startActivity(i);
                finish();
           }
        }
    }



    public GoogleApiClient getGoogleApiClient(){
        return Globals.mGoogleApiClient;
    }


    protected void onStart() {
        super.onStart();
        Globals.mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void resolveSignInError(){
        if (Globals.mConnectionResult.hasResolution()) {
            try {
                Globals.mIntentInProgress = true;
                Globals.mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                Globals.mIntentInProgress = false;
                Globals.mGoogleApiClient.connect();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConnected(Bundle bundle) {
        Globals.mSignInClicked = false;
        getProfileInformation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Globals.mGoogleApiClient.connect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign_in:
                    signInWithGplus();
                break;
            case R.id.btnMakeADealLogin:
                    Intent signUpIntent = new Intent(LoginActivity.this,SignupActivity.class);
                    startActivity(signUpIntent);
                break;
            case R.id.txtLogin:
                    Intent loginIntent = new Intent(LoginActivity.this,MakeADealLoginActivity.class);
                    startActivity(loginIntent);
                break;
        }
    }


    private void signInWithGplus() {
        if (!Globals.mGoogleApiClient.isConnecting()) {

            Globals.mSignInClicked = true;
            resolveSignInError();
            getProfileInformation();
        }
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (Globals.signout == 2) {
            Globals.signout = 1;
            Plus.AccountApi.clearDefaultAccount(Globals.mGoogleApiClient);
                Globals.mGoogleApiClient.disconnect();
                Globals.mGoogleApiClient.connect();

            //updateUI(false);
        }
    }

    public void signOutFromGplus() {
        if (Globals.mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(Globals.mGoogleApiClient);
            Globals.mGoogleApiClient.disconnect();
            Globals.mGoogleApiClient.connect();
            //updateUI(false);
        }
    }

    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(Globals.mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(Globals.mGoogleApiClient);
                personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                email = Plus.AccountApi.getAccountName(Globals.mGoogleApiClient);
                new TASk().execute();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendUserDetails(String email,String name){
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
        gloginapi gapi = restAdapter.create(gloginapi.class);
        gapi.login(email, name, "GooglePlus", new Callback<GooglePlusData>() {
            @Override
            public void success(GooglePlusData googlePlusData, Response response) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                ServiceHandlers.USERID = googlePlusData.getId();
                editor.putString(ServiceHandlers.EMAIL, googlePlusData.getEmail());
                editor.putString(ServiceHandlers.USER_ID, googlePlusData.getId());
                editor.putString(ServiceHandlers.USER_NAME, googlePlusData.getUser_name());
                editor.commit();

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }



    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!Globals.mIntentInProgress) {
            Globals.mConnectionResult = result;

            if (Globals.mSignInClicked) {
                resolveSignInError();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                Globals.mSignInClicked = false;
            }

            Globals.mIntentInProgress = false;

            if (!Globals.mGoogleApiClient.isConnecting()) {
                Globals.mGoogleApiClient.connect();
            }
        }
    }



    public class TASk extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(
                    "http://www.makeadeal.in/json/google.php");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("username",personName));
                nameValuePairs.add(new BasicNameValuePair("oauth_provider", "GooglePlus"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                @SuppressWarnings("unused")
                HttpResponse response = httpclient.execute(httppost);
                res = EntityUtils.toString(response.getEntity());
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
                // TODO Auto-generated catch block
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            String response=res;
            String responseOne="";
            try{
                JSONObject jsonObject = new JSONObject(response);
                responseOne = jsonObject.getString("response");
                ServiceHandlers.USERID = jsonObject.getString("id");
                progressBar.setVisibility(View.INVISIBLE);
                Intent i = new Intent(LoginActivity.this,CategoriesActivity.class);
                startActivity(i);
                finish();
            }catch (Exception e){
                e.printStackTrace();
            }

            if (res.equalsIgnoreCase(response)) {
                finish();
            }else {
            }

        }

    }



}
