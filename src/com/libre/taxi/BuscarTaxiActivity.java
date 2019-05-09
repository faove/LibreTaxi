package com.libre.taxi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class BuscarTaxiActivity extends Activity {
	
	LocationManager lm;
	Criteria criteria;
	
	private final static String TAG = "BuscarTaxiActivity";
	private Context mContext;
	private Context Context;
	static boolean canGetLocation = false;
	final static private long ONE_SECOND = 1000;
    //final static private long TWENTY_SECONDS = ONE_SECOND * 500;
	final static private long TWENTY_SECONDS = ONE_SECOND * 50;
	//final static private long TWENTY_SECONDS = ONE_SECOND;
	PendingIntent pi;
	BroadcastReceiver br;
	AlarmManager am;     
	private LocationManager locationManager;
	private LocationListener locationListener;
	private String provider;
	private Boolean cancelar=false;
	private String boton=null;
    List<Address> user = null;
   // double lat;
    //double lng;
    double latitude; // latitude
    double longitude; // longitude
 
	private static LatLng cum = new LatLng(10.471106, -64.160987);
	private GoogleMap mapa;
	private LatLng cum1,pos;
	JSONArray jsonTaxiArray = null;
	JSONArray jsonArray = null;
	JSONArray jsonTaxis = null;
	JSONArray jsonUsuario = null;
	JSONArray jsonListataxidisponible=null;
	TextView editText;
	String usuario="",password="";
	String usuarios="";
	String SesionUsuario="";
	String texto="";
	ImageButton btn;
	Bitmap bmp;
	Uri imagen=null;
	//Variable de entorno de BuscarActivity
	public static Activity ba;
	
	private LibreTaxiActivity libretax= new LibreTaxiActivity();
	//@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	//@TargetApi(11)
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	@Override
    public void onCreate(Bundle savedInstanceState) {
         
		super.onCreate(savedInstanceState);
        
		setContentView(R.layout.buscartaxi);
		
		ba = this;
		
		libretax.libretax.finish();
			
		//	usuario = i.getExtras().getString("SesionUsuario");
		
		//Intent i= getIntent();
		//usuario = i.getStringExtra("SesionUsuario");
		//if (usuario!=null){
		
		/*
		 * Verifica si existe en el archivo libretaxi.jsom
		 * ya esta registrado y puede solicitar un taxi
		 */
		
		
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
			ex.printStackTrace();
		    //Log.e("Ficheros", "Error al leer fichero desde memoria interna");
		}
		if (texto!=""){
			
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
			
		}
		
		if (texto==""){
			//Iniciar Sesion, archivo libretaxi.json no exist
	     		Toast.makeText(getApplicationContext(),"Usted debe iniciar sesión",Toast.LENGTH_LONG).show();
	     		
	     		Intent intent = new Intent(BuscarTaxiActivity.this,LoginActivity.class);
		
	     		startActivity(intent);
		}else{
		
		//}
		//editText = (TextView)findViewById(R.id.textjson);
		//ImageButton btn = new ImageButton(Context);
		//this.setRetainInstance(true);
		
		btn = (ImageButton)findViewById(R.id.BtnPedir);
		//Object last = getLastNonConfigurationInstance();
		//boton= savedInstanceState.getString("BOTON");
		
		
		
		/*
		 * if (rotation.equals(false)){
			
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pedirtaxi);
    		btn.setImageBitmap(bmp);
    		
		}else{
			
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.cancelartaxi);
    		btn.setImageBitmap(bmp);
    		
		}*/
		
        btn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		 
        		
        		if (usuario!=null) {
        			
        			
        			//Tengo la variable cancelar
        			//la cual indica si el usuario pulso el boton de cancelar
        			
        			if (cancelar.equals(false)){
        				
        				cancelar=true;
        				
	        			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.cancelartaxi);
	        			
	            		btn.setImageBitmap(bmp);
	            		
	            		boton="2";
	            		
	            		//rotation=true;
	        			tareaPedirTaxi taskPedir = new tareaPedirTaxi();	        			
	        			taskPedir.execute(new String[] { "http://www.dissoft.info/libretaxi.com/pedirtaxi.php" });
	       	     		//taskPedir.execute(new String[] { "http://www.libretaxi.com/pedirtaxi.php" });
	       	     		
	       	     		//Crear lista de taxistas
	       	     		Toast.makeText(getApplicationContext(),"El sistema esta buscando los taxistas disponibles, espere...",Toast.LENGTH_LONG).show();
	       	     		/*try {
							wait(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
	       	     		
	       	     		Intent intent = new Intent(BuscarTaxiActivity.this, SeleccionarTaxiActivity.class);
        
	       	     		startActivity(intent);
	       	     		
        			}else{
        				
        				cancelar=false;
        				//rotation=false;
        				bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pedirtaxi);
	            		btn.setImageBitmap(bmp);
	            		boton="1";
        				//Aqui va el procedimiento de cambiar el la base de datos la disponibilidad
        				//del usuario a 0, esto cancela inmediatemente el servicio
        				tareaCancelarTaxi taskCancelar = new tareaCancelarTaxi();	
        				taskCancelar.execute(new String[] { "http://www.dissoft.info/libretaxi.com/cancelartaxi.php" });
	       	     		//taskCancelar.execute(new String[] { "http://www.libretaxi.com/cancelartaxi.php" });
        				
        			}
       	     		//PedirTaxi();
        		}else{
        			/*Intent intent = new Intent(BuscarTaxiActivity.this, LoginActivity.class);
            		//startActivityForResult
            		//startActivityForResult(intent,PICK_CONTACT);
            		startActivity(intent);*/
        		}
        }
        });
		
		
	    
	    tareaGPS taskGPS = new tareaGPS();
	    taskGPS.execute(new String[] { "" + cum });
	      
	    Toast.makeText(getApplicationContext(),"El sistema esta localizando su posición, espere...",Toast.LENGTH_LONG).show();
	    
	     tarea task = new tarea();
	     task.execute(new String[] { "http://www.dissoft.info/libretaxi.com/datotaxi.json" });
	     //task.execute(new String[] { "http://www.libretaxi.com/datotaxi.json" });
	     
	     mapa = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				 .getMap();
	     
	     if (mapa!=null){
		 mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	 
		 mapa.setMyLocationEnabled(true);
	 
		 mapa.getUiSettings().setZoomControlsEnabled(false);
	 
		 mapa.getUiSettings().setCompassEnabled(true);
	     }	 
	     //jsonTaxis=task.getjson();
	     
	     //editText.setText(task.toString());
	     /*try {
	    	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		 
	     
	     
	     //String editTextStr = editText.getText().toString();
	     
		}//Fin del else de texto == ""
	 }//fin de onCreater
	 @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         //Alternativa 1
         getMenuInflater().inflate(R.menu.buscartaxi, menu);
         return true;
     }
	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 double distancia;
		 String msjdistancia,msjayuda;
	     switch (item.getItemId()) {
	         case R.id.MnuOpc1:
	        	 tareaGPS taskGPS = new tareaGPS();
	     	     taskGPS.execute(new String[] { "" + cum });
	        	 //Toast.makeText(getApplicationContext(),"Opcion 1 pulsada!",Toast.LENGTH_LONG).show();
	             //lblMensaje.setText("Opcion 1 pulsada!");
	        	 
	        		// --- distancia=getDistance(lat_a,lng_a,lat_b, lon_b); 
	        	 	//msjdistancia=""+distancia+"";
	        		// --- msjdistancia=Distance(distancia) + " del Sismo";
	        	 	//msjdistancia=""+distancia+"";
	        	 	// ---showNeutralAlert("Usted se encuentra a:",msjdistancia);
	        	 	return true;
	         //case R.id.MnuOpc2:
	        	 //Toast.makeText(getApplicationContext(),"Opcion 2 pulsada!",Toast.LENGTH_LONG).show();
	             //lblMensaje.setText("Opcion 2 pulsada!");;
	        	/* msjayuda="Este sistema muestra los últimos sismos de la Región Nor-Oriental de Venezuela, Los sismos" +
	        	 		"son de los últimos 30 días, usted puede pulsar el cada sismo y observar su fecha y magnitud";
	        	 showNeutralAlert("Centro de Sismología UDO",msjayuda);*/
	        	//llamada a la pantalla de espera por confirmacion del taxis
					// --- Intent intent = new Intent(this,ListaSismoActivity.class);
					
					//intent.putExtra("conductor",jsonSismoArray);
					// --- String valores=String.valueOf(jsonArrayLista);
					// ---intent.putExtra("array", valores);
		     		// ---startActivity(intent);
	            // return true;
	         case R.id.MnuOpc3:
	        	 //Toast.makeText(getApplicationContext(),"Opcion 3 pulsada!",Toast.LENGTH_LONG).show();
	        	 showYesNoAlert("LibreTaxi","Desea Salir de LibreTaxi.com?");
	             //lblMensaje.setText("Opcion 3 pulsada!");;
	        	 
	             return true;
	         default:
	             return super.onOptionsItemSelected(item);
	     }
	 }
	/*----------------------------------------------------------------------------------
	 * Funcion que muestra un mensaje al usuario para salir del sistema.
	 ----------------------------------------------------------------------------------*/

	public void showYesNoAlert(String Titulo,String Mensaje){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setMessage(Mensaje)
				.setTitle(Titulo)
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								
								//Intent intent=new Intent( Intent.ACTION_MAIN); //Llamando a la activity principal
								//startActivity(intent);
								ba.finish();
								libretax.finish();
								finish();
							}
						})
				.setNeutralButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	   
	}
	/*
	@SuppressWarnings("deprecation")
	@Override
	public Object onRetainNonConfigurationInstance() {
		 return bmp;
	}*/
	
	/*public Object onRetainNonConfigurationInstance()
	{
	   return btn;
	}*/
	
	@Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("BOTON", boton);

	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
	    super.onRestoreInstanceState(savedInstanceState);
	    boton = savedInstanceState.getString("BOTON");
	    if( boton == null )
		{
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pedirtaxi);
			
			btn.setImageBitmap(bmp);
		   //DisplayPhoto.setImageBitmap( (Bitmap) last );
		}else if (boton == "2"){
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.cancelartaxi);
			btn.setImageBitmap(bmp);
		
		}else if (boton =="1"){
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pedirtaxi);
			btn.setImageBitmap(bmp);
		}
	    //tvCont.setText(String.valueOf(boton));
	}
	/*
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//Intent i= getIntent();
		//usuario = i.getStringExtra("SesionUsuario");
		//Log.i(TAG, "On Resume .....");
	}
*/
	/* (non-Javadoc)
	* @see android.app.Activity#onStart()
	*/
	/*@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Intent i= getIntent();
		
		i.getExtras();
		
		Log.i(TAG, "On Start .....");

	}*/


	public void moveCamera(View view) {
	         mapa.moveCamera(CameraUpdateFactory.newLatLng(cum));
	   }
	 
	   public void animateCamera(View view) {
	      if (mapa.getMyLocation() != null)
	         mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(
	            new LatLng( mapa.getMyLocation().getLatitude(),mapa.getMyLocation().getLongitude()), 15));
	   }
	 
	   public void addMarker(View view) {
	      mapa.addMarker(new MarkerOptions().position(
	           new LatLng(mapa.getCameraPosition().target.latitude,
	      mapa.getCameraPosition().target.longitude)));

	   }
	 
	   public void onMapClick(LatLng puntoPulsado) {
	      mapa.addMarker(new MarkerOptions().position(puntoPulsado).
	         icon(BitmapDescriptorFactory
	            .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
	   }
	   
public  class MiLocationListener implements LocationListener
	  {
		  public void onLocationChanged(Location loc)
		  {
			  
			  loc.getLatitude();
			  
			  loc.getLongitude();
			  
			  String coordenadas = "Mis coordenadas son: " + "Latitud = " + loc.getLatitude() + "Longitud = " + loc.getLongitude();
			  
			  Toast.makeText( getApplicationContext(),coordenadas,Toast.LENGTH_LONG).show();
			  
			  //LatLng cum = new LatLng(loc.getLatitude(), loc.getLongitude());
			  //Marker newmarker = map.addMarker(new MarkerOptions().position(loc.getLatitude()).title("marker title").icon(BitmapDescriptorFactory.fromResource(R.drawable.here)));
		 
		  }
		  public void onProviderDisabled(String provider)
		  {
			  Toast.makeText( getApplicationContext(),"Gps Desactivado",Toast.LENGTH_SHORT ).show();
		  }
		  public void onProviderEnabled(String provider)
		  {
			  Toast.makeText( getApplicationContext(),"Gps Activo",Toast.LENGTH_SHORT ).show();
		  }
		  
		  public void onStatusChanged(String provider, int status, Bundle extras){}
		  
		 
	  }
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------INICIO DE LOS ASYNCTASK-----------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

/*
 * Procedimiento de buscar taxis disponibles 
 */

private class tareaBuscarTaxi extends AsyncTask<String, Void, Boolean> {
	Boolean datoenviado=false; 
    @Override
    protected Boolean doInBackground(String... urls){
    	datoenviado=PedirTaxi(datoenviado);
		return datoenviado;
    }
    
    protected  void onPostExecute(String result) {
    	
    }
}

/*
 * Procedimiento para pedir taxi 
 */

private class tareaPedirTaxi extends AsyncTask<String, Void, Boolean> {
	Boolean datoenviado=false; 
    @Override
    protected Boolean doInBackground(String... urls){
    	datoenviado=PedirTaxi(datoenviado);
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
 * Procedimiento crear lista de taxis disponibles 
 */
/*
private class tareaCrearListaTaxi extends AsyncTask<String, Void, Boolean> {
	Boolean datoenviado=false; 
	
  @Override
  protected Boolean doInBackground(String... urls){
	  try {
  		datoenviado=CrearListaTaxi(datoenviado);
  		jsonListataxidisponible=CrearListaTaxiDisponibles();
  		
	  } catch (IOException e) {
	 		// TODO Auto-generated catch block
	 		e.printStackTrace();
	 	}
	  return datoenviado;
  }
  
  protected  void onPostExecute(String result) {
	  // json=BuscarTaxiDisponibles(var);
	  
	 		//jsonListataxidisponible=CrearListaTaxiDisponibles();
	 	
	 
  }
}*/

/*
 * Procedimiento de ubicar al usuario a traves de GPS o network
 */

private class tareaGPS extends AsyncTask<String, Void, LatLng> {
	
	Geocoder geocoder;
    String bestProvider;
	JSONArray json;
	String var;
	
    @Override
    protected LatLng doInBackground(String... urls){
    	
    	lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	
    	Location loc1 =	lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	Location loc2 =	lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
    	
       	/*criteria = new Criteria();
       	criteria.setAccuracy(Criteria.ACCURACY_FINE);
       	//// criteria.setAccuracy(Criteria.ACCURACY_COARSE);
       	criteria.setAltitudeRequired(false);
       	criteria.setBearingRequired(false);
       	criteria.setCostAllowed(true);
       	criteria.setPowerRequirement(Criteria.POWER_LOW); //.POWER_LOW  POWER_HIGH
       	String provider = lm.GPS_PROVIDER;//lm.getBestProvider(criteria, false);*/
    	
    	
       	if (!lm.isProviderEnabled(lm.GPS_PROVIDER)) {
       		//Toast.makeText(getApplicationContext(),"GPS no esta activado",Toast.LENGTH_LONG).show();
       		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
             mContext.startActivity(intent); 
       }
       	LocationListener loc_listener=new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub
            	mostrarPosicion(lm);
            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub

            }
        };
   	//String bestProvider = lm.getBestProvider(criteria,false);   
   	try{
   		//Looper.prepare();
   		//lm.requestLocationUpdates(provider, 0, 0, loc_listener);
   		lm.requestLocationUpdates(
   				LocationManager.GPS_PROVIDER, 30000, 0, loc_listener);
    }catch(Exception e){
        e.printStackTrace();
    }
   	//Location location=lm.getLastKnownLocation(provider);
   	//Location location =	lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
   	
    try {
		 if (loc1 == null){
			 loc2 = lm
                     .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER); //.NETWORK_PROVIDER .GPS_PROVIDER
			 BuscarTaxiActivity.canGetLocation = true;
			 
	    	 LatLng pos = new LatLng(0.0, 0.0);
	         //Toast.makeText(getApplicationContext(),"Location Not found",Toast.LENGTH_LONG).show();
		 
		 //if (provider.equals("gps")){
			 criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			 //// criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			 criteria.setAltitudeRequired(false);
			 criteria.setBearingRequired(false);
			 criteria.setCostAllowed(true);
			 criteria.setPowerRequirement(Criteria.POWER_LOW);
			 provider = lm.getBestProvider(criteria, false);
			 loc2=lm.getLastKnownLocation(provider);
			 geocoder = new Geocoder(getApplicationContext());
    
			 user = geocoder.getFromLocation(loc2.getLatitude(), loc2.getLongitude(), 1);
			 latitude=(double)user.get(0).getLatitude();
			 longitude=(double)user.get(0).getLongitude();
		        //System.out.println(" DDD lat: " +lat+",  longitude: "+lng);
		        //LatLng pos = new LatLng((double)user.get(0).getLatitude(), (double)user.get(0).getLongitude());
			
		// }
		 }else{
			 latitude = loc1.getLatitude();
			 longitude = loc1.getLongitude();
		 }
		 
		 
	 }catch (Exception e) {
	      e.printStackTrace();
	 }
    
      return pos;
    }
    
    
    public void mostrarPosicion(LocationManager lm){
    	Location location =	lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       	
        try {
    		 if (location == null){
    			 location = lm
                         .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    			 BuscarTaxiActivity.canGetLocation = true;
    	    	 LatLng pos = new LatLng(0.0, 0.0);
    	         //Toast.makeText(getApplicationContext(),"Location Not found",Toast.LENGTH_LONG).show();
    		 
    		 //if (provider.equals("gps")){
    			 criteria.setAccuracy(Criteria.ACCURACY_COARSE);
    			 //// criteria.setAccuracy(Criteria.ACCURACY_COARSE);
    			 criteria.setAltitudeRequired(false);
    			 criteria.setBearingRequired(false);
    			 criteria.setCostAllowed(true);
    			 criteria.setPowerRequirement(Criteria.POWER_LOW);
    			 provider = lm.getBestProvider(criteria, false);
    			 location=lm.getLastKnownLocation(provider);
    			 geocoder = new Geocoder(getApplicationContext());
        
    			 user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
    			 latitude=(double)user.get(0).getLatitude();
    			 longitude=(double)user.get(0).getLongitude();
    		        //System.out.println(" DDD lat: " +lat+",  longitude: "+lng);
    		        //LatLng pos = new LatLng((double)user.get(0).getLatitude(), (double)user.get(0).getLongitude());
    			
    		// }
    		 }else{
    			 latitude = location.getLatitude();
    			 longitude = location.getLongitude();
    		 }
    		 
    		 
    	 }catch (Exception e) {
    	      e.printStackTrace();
    	 }
    }
    
    @Override
    protected  void onPostExecute(LatLng result) {
    	//result =json.toString();
    	//Este es la forma de sacar los varoles de un hilo al programa principal
    	//editText.setText(result);
    	//jsonTaxis= json;
    	LatLng pos = new LatLng(latitude, longitude);
    	GraficarUsuario(pos);
    	//return pos;
    }
}


/*
 * Thears buscar taxis para mostrar en los mapa de google 
 */
private class tarea extends AsyncTask<String, Void, String> {
	JSONArray json;
	String var;
	/*
	@Override
	protected void onPreExecute(){
    	var = "";
	}
	public tarea() {
 		super();
 		this.json = null; 		
	}
	
	public JSONArray getjson() {
		return json;
	}

	public void setjson(JSONArray json) {
		this.json = json;
	}*/
	
	@SuppressWarnings("unused")
	protected void onProgressUpdate(String... values) {
		editText.setText(values[0]);
    }
 
    @Override
    protected String doInBackground(String... urls) {
      //String result = "";
      try {
    	  json=BuscarTaxiDisponibles(var);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      /*for (String url : urls) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
          HttpResponse execute = client.execute(httpGet);
          InputStream content = execute.getEntity().getContent();

          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
          String s = "";
          while ((s = buffer.readLine()) != null) {
            response += s;
          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      }*/
      return var;
    }
    
    @Override
    protected void onPostExecute(String result) {
    	//result =json.toString();
    	//Este es la forma de sacar los varoles de un hilo al programa principal
    	//editText.setText(result);
    	jsonTaxis= json;
    	GraficarTaxis(json);
    }
}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------FIN DE LOS ASYNCTASK -------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


public void showSettingsAlert(){
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
  
    // Setting Dialog Title
    alertDialog.setTitle("GPS is settings");

    // Setting Dialog Message
    alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

    // Setting Icon to Dialog
    //alertDialog.setIcon(R.drawable.delete);

    // On pressing Settings button
    alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(intent);
        }
    });

    // on pressing cancel button
    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
        }
    });

    // Showing Alert Message
    alertDialog.show();
}


private void GraficarUsuario(LatLng pos){
	if (pos!=null){
		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
		mapa.addMarker(new MarkerOptions()
           .position(pos)
           .title("Usted esta aquí!")
           .snippet(usuario)
           .icon(BitmapDescriptorFactory
                  .fromResource(R.drawable.here))
           .anchor(0.5f, 0.5f));
	}
}

private JSONArray GraficarTaxis(JSONArray json) {
	String user_conductor,lat,lon,telefono,email,nombre,disponible;
	String [] arraydata;
	LatLng taxis = new LatLng(10.471106, -64.160987);
	
    try {
        //jsonArray = new JSONArray(result);
        
        arraydata = new String[json.length()];
        for (int i = 0; i < json.length(); i++) {
            JSONObject row = json.getJSONObject(i);
            user_conductor = row.getString("conductor");
            lat = row.getString("lat");
            lon = row.getString("lon");
            telefono = row.getString("telefono");
            disponible = row.getString("disponible");
            //arraydata[i]=tlf;
            taxis = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
            if (disponible.equals("0")){
	            mapa.addMarker(new MarkerOptions()
	            .position(taxis)
	            .title(user_conductor)
	            //.snippet("Usted esta aquí")
	            .icon(BitmapDescriptorFactory
	                   .fromResource(R.drawable.taxi_inicio))
	            .anchor(0.5f, 0.5f));
            }else{
        	    mapa.addMarker(new MarkerOptions()
	            .position(taxis)
	            .title(user_conductor)
	            //.snippet("Usted esta aquí")
	            .icon(BitmapDescriptorFactory
	                   .fromResource(R.drawable.taxi_ultimo))
	            .anchor(0.5f, 0.5f));
            }
        }
       
      } catch (Exception e) {
        e.printStackTrace();
      }
	return json;
	
}

private Boolean PedirTaxi(Boolean datoenviado) {
	
	//String action(){
   // br = new BroadcastReceiver() {
    	
    String user_conductor="", ideventbefore="", ideventafter="",flag1="";  
    
    int w=0;
    
	//@Override
	//public void onReceive(Context arg0, Intent arg1) {
	// TODO Auto-generated method stub
	//Toast.makeText(arg0, "Rise and Shine!", Toast.LENGTH_LONG).show();
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
		
		//enviardata.url = "http://www.dissoft.info/dato.php;deviceside=true";
		//enviardata.url = "http://www.dissoft.info/datobb.php;deviceside=true";
		//enviardata.url = "http://150.186.92.201/datobbcsudo.php;deviceside=true";
		//enviardata.url = "http://www.dissoft.info/pedirtaxi.php";
		//enviardata.url = "http://www.libretaxi.com/pedirtaxi.php";
		enviardata.url = "http://www.dissoft.info/libretaxi.com/pedirtaxi.php";
		/*
		 * Datotaxi.json contiene todos los taxis disponibles y no disponibles
		 */
		//enviardata.taxijson = "http://www.dissoft.info/datotaxi.json";
		//enviardata.taxijson = "http://www.libretaxi.com/datotaxi.json";
		//enviardata.taxijson = "http://150.186.92.201/datosismo.json";
		//enviardata.urljson = "http://www.libretaxi.com/datocsudo.json";
		
		//enviardata.url = "http://150.186.92.201/";
		//enviardata.url = "http://www.dissoft.info/datobb.php";
		
		enviardata.is = null;
		
		enviardata.os = null;
		
		try {
			
			datoenviado=true;
			
			enviardata.ConectarData(enviardata.httpConn, enviardata.url, enviardata.is, enviardata.os, enviardata.name, enviardata.usuario, enviardata.codigo, enviardata.lat, enviardata.lon,enviardata.fecha,enviardata.hora,enviardata.disponible);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			datoenviado=false;
			e.printStackTrace();
		}
	
	return datoenviado;
}



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

private JSONArray BuscarTaxiDisponibles(String json) throws IOException {
	
	//String action(){
   // br = new BroadcastReceiver() {
    	
    String user_conductor="", ideventbefore="", ideventafter="",flag1="";  
    
    int w=0;
    
	//@Override
	//public void onReceive(Context arg0, Intent arg1) {
	// TODO Auto-generated method stub
	//Toast.makeText(arg0, "Rise and Shine!", Toast.LENGTH_LONG).show();
	String lat = null,lon = null,telefono = null,disponible = null,hora = null,fecha = null;
	
	EnviarData enviardata = new EnviarData();
	
	enviardata.name="Francisco Alvarez";
	
	enviardata.usuario=usuario;
	
	enviardata.lat = "" + String.valueOf(10.2556) + "";
	
	enviardata.lon = "" + String.valueOf(-62.5265) + "";
	
	enviardata.codigo="5555";
	
	enviardata.httpConn = null;
	
	//enviardata.url = "http://www.dissoft.info/dato.php;deviceside=true";
	//enviardata.url = "http://www.dissoft.info/datobb.php;deviceside=true";
	//enviardata.url = "http://150.186.92.201/datobbcsudo.php;deviceside=true";
	
	//enviardata.url = "http://www.libretaxi.com/dato.php";
	/*
	 * Datotaxi.json contiene todos los taxis disponibles y no disponibles
	 */
	//enviardata.taxijson = "http://www.dissoft.info/datotaxi.json";
	//enviardata.taxijson = "http://www.libretaxi.com/datotaxi.json";
	enviardata.taxijson = "http://www.dissoft.info/libretaxi.com/datotaxi.json";
	//enviardata.taxijson = "http://150.186.92.201/datosismo.json";
	//enviardata.urljson = "http://www.libretaxi.com/datocsudo.json";
	
	//enviardata.url = "http://150.186.92.201/";
	//enviardata.url = "http://www.dissoft.info/datobb.php";
	
	enviardata.is = null;
	
	enviardata.os = null;
	
	//JSONArray jsonTaxiArray = null;
	//ObtenerJSON
	//jsonTaxiArray=enviardata.ObtenerJSON(enviardata.taxijson);
	//ContentResolver cr = getContentResolver();
	//TraerData1
	//enviardata.TraerData1(enviardata.taxijson,cr);
	//jsonTaxiArray=enviardata.ObtenerTaxiJSON(enviardata.taxijson);
	//ObtenerJSON
	jsonTaxiArray=enviardata.ObtenerJSON(enviardata.taxijson);
	 try {
		 //result = new JSONArray(result);
	     
	     //arraydata = new String[jsonArray.length()];
	     for (int i = 0; i < jsonTaxiArray.length(); i++) {
	         
	    	 JSONObject row = jsonTaxiArray.getJSONObject(i);
	         
	    	 user_conductor = row.getString("conductor");
	         
	         lat = row.getString("lat");
	         
	         lon = row.getString("lon");
	         
	         telefono = row.getString("telefono");
	         
	         fecha = row.getString("fecha");
	         
	         hora = row.getString("hora");
	         
	         disponible = row.getString("disponible");
	         
	         //flag1 = row.getString("flag1");
	         
	         /*if (ideventafter.compareTo(user_conductor) != 0 ) {
	        	 
	        	 ideventbefore="";
	        	 
	         }
	         
	         if (ideventbefore.equals(idevent)) {
	        	 
	        	 ideventbefore=idevent;
	        	 
	         }else{
	        	 ideventbefore="";
	         }
	         
	        /* if (ideventbefore.compareTo(idevent) == 0 ) {
	        	 
	        	 ideventbefore="";
	        	 
	         }*/
	         
	     }
	    
	   } catch (Exception e) {
	     e.printStackTrace();
	   }
	
		return jsonTaxiArray;  
		   /*
		   if (flag1.equals("0")){
		    	
		    if (idevent.length()>0){
		   
		      if (ideventbefore.length()==0 ){	
		    	  
		    	  ideventafter = idevent;
		    try {
		    	
		    	JSONArray jsonArray = null;
		    	
		    	jsonArray=enviardata.ObtenerJSON(enviardata.urljson);
		    	
				String id,tlf,email,nombre;
				
				tlf ="";
				
		         try {
		        	 //result = new JSONArray(result);
		             String myMessage = "" + comment + " Fecha" + locdatetime + " Lat" + lat +" Lon" + lon +"Mag" + magnivalue +"Prof" + depth +"";
			 				
	 				//String myMessage = "Fecha:" + locdatetime + " Lat" + lat +" Lon:" + lon +" Magnitud:" + magnivalue +" Prof:" + depth +"";
	 				
	 				if (myMessage.length() > 160) { 
	 					
	 					comment = comment.replaceAll(" +", " ");
	 					
	 					myMessage = "" + comment + " Fecha" + locdatetime + " Lat" + lat +" Lon" + lon +" Mag" + magnivalue +"Prof" + depth +"";
		 				
	 				}
	 				
	 				if (myMessage.length() > 160) {
	 					
	 					myMessage = "" + comment + "Fecha" + locdatetime + "Mag" + magnivalue +"";
		 				
	 				} 	
		             //arraydata = new String[jsonArray.length()];
		        	 int total = jsonArray.length();
		        	 
		        	 
		        	 
		        	 while (w<total){
		        		 
		        		 int rest = total-w;
		        		 
		        		 if (rest<9){
		        			 for (int i = w; i <= total; i++) {
					        	 //for (int i = 0; i < 1; i++) {
					            	 
					                JSONObject row = jsonArray.getJSONObject(w); 
					                 
					                 id = row.getString("id");
					                 
					                 email = row.getString("email");
					                 
					                 nombre = row.getString("nombre");
					                 
					                 if (tlf == "") {
					                	 tlf = row.getString("tlf");
					                 }else{
					                	 tlf = tlf +","+ row.getString("tlf");
					                 }
					                 w++;
					             } //fin del for
		        		 }else{
		        			 
		        		 
				             for (int i = 0; i < 9; i++) {
				        	 //for (int i = 0; i < 1; i++) {
				            	 
				                JSONObject row = jsonArray.getJSONObject(w); 
				                 
				                 id = row.getString("id");
				                 
				                 email = row.getString("email");
				                 
				                 nombre = row.getString("nombre");
				                 
				                 if (tlf == "") {
				                	 tlf = row.getString("tlf");
				                 }else{
				                	 tlf = tlf +","+ row.getString("tlf");
				                 }
				                 w++;
				             } //fin del for
		        		 }
			             //Enviando SMS en grupos de 10 
			             SmsManager smsManager = SmsManager.getDefault();
			 				
			 			 smsManager.sendTextMessage(tlf, null, myMessage, null, null);
			             
			 			 tlf="";
			             
			 			 
			 			 
		        	 }//fin del mientras  
		        	 */ 
		                 /*
		         		 * Envia msj de texto
		         		 */
		                 //Verifica si la flag es 0 para enviar el msj

			 				//String sendTo = "04268870818";
			 				//tlf = "04148242002";
			 						 				
			 				//ideventbefore = idevent;
			 				//flag1="1";
		                 
		                 //arraydata[i]=tlf;
		             //} //fin del for
		          /*  
		           } catch (Exception e) {
		             e.printStackTrace();
		           }
		         
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		   //}
		  //}  
		 //}
	   //}
     //};
   /*
    * Procedimiento de Alarm service        
    */
     /*
    registerReceiver(br, new IntentFilter("csudo.udo.edu") );
    
    pi = PendingIntent.getBroadcast( this, 0, new Intent("csudo.udo.edu"),0 );
    
    am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
    
    am.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime(), TWENTY_SECONDS, pi);
    */
    //am.set( AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 
    	//	TWENTY_SECONDS, pi );
    
    }

   
  
}
	

