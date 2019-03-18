package edu.freshfutures.seeka;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebsiteActivity extends AppCompatActivity {

    private ProgressDialog pb;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_website);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        pb = new ProgressDialog(WebsiteActivity.this);

        pb.setMessage("Loading...");
        pb.show();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            url = extras.getString("ARTICLE_LINK");

            if (url != null) {

                if (myWebView != null) {
                    myWebView.loadUrl(url);
                }

            }
            else {
                if (myWebView != null) {
                    myWebView.loadUrl("http://www.google.com");
                }
            }

        }

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.setWebViewClient(new MyWebViewClient());

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("https://m.princeton.edu")) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);

            if (!pb.isShowing()) {
                pb.show();
            }

            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            if (pb.isShowing()) {
                pb.dismiss();
            }

        }

    }

}
