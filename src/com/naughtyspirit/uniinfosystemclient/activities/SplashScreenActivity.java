package com.naughtyspirit.uniinfosystemclient.activities;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.actionbarsherlock.app.SherlockActivity;
import com.naughtyspirit.uniinfosystemclient.R;
import com.naughtyspirit.uniinfosystemclient.helpers.Constants;
import com.naughtyspirit.uniinfosystemclient.helpers.SharedPreferencesHelper;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class SplashScreenActivity extends SherlockActivity {

	private Handler handler;
	private Intent intent;

	private int responseCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		requestThread.start();

		handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {

				if (SharedPreferencesHelper.getStringPreference(getApplicationContext(), Constants.TOKEN_TAG) != null
						&& responseCode == 200) {
					intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
					startActivity(intent);
				} else {
					intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
					startActivity(intent);
				}

				// Calling finish() method in order to pop out the Splash Screen
				// Activity from the activities stack
				SplashScreenActivity.this.finish();

				// Changing the default transition animations with custom ones
				SplashScreenActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		}, Constants.SPLASH_SCREEN_DELAY);
	}

	Thread requestThread = new Thread(new Runnable() {

		@Override
		public void run() {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(Constants.SERVER_URL + "/api/auth/");
			httpPost.addHeader("token",
					SharedPreferencesHelper.getStringPreference(getApplicationContext(), Constants.TOKEN_TAG));
			httpPost.setHeader("user",
					SharedPreferencesHelper.getStringPreference(getApplicationContext(), Constants.USER_TAG));

			try {
				HttpResponse response = httpClient.execute(httpPost);
				responseCode = response.getStatusLine().getStatusCode();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
}
