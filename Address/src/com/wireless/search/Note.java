package com.wireless.search;

import com.google.zxing.client.android.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Note extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);

	}

	// µã»÷¿ªÊ¼É¨Ãè
	public void click(View v) {
		Intent intent = new Intent(this,CaptureActivity.class);
		startActivity(intent);
	}
}