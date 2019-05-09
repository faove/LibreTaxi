package com.libre.taxi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LibreTaxiActivity extends Activity implements OnChronometerTickListener {//implements Chronometer.OnChronometerTickListener {
	Chronometer chronometer;
	public static Activity libretax;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		libretax=this;
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_libre_taxi);
		
		
		/*Button btn = (Button)findViewById(R.id.button1);
        
        btn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent intent = new Intent(LibreTaxiActivity.this, BuscarTaxiActivity.class);
        		//startActivityForResult
        		//startActivityForResult(intent,PICK_CONTACT);
        		startActivity(intent);
        }
        });*/
		chronometer = (Chronometer)findViewById(R.id.chronometer1);
		//chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.setOnChronometerTickListener(this);
		chronometer.start();
	
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.libre_taxi, menu);
		return true;
	}
*/
	@Override
	public void onChronometerTick(Chronometer chronometer) {
		// TODO Auto-generated method stub
		CharSequence text = chronometer.getText();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date start = null,end = null;
		
		try {
			start = sdf.parse(text.toString());
			end   = sdf.parse("00:01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		long difference = end.getTime() - start.getTime();
		
		if (start.getMinutes() > end.getMinutes()){
		//CharSequence text = chronometer.getText();
        //if (text "00:15") {
            
		//if (chronometer.getText() > 00:10) {
			
			chronometer.stop();
            // Create Intent and start the new Activity here
			Intent intent = new Intent(LibreTaxiActivity.this, BuscarTaxiActivity.class);
    		//startActivityForResult
    		//startActivityForResult(intent,PICK_CONTACT);
    		startActivity(intent);
        }
		
	}

}
