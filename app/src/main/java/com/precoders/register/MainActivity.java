package com.precoders.register;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

////////////////////////////////////////
//      @Author     : Farsheel
//      @Website    : precoders.com
///////////////////////////////////////
public class MainActivity extends AppCompatActivity {

    //Creating objects for one button and 2 EditText
    Button btnReg;
    EditText etName,etEmail,etPassword;

    //Progress Dialog to show to user when waiting a response from the server
    ProgressDialog pd=null;

    // Declaring some Varibales
    String name,email,password;
    String REGISTER_URL="http://192.168.43.106/precoders/demo/android_register/register_api.php";
    /*
    Edit the above URL.type ipconfig in cmd to get the IP Address of your HOST.use ifconfig in Linux and Mac
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Referencing the views
        etName=(EditText)findViewById(R.id.etName);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        btnReg=(Button)findViewById(R.id.btnRegister);

        pd=new ProgressDialog(this); //Initialising ProgressDialog

        //Setting OnClickListener(Event) to the Registration Button
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // When User Clicks on Registration Button

                //Storing values from EditText to Variables
                name=etName.getText().toString();
                email=etEmail.getText().toString();
                password=etPassword.getText().toString();

                //Creating object for UserRegister class Which Extends AsyncTask
                UserRegister ur=new UserRegister();
                ur.execute(); //Executing the AsyncTask

            }
        });
    }

    private class UserRegister extends AsyncTask<Void,Integer,String> //AsyncTask class
    {
    InputStream is=null;
        @Override
        protected void onPreExecute() {
            //this method is called before the task is executed
            super.onPreExecute();

            //Showing ProgressDialog to the User
            pd.setTitle("Loging in..");
            pd.setMessage("Please Wait...");
            pd.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            String response=Register();


            return response; //returns response from the server via Register()
        }

        private String Register() {

            String pUrl= REGISTER_URL+"?name="+name+"&email="+email+"&password="+password; //Appending parameters to the URL
            pUrl=pUrl.replaceAll(" ","%20"); //Removing spaces from the URL

            try {
                URL url=new URL(pUrl); // Creating URL object
                try {
                    HttpURLConnection con= (HttpURLConnection) url.openConnection(); //Opening Connection
                    con.setConnectTimeout(15000); //setting connection timeout
                    con.setReadTimeout(15000); // setting read timeout

                    is=new BufferedInputStream(con.getInputStream());
                    BufferedReader br=new BufferedReader(new InputStreamReader(is));
                    if(br!=null)
                    {
                        return br.readLine().toString(); //returns response from the server
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            //This method is called after the task is executed

            super.onPostExecute(s);
            pd.cancel(); // Canceling the ProgressDialog

            if(s.equalsIgnoreCase("true")) //if response is true
            {
                Toast.makeText(getApplicationContext(),"Registration Success",Toast.LENGTH_SHORT).show();
            }
            else if (s.equalsIgnoreCase("false")) //if response is false
            {
                Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();

            }
            else
            {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show(); // Toast if any other response

            }



        }
    }
}
