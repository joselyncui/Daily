package com.mengzhu.daily;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mengzhu.daily.util.DataUtil;

public class StatisticFragment extends BaseFragment {
	public static final String TAG = "StatisticFragment";
	private ListView listView;
	private ArrayAdapter<String> adapter;
	int scroll;
	Activity activity;
	Handler handler = new Handler();
	Map<Integer, Float> heightMap = new HashMap<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.statistic_fragment_layout,
				container, false);
		activity = getActivity();

		listView = (ListView) view.findViewById(R.id.listview);

		adapter = new MAdapter(getActivity(), DataUtil.getStrs());
		listView.setAdapter(adapter);
		return view;
	}

	@Override
	public void action(Activity activity) {

	}

	class MAdapter extends ArrayAdapter<String> {
		LayoutInflater inflater;
		List<String> strs;

		public MAdapter(Context context, List<String> strs) {
			super(context, 0);
			inflater = LayoutInflater.from(context);
			this.strs = strs;
		}

		@Override
		public String getItem(int position) {
			return strs.get(position);
		}

		@Override
		public int getCount() {
			return strs.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_webview, parent,
						false);
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			String image = getItem(position);
			viewHolder.loadingWeb(image,position);

			return convertView;
		}

		class ViewHolder {
			WebView webView;
			int position;

			public ViewHolder(View convertView) {
				this.webView = (WebView) convertView.findViewById(R.id.webview);
				setupWebView(this.webView);
			}

			private void loadingWeb(String content,int position) {
				this.position = position;
				
				webView.loadDataWithBaseURL(null, content, "text/html",
						"utf-8", null);
				if (heightMap.get(position) != null) {
					System.out.println("resize $$$$$$$$$$$");
					resize(heightMap.get(position));
				}
			}

			private void setupWebView(final WebView webView) {
				WebSettings settings = webView.getSettings();
				settings.setJavaScriptEnabled(true);
				settings.setBlockNetworkImage(false);
				
				webView.setWebViewClient(new WebViewClient() {
					@Override
					public void onPageFinished(WebView view, String url) {
						System.out.println("finish " + heightMap.get(position));
						if (heightMap.get(position) == null) {
							webView.loadUrl("javascript:ViewHolder.resize(document.getElementById('test').scrollHeight)");
						} 
						
						super.onPageFinished(view, url);
					}
				});
				webView.addJavascriptInterface(this, "ViewHolder");
			}

			@android.webkit.JavascriptInterface
			public void resize(final float height) {
				heightMap.put(position, height);

				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						webView.setLayoutParams(new LinearLayout.LayoutParams(
								getResources().getDisplayMetrics().widthPixels,
								(int) ((height + 10) * getResources()
										.getDisplayMetrics().density)));
						System.out.println("height " + height);
					}
				});
			}
		}

	}

}
