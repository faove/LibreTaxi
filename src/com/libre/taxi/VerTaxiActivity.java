package com.libre.taxi;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class VerTaxiActivity extends Activity {
	Button btn_atras;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_taxi);
		
		btn_atras = (Button)findViewById(R.id.btn_atras);
        
		btn_atras.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		//Intent intent = new Intent(VerTaxiActivity.this, EsperarConfirmacionTaxiActivity.class);
        		//startActivityForResult
        		//startActivityForResult(intent,PICK_CONTACT);
        		//startActivity(intent);
                
        		try {
					this.finalize();
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		finish();
        }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ver_taxi, menu);
		return true;
	}

}
