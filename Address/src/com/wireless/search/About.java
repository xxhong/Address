package com.wireless.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class About extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);

	}

	public void share(View view) {
		Intent it = new Intent("android.intent.action.SEND");
		it.setType("image/*");
		it.setType("text/plain");
		it.putExtra(Intent.EXTRA_SUBJECT, "����");
		it.putExtra(Intent.EXTRA_TEXT,
				"������������ʵ���ֻ��ͻ�������������Ϣ,��Ҳ���Թ� http://www.����ʵ��.cn");
		it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(it, getTitle()));
	}

	public void checkVersion(View v) {
		Toast.makeText(this, "���ڼ��汾...", 0).show();
		new Thread() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			};

		}.start();

		Toast.makeText(this, "�����°汾", 0).show();
	}
}