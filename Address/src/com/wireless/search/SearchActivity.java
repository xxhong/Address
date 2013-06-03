package com.wireless.search;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.wireless.search.domain.KeyWords;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class SearchActivity extends Activity implements OnClickListener {
	private LayoutInflater lf;
	private LinearLayout llTabItem, llMain;
	private EditText etSearchText;
	private Button btSearch;
	private Gallery gl;
	private ProgressDialog pd;
	private ImageSwitcher ims;
	private ArrayList<View> vessel = new ArrayList<View>();
	// ��������
	String[] strsCyly = new String[] { "���ɽ", "̨��С��", "��������", "��������", "���˲���",
			"ɳ��С��", "��ľ��", "������" };
	// ��ҵ����
	String[] strsSyls = new String[] { "�ʺʹ���", "����������", "���ұ���", "�ھ�", "����",
			"���Ƴ�", "�̵���", "�������" };
	// ҽ�Ʊ���
	String[] strsYlbj = new String[] { "���ױ���Ժ", "��������", "�����Ž���", "Ư��Ů��", "����",
			"��������", "���տƼ�", "Э��ʫ��" };
	// װ�ν���
	String[] strsZsjc = new String[] { "����", "԰�־���", "����˿", "��ӡ��װ", "�������",
			"����", "�й��Ҿ߳�", "�й�Ļǽ��" };
	// ��������
	String[] strsJywy = new String[] { "��ɽѧУ", "�Ļ�����", "��ѵ��", "��Ӱ���Ϲ�", "��ؾ糡",
			"���ﴫý", "�й�������", "����Ʒ��" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		lf = LayoutInflater.from(this);
		init();
		setListeners();
		
	}

	private void init() {
		pd = new ProgressDialog(this.getParent());
		pd.setMessage("���Ժ�...");
		View iv1 = lf.inflate(R.layout.ad, null);
		View iv2 = lf.inflate(R.layout.ad, null);
		View iv3 = lf.inflate(R.layout.ad, null);
		View iv4 = lf.inflate(R.layout.ad, null);
		ImageView imageview1 = (ImageView) iv1.findViewById(R.id.iv_ad);
		imageview1.setBackgroundResource(R.drawable.banner1);
		
		ImageView imageview2 = (ImageView) iv2.findViewById(R.id.iv_ad);
		imageview2.setBackgroundResource(R.drawable.banner2);
		
		ImageView imageview3 = (ImageView) iv3.findViewById(R.id.iv_ad);
		imageview3.setBackgroundResource(R.drawable.banner3);
		
		vessel.add(iv1);
		vessel.add(iv2);
		vessel.add(iv3);
		
		
		gl = (Gallery) this.findViewById(R.id.gallery);
		gl.setAdapter(new ImageAdapter());
		AlphaAnimation aa = new AlphaAnimation(0, 1);
		aa.setDuration(1000);
		gl.setLayoutAnimation(new LayoutAnimationController(aa));
		gl.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(SearchActivity.this,
						WebActivity.class);
				String url = "http://www.nmobi.cn/";
				intent.putExtra("url", url);
				startActivity(intent);
			}
		});
		llTabItem = (LinearLayout) this
				.findViewById(R.id.ll_search_tablayout_item);
		etSearchText = (EditText) this.findViewById(R.id.st_edittext_searchkey);
		btSearch = (Button) this.findViewById(R.id.search_tools_bt_search);
		btSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String key = etSearchText.getText().toString();
				if ("".equals(key) && "".equals(key.trim())) {
					etSearchText.setError("�ؼ��ʲ���Ϊ��");
					return;
				}
				pd.show();
				new Thread(){
					public void run() {
						boolean isReg = true;
						String url= "http://m.baidu.com/ssid=0/from=0/bd_page_type=1/uid=36933B631226AE82258C6AB8C3E027E5/s?word="
								+ key
								+ "&uc_param_str=upssntdnvelami&st_1=111041&st_2=102041&pu=sz%40224_220&idx=20000&tn_1=webmain&tn_2=fwapadv&ct_1=%E6%90%9C%E7%BD%91%E9%A1%B5";
						String info = Dialup.requestData(key);
						if(!TextUtils.isEmpty(info)){
							try {
								KeyWords key = JSON.parseObject(info, KeyWords.class);
								String index = key.getWebsite();
								if(!TextUtils.isEmpty(index)){
									url=index;
									isReg=false;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						Intent intent = new Intent(SearchActivity.this,
								WebActivity.class);
						
						intent.putExtra("key", key);
						intent.putExtra("url", url);
						intent.putExtra("isReg", isReg);
						startActivity(intent);
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								pd.cancel();
							}
						});
					
					};
					
				}.start();
				

			}
		});
		
	}

	private void setListeners() {

		setCyly();// ��������
		setSyls();// ��ҵ����
		setYlbj();// ҽ�Ʊ���
		setZhjc();// װ�ν���
		setJywy();// ��������

	}

	private void setJywy() {// ��������
		View view = lf.inflate(R.layout.tablayout_item, null);
		TextView tvTitle = (TextView) view
				.findViewById(R.id.tv_tablayout_title);
		tvTitle.setText("��������");
		int[] ids = { R.id.tv_tablayout_col1, R.id.tv_tablayout_col2,
				R.id.tv_tablayout_col3, R.id.tv_tablayout_col4,
				R.id.tv_tablayout_col5, R.id.tv_tablayout_col6,
				R.id.tv_tablayout_col7, R.id.tv_tablayout_col8 };

		for (int i = 0; i < ids.length; i++) {
			TextView tv = (TextView) view.findViewById(ids[i]);
			tv.setText(strsJywy[i]);
			tv.setTag(50 + i);
			tv.setOnClickListener(this);
		}
		llTabItem.addView(view);
	}

	private void setZhjc() {// װ�ν���40
		View view = lf.inflate(R.layout.tablayout_item, null);
		TextView tvTitle = (TextView) view
				.findViewById(R.id.tv_tablayout_title);
		tvTitle.setText("װ�ν���");
		int[] ids = { R.id.tv_tablayout_col1, R.id.tv_tablayout_col2,
				R.id.tv_tablayout_col3, R.id.tv_tablayout_col4,
				R.id.tv_tablayout_col5, R.id.tv_tablayout_col6,
				R.id.tv_tablayout_col7, R.id.tv_tablayout_col8 };

		for (int i = 0; i < ids.length; i++) {
			TextView tv = (TextView) view.findViewById(ids[i]);
			tv.setText(strsZsjc[i]);
			tv.setTag(40 + i);
			tv.setOnClickListener(this);
		}
		llTabItem.addView(view);
	}

	private void setYlbj() {// ҽ�Ʊ��� 30
		View view = lf.inflate(R.layout.tablayout_item, null);
		TextView tvTitle = (TextView) view
				.findViewById(R.id.tv_tablayout_title);
		tvTitle.setText("ҽ�Ʊ���");
		int[] ids = { R.id.tv_tablayout_col1, R.id.tv_tablayout_col2,
				R.id.tv_tablayout_col3, R.id.tv_tablayout_col4,
				R.id.tv_tablayout_col5, R.id.tv_tablayout_col6,
				R.id.tv_tablayout_col7, R.id.tv_tablayout_col8 };

		for (int i = 0; i < ids.length; i++) {
			TextView tv = (TextView) view.findViewById(ids[i]);
			tv.setText(strsYlbj[i]);
			tv.setTag(30 + i);
			tv.setOnClickListener(this);
		}
		llTabItem.addView(view);
	}

	private void setSyls() {// ��ҵ���� 20
		View view = lf.inflate(R.layout.tablayout_item, null);
		TextView tvTitle = (TextView) view
				.findViewById(R.id.tv_tablayout_title);
		tvTitle.setText("��ҵ����");
		int[] ids = { R.id.tv_tablayout_col1, R.id.tv_tablayout_col2,
				R.id.tv_tablayout_col3, R.id.tv_tablayout_col4,
				R.id.tv_tablayout_col5, R.id.tv_tablayout_col6,
				R.id.tv_tablayout_col7, R.id.tv_tablayout_col8 };

		for (int i = 0; i < ids.length; i++) {
			TextView tv = (TextView) view.findViewById(ids[i]);
			tv.setText(strsSyls[i]);
			tv.setTag(20 + i);
			tv.setOnClickListener(this);
		}
		llTabItem.addView(view);
	}

	private void setCyly() {// �������� 10
		View view = lf.inflate(R.layout.tablayout_item, null);
		int[] ids = { R.id.tv_tablayout_col1, R.id.tv_tablayout_col2,
				R.id.tv_tablayout_col3, R.id.tv_tablayout_col4,
				R.id.tv_tablayout_col5, R.id.tv_tablayout_col6,
				R.id.tv_tablayout_col7, R.id.tv_tablayout_col8 };

		for (int i = 0; i < ids.length; i++) {
			TextView tv = (TextView) view.findViewById(ids[i]);
			tv.setText(strsCyly[i]);
			tv.setTag(10 + i);
			tv.setOnClickListener(this);
		}
		llTabItem.addView(view);
	}

	public void onClick(View arg0) {
		Integer tag = (Integer) arg0.getTag();
		switch (tag) {
		case 10:
			setCylyData(tag);
			break;
		case 11:
			setCylyData(tag);
			break;
		case 12:
			setCylyData(tag);
			break;
		case 13:
			setCylyData(tag);
			break;
		case 14:
			setCylyData(tag);
			break;
		case 15:
			setCylyData(tag);
			break;
		case 16:
			setCylyData(tag);
			break;
		case 17:
			setCylyData(tag);
			break;

		case 20:
			setSylsData(tag);
			break;
		case 21:
			setSylsData(tag);
			break;
		case 22:
			setSylsData(tag);
			break;
		case 23:
			setSylsData(tag);
			break;
		case 24:
			setSylsData(tag);
			break;
		case 25:
			setSylsData(tag);
			break;
		case 26:
			setSylsData(tag);
			break;
		case 27:
			setSylsData(tag);
			break;

		case 30:
			setYlbjData(tag);
			break;
		case 31:
			setYlbjData(tag);
			break;
		case 32:
			setYlbjData(tag);
			break;
		case 33:
			setYlbjData(tag);
			break;
		case 34:
			setYlbjData(tag);
			break;
		case 35:
			setYlbjData(tag);
			break;
		case 36:
			setYlbjData(tag);
			break;
		case 37:
			setYlbjData(tag);
			break;

		case 40:
			setZsjcData(tag);
			break;
		case 41:
			setZsjcData(tag);
			break;
		case 42:
			setZsjcData(tag);
			break;
		case 43:
			setZsjcData(tag);
			break;
		case 44:
			setZsjcData(tag);
			break;
		case 45:
			setZsjcData(tag);
			break;
		case 46:
			setZsjcData(tag);
			break;
		case 47:
			setZsjcData(tag);
			break;

		case 50:
			setJywyData(tag);
			break;
		case 51:
			setJywyData(tag);
			break;
		case 52:
			setJywyData(tag);
			break;
		case 53:
			setJywyData(tag);
			break;
		case 54:
			setJywyData(tag);
			break;
		case 55:
			setJywyData(tag);
			break;
		case 56:
			setJywyData(tag);
			break;
		case 57:
			setJywyData(tag);
			break;
		default:
			break;
		}

	}

	// ��������
	private void setCylyData(Integer tag) {
		Intent intent = new Intent(this, WebActivity.class);
		String url = "http://www.����ʵ��.cn/Search?keyword=";
		url = url + strsCyly[tag - 10];
		intent.putExtra("url", url);
		startActivity(intent);
	}

	// ��ҵ����
	private void setSylsData(Integer tag) {
		Intent intent = new Intent(this, WebActivity.class);
		String url = "http://www.����ʵ��.cn/Search?keyword=";
		url = url + strsSyls[tag - 20];
		intent.putExtra("url", url);
		startActivity(intent);
	}

	// ҽ�Ʊ���
	private void setYlbjData(Integer tag) {
		Intent intent = new Intent(this, WebActivity.class);
		String url = "http://www.����ʵ��.cn/Search?keyword=";
		url = url + strsYlbj[tag - 30];
		intent.putExtra("url", url);
		startActivity(intent);
	}

	// װ�ν���
	private void setZsjcData(Integer tag) {
		Intent intent = new Intent(this, WebActivity.class);
		String url = "http://www.����ʵ��.cn/Search?keyword=";
		url = url + strsZsjc[tag - 40];
		intent.putExtra("url", url);
		startActivity(intent);
	}

	// ��������
	private void setJywyData(Integer tag) {
		Intent intent = new Intent(this, WebActivity.class);
		String url = "http://www.����ʵ��.cn/Search?keyword=";
		url = url + strsJywy[tag - 50];
		intent.putExtra("url", url);
		startActivity(intent);
	}
	
	
	class ImageAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return vessel.size();
		}

		@Override
		public Object getItem(int position) {
			return vessel.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return vessel.get(position);
		}
		
	}
}