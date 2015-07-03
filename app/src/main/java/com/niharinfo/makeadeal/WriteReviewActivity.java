package com.niharinfo.makeadeal;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Toast;
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


public class WriteReviewActivity extends Activity {

    EditText eTHeading,eTSummry,eTPros,eTPros1,eTpros2,eTPros3,eTCons,eTCons1,eTCons2,eTCons3;
    ImageButton ibtnPros1,ibtnPros2,ibtnPros3,ibtnCons1,ibtnCons2,ibtnCons3;
    Button btnProAdd,btnConsAdd,btnSubmit;
    String review_heading,summary,pros,cons;

    RatingBar Rbar;
    String res=null;
    int cur_rat;
    int A;
    int B;
    int numStars=0;
    String productID;
    ActionBar actionBar;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        actionBar = getActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Write Review");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        progressBar = (ProgressBar)findViewById(R.id.pbWriteReview);
        eTHeading=(EditText) findViewById(R.id.eTHeading);
        eTSummry=(EditText) findViewById(R.id.etSummary);
        eTPros=(EditText) findViewById(R.id.eTPros);
        eTPros1=(EditText) findViewById(R.id.eTPros1);
        eTpros2=(EditText) findViewById(R.id.eTPros2);
        eTPros3=(EditText) findViewById(R.id.eTPros3);
        eTCons=(EditText) findViewById(R.id.eTCons);
        eTCons1=(EditText) findViewById(R.id.eTCons1);
        eTCons2=(EditText) findViewById(R.id.eTCons2);
        eTCons3=(EditText) findViewById(R.id.eTCons3);
        ibtnPros1=(ImageButton) findViewById(R.id.ibtnPros1);
        ibtnPros2=(ImageButton) findViewById(R.id.ibtnPros2);
        ibtnPros3=(ImageButton) findViewById(R.id.ibtnPros3);
        ibtnCons1=(ImageButton) findViewById(R.id.ibtnCons1);
        ibtnCons2=(ImageButton) findViewById(R.id.ibtnCons2);
        ibtnCons3=(ImageButton) findViewById(R.id.ibtnCons3);
        btnConsAdd=(Button) findViewById(R.id.btnConsAdd);
        btnProAdd=(Button) findViewById(R.id.btnProAdd);
        btnSubmit=(Button) findViewById(R.id.btnSubmit);
        Rbar=(RatingBar) findViewById(R.id.ratingBar);
        productID = getIntent().getStringExtra("productID");

        RatingBar.OnRatingBarChangeListener barChangeListener = new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar rBar, float fRating, boolean fromUser) {
                int rating = (int) fRating;
                numStars = Rbar.getNumStars();
                ColorDrawable colorDrawable = new ColorDrawable();
                colorDrawable.setColor(0xff006ba1);
                LayerDrawable stars = (LayerDrawable) Rbar.getProgressDrawable();
                stars.getDrawable(2).setColorFilter(0xff006ba1,  PorterDuff.Mode.SRC_ATOP);
                String message = null;
                switch(rating) {
                    case 1: message = "Sorry you're really upset with us"; break;
                    case 2: message = "Sorry you're not happy"; break;
                    case 3: message = "Good enough is not good enough"; break;
                    case 4: message = "Thanks, we're glad you liked it."; break;
                    case 5: message = "Awesome - thanks!"; break;
                }

            }
        };

        Rbar.setOnRatingBarChangeListener(barChangeListener);

        //Rbar.setOnRatingBarChangeListener(this);
        btnConsAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int b=A;
                for (int i = A+1; i < 4; i++) {


                    A=i++;
                    b=A;
                    break;
                }
                int a=b;
                if (a==1) {
                    eTCons1.setVisibility(View.VISIBLE);
                    ibtnCons1.setVisibility(View.VISIBLE);



                } else if (a==2) {
                    eTCons2.setVisibility(View.VISIBLE);
                    ibtnCons2.setVisibility(View.VISIBLE);



                }else if (a==3) {
                    eTCons3.setVisibility(View.VISIBLE);
                    ibtnCons3.setVisibility(View.VISIBLE);


                }

            }
        });

        btnProAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int c=B;
                for (int i = B+1; i < 4; i++) {
                    B=i++;
                    c=B;
                    break;
                }
                int d=c;
                if (d==1) {
                    eTPros1.setVisibility(View.VISIBLE);
                    ibtnPros1.setVisibility(View.VISIBLE);
                } else if (d==2) {
                    eTpros2.setVisibility(View.VISIBLE);
                    ibtnPros2.setVisibility(View.VISIBLE);
                }else if (d==3) {
                    eTPros3.setVisibility(View.VISIBLE);
                    ibtnPros3.setVisibility(View.VISIBLE);
                }
            }
        });

        ibtnCons1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                eTCons1.setVisibility(v.GONE);
                ibtnCons1.setVisibility(v.GONE);
                eTCons1.setText("");
            }
        });
        ibtnCons2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                eTCons2.setVisibility(v.GONE);
                ibtnCons2.setVisibility(v.GONE);
                eTCons2.setText("");

            }
        });
        ibtnCons3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                eTCons3.setVisibility(v.GONE);
                ibtnCons3.setVisibility(v.GONE);
                eTCons3.setText("");
            }
        });
        ibtnPros1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                eTPros1.setVisibility(v.GONE);
                ibtnPros1.setVisibility(v.GONE);
                eTPros1.setText("");
            }
        });
        ibtnPros2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                eTpros2.setVisibility(v.GONE);
                ibtnPros2.setVisibility(v.GONE);
                eTpros2.setText("");

            }
        });
        ibtnPros3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                eTPros3.setVisibility(v.GONE);
                ibtnPros3.setVisibility(v.GONE);
                eTPros3.setText("");

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                review_heading=eTHeading.getText().toString();
                summary=eTSummry.getText().toString();
                pros=eTPros.getText().toString()+","+eTPros1.getText().toString()+","+eTpros2.getText().toString()+","+eTPros3.getText().toString();
                cons=eTCons.getText().toString()+","+eTCons1.getText().toString()+","+eTCons2.getText().toString()+","+eTCons3.getText().toString();
                cur_rat=(int) Rbar.getRating();
                if (review_heading.length()== 0) {
                    Toast.makeText(getApplicationContext(), "Please Enter Heading", Toast.LENGTH_LONG).show();
                    eTHeading.requestFocus();
                } else if(summary.length()== 0)  {
                    Toast.makeText(getApplicationContext(), "Plese Enter Summary", Toast.LENGTH_LONG).show();
                    eTSummry.requestFocus();
                } else if(pros.length() == 0)  {
                    Toast.makeText(getApplicationContext(), "Plese Enter Postive or negative points", Toast.LENGTH_LONG).show();
                    eTPros.requestFocus();
                } else if(cons.length()== 0)  {
                    Toast.makeText(getApplicationContext(), "Plese Enter Your Message", Toast.LENGTH_LONG).show();
                    eTCons.requestFocus();
                } else if((int) Rbar.getRating() == 0)  {
                    Toast.makeText(getApplicationContext(), "Plese Enter Your Rating", Toast.LENGTH_LONG).show();
                }else  {
                    new TASk().execute();

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write_review, menu);
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


    public class TASk extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String cur_rat2=String.valueOf(Rbar.getRating());
            String cur_rat1="3";
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(
                    "http://www.makeadeal.in/json/addtopricealerts.php");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
                nameValuePairs.add(new BasicNameValuePair("userid", ServiceHandlers.USERID.toString()));
                nameValuePairs.add(new BasicNameValuePair("productid",productID));
                nameValuePairs.add(new BasicNameValuePair("review_heading", review_heading.toString()));
                nameValuePairs.add(new BasicNameValuePair("summary",summary.toString()));
                nameValuePairs.add(new BasicNameValuePair("pros",pros.toString()));
                nameValuePairs.add(new BasicNameValuePair("message",cons.toString()));
                nameValuePairs.add(new BasicNameValuePair("cur_rat",cur_rat2.toString()));
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
            }catch (Exception e){
                e.printStackTrace();
            }

            if (res.equalsIgnoreCase(response)) {
                Toast.makeText(getApplicationContext(), responseOne, Toast.LENGTH_LONG).show();
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "res", Toast.LENGTH_LONG).show();
            }
            progressBar.setVisibility(View.INVISIBLE);

        }

    }


}
