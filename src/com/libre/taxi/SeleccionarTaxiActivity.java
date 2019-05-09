package com.libre.taxi;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SeleccionarTaxiActivity extends Activity {
	
	double latitude;
    double longitude;
    JSONArray jsonTaxiArray = null;
	JSONArray jsonListataxidisponible=null;
	JSONArray jsonUsuario = null;
	String texto="",conductor="";
	String usuario="",password="";
	String SesionUsuario="";
	ListView listView;
	ArrayList<Fotos> results;
	ArrayList<String> result;
	Boolean presionarbtn = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seleccionar_taxi);
		
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
		Button btn = (Button)findViewById(R.id.actualizar);
		btn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		
        		ArrayList<Fotos> results = GetSearchResults();
        		lista(results);
        		//ArrayList<String> result = SearchResults();
        		//lista(result);
        		
        		presionarbtn=true;
        		
				//Intent intent = new Intent(SeleccionarTaxiActivity.this, SeleccionarTaxiActivity.class);
				//intent.putExtra("SesionUsuario",SesionUsuario);
				//intent.getExtras();
				//setResult(RESULT_OK,intent);     
				//finish();
        		//startActivityForResult
        		//tartActivityForResult(intent,REQUEST_CODE);
        		//startActivity(intent);
        		
        		boolean varesult = results.isEmpty();
        		
        		if (varesult==true){
        			
        			//Buscar lista de taxista en el servidor 
        			
        			tareaCrearListaTaxi taskCrearLista = new tareaCrearListaTaxi();		       	  
        			//taskCrearLista.execute(new String[] { "http://www.libretaxi.com/listataxidisponibles.php" });
        			taskCrearLista.execute(new String[] { "http://www.dissoft.info/libretaxi.com/listataxidisponibles.php" });
        	    		
        		}
			}

		
		});
		
		if (presionarbtn!=true){
			
			//Buscar lista de taxista en el servidor 
			
			tareaCrearListaTaxi taskCrearLista = new tareaCrearListaTaxi();		       	  
			//taskCrearLista.execute(new String[] { "http://www.libretaxi.com/listataxidisponibles.php" });
			taskCrearLista.execute(new String[] { "http://www.dissoft.info/libretaxi.com/listataxidisponibles.php" });
	    		
		}
		
	     
	    /*if (jsonListataxidisponible.length() > 0){
	    	
			Intent intent = new Intent(BuscarTaxiActivity.this, SeleccionarTaxiActivity.class);
			
			//intent.putExtra("results",results);
			
     		startActivity(intent);
     		
		}*/
		
		ArrayList<Fotos> results = GetSearchResults();
		lista(results);
		//results = null;
		
	}
	
	private ArrayList<Fotos> lista(ArrayList<Fotos> results){
		
		if (results == null){
			
			
			//Crear lista de taxistas
			Toast.makeText(getApplicationContext(),"No hay Taxis Libres, presione el boton y espere...",Toast.LENGTH_LONG).show();
	     		
		}else{
		
			/*if (results.equals(null)){
				ArrayList<Fotos> results = GetSearchResults();
			} else{
				ArrayList<Fotos>  results;
			} */
			// verifica si jsonListataxidisponible tiene valor se la tarea se ha realizado
			if (jsonListataxidisponible!=null){
				final ListView lv1 = (ListView) findViewById(R.id.listView);
				lv1.setAdapter(new ItemListBaseAdapter(SeleccionarTaxiActivity.this, results));
				 
				lv1.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> a, View v, int position, long id) {
					Object o = lv1.getItemAtPosition(position);
					Fotos obj_Fotos = (Fotos)o;
					Toast.makeText(SeleccionarTaxiActivity.this, "El taxi que eligio : " + " " + obj_Fotos.getConductor(), Toast.LENGTH_LONG).show();
					conductor=obj_Fotos.getConductor();
					//Procedimiento para notificar al taxista que un usuario lo llama
					tareaTaxiSeleccionado taskTaxiSeleccionado = new tareaTaxiSeleccionado();		       	  
					
					//taskTaxiSeleccionado.execute(new String[] { "http://www.libretaxi.com/taxiseleccionado.php" });
					taskTaxiSeleccionado.execute(new String[] { "http://www.dissoft.info/libretaxi.com/taxiseleccionado.php" });
					
					//llamada a la pantalla de espera por confirmacion del taxis
					Intent intent = new Intent(SeleccionarTaxiActivity.this,EsperarConfirmacionTaxiActivity.class);
					
					intent.putExtra("conductor",conductor);
					
		     		startActivity(intent);
				} 
				});
			}
		}
		return results;
	}
	/*
	 * Procedimiento crear lista de taxis disponibles 
	 */

	private class tareaCrearListaTaxi extends AsyncTask<String, Void, Boolean> {
		Boolean datoenviado=false; 
		
	  @Override
	  protected Boolean doInBackground(String... urls){
		  try {
	  		datoenviado=CrearListaTaxi(datoenviado);
	  		jsonListataxidisponible=CrearListaTaxiDisponibles();
	  		//ArrayList<Fotos> results = GetSearchResults();
	  		//lista(results);
	  		
		  } catch (IOException e) {
		 		// TODO Auto-generated catch block
		 		e.printStackTrace();
		 	}
		  return datoenviado;
	  }
	  protected  void onPostExecute(Void result) {
		  //super.onPostExecute(result);
		  // json=BuscarTaxiDisponibles(var);
		  
		 		//jsonListataxidisponible=CrearListaTaxiDisponibles();
		 	
		 
	  }
	}
	/*
	 * Procedimiento crear lista de taxis disponibles 
	 */

	private class tareaTaxiSeleccionado extends AsyncTask<String, Void, Boolean> {
		Boolean datoenviado=false; 
		
	  @Override
	  protected Boolean doInBackground(String... urls){
		  datoenviado=TaxiSeleccionado(datoenviado);
		//jsonListataxidisponible=CrearListaTaxiDisponibles();
		//ArrayList<Fotos> results = GetSearchResults();
		//lista(results);
		  return datoenviado;
	  }
	 
	}
	

	//Prueba ArrayList tipo String
	private ArrayList<String> SearchResults(){
		
		// falta validar codigo si json es vacio
		
		ArrayList<String> result = new ArrayList<String>();
		
		 
		String item_details;
		
		if (jsonListataxidisponible!=null){
			
		     for (int i = 0; i < jsonListataxidisponible.length(); i++) {
		    	 
		    	 try{
		    		 
		    	 JSONObject row = jsonListataxidisponible.getJSONObject(i);
		    	 
		         item_details = row.getString("conductor");
		         /*
				 item_details.setTelefono(row.getString("telefono"));
				 
				 item_details.setFecha(row.getString("fecha"));
				 
				 item_details.setImageNumber(1);*/
				 
				 result.add(item_details);
				 
		    	 } catch (Exception e) {
				     e.printStackTrace();
				}
				 
		         
		     }
		
		
		}
		return result;
	 	
	}
	
	private ArrayList<Fotos> GetSearchResults(){
		
		// falta validar codigo si json es vacio
		
		ArrayList<Fotos> results = new ArrayList<Fotos>();
		/*
		 * Si el json existe entoncews envia el json a SeleccionartaxiActivity.class
		 * 		  
		 */
		//ArrayList<Fotos> results = new ArrayList<Fotos>();
		 
		Fotos item_details = new Fotos();
		//ItemDetails item_details = new ItemDetails();
		
		if (jsonListataxidisponible!=null){
		     for (int i = 0; i < jsonListataxidisponible.length(); i++) {
		    	 try{
		    		 
		    	 JSONObject row = jsonListataxidisponible.getJSONObject(i);
		    	 
		    	 //user_conductor = ;
		         
		         //lat = row.getString("lat");
		         
		         //lon = row.getString("lon");
		         
		         //telefono = ;
		         
		         //fecha = ;
		         
		         //hora = row.getString("hora");
		         
		         //disponible = row.getString("disponible");
		         
		         item_details.setConductor(row.getString("conductor"));
		         
				// item_details.setTelefono(row.getString("telefono"));
				 
				 //item_details.setFecha(row.getString("fecha"));
				 
				 item_details.setImageNumber(1);
				 
				 results.add(item_details);
				 
				 item_details = new Fotos();
				 
		    	 } catch (Exception e) {
				     e.printStackTrace();
				}
				 
		         
		     }
		/*Fotos item_details = new Fotos();
		item_details.setName("Pizza");
		item_details.setItemDescription("Spicy Chiken Pizza");
		item_details.setPrice("RS 310.00");
		item_details.setImageNumber(1);
		results.add(item_details);
		 
		item_details = new Fotos();
		item_details.setName("Burger");
		item_details.setItemDescription("Beef Burger");
		item_details.setPrice("RS 350.00");
		item_details.setImageNumber(2);
		results.add(item_details);
		 
		item_details = new Fotos();
		item_details.setName("Pizza");
		item_details.setItemDescription("Chiken Pizza");
		item_details.setPrice("RS 250.00");
		item_details.setImageNumber(3);
		results.add(item_details);
		 
		item_details = new Fotos();
		item_details.setName("Burger");
		item_details.setItemDescription("Chicken Burger");
		item_details.setPrice("RS 350.00");
		item_details.setImageNumber(4);
		results.add(item_details);
		 
		item_details = new Fotos();
		item_details.setName("Burger");
		item_details.setItemDescription("Fish Burger");
		item_details.setPrice("RS 310.00");
		item_details.setImageNumber(5);
		results.add(item_details);
		 
		item_details = new Fotos();
		item_details.setName("Mango");
		item_details.setItemDescription("Mango Juice");
		item_details.setPrice("RS 250.00");
		item_details.setImageNumber(6);
		results.add(item_details);*/
		
		}
	return results;
	 	
}
	
	//TaxiSeleccionado
	private Boolean TaxiSeleccionado(Boolean datoenviado) {
			
			
		    String user_conductor="", ideventbefore="", ideventafter="",flag1="";  
		    
		    int w=0;
		    
			
			String lat = null,lon = null,telefono = null,disponible = null,hora = null,fecha = null;
			
			
			EnviarData enviardata = new EnviarData();
			
			enviardata.name=conductor;
			
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
			//crear json de los disponibles
			//el taxi ya no esta disponibleg
			enviardata.disponible=false;
			
			enviardata.httpConn = null;
			//enviardata.url = "http://www.libretaxi.com/taxiseleccionado.php";//listataxidisponibles.php
			enviardata.url = "http://www.dissoft.info/libretaxi.com/taxiseleccionado.php";//listataxidisponibles.php

			
			enviardata.is = null;
			
			enviardata.os = null;
			
			try {
				
				datoenviado=true;
				
				enviardata.ConectarDataUsuario(enviardata.httpConn, enviardata.url, enviardata.is, enviardata.os, enviardata.name, enviardata.usuario, enviardata.codigo, enviardata.lat, enviardata.lon,enviardata.fecha,enviardata.hora,enviardata.disponible);
			
			} catch (IOException e) {
				
				datoenviado=false;
				
				e.printStackTrace();
			}
			
			return datoenviado;
		}
	//CrearListaTaxi
	private Boolean CrearListaTaxi(Boolean datoenviado) {
		
		
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
		//crear json de los disponibles
		enviardata.disponible=true;
		
		enviardata.httpConn = null;
		
		enviardata.url = "http://www.dissoft.info/libretaxi.com/listataxidisponibles.php";//listataxidisponibles.php
		//enviardata.url = "http://www.libretaxi.com/listataxidisponibles.php";//listataxidisponibles.php
		
		enviardata.is = null;
		
		enviardata.os = null;
		
		try {
			
			datoenviado=true;
			
			enviardata.ConectarDataListaTaxis(enviardata.httpConn, enviardata.url, enviardata.is, enviardata.os, enviardata.name, enviardata.usuario, enviardata.codigo, enviardata.lat, enviardata.lon,enviardata.fecha,enviardata.hora,enviardata.disponible);
		
		} catch (IOException e) {
			
			datoenviado=false;
			
			e.printStackTrace();
		}
		
		return datoenviado;
	}
	/**
	 * Crear lista de taxis disponible trae del servidor json de solo los que estan libres
	 * 
	 * intent.putExtra("SesionUsuario",SesionUsuario);
	 * @return
	 * @throws IOException
	 */
	private JSONArray CrearListaTaxiDisponibles() throws IOException {
		
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
		
		/*
		 * Datotaxi.json contiene todos los taxis disponibles y no disponibles
		 */
		enviardata.taxijson = "http://www.dissoft.info/libretaxi.com/listataxidisponibles.json";
		//enviardata.taxijson = "http://www.libretaxi.com/listataxidisponibles.json";
		
		
		enviardata.is = null;
		
		enviardata.os = null;
		
		
		//ObtenerJSON el cual se pasa la class SeleccionarTaxiActivity.java
		
		jsonTaxiArray=enviardata.ObtenerJSON(enviardata.taxijson);
		
		return jsonTaxiArray;  
			   
	    
	    }
		
}
