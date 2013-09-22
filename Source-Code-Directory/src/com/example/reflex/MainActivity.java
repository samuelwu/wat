package com.example.reflex;

import java.util.Calendar;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	Button Reset;
	Button Start;
	Button Push;
	TextView Go;
	TextView Timed;
	TextView HS;
	
	TimerTask popup;
	final Handler handler = new Handler();
	Timer t = new Timer();
	
	long starttime;
	
	long record = 999999;
	
	boolean started = false;
	boolean clicked = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Reset = (Button) findViewById(R.id.button2);
		Start = (Button) findViewById(R.id.button3);
		Push = (Button) findViewById(R.id.button1);
		Go = (TextView) findViewById(R.id.textView3);
		Timed = (TextView) findViewById(R.id.textView4);
		HS = (TextView) findViewById(R.id.textView2);
		
		Start.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if (!started){
					started = true;
			        Random r = new Random(); //Just creating randomness
			        int num = r.nextInt(5) + 3; //Buffer
			        popup = new TimerTask(){
						public void run(){
							handler.post(new Runnable(){
								public void run(){
									Calendar thetime = Calendar.getInstance();
									starttime = thetime.getTimeInMillis();
									Go.setText("GO!");
								}
							});
						}
					};
					t.schedule(popup, num*1000);
				}
			}
		});
		
		Push.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				if (!started || clicked){
					return;
				}
				if (Go.getText() != "GO!"){
					clicked = true;
					popup.cancel();
					Timed.setText("Too Early!");
					Go.setText("Cancelled");
				}
				else{
					clicked = true;
					Calendar thetime = Calendar.getInstance();
					long endtime = thetime.getTimeInMillis();
					long difference = endtime - starttime;
					String str = Long.toString(difference);
					if (difference < record){
						record = difference;
					}
					Timed.setText(str + " ms");
				}
			}
		});
   
		Reset.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				HS.setText("Current HS: " + record + " ms");
				Timed.setText("...");
				Go.setText("");
				started = false;
				clicked = false;
				popup.cancel();
			}
		});
   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
