package com.hackbaba.Doto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mikepenz.materialize.color.Material;


public class web extends AppCompatActivity {

    //private AppUpdateManager appUpdateManager;


    private Context mContext = web.this;
    private int MY_REQUEST_CODE = 1;
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         // Window w = getWindow();
         //w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //}


        Toolbar toolbar = findViewById(R.id.toolbar2);
        //change toolbar color from here
        //toolbar.setBackground(getResources().getDrawable(R.color.accent));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Privacy Policy");
        toolbar.setTitleTextColor(getResources().getColor(R.color.YELLOW));

        // WebView wv = findViewById(R.id.webview);


        // Prepare the progress bar

        // Locate the WebView in webview.xml
        webview =  findViewById(R.id.webview);

        // Enable Javascript to run in WebView
        webview.getSettings().setJavaScriptEnabled(true);

        // Allow Zoom in/out controls
        webview.getSettings().setBuiltInZoomControls(true);

        // Zoom out the best fit your screen
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);


       /* appUpdateManager = AppUpdateManagerFactory.create(mContext);

        // Don't need to do this here anymore
        // Returns an intent object that you use to check for an update.
        //Task<AppUpdateInfo> appUpdateInfo = appUpdateManager.getAppUpdateInfo();



        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            // Checks that the platform will allow the specified type of update.
                            if ((appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE)
                                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
                            {
                                // Request the update.
                                try {
                                    appUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            web.this,
                                            MY_REQUEST_CODE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
*/

        // Load URL
        webview.loadUrl("https://deepsingh132.github.io/2Dos/privacy-policy.html");

        // Show the progress bar
        webview.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                setProgress(progress * 100 / 2);
            }
        });

        // Call private class InsideWebViewClient
        webview.setWebViewClient(new InsideWebViewClient());

    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;

        }

    }

}

