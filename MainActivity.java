package com.hari.hearingaid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btnCallMain = (Button) findViewById(R.id.cmrwjd);
		btnCallMain.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{

				Intent intentSubActivity =
						new Intent(MainActivity.this, TTS_STT.class);
				startActivity(intentSubActivity);
			}
		});
		// 음성 버튼 이벤트
		Button btnCallMain2 = (Button) findViewById(R.id.voice);
		btnCallMain2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{

				Intent intentSubActivity =
						new Intent(MainActivity.this, Classify.class);
				startActivity(intentSubActivity);
			}
		});


		// 종료버튼 이벤트
		Button btnCallMain3 = (Button) findViewById(R.id.finish);
		btnCallMain3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{

				moveTaskToBack(true);
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());

			}
		});



	}

}

