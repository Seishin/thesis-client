package com.naughtyspirit.uniinfosystemclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.naughtyspirit.uniinfosystemclient.R;
import com.naughtyspirit.uniinfosystemclient.fragments.MarksFragment;
import com.naughtyspirit.uniinfosystemclient.fragments.StudentInfoFragment;
import com.naughtyspirit.uniinfosystemclient.helpers.Constants;
import com.naughtyspirit.uniinfosystemclient.helpers.SharedPreferencesHelper;
import com.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends SherlockFragmentActivity implements OnClickListener {

	// UI components
	private SlidingMenu slidingMenu;

	private Button logout;
	private Button profile;
	private Button marks;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		initUI();
	}

	private void initUI() {

		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		slidingMenu.setShadowWidth(30);
		slidingMenu.setShadowDrawable(R.drawable.shadow);
		slidingMenu.setBehindOffset(200);
		slidingMenu.setFadeDegree(0.35f);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		slidingMenu.setMenu(R.layout.sliding_menu);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (findViewById(R.id.container) != null) {
			getSupportFragmentManager().beginTransaction().replace(R.id.container, new StudentInfoFragment()).commit();
		} else {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new StudentInfoFragment()).commit();
		}

		logout = (Button) findViewById(R.id.logout);
		logout.setOnClickListener(this);
		
		profile = (Button) findViewById(R.id.profile);
		profile.setOnClickListener(this);
		
		marks = (Button) findViewById(R.id.marks);
		marks.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.logout:
			SharedPreferencesHelper.deletePreference(getApplicationContext(), Constants.TOKEN_TAG);
			SharedPreferencesHelper.deletePreference(getApplicationContext(), Constants.USER_TAG);

			Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
			startActivity(intent);

			HomeActivity.this.finish();

			break;

		case R.id.profile:

			getSupportFragmentManager().beginTransaction().replace(R.id.container, new StudentInfoFragment()).commit();
			
			slidingMenu.toggle();
			
			break;
			
		case R.id.marks:

			getSupportFragmentManager().beginTransaction().replace(R.id.container, new MarksFragment()).commit();
			
			slidingMenu.toggle();
			
			break;

		default:
			break;
		}
	}

}
