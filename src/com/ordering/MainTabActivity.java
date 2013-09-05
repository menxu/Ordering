package com.ordering;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ordering.tab.MessionActivity;
import com.ordering.tab.RiceActivity;
import com.ordering.tab.HomeActivity;
import com.ordering.tab.WeatherActivity;

public class MainTabActivity extends TabActivity  implements OnClickListener {
	public static String TAB_TAG_HOME = "home";
	public static String TAB_TAG_RICE = "rice";
	public static String TAB_TAG_MESSION = "mession";
	public static String TAB_TAG_WEATHER = "weather";
	
	private TabHost mTabHost;
	
	static final int COLOR1 = Color.parseColor("#787878");
	static final int COLOR2 = Color.parseColor("#ffffff");
	
	public static final String TAB_HOME="tabHome";
    public static final String TAB_MSG = "tabMSG";
    public static final String TAB_HAIR_LOW="tabHairLow";
    public static final String TAB_USERINFO = "tabUserInFo";
    
    ImageView mBut1, mBut2, mBut3, mBut4, mBut5;
	TextView mCateText1, mCateText2, mCateText3, mCateText4, mCateText5;
	
	Intent mHomeItent, mRiceIntent, mMessionIntent, mWeatherIntent;
	
	int mCurTabId = R.id.channel1;
	
	private Animation left_in, left_out;
	private Animation right_in, right_out;
	
	
	public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.main_tab_wx);  
               
        prepareAnim();
        prepareIntent();
		setupIntent();
		prepareView();
    }
	
	private void prepareView() {
		mBut1 = (ImageView) findViewById(R.id.imageView1);
		mBut2 = (ImageView) findViewById(R.id.imageView2);
		mBut3 = (ImageView) findViewById(R.id.imageView3);
		mBut4 = (ImageView) findViewById(R.id.imageView4);
		
		findViewById(R.id.channel1).setOnClickListener(this);
		findViewById(R.id.channel2).setOnClickListener(this);
		findViewById(R.id.channel3).setOnClickListener(this);
		findViewById(R.id.channel4).setOnClickListener(this);
		
		mCateText1 = (TextView) findViewById(R.id.textView1);
		mCateText2 = (TextView) findViewById(R.id.textView2);
		mCateText3 = (TextView) findViewById(R.id.textView3);
		mCateText4 = (TextView) findViewById(R.id.textView4);
	}
	
	private void prepareAnim() {
		left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);

		right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
		right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);
	}
	
	private void prepareIntent() {
		mHomeItent = new Intent(this, HomeActivity.class);
		mRiceIntent = new Intent(this, RiceActivity.class);
		mMessionIntent = new Intent(this, MessionActivity.class);
		mWeatherIntent = new Intent(this, WeatherActivity.class);
	}
	
	private void setupIntent() {
		mTabHost = getTabHost();
		mTabHost.addTab(buildTabSpec(
				TAB_TAG_HOME, 
				R.string.category_home,
				R.drawable.tab_weixin_normal, 
				mHomeItent));
		
		mTabHost.addTab(buildTabSpec(
				TAB_TAG_RICE,
				R.string.category_rice, 
				R.drawable.tab_address_normal, 
				mRiceIntent));
		
		mTabHost.addTab(buildTabSpec(
				TAB_TAG_MESSION, 
				R.string.category_mission,
				R.drawable.tab_find_frd_normal, 
				mMessionIntent));
		
		mTabHost.addTab(buildTabSpec(
				TAB_TAG_WEATHER,
				R.string.category_weather, 
				R.drawable.tab_settings_normal, 
				mWeatherIntent));
	}
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel),
						getResources().getDrawable(resIcon))
				.setContent(content);
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			mBut1.performClick();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View v) {
		if (mCurTabId == v.getId()) {
			return;
		}
		mBut1.setImageResource(R.drawable.tab_weixin_normal);
		mBut2.setImageResource(R.drawable.tab_address_normal);
		mBut3.setImageResource(R.drawable.tab_find_frd_normal);
		mBut4.setImageResource(R.drawable.tab_settings_normal);
		
		mCateText1.setTextColor(COLOR1);
		mCateText2.setTextColor(COLOR1);
		mCateText3.setTextColor(COLOR1);
		mCateText4.setTextColor(COLOR1);
		
		int checkedId = v.getId();
		final boolean o;
		if (mCurTabId < checkedId)
			o = true;
		else
			o = false;
		if (o)
			mTabHost.getCurrentView().startAnimation(left_out);
		else
			mTabHost.getCurrentView().startAnimation(right_out);
		switch (checkedId) {
		case R.id.channel1:
			mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
			mBut1.setImageResource(R.drawable.tab_weixin_pressed);
			mCateText1.setTextColor(COLOR2);
			break;
		case R.id.channel2:
			mTabHost.setCurrentTabByTag(TAB_TAG_RICE);
			mBut2.setImageResource(R.drawable.tab_address_pressed);
			mCateText2.setTextColor(COLOR2);
			break;
		case R.id.channel3:
			mTabHost.setCurrentTabByTag(TAB_TAG_MESSION);
			mBut3.setImageResource(R.drawable.tab_find_frd_pressed);
			mCateText3.setTextColor(COLOR2);
			break;
		case R.id.channel4:
			mTabHost.setCurrentTabByTag(TAB_TAG_WEATHER);
			mBut4.setImageResource(R.drawable.tab_settings_pressed);
			mCateText4.setTextColor(COLOR2);
			break;
		default:
			break;
		}

		if (o)
			mTabHost.getCurrentView().startAnimation(left_in);
		else
			mTabHost.getCurrentView().startAnimation(right_in);
		mCurTabId = checkedId;
	} 
}
