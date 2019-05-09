package com.libre.taxi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EsperarConfirmacionTaxiActivity extends Activity {
	double latitude;
    double longitude;
    JSONArray jsonTaxiArray = null;
	JSONArray jsonListataxidisponible=null;
	JSONArray jsonUsuario = null;
	JSONArray jsonTaxiConfirmar = null;
	JSONArray jsonMeMonteTaxi = null;
	String texto="";
	String usuario="",password="",conductor="",disponibleConfirmo="";
	String SesionUsuario="";
	final static private long ONE_SECOND = 100;
    //final static private long TWENTY_SECONDS = ONE_SECOND * 500;
	final static private long TWENTY_SECONDS = ONE_SECOND * 10;
	//final static private long TWENTY_SECONDS = ONE_SECOND;
	 PendingIntent pi;
     BroadcastReceiver br;
     AlarmManager am;
     TextView txtCambiado;
     Button btn_verubicacion;
     Button btn_memonte;
     //Button btn_cancelar;
     Button btn_salir;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_esperar_confirmacion_taxi);
		
		Bundle reicieveParams = getIntent().getExtras();
		
	    //Debo pasar los parametros latitude y longitude
		
		//params.setText(reicieveParams.getString("conductor"));
		conductor=reicieveParams.getString("conductor");
		
		
		
		
		//txtCambiado.setText(conductor);
		//El siguiente procedimiento hay que sacarlo como una clase
		
		try
		{
		    BufferedReader fin =
		        new BufferedReader(
		            new InputStreamReader(
		                openFileInput("libretaxi.json")));
		 
		    texto = fin.readLine();
		    fin.close();
		}
		catch (Exception ex)
		{
		    Log.e("Ficheros", "Error al leer fichero desde memoria interna");
		}
		if (texto!=null){	
			
			try {
				
				jsonUsuario = new JSONArray(texto);
				
				for (int j = 0; j < jsonUsuario.length(); j++) {
			         
			    	 JSONObject row;
					
					 row = jsonUsuario.getJSONObject(j);				
			         
			    	 usuario = row.getString("usuario");
			         
			         password = row.getString("password");
			         
			         SesionUsuario=usuario;
			         
			     }
			 } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}   
		}
		/*if (!disponibleConfirmo.isEmpty()){
			txtCambiado = (TextView)findViewById(R.id.txt_confirmacion);
			txtCambiado.setText(conductor);
		}*/
		
		//corre();
		btn_verubicacion = (Button)findViewById(R.id.btn_verubicacion);
        
		btn_verubicacion.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		Intent intent = new Intent(EsperarConfirmacionTaxiActivity.this, VerTaxiActivity.class);
        		//startActivityForResult
        		//startActivityForResult(intent,PICK_CONTACT);
        		startActivity(intent);
                
        		//finish();
        }
        });
		//El usuario se monto en el taxi y va en camino
		//a su lugar de destino
		
		btn_memonte = (Button)findViewById(R.id.btn_memonte);
        
		btn_memonte.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		tareaMeMonteTaxi taskMeMonteTaxi = new tareaMeMonteTaxi();	     
        		//taskMeMonteTaxi.execute(new String[] { "http://www.libretaxi.com/memontetaxi.php" });
        		taskMeMonteTaxi.execute(new String[] { "http://www.dissoft.info/libretaxi.com/confirmo/memontetaxi.php" }); 
                
        		//finish();
        }
        });
		btn_salir = (Button)findViewById(R.id.btn_salir);
        
        btn_salir.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		//tareaCancelarTaxi taskCancelar = new tareaCancelarTaxi();
        		//taskCancelar.execute(new String[] { "http://www.dissoft.info/libretaxi.com/cancelartaxi.php" });
   	     		//taskCancelar.execute(new String[] { "http://www.libretaxi.com/cancelartaxi.php" });                
        		finish();
        }
        });
		/*
		btn_cancelar = (Button)findViewById(R.id.btn_cancelarservicio);
	        
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		tareaCancelarTaxi taskCancelar = new tareaCancelarTaxi();
        		taskCancelar.execute(new String[] { "http://www.dissoft.info/libretaxi.com/cancelartaxi.php" });
   	     		//taskCancelar.execute(new String[] { "http://www.libretaxi.com/cancelartaxi.php" });                
        		finish();
        }
        });*/
        
       
        
	}
	
	public void onResumen() {
    	super.onResume();
    	//corre();
    }
	
	public void onRestart() {
    	super.onRestart();
    	//corre();
    }
    
    public void onStop() {
    	super.onStop();
    	corre();
    }
    public void onStart() {
    	super.onStart();
    	corre();
    }
    @Override
    protected void onDestroy() {
           am.cancel(pi);
           unregisterReceiver(br);
           super.onDestroy();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.esperar_confirmacion_taxi, menu);
		return true;
	}
	
	
	
	/*
	 * --------------------------------------------------------------------------
	 * Tareas Async
	 * --------------------------------------------------------------------------
	 */
	
	/*
	 * Procedimiento para me Monte taxi 
	 */

	private class tareaMeMonteTaxi extends AsyncTask<String, Void, Boolean> {
		Boolean datoenviado=false; 
	    @Override
	    protected Boolean doInBackground(String... urls){
	    	try {
				MeMonteTaxi(datoenviado);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return datoenviado;
	    }
	    protected  void onPostExecute(String result) {
	    	
	    }
	}
	/*
	 * Procedimiento para cancelar taxi 
	 */

	private class tareaCancelarTaxi extends AsyncTask<String, Void, Boolean> {
		Boolean datoenviado=false; 
	    @Override
	    protected Boolean doInBackground(String... urls){
	    	datoenviado=CancelarTaxi(datoenviado);
			return datoenviado;
	    }
	    protected  void onPostExecute(String result) {
	    	
	    }
	}
	
	/*
	 * Procedimiento para confirmar taxi 
	 */

	
	private class tareaConfirmarTaxi extends AsyncTask<String, Void, Boolean> {
		JSONArray json;
		String var;
		Boolean datoenviado=false; 
		
		@SuppressWarnings("unused")
		protected void onProgressUpdate(String... values) {
			//editText.setText(values[0]);
			//txtCambiado = (TextView)findViewById(R.id.txt_confirmacion);
			//txtCambiado.setText(conductor);
	    }
	 
	    @Override
	    protected Boolean doInBackground(String... urls) {
	      //String result = "";
	      try {
	    	  jsonTaxiConfirmar=ConfirmoTaxi(datoenviado);
	    	  //json=ConfirmoTaxi(var);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      return datoenviado;
	      //return var;
	    }
	    protected  void onPostExecute(String result) {
	    	//txtCambiado = (TextView)findViewById(R.id.txt_confirmacion);
			//txtCambiado.setText(conductor);
	    	
	    }
	}
	    
	/*
	 * --------------------------------------------------------------------------
	 * END Tareas Async
	 * --------------------------------------------------------------------------
	 */
	
	private Boolean CancelarTaxi(Boolean datoenviado) {
		
    	
	    String user_conductor="", ideventbefore="", ideventafter="",flag1="";  
	    
	    int w=0;
	    
		
		String lat = null,lon = null,telefono = null,disponible = null,hora = null,fecha = null;
		
		
			EnviarData enviardata = new EnviarData();
			
			enviardata.name="Francisco Alvarez";
			
			enviardata.usuario=usuario;
			
			enviardata.lat = "" + String.valueOf(latitude) + "";
			
			enviardata.lon = "" + String.valueOf(longitude) + "";
			
			enviardata.codigo="5555";
			
			Calendar c = Calendar.getInstance();
		    
		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    
		    String formattedDate = df.format(c.getTime());
		    
			enviardata.fecha=formattedDate;
			
			SimpleDateFormat dh = new SimpleDateFormat("HH:mm:ss");
		    
		    String formattedHora = dh.format(c.getTime());
		    
			enviardata.hora = formattedHora;
			
			enviardata.disponible=false;
			
			enviardata.httpConn = null;
			
			//enviardata.url = "http://www.libretaxi.com/cancelartaxi.php";
			enviardata.url = "http://www.dissoft.info/libretaxi.com/cancelartaxi.php";

			
			enviardata.is = null;
			
			enviardata.os = null;
			
			try {
				
				datoenviado=true;
				
				enviardata.ConectarData(enviardata.httpConn, enviardata.url, enviardata.is, enviardata.os, enviardata.name, enviardata.usuario, enviardata.codigo, enviardata.lat, enviardata.lon,enviardata.fecha,enviardata.hora,enviardata.disponible);
			
			} catch (IOException e) {
				
				datoenviado=false;
				
				e.printStackTrace();
			}
		
		return datoenviado;
	}
	
	private void corre() {
		/*
	     * Procedimiento de Alarm service        
	     */
		br = new BroadcastReceiver() {
			@Override
            public void onReceive(Context c, Intent i) {
                   	//Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();
				   	//Aqui busco la confirmacion del taxi
				
					//**********************************************************************
					//En este punto espero que el taxi modifique la tabla posicion conductor
					//El conductor se agregar en la tabla posicion_usuario y cambia la dispo
					//nibilidad del usuario a false
					//**********************************************************************
				 	if (!disponibleConfirmo.isEmpty()){
					    txtCambiado = (TextView)findViewById(R.id.txt_confirmacion);
						txtCambiado.setText(conductor);
						//btn_verubicacion = (Button)findViewById(R.id.btn_verubicacion);
						btn_verubicacion.setEnabled(true);
						btn_memonte.setEnabled(true);
						//btn_cancelar.setEnabled(false);
						btn_salir.setEnabled(false);
					}
					
					tareaConfirmarTaxi taskConfirmarTaxi = new tareaConfirmarTaxi();
					//taskConfirmarTaxi.execute(new String[] { "http://www.libretaxi.com/confirma/confirmaciontaxi.php" });
					taskConfirmarTaxi.execute(new String[] { "http://www.dissoft.info/libretaxi.com/confirma/confirmaciontaxi.php" });
					//taskConfirmarTaxi.execute(new String[] { "http://www.dissoft.info/libretaxi.com/confirma/confirmaciontaxi.php" });
					
					
					/**
					 * En la url ejecutar http://www.libretaxi.com/confirma/confirmaciontaxi.php?usuario=t@t&conductor=chottu55@hotmail.com&disponibilida=1
					 */
                   }
            };
		
	     registerReceiver(br, new IntentFilter("com.libre.taxi") );
	     
	     pi = PendingIntent.getBroadcast( this, 0, new Intent("com.libre.taxi"),0 );
	     
	     am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
	     
	     am.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), TWENTY_SECONDS, pi);
	     
	     //am.set( AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 
	     	//	TWENTY_SECONDS, pi );
	
	}
	
	private JSONArray ConfirmoTaxi(Boolean datoenviado) throws IOException {
		
	    String user_conductor="", ideventbefore="", ideventafter="",flag1="";  
	    
	    int w=0;
		
		String lat = null,lon = null,telefono = null,hora = null,fecha = null;
		
		EnviarData enviardata = new EnviarData();
		
		enviardata.name=conductor;
		
		enviardata.usuario=usuario;
		
		enviardata.lat = "" + String.valueOf(10.2556) + "";
		
		enviardata.lon = "" + String.valueOf(-62.5265) + "";
		
		enviardata.codigo="5555";
		
		enviardata.httpConn = null;	
		
		
	
		/*
		 * conductor.json indica si el taxi acepto ir a buscarte.
		 * La version conductor crea el archivo por ejemplo:
		 * faove@hotmail.com.json
		 */
	
		//enviardata.taxijson = "http://www.libretaxi.com/confirma/"+conductor+".json";
		//enviardata.url= "http://www.dissoft.info/libretaxi.com/confirma/"+conductor+".json";
		enviardata.url= "http://www.dissoft.info/libretaxi.com/confirma/"+conductor+".json";
	
		enviardata.is = null;
		
		enviardata.os = null;
		
		/*try {
			
			datoenviado=true;
			
			enviardata.ConectarDataConfirmarTaxi(enviardata.httpConn, enviardata.url, enviardata.is, enviardata.os, enviardata.name, enviardata.usuario, enviardata.codigo, enviardata.lat, enviardata.lon,enviardata.fecha,enviardata.hora,enviardata.disponible);
		*/
			jsonTaxiConfirmar=enviardata.ObtenerJSON(enviardata.url);
			 try {
				 //result = new JSONArray(result);
			     
			     //arraydata = new String[jsonArray.length()];
				 
				 //AQUI
			     for (int i = 0; i < jsonTaxiConfirmar.length(); i++) {
			         
			    	 JSONObject row = jsonTaxiConfirmar.getJSONObject(i);
			         
			    	 user_conductor = row.getString("conductor");
			         
			         lat = row.getString("lat");
			         
			         lon = row.getString("lon");
			         
			         telefono = row.getString("telefono");
			         
			         fecha = row.getString("fecha");
			         
			         hora = row.getString("hora");
			         
			         disponibleConfirmo = row.getString("disponible");
			         
			         
			         
			     }
			    
			   } catch (Exception e) {
			     e.printStackTrace();
			   }
			
				return jsonTaxiConfirmar;  
		
		
	}
private void MeMonteTaxi(Boolean datoenviado) throws IOException {
		
	    String user_conductor="", ideventbefore="", ideventafter="",flag1="";  
	    
	    int w=0;
		
		String lat = null,lon = null,telefono = null,hora = null,fecha = null;
		
		EnviarData enviardata = new EnviarData();
		
		enviardata.name=conductor;
		
		enviardata.usuario=usuario;
		
		enviardata.lat = "" + String.valueOf(latitude) + "";
		
		enviardata.lon = "" + String.valueOf(longitude) + "";
		
		enviardata.codigo="5555";
		
		enviardata.httpConn = null;	
		
		
	
		/*
		 * conductor.json indica si el taxi acepto ir a buscarte.
		 */
		//enviardata.url= "http://www.dissoft.info/libretaxi.com/confirma/"+conductor+".json";
		//enviardata.url = "http://www.libretaxi.com/confirma/memontetaxi.php";
		enviardata.url = "http://www.dissoft.info/libretaxi.com/confirma/memontetaxi.php";
		
		enviardata.disponible=true;
	
		enviardata.is = null;
		
		enviardata.os = null;
		
		//El usuario pulso el boton ya aborde 
		//por lo tanto el usuario pasa a no estar disponible.
		
		/*try {
			
			datoenviado=true;*/
			
			enviardata.ConectarMeMonteTaxi(enviardata.httpConn, enviardata.url, enviardata.is, enviardata.os, enviardata.name, enviardata.usuario, enviardata.codigo, enviardata.lat, enviardata.lon,enviardata.fecha,enviardata.hora,enviardata.disponible);
		
			
		
		
	}
}
