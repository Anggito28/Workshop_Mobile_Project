package com.kelompok2.rudibonsai.ui.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.kelompok2.rudibonsai.R;
import com.kelompok2.rudibonsai.constant.ConstantValue;
import com.kelompok2.rudibonsai.ui.login.LoginActivity;

public class RegisterWebViewActivity extends AppCompatActivity {

    private ActionBar actionBar;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_web_view);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Daftar");
        actionBar.setDisplayHomeAsUpEnabled(true);

        bar = findViewById(R.id.pb_register_wv);

        WebView webView = (WebView) findViewById(R.id.wv_register);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        // Tiga baris di bawah ini agar laman yang dimuat dapat
        // melakukan zoom.
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        String registerUrl = ConstantValue.DOMAIN + "/register";

        webView.setWebViewClient(new MyWebViewClient(registerUrl));
        webView.loadUrl(registerUrl);
    }

    public class MyWebViewClient extends WebViewClient{
        private String currentUrl;

        public MyWebViewClient(String currentUrl) {
            this.currentUrl = currentUrl;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            bar.setVisibility(View.GONE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            bar.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String verifyEmailUrl = ConstantValue.DOMAIN + "/verify-email";

            if (url.equals(currentUrl) || url.equals(verifyEmailUrl)) {
                view.loadUrl(url);
            }

            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK)){
            moveToLogin();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                moveToLogin();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveToLogin() {
        Intent intent = new Intent(RegisterWebViewActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}