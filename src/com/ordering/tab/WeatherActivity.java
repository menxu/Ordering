package com.ordering.tab;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.ordering.R;
import com.ordering.cache.image.ImageCache;

public class WeatherActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_weather);
		
		ImageView image_view = (ImageView)findViewById(R.id.image_view);
		ImageCache.load_cached_image("http://img.my.csdn.net/uploads/201209/28/1348764122_8982.png", image_view);
	}
}
