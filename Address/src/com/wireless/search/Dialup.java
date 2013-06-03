package com.wireless.search;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Source;

import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * ÍøÒ³²éÑ¯
 * @author xxhong
 *
 */
public class Dialup extends Activity{
	Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 777:
				try {
					if(pd!=null)
						pd.cancel();
					String info  = (String) msg.obj;
					Intent intent = new Intent(Dialup.this, ShowResultActivity.class);
					intent.putExtra("info", info);
					startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		}
		
	};
	private ProgressDialog pd;
	private EditText etSearchkey;
	private Button btSearch;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialup);
        etSearchkey = (EditText) this.findViewById(R.id.st_edittext_searchkey);
        btSearch = (Button) this.findViewById(R.id.search_tools_bt_search);
        setListeners();
        pd = new ProgressDialog(this);
        pd.setMessage("ÇëÉÔºò...");
        
	}
	private void setListeners() {
		btSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String key = etSearchkey.getText().toString().trim();
				if(TextUtils.isEmpty(key)){
					etSearchkey.setError("ËÑË÷ÄÚÈÝ²»ÄÜÎª¿Õ£¡");
					return;
				}
				pd.show();
				new Thread(){
					public void run() {
						String info = requestData(key);
						Message msg = handler.obtainMessage();
						msg.what=777;
						msg.obj=info;
						handler.sendMessage(msg);
					};
					
				}.start();
				
			}
		});
	}
	
	public static String requestData(String key){
		String info="";
		try {
			String path="http://chaxun.nmobi.cn/SearchByName";
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(path);
			List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("name", key));
			
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
			post.setEntity(entity);
			
			HttpResponse response = client.execute(post);
			int code =
					response.getStatusLine().getStatusCode();
			if(200==code){
				InputStream content = response.getEntity().getContent();
				Source source = new Source(content);
				String data = source.toString();
				info= new String(data.getBytes("iso-8859-1"),"utf-8");
				Log.i("xxhong", info);
				return info;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}
}