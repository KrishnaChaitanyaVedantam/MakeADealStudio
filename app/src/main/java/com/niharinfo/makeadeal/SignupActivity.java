package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.niharinfo.makeadeal.RetrofitHelpers.RegistrationResponse;
import com.niharinfo.makeadeal.RetrofitHelpers.registrationapi;
import com.niharinfo.makeadeal.helper.Globals;
import com.niharinfo.makeadeal.helper.ServiceHandlers;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBarUtils;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignupActivity extends Activity implements View.OnClickListener{

    TextView txtTerms,txtMessage;
    EditText edtEmail,edtName,edtPassword,edtConfirmPassword,edtPhoneNumber;
    Button btnRegister;
    SmoothProgressBar smoothProgressBar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        txtTerms = (TextView)findViewById(R.id.txtTerms);
        String first = "By signing up,you agree with the";
        String second = "<font color='#123456'>Terms of service</font>"+" & Privacy Policy";
        txtTerms.setText(Html.fromHtml(first + "<br>" + second));
        actionBar = getActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        smoothProgressBar = (SmoothProgressBar)findViewById( R.id.smoothPB);
        smoothProgressBar.setSmoothProgressDrawableBackgroundDrawable(
                SmoothProgressBarUtils.generateDrawableWithColors(
                        getResources().getIntArray(R.array.pocket_background_colors),
                        ((SmoothProgressDrawable) smoothProgressBar.getIndeterminateDrawable()).getStrokeWidth()));
        txtMessage = (TextView)findViewById(R.id.txtRegistrationMessage);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtPassword = (EditText)findViewById(R.id.edtSignUpPassword);
        edtConfirmPassword = (EditText)findViewById(R.id.edtSignUpConfirmPassword);
        edtName = (EditText)findViewById(R.id.edtDisplayName);
        edtPhoneNumber = (EditText)findViewById(R.id.edtPhoneNumber);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

    }

    public boolean validateFields(){
        if(edtEmail.getText().length()==0){
            edtEmail.setError("Enter your Email ID");
            edtEmail.requestFocus();
            return false;
        }else if(!edtEmail.getText().toString().matches(Globals.emailPattern)) {
            edtEmail.setError("Please enter valid Email ID");
            edtEmail.setText("");
            edtEmail.requestFocus();
            return false;
        }else if(edtName.getText().toString().length() == 0){
            edtName.setError("Please enter user name");
            edtName.requestFocus();
            return false;
        }else if(edtPassword.getText().length()==0){
            edtPassword.setError("Please type password");
            edtPassword.requestFocus();
            return false;
        }else if(edtConfirmPassword.getText().length()==0){
            edtConfirmPassword.setError("Please re-type the password");
            edtConfirmPassword.requestFocus();
            return false;
        }else if(!edtConfirmPassword.getText().toString().equals(edtPassword.getText().toString())){
            edtConfirmPassword.setError("Password didn't match");
            edtConfirmPassword.setText("");
            edtConfirmPassword.requestFocus();
            return false;
        }else if(edtPhoneNumber.getText().toString().length()==0){
            edtPhoneNumber.setError("Please enter phone number");
            edtPhoneNumber.requestFocus();
            return false;
        }
        return true;
    }

    public void clearFields(){
        edtEmail.setText("");
        edtName.setText("");
        edtPassword.setText("");
        edtConfirmPassword.setText("");
        edtPhoneNumber.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                smoothProgressBar.setVisibility(View.VISIBLE);
                txtMessage.setVisibility(View.INVISIBLE);
                    if(validateFields()){

                        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ServiceHandlers.retrofitLiveApi).build();
                        registrationapi registrationApi = restAdapter.create(registrationapi.class);
                        registrationApi.UserRegistration(edtName.getText().toString(), edtEmail.getText().toString(), edtPassword.getText().toString(), edtPhoneNumber.getText().toString(), new Callback<RegistrationResponse>() {
                            @Override
                            public void success(RegistrationResponse registrationResponse, Response response) {
                                String res = registrationResponse.getRegistrationResponse();
                                if (res.equalsIgnoreCase("You already have an account")) {
                                    txtMessage.setBackgroundColor(getResources().getColor(R.color.red));
                                    txtMessage.setText("Email already exists");
                                    txtMessage.setVisibility(View.VISIBLE);
                                }
                                if (res.equalsIgnoreCase("sucess")) {
                                    txtMessage.setBackgroundColor(getResources().getColor(R.color.dark_green));
                                    txtMessage.setText("Successfully Registered");
                                    txtMessage.setVisibility(View.VISIBLE);
                                }
                                clearFields();
                                edtEmail.requestFocus();
                                smoothProgressBar.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                smoothProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });

                    }else{
                        smoothProgressBar.setVisibility(View.INVISIBLE);
                    }
                break;
        }
    }
}
