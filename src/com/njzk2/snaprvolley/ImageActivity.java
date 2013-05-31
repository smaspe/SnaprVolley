package com.njzk2.snaprvolley;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.toolbox.NetworkImageView;

public class ImageActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		((NetworkImageView) findViewById(R.id.image)).setImageUrl(
				getIntent().getStringExtra("image").replace("thm2", "lrg"), MainActivity.mImageLoader);
	}
}
