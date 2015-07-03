package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.niharinfo.makeadeal.RetrofitHelpers.LoginResponse;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import com.niharinfo.makeadeal.RetrofitHelpers.loginapi;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MakeADealLoginActivity extends Activity implements View.OnClickListener{

    TextView txtChangePassword;
    EditText edtLogin,edtPassword;
    Button btnLogin;
    //SmoothProgressBar progressBar;
    SharedPreferences sharedpreferences;
    ActionBar actionBar;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_adeal_login);
        actionBar = getActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        sharedpreferences = getSharedPreferences(ServiceHandlers.MyPREFERENCES, Context.MODE_PRIVATE);
        edtLogin = (EditText)findViewById(R.id.edtLoginID);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        btnLogin = (Button)findViewById(R.id.btnLoginMakeADeal);
        txtChangePassword = (TextView)findViewById(R.id.txtForgotPassword);
        btnLogin.setOnClickListener(this);
        txtChangePassword.setOnClickListener(this);
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener(){

        @Override
        public void onRefresh() {

            //simulate doing something
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(false);
                }

            }, 2000);
        }};

    public boolean isValid(){
        if(edtLogin.getText().length()==0){
            edtLogin.setError("Please enter emailID");
            edtLogin.requestFocus();
            return false;
        }else if(!edtLogin.getText().toString().matches(Globals.emailPattern)){
            edtLogin.setError("Please type valid emailID");
            edtLogin.setText("");
            edtLogin.requestFocus();
            return false;
        }
        if(edtPassword.getText().length()==0){
            edtPassword.setError("Please enter password");
            edtPassword.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_make_adeal_login, menu);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtForgotPassword:
                Intent i = new Intent(MakeADealLoginActivity.this,ChangePwdActivity.class);
                startActivity(i);
                break;
            case R.id.btnLoginMakeADeal:
                swipeRefreshLayout.setRefreshing(true);
                if(isValid()) {

                   // progressBar.setVisibility(View.VISIBLE);
                    String username = edtLogin.getText().toString();
                    String password = edtPassword.getText().toString();
                    RestAdapter restAdapter = new RestAdapter.Builder()
                            .setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                    loginapi gitapi = restAdapter.create(loginapi.class);
                    gitapi.login(username, password, new Callback<LoginResponse>() {
                        @Override
                        public void success(LoginResponse loginResponse, Response response) {
                            String msg = loginResponse.getResponse();
                            String token = loginResponse.getToken();
                            String email = loginResponse.getEmail();
                            String phone = loginResponse.getPhoneno();
                            String fbUserName = loginResponse.getFbUserName();
                            String name = loginResponse.getUserName();
                            String id = loginResponse.getId();
                            if (token != null) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                ServiceHandlers.USERID=loginResponse.getId();
                                editor.putString(ServiceHandlers.ACCESS_TOKEN, loginResponse.getToken());
                                editor.putString(ServiceHandlers.EMAIL, loginResponse.getEmail());
                                editor.putString(ServiceHandlers.USER_ID, loginResponse.getId());
                                editor.putString(ServiceHandlers.PHONE_NUMBER, loginResponse.getPhoneno());
                                editor.putString(ServiceHandlers.USER_NAME, loginResponse.getUserName());
                                editor.commit();
                                Intent i = new Intent(MakeADealLoginActivity.this, CategoriesActivity.class);
                                startActivity(i);
                            }
                            //progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            String msg = "fail";
                           // progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                break;
        }
    }
}
