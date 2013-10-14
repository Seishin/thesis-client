package com.naughtyspirit.uniinfosystemclient.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.androidquery.AQuery;
import com.androidquery.auth.BasicHandle;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.naughtyspirit.uniinfosystemclient.R;
import com.naughtyspirit.uniinfosystemclient.helpers.Constants;
import com.naughtyspirit.uniinfosystemclient.helpers.SharedPreferencesHelper;

public class LoginActivity extends SherlockActivity {

	private EditText mUsername, mPassword;
	private Button mLogin;

	private AQuery mLoginQuery;
	private BasicHandle mLoginHandle;

	private Intent mIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initUI();
	}

	private void initUI() {

		mUsername = (EditText) findViewById(R.id.et_username);
		mPassword = (EditText) findViewById(R.id.et_password);

		mLogin = (Button) findViewById(R.id.btn_login);
		mLogin.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				mLoginQuery = new AQuery(getApplicationContext());

				mLoginHandle = new BasicHandle(mUsername.getText().toString(), mPassword.getText().toString());
				
				ProgressDialog dialog = new ProgressDialog(getApplicationContext());
				dialog.setCancelable(false);
				dialog.setTitle(getApplicationContext().getString(R.string.msg_please_wait));
				
				mLoginQuery.progress(dialog).auth(mLoginHandle).ajax(Constants.SERVER_URL + "/api/auth/", JSONObject.class,
						new AjaxCallback<JSONObject>() {

							@Override
							public void callback(String url, JSONObject response, AjaxStatus status) {
								try {
									if (status.getCode() == 200 && response.getString("token") != null) {
										SharedPreferencesHelper.setPreference(getApplicationContext(),
												Constants.TOKEN_TAG, response.getString("token"));

										SharedPreferencesHelper.setPreference(getApplicationContext(),
												Constants.USER_TAG, mUsername.getText().toString());

										mIntent = new Intent(LoginActivity.this, HomeActivity.class);
										startActivity(mIntent);

										// Calling finish() method in order to
										// pop out the Splash Screen
										// Activity from the activities stack
										LoginActivity.this.finish();

										// Changing the default transition
										// animations with custom ones
										LoginActivity.this.overridePendingTransition(android.R.anim.fade_in,
												android.R.anim.fade_out);
									} else {
										Toast.makeText(getApplicationContext(),
												getApplicationContext().getString(R.string.msg_bad_login),
												Toast.LENGTH_LONG).show();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
			}
		});
	}
}
