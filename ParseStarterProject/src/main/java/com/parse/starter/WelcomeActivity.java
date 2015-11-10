package com.parse.starter;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class WelcomeActivity extends ActionBarActivity {
    EditText username;
    EditText password;
    WebView mWebView;
    String webUrl = "file:///android_asset/WaffleWebApp.html";
    TextView welcomeTitle;


    // The URL scheme should be non-hierarchical (no trailing slashes)
   // private static final String APP_SCHEME = "waffleApp:";

    //@TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mWebView = (WebView) findViewById(R.id.welcomeWebView);
        username = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        //welcomeTitle = (TextView)findViewById(R.id.welcomeTitle);


        autoSignIn();



       // String htmlCode = "<h1> <a href=\"#\"> WAFFLE </a> </h1>";

     // welcomeTitle.setText(Html.fromHtml(htmlCode));




        // Force links and redirects to open
        // in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
        // Stop local links and redirects from
        // opening in browser instead of WebView
        mWebView.setWebViewClient(new MyAppWebViewClient());



        mWebView.loadUrl(webUrl);
       // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();

    if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setTextZoom(webSettings.getTextZoom() + 90); //possibly creates not debuggable error
    }

        webSettings.setJavaScriptEnabled(true);
     //   mWebView.setWebContentsDebuggingEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());






        // Log in button click handler
        Button loginButton = (Button) findViewById(R.id.loginBtt);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent of the log in activity
                //startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                login();
            }
        });

        // Sign up button click handler
        Button signupButton = (Button) findViewById(R.id.signUpBttn);
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Starts an intent for the sign up activity
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                intent.putExtra("userName", username.getText().toString().trim());
                intent.putExtra("password", password.getText().toString().trim());
                startActivity(intent);
                finish();
            }
        });


/*
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
*/

}

/*
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) throws UnsupportedEncodingException {
        url = webUrl;
        if (url.startsWith(APP_SCHEME)) {
            urlData = URLDecoder.decode(url.substring(APP_SCHEME.length()), "UTF-8");
            respondToData(urlData);
            return true;
        }
        return false;
    }
*/

    public void autoSignIn(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        
        if (currentUser.getUsername() != null) {
            Log.d("isCurrentUser", "There is a user and we start the MainActivity");
            // do stuff with the user

            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            intent.putExtra("userName", currentUser.getUsername().toString().trim());
            startActivity(intent);
            finish();
        } else {
            Log.d("noCurrentUser", "There is no current user and we start welcomeActivity");
            // show the signup or login screen
            //Intent intent2 = new Intent(WelcomeActivity.this, MainActivity.class);
            //startActivity(intent2);

        }
    }

    private void login() {
        String username2 = username.getText().toString().trim();
        String password2 = password.getText().toString().trim();

        // Validate the log in data
        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
        if (username2.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }
        if (password2.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        }
        validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(WelcomeActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(WelcomeActivity.this);
        dialog.setMessage(getString(R.string.progress_login));
        dialog.show();
        // Call the Parse login method
        ParseUser.logInInBackground(username2, password2, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                dialog.dismiss();
                if (e != null) {
                    // Show the error message
                    Toast.makeText(WelcomeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    // Start an intent for the main activity
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    intent.putExtra("userName", currentUser.getUsername().toString().trim());
                    //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    public void forgotPassword(View view){
        Toast.makeText(WelcomeActivity.this, "test", Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
