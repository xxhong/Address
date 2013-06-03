package com.wireless.search;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

/*
 * Ĭ�ϵ�TabHost���ֱ���̳�ActivityGroup
 */
public class MainActivity extends ActivityGroup {
	// ����һ��TabHost�ؼ�
	private TabHost tabHost;
	LayoutInflater lf;

	public void onCreate(Bundle savedInstanceState) {
		// ���ñ�����Ϊ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ��ȡTabHost����
		lf = LayoutInflater.from(this);
		tabHost = (TabHost) findViewById(R.id.tabhost);
		//
		tabHost.setup(this.getLocalActivityManager());

		TabSpec tab1 = tabHost.newTabSpec("dialup");
		tab1.setIndicator("����ʵ��",
				getResources().getDrawable(R.drawable.maintab_search_selector));
		tab1.setContent(new Intent(this, SearchActivity.class));
		tabHost.addTab(tab1);

		TabSpec tab2 = tabHost.newTabSpec("linkman");
		tab2.setIndicator("��վ��ѯ",
				getResources().getDrawable(R.drawable.maintab_website_selector));
		tab2.setContent(new Intent(this, Dialup.class));
		tabHost.addTab(tab2);

		TabSpec tab3 = tabHost.newTabSpec("note");
		tab3.setIndicator("��ά��",
				getResources().getDrawable(R.drawable.maintab_erwm_selector));
		tab3.setContent(new Intent(this, Note.class));
		tabHost.addTab(tab3);

		TabSpec tab4 = tabHost.newTabSpec("about");

//		tab4.setIndicator(getView());
		tab4.setIndicator("����",
				getResources().getDrawable(R.drawable.maintab_more_selector));
		tab4.setContent(new Intent(this, About.class));
		tabHost.addTab(tab4);
		tabHost.setCurrentTab(0);

		TabWidget tabWidget = tabHost.getTabWidget();
		for (int i = 0; i < tabWidget.getChildCount(); i++) {
			TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(
					android.R.id.title);
			tv.setTextColor(Color.LTGRAY);// �����������ɫ��
		}
	}

	private View getView() {
		View view = lf.inflate(R.layout.maintab_view, null);

		return view;
	}

}