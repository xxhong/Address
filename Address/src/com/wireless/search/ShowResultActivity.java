package com.wireless.search;

import javax.xml.transform.Source;

import com.alibaba.fastjson.JSON;
import com.wireless.search.domain.KeyWords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowResultActivity extends Activity {
	private TextView tvWangzhi, tvDomain, tvOwner, tvTime, tvContact, tvType,
			noKeyName, noKeyType;
	private LinearLayout llMain, llNokeyWords;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.showresult_view);
		init();
		Intent intent = this.getIntent();

		String info = intent.getStringExtra("info");
		try {
			if (!TextUtils.isEmpty(info)) {
				KeyWords key = JSON.parseObject(info, KeyWords.class);
				if (key != null) {
					String name = key.getName();
					String index = key.getIndex();
					String type = key.getType();
					String starttime = key.getStarttime();
					if(!TextUtils.isEmpty(index)&&!TextUtils.isEmpty(starttime)){
						tvType.setText(key.getType());
						tvWangzhi.setText(key.getName());
						tvDomain.setText(key.getAgent());
						tvOwner.setText(key.getOwner());
						tvTime.setText(key.getStarttime() + "÷¡" + key.getEndtime());
						tvContact.setText(key.getContact());
					}else{
						llMain.setVisibility(View.GONE);
						llNokeyWords.setVisibility(View.VISIBLE);
						noKeyName.setText(key.getName());
						String tp = key.getType();
						if(" ‰»Î”–ŒÛ".equals(tp)){
							tp="";
						}
						noKeyType.setText(tp);
					}
					
				}
			} else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private void init() {
		noKeyName = (TextView) this.findViewById(R.id.tv_nokeywords_name);
		noKeyType = (TextView) this.findViewById(R.id.tv_nokeywords_type);
		tvType = (TextView) this.findViewById(R.id.tv_type);
		tvWangzhi = (TextView) this.findViewById(R.id.tv_wangzhi);
		llMain = (LinearLayout) this.findViewById(R.id.ll_main);
		llNokeyWords = (LinearLayout) this.findViewById(R.id.ll_nokeywords);
		tvDomain = (TextView) this.findViewById(R.id.tv_zhucejigou);
		tvOwner = (TextView) this.findViewById(R.id.tv_chiyouren);
		tvTime = (TextView) this.findViewById(R.id.tv_chiyoushijian);
		tvContact = (TextView) this.findViewById(R.id.tv_chiyoulianxiren);

	}
}
