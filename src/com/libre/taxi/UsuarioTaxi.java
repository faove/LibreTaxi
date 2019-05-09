package com.libre.taxi;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class UsuarioTaxi {
	
	/*String usuario,password;
	public UsuarioTaxi{
		super();
		this.usuario="";
		this.password="";
		
	}
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public FileUsuario(){
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
	}*/
}
