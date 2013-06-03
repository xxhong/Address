package com.wireless.search;

import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.wireless.search.domain.KeyWords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebActivity extends Activity {
	private WebView wv;
	private ProgressBar pb;
	private EditText etSearchText;
	private Button btSearch, btBack, btHome, btRefresh, btGo;
	private TextView tvNotice;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lib_weibo_view);

		Intent intent = this.getIntent();
		String path = intent.getStringExtra("url");
		String key = intent.getStringExtra("key");
		boolean isShowNotice = intent.getBooleanExtra("isReg", false);
		
		init();
		if(isShowNotice){
			tvNotice.setVisibility(View.VISIBLE);
		}else{
			tvNotice.setVisibility(View.GONE);
		}
		wv.requestFocusFromTouch();
		WebSettings settings = wv.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setUseWideViewPort(false);
		settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		wv.setInitialScale(150);
		settings.setSupportZoom(true);
		settings.setBuiltInZoomControls(true);
		settings.setAppCacheEnabled(false);

		if (!TextUtils.isEmpty(key)) {
			etSearchText.setText(key);
		}
		wv.loadUrl(path);
		wv.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					pb.setVisibility(View.GONE);
				} else {
					pb.setVisibility(View.VISIBLE);
				}
				pb.setProgress(newProgress);
			}
		});
		btSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String key = etSearchText.getText().toString();
				if ("".equals(key) && "".equals(key.trim())) {
					etSearchText.setError("关键词不能为空");
					return;
				}
				new Thread(){
					
					public void run() {
						String url = "http://m.baidu.com/ssid=0/from=0/bd_page_type=1/uid=36933B631226AE82258C6AB8C3E027E5/s?word="
								+ key
								+ "&uc_param_str=upssntdnvelami&st_1=111041&st_2=102041&pu=sz%40224_220&idx=20000&tn_1=webmain&tn_2=fwapadv&ct_1=%E6%90%9C%E7%BD%91%E9%A1%B5";
						
						String info = Dialup.requestData(key);
						if(!TextUtils.isEmpty(info)){
							try {
								KeyWords keywords = JSON.parseObject(info, KeyWords.class);
								String index = keywords.getWebsite();
								if(!TextUtils.isEmpty(index)){
									url=index;
									tvNotice.setVisibility(View.GONE);
								}else{
									tvNotice.setVisibility(View.VISIBLE);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else{
							
						}
						wv.loadUrl(url);
						
					};
				}.start();
				
				
			}
		});

		wv.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

	}

	private void init() {
		tvNotice = (TextView) this.findViewById(R.id.tv_notice);
		etSearchText = (EditText) this.findViewById(R.id.st_edittext_searchkey);
		btSearch = (Button) this.findViewById(R.id.search_tools_bt_search);
		wv = (WebView) this.findViewById(R.id.weibolib_webView01);
		pb = (ProgressBar) this.findViewById(R.id.weibolib_pb);
		btBack = (Button) this.findViewById(R.id.bt_back);
		btHome = (Button) this.findViewById(R.id.bt_home);
		btRefresh = (Button) this.findViewById(R.id.bt_refresh);
		btGo = (Button) this.findViewById(R.id.bt_go);
		btBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				wv.goBack();
			}
		});
		btHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WebActivity.this.finish();
			}
		});
		btRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				wv.reload();
			}
		});
		btGo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				wv.goForward();
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
			wv.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}