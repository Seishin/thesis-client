package com.naughtyspirit.uniinfosystemclient.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naughtyspirit.uniinfosystemclient.R;
import com.naughtyspirit.uniinfosystemclient.models.Mark;
import com.naughtyspirit.uniinfosystemclient.models.StudentInfo;

public class JsonParserHelper {

	private String token, user;

	private Context ctx;

	public static JsonParserHelper getInstance() {
		return new JsonParserHelper();
	}

	public StudentInfo getStudentInfo(Context ctx) throws InterruptedException, ExecutionException {

		this.ctx = ctx;

		this.user = SharedPreferencesHelper.getStringPreference(ctx, Constants.USER_TAG);
		this.token = SharedPreferencesHelper.getStringPreference(ctx, Constants.TOKEN_TAG);

		GetUserProfileTask profileTask = new GetUserProfileTask();
		profileTask.execute();

		return profileTask.get();
	}

	public ArrayList<Mark> getMarks(Context ctx) throws InterruptedException, ExecutionException {

		this.ctx = ctx;

		this.user = SharedPreferencesHelper.getStringPreference(ctx, Constants.USER_TAG);
		this.token = SharedPreferencesHelper.getStringPreference(ctx, Constants.TOKEN_TAG);
		
		GetUserMarksTask marksTask = new GetUserMarksTask();
		marksTask.execute();
		
		return marksTask.get();
	}

	class GetUserProfileTask extends AsyncTask<Void, Void, StudentInfo> {

		private String url = Constants.SERVER_URL + "/api/profile/";
		private StudentInfo info = new StudentInfo();

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog = ProgressDialog.show(ctx, "", ctx.getString(R.string.msg_please_wait));
			dialog.setCancelable(false);
		}

		@Override
		protected StudentInfo doInBackground(Void... params) {

			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			get.addHeader("user", user);
			get.addHeader("token", token);

			try {
				HttpResponse response = client.execute(get);
				HttpEntity entity = response.getEntity();

				InputStream jsonInput = entity.getContent();

				BufferedReader buffer = new BufferedReader(new InputStreamReader(jsonInput, "utf-8"));
				StringBuilder str = new StringBuilder();

				String line = null;

				while ((line = buffer.readLine()) != null) {
					str.append(line + "\n");
				}

				jsonInput.close();

				ObjectMapper mapper = new ObjectMapper();
				info = mapper.readValue(str.toString(), StudentInfo.class);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return info;
		}

		@Override
		protected void onPostExecute(StudentInfo result) {
			dialog.dismiss();
			super.onPostExecute(result);
		}
	}
	
	class GetUserMarksTask extends AsyncTask<Void, Void, ArrayList<Mark>> {

		private String url = Constants.SERVER_URL + "/api/marks/";
		private ArrayList<Mark> marks = new ArrayList<Mark>();

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog = ProgressDialog.show(ctx, "", ctx.getString(R.string.msg_please_wait));
			dialog.setCancelable(false);
		}

		@Override
		protected ArrayList<Mark> doInBackground(Void... params) {

			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(url);
			get.addHeader("user", user);
			get.addHeader("token", token);

			try {
				HttpResponse response = client.execute(get);
				HttpEntity entity = response.getEntity();

				InputStream jsonInput = entity.getContent();

				BufferedReader buffer = new BufferedReader(new InputStreamReader(jsonInput, "utf-8"));
				StringBuilder str = new StringBuilder();

				String line = null;

				while ((line = buffer.readLine()) != null) {
					str.append(line + "\n");
				}

				jsonInput.close();

				ObjectMapper mapper = new ObjectMapper();
				JsonFactory factory = new JsonFactory();
				JsonParser parser = factory.createParser(str.toString());
				
				parser.nextToken();
				
				while(parser.nextToken() == JsonToken.START_OBJECT) {
					Mark mark = mapper.readValue(parser, Mark.class);
					marks.add(mark);
				}
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return marks;
		}

		@Override
		protected void onPostExecute(ArrayList<Mark> result) {
			dialog.dismiss();
			super.onPostExecute(result);
		}
	}
}
