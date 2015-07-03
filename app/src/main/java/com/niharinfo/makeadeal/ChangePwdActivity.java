package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.niharinfo.makeadeal.RetrofitHelpers.ForgorPasswordApi;
import com.niharinfo.makeadeal.RetrofitHelpers.ForgotPassword;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBarUtils;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ChangePwdActivity extends Activity implements View.OnClickListener{

    Button btnReset;
    EditText edtEmail;
    TextView txtMessage;
    SmoothProgressBar progressBar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        actionBar = getActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        btnReset = (Button)findViewById(R.id.btnReset);
        edtEmail = (EditText)findViewById(R.id.edtChangePasswordEmail);
        txtMessage = (TextView)findViewById(R.id.txtResponseMessageChPwd);
        btnReset.setOnClickListener(this);
        progressBar = (SmoothProgressBar)findViewById(R.id.pb_google_plus);
        progressBar.setSmoothProgressDrawableBackgroundDrawable(
                SmoothProgressBarUtils.generateDrawableWithColors(
                        getResources().getIntArray(R.array.pocket_background_colors),
                        ((SmoothProgressDrawable) progressBar.getIndeterminateDrawable()).getStrokeWidth()));
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtMessage.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_change_password, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public boolean isValid(){
        if(edtEmail.getText().toString().length()==0){
            txtMessage.setBackgroundColor(getResources().getColor(R.color.red));
            txtMessage.setText("Please type emailId...");
            txtMessage.setVisibility(View.VISIBLE);
            return false;
        }else if(!edtEmail.getText().toString().matches(Globals.emailPattern)){
            txtMessage.setBackgroundColor(getResources().getColor(R.color.red));
            txtMessage.setText("Please type valid emailId...");
            edtEmail.setText("");
            edtEmail.requestFocus();
            txtMessage.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnReset:
                if(isValid()) {
                    progressBar.setVisibility(View.VISIBLE);
                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                    final ForgorPasswordApi forgorPasswordApi = restAdapter.create(ForgorPasswordApi.class);
                    forgorPasswordApi.GetNewPassword(edtEmail.getText().toString(), new Callback<ForgotPassword>() {
                        @Override
                        public void success(ForgotPassword forgotPassword, Response response) {
                            String res = forgotPassword.getMessage();
                            if (res.equalsIgnoreCase("Account not found")) {
                                txtMessage.setBackgroundColor(getResources().getColor(R.color.red));
                                txtMessage.setText("Account was not found...");
                                edtEmail.setText("");
                                edtEmail.requestFocus();
                                txtMessage.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                            if (res.equalsIgnoreCase("Reset link was sent to your email!")) {
                                txtMessage.setBackgroundColor(getResources().getColor(R.color.dark_green));
                                txtMessage.setText("Please visit your email and reset the password");
                                edtEmail.setText("");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(ChangePwdActivity.this, MakeADealLoginActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }, 3000);
                                txtMessage.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }

                break;
        }
    }
}
