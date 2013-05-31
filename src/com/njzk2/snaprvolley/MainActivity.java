package com.njzk2.snaprvolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity implements OnItemClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	private static final String SNAPR_URL = "http://sna.pr/api/search/";
	public static ImageLoader mImageLoader;
	public static RequestQueue mRequestQueue;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mRequestQueue = Volley.newRequestQueue(this);
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapLru(64000));
		GridView gridView = (GridView) findViewById(R.id.grid);
		final ImageUrlAdapter imageUrlAdapter = new ImageUrlAdapter(this);
		gridView.setAdapter(imageUrlAdapter);
		gridView.setOnItemClickListener(this);
		mRequestQueue.add(new JsonObjectRequest(SNAPR_URL, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				if (!response.optBoolean("success", false)) {
					Log.w(TAG, "Request failed");
					try {
						Log.w(TAG, "Details " + response.toString(2));
					} catch (JSONException e) {
						e.printStackTrace();
					}
					return;
				}
				JSONArray images = null;
				try {
					images = response.getJSONObject("response").getJSONArray("photos");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				if (images != null) {
					for (int i = 0; i < images.length(); i++) {
						JSONObject object = images.optJSONObject(i);
						if (object == null) {
							continue;
						}
						String imageUrl = "http://media-server" + object.optString("server_id") + ".snapr.us/thm2/"
								+ object.optString("secret") + "/" + object.optString("id") + ".jpg";
						imageUrlAdapter.add(imageUrl);
					}
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				error.printStackTrace();
			}
		}));
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
		Intent intent = new Intent(this, ImageActivity.class);
		intent.putExtra("image", adapter.getItemAtPosition(position).toString());
		startActivity(intent);
	}
}
