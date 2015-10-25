package com.parse.starter;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.net.Uri.parse;

/**
 * Created by Zachary on 10/3/2015.
 */
public class MyAppWebViewClient extends WebViewClient {


    public void setLoadWithOverviewMode (boolean overview){
        overview = true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(Uri.parse(url).getHost().length() == 0) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, parse(url));
        view.getContext().startActivity(intent);
        return true;
    }
}