package com.njzk2.snaprvolley;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.toolbox.NetworkImageView;

public class ImageUrlAdapter extends ArrayAdapter<String> {

	public ImageUrlAdapter(Context context) {
		super(context, 0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NetworkImageView imageView;
		if (convertView == null) {
			imageView = new NetworkImageView(getContext());
		} else {
			imageView = (NetworkImageView) convertView;
		}
		String url = getItem(position);
		imageView.setImageUrl(url, MainActivity.mImageLoader);
		return imageView;
	}
}
