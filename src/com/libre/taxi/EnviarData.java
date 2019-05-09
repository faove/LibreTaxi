package com.libre.taxi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.content.ContentResolver;

public class EnviarData

	{
	
	 HttpConnection httpConn = null;
     String url,urljson,taxijson;
     InputStream is = null;
     OutputStream os = null;
     String name,usuario,telefono,codigo,lat,lon,fecha,hora,password;
     Boolean disponible;
     
     public EnviarData() {
 		super();
 		this.httpConn = null;
 		this.url = null;
 		this.urljson = null;
 		this.taxijson = null;
 		this.is = null;
 		this.os = null;
 		this.name = "";
 		this.usuario ="";
 		this.password="";
 		this.telefono="";
 		this.codigo ="";
 		this.lat="";
 		this.lon="";
 		this.fecha="";
 		this.hora="";
 		this.disponible=false;
 		
 		
 		
 	}
     
     public EnviarData(HttpConnection httpConn, String url,String urljson,String taxijson, InputStream is,
			OutputStream os, String name,String usuario,String password,String telefono,String codigo,String lat,String lon,String fecha,String hora,Boolean disponible) {
		super();
		this.httpConn = httpConn;
		this.url = url;
		this.urljson = urljson;
		this.taxijson = taxijson;
		this.is = is;
		this.os = os;
		this.name = name;
		this.usuario = usuario;
		this.password=password;
		this.telefono=telefono;
		this.codigo = codigo;
		this.lat = lat;
		this.lon = lon;
		this.fecha=fecha;
		this.hora=hora;
 		this.disponible=disponible;
 		
	}

	public HttpConnection getHttpConn() {
		return httpConn;
	}

	public void setHttpConn(HttpConnection httpConn) {
		this.httpConn = httpConn;
	}
	
	public String getlat() {
		return lat;
	}

	public void setlat(String lat) {
		this.lat = lat;
	}
	
	public String getlon() {
		return lon;
	}

	public void setlon(String lon) {
		this.lon = lon;
	}	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getTaxijson() {
		return taxijson;
	}

	public void setTaxijson(String taxijson) {
		this.taxijson = urljson;
	}
	
	public String getUrljson() {
		return taxijson;
	}

	public void setUrljson(String taxijson) {
		this.taxijson = taxijson;
	}

	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	//Telefono
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	//contraseña
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	//codigo
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	//fecha
	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	//hora
	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}
	
	//disponible
	public Boolean getDisponible() {
		return disponible;
	}

	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}
	public String ConectarLogin(HttpConnection httpConn, String urll, String name, String usuario,String password,String telefono,String fecha) throws IOException{
		
		try {

		  HttpClient httpclient = new DefaultHttpClient();
		
		/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
		
		  HttpPost httppost = new HttpPost(urll);
		
		/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
		
		  //AÑADIR PARAMETROS
		
		  List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		  params.add(new BasicNameValuePair("usuario",usuario));
		
		  params.add(new BasicNameValuePair("password",password));
		  
		  params.add(new BasicNameValuePair("telefono",telefono));
		  
		  params.add(new BasicNameValuePair("fecha",fecha));
		  
		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
		
		  httppost.setEntity(new UrlEncodedFormEntity(params));
		
		  /*Finalmente ejecutamos enviando la info al server*/
		
		  HttpResponse resp = httpclient.execute(httppost);
		
		  HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

          String text = EntityUtils.toString(ent);

          return text;

      }

      catch(Exception e) { 
    	  return "error";
	}
	}
	public String ConectarMeMonteTaxi(HttpConnection httpConn, String urll, InputStream is,
			OutputStream os, String name, String usuario, String codigo,String lat,String lon,String fecha,String hora,Boolean disponible) throws IOException{
		
		try {

		  HttpClient httpclient = new DefaultHttpClient();
		
		/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
		
		  HttpPost httppost = new HttpPost(urll);
		
		/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
		
		  //AÑADIR PARAMETROS
		
		  List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		  params.add(new BasicNameValuePair("usuario",usuario));
		  //params.add(new BasicNameValuePair("lat",lat));
		  //params.add(new BasicNameValuePair("lon",lon));
		  //params.add(new BasicNameValuePair("fecha",fecha));
		  //params.add(new BasicNameValuePair("hora",hora));
		  
		  if (disponible){		  
		  
			  params.add(new BasicNameValuePair("disponible","1"));
			  
		  }else{
			  
			  params.add(new BasicNameValuePair("disponible","0"));
			  
		  }
		  params.add(new BasicNameValuePair("conductor",name));
		  //params.add(new BasicNameValuePair("disponible",disponible.toString()));
		
		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
		
		  httppost.setEntity(new UrlEncodedFormEntity(params));
		
		  /*Finalmente ejecutamos enviando la info al server*/
		
		  HttpResponse resp = httpclient.execute(httppost);
		
		  HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

          String text = EntityUtils.toString(ent);

          return text;

      }

      catch(Exception e) { 
    	  return "error";
	}

		
     	}
	
	/*
	 * ConectarDataUsuario cambia el status del chofer de disponible 0 a 1
	 * */
	public String ConectarDataUsuario(HttpConnection httpConn, String urll, InputStream is,
			OutputStream os, String name, String usuario, String codigo,String lat,String lon,String fecha,String hora,Boolean disponible) throws IOException{
		
		try {

		  HttpClient httpclient = new DefaultHttpClient();
		
		/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
		
		  HttpPost httppost = new HttpPost(urll);
		
		/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
		
		  //AÑADIR PARAMETROS
		
		  List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		  params.add(new BasicNameValuePair("usuario",usuario));
		  //params.add(new BasicNameValuePair("lat",lat));
		  //params.add(new BasicNameValuePair("lon",lon));
		  //params.add(new BasicNameValuePair("fecha",fecha));
		  //params.add(new BasicNameValuePair("hora",hora));
		  
		  if (disponible){		  
		  
			  params.add(new BasicNameValuePair("disponible","0"));
			  
		  }else{
			  
			  params.add(new BasicNameValuePair("disponible","1"));
			  
		  }
		  params.add(new BasicNameValuePair("conductor",name));
		  //params.add(new BasicNameValuePair("disponible",disponible.toString()));
		
		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
		
		  httppost.setEntity(new UrlEncodedFormEntity(params));
		
		  /*Finalmente ejecutamos enviando la info al server*/
		
		  HttpResponse resp = httpclient.execute(httppost);
		
		  HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

          String text = EntityUtils.toString(ent);

          return text;

      }

      catch(Exception e) { 
    	  return "error";
	}

		
     	}
	public String ConectarDataListaTaxis(HttpConnection httpConn, String urll, InputStream is,
			OutputStream os, String name, String usuario, String codigo,String lat,String lon,String fecha,String hora,Boolean disponible) throws IOException{
		
		try {

		  HttpClient httpclient = new DefaultHttpClient();
		
		/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
		
		  HttpPost httppost = new HttpPost(urll);
		
		/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
		
		  //AÑADIR PARAMETROS
		
		  List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		  params.add(new BasicNameValuePair("usuario",usuario));
		  params.add(new BasicNameValuePair("lat",lat));
		  params.add(new BasicNameValuePair("lon",lon));
		  params.add(new BasicNameValuePair("fecha",fecha));
		  params.add(new BasicNameValuePair("hora",hora));
		  //params.add(new BasicNameValuePair("disponible","0"));
		  
		  if (disponible){		  
			  
			  params.add(new BasicNameValuePair("disponible","0"));
			  
		  }else{
			  
			  params.add(new BasicNameValuePair("disponible","1"));
			  
		  }
		  //params.add(new BasicNameValuePair("disponible",disponible.toString()));
		
		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
		
		  httppost.setEntity(new UrlEncodedFormEntity(params));
		
		  /*Finalmente ejecutamos enviando la info al server*/
		
		  HttpResponse resp = httpclient.execute(httppost);
		
		  HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

          String text = EntityUtils.toString(ent);

          return text;

      }

      catch(Exception e) { 
    	  return "error";
	}
	}
	//ConectarDataConfirmarTaxi
	public String ConectarDataConfirmarTaxi(HttpConnection httpConn, String urll, InputStream is,
			OutputStream os, String name, String usuario, String codigo,String lat,String lon,String fecha,String hora,Boolean disponible) throws IOException{
		
		try {

		  HttpClient httpclient = new DefaultHttpClient();
		
		/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
		
		  HttpPost httppost = new HttpPost(urll);
		
		/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
		
		  //AÑADIR PARAMETROS
		
		  List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		  params.add(new BasicNameValuePair("usuario",usuario));
		  params.add(new BasicNameValuePair("lat",lat));
		  params.add(new BasicNameValuePair("lon",lon));
		  params.add(new BasicNameValuePair("fecha",fecha));
		  params.add(new BasicNameValuePair("hora",hora));
		  
		  //Este metodo es llamado de la function corre de la clase EsperarConfirmacion 
		  //coloca en estado ocupado al usuario, un taxi lo selecciono, y va en camino
		  //params.add(new BasicNameValuePair("disponible","1"));
		  
		  if (disponible){		  
			  
			  params.add(new BasicNameValuePair("disponible","1"));
			  
		  }else{
			  
			  params.add(new BasicNameValuePair("disponible","0"));
			  
		  }
		  //params.add(new BasicNameValuePair("disponible",disponible.toString()));
		
		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
		
		  httppost.setEntity(new UrlEncodedFormEntity(params));
		
		  /*Finalmente ejecutamos enviando la info al server*/
		
		  HttpResponse resp = httpclient.execute(httppost);
		
		  HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

          String text = EntityUtils.toString(ent);

          return text;

      }

      catch(Exception e) { 
    	  return "error";
	}
	}	
	public String ConectarData(HttpConnection httpConn, String urll, InputStream is,
			OutputStream os, String name, String usuario, String codigo,String lat,String lon,String fecha,String hora,Boolean disponible) throws IOException{
		
		try {

		  HttpClient httpclient = new DefaultHttpClient();
		
		/*Creamos el objeto de HttpClient que nos permitira conectarnos mediante peticiones http*/
		
		  HttpPost httppost = new HttpPost(urll);
		
		/*El objeto HttpPost permite que enviemos una peticion de tipo POST a una URL especificada*/
		
		  //AÑADIR PARAMETROS
		
		  List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		  params.add(new BasicNameValuePair("usuario",usuario));
		  params.add(new BasicNameValuePair("lat",lat));
		  params.add(new BasicNameValuePair("lon",lon));
		  params.add(new BasicNameValuePair("fecha",fecha));
		  params.add(new BasicNameValuePair("hora",hora));
		  
		  if (disponible){		  
			  
			  params.add(new BasicNameValuePair("disponible","1"));
			  
		  }else{
			  
			  params.add(new BasicNameValuePair("disponible","0"));
			  
		  }
		  //params.add(new BasicNameValuePair("disponible","0"));
		  //params.add(new BasicNameValuePair("disponible",disponible.toString()));
		
		/*Una vez añadidos los parametros actualizamos la entidad de httppost, esto quiere decir en pocas palabras anexamos los parametros al objeto para que al enviarse al servidor envien los datos que hemos añadido*/
		
		  httppost.setEntity(new UrlEncodedFormEntity(params));
		
		  /*Finalmente ejecutamos enviando la info al server*/
		
		  HttpResponse resp = httpclient.execute(httppost);
		
		  HttpEntity ent = resp.getEntity();/*y obtenemos una respuesta*/

          String text = EntityUtils.toString(ent);

          return text;

      }

      catch(Exception e) { 
    	  return "error";
	}

		  /* 
		   * Este funciona pero no envia
		   * data, y no da errror
		   * try {
			   List<NameValuePair> params = new ArrayList<NameValuePair>();
	        	
	        	params.add(new BasicNameValuePair("usuario", usuario));
	        	
	        	params.add(new BasicNameValuePair("lat", lat));
	        	
	        	params.add(new BasicNameValuePair("lon", lon));
	        	
	        	params.add(new BasicNameValuePair("codigo", codigo));
	        	
	        	String resul = getQuery(params);
			   
			   URL url = new URL(urll);
			   
			   HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			   
			   byte[] bytes = resul.getBytes("UTF-8");
			   
			   urlConn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
			   
			   urlConn.setRequestMethod("GET");
			   
			   urlConn.setDoOutput(true);
			   
			   urlConn.connect();
			   
			   os = urlConn.getOutputStream();
			   
			   os.write(bytes);
			   
			   
		
		     } finally {
		       if(is!= null)
		          is.close();
		         if(os != null)
		           os.close();
		     if(httpConn != null)
		           httpConn.close();
		   }*/
     	}
	
	
	
	private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
	    StringBuilder result = new StringBuilder();
	    boolean first = true;

	    for (NameValuePair pair : params)
	    {
	        if (first)
	            first = false;
	        else
	            result.append("&");

	        result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
	        result.append("=");
	        result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
	    }

	    return result.toString();
	}
	 public  JSONArray ObtenerJSON(String url) throws IOException{
	     InputStream is = null;
	     String result = "";
	     JSONObject json = null;
	     JSONArray jsonArray = null;
	     String [] arraydata;
	      try{
	         HttpClient httpclient = new DefaultHttpClient();
	         HttpPost httppost = new HttpPost(url);
	         HttpResponse response = httpclient.execute(httppost);
	         Log.v("response code", response.getStatusLine()
                     .getStatusCode() + ""); 
	         HttpEntity entity = response.getEntity();
	         is = entity.getContent();
	     }catch(Exception e){}

	      try{
	         BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	         StringBuilder sb = new StringBuilder();
	         String line = null;
	         while ((line = reader.readLine()) != null) {
	             sb.append(line + "\n");
	         }
	         
	         is.close();
	         
	         //result es la variable que contiene json
	         result=sb.toString();
	         
	         jsonArray = new JSONArray(result);
	         /*
	         String id,tlf,email,nombre;
	         try {
	             jsonArray = new JSONArray(result);
	             
	             arraydata = new String[jsonArray.length()];
	             for (int i = 0; i < jsonArray.length(); i++) {
	                 JSONObject row = jsonArray.getJSONObject(i);
	                 id = row.getString("id");
	                 email = row.getString("email");
	                 nombre = row.getString("nombre");
	                 tlf = row.getString("tlf");
	                 arraydata[i]=tlf;
	             }
	            
	           } catch (Exception e) {
	             e.printStackTrace();
	           }
	         */
     
	         
	     } catch(Exception e){}

	     
	      return jsonArray;
	 }
	 
	 public  JSONArray ObtenerTaxiJSON(String url) throws IOException{
		 String resultado = null;
		 InputStream is = null;
	     String result = "";
	     JSONObject json = null;
	     JSONArray jsonArray = null;
	     String [] arraydata;
	     
		 HttpClient httpclient = new DefaultHttpClient();
		 HttpGet request = new HttpGet(url);
		 request.setHeader("content-type", "application/json");
		 ResponseHandler handler = new BasicResponseHandler();
		 try {
		 resultado = httpclient.execute(request, handler);
		 } catch (ClientProtocolException e) {
		 e.printStackTrace();
		 } catch (IOException e) {
		 e.printStackTrace();
		 }
	     
	     /* try{
	         HttpClient httpclient = new DefaultHttpClient();
	         
	         HttpPost httppost = new HttpPost(url);
	         try{
		         HttpResponse response = httpclient.execute(httppost);
		         HttpEntity entity = response.getEntity();
		         is = entity.getContent();
	         
	         } catch (ClientProtocolException er) {
	        	 er.printStackTrace();
	         }
	     }catch(Exception e){
	    	 Log.e("log_tag", "Error in http connection " + e.toString());
	    	 
	     }*/

	      try{
	         BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	         StringBuilder sb = new StringBuilder();
	         String line = null;
	         while ((line = reader.readLine()) != null) {
	             sb.append(line + "\n");
	         }
	         is.close();
	         result=sb.toString();
	         jsonArray = new JSONArray(result);
	         /*
	         String id,tlf,email,nombre;
	         try {
	             jsonArray = new JSONArray(result);
	             
	             arraydata = new String[jsonArray.length()];
	             for (int i = 0; i < jsonArray.length(); i++) {
	                 JSONObject row = jsonArray.getJSONObject(i);
	                 id = row.getString("id");
	                 email = row.getString("email");
	                 nombre = row.getString("nombre");
	                 tlf = row.getString("tlf");
	                 arraydata[i]=tlf;
	             }
	            
	           } catch (Exception e) {
	             e.printStackTrace();
	           }
	         */
     
	         
	     } catch(Exception e){}

	     
	      return jsonArray;
	 }
	 
	public void TraerData1(String urll,ContentResolver resolver) throws IOException {
		//public byte[] TraerData(InputStream inputStream) throws IOException {
		  // this dynamically extends to take the bytes you read
			//ContentResolver cr = getContentResolver();
			
			byte[] response;
			//URL url = new URL(urll);
			//Uri uri = Uri.parse("android.resource://150.186.92.201/dato.php");
			Uri uri = Uri.parse("content://150.186.92.201/dato.php");
			
			//InputStream inStream = cr.openInputStream(thisPhotoUri);
			//ContentResolver cr = uri..getContentResolver();
			
			
			InputStream inputStream = resolver.openInputStream(uri);
			  
			String str=is.toString();
			
			ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		
			// this is storage overwritten on each iteration with bytes
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
		
			// we need to know how may bytes were read to write them to the byteBuffer
			int len = 0;
		  
			while ((len = inputStream.read(buffer)) != -1) {
			    byteBuffer.write(buffer, 0, len);
			}
				
				
			// and then we can return your byte array.
			response = byteBuffer.toByteArray();
				
				
	}
	public void TraerData(String url2, ContentResolver resolver) {
		// TODO Auto-generated method stub
		
	}


		
	}//Fin de enviardata		
		
		//*****************************************************************************
	/*
	 * 
	 * 
	 *  //***********************************************************************************
	         //json = new JSONObject(result);
	         /*
	         JSONArray jArray = json.getJSONArray(result);

	         System.out.println("*****JARRAY*****"+result.length());
	         for(int i=0;i<result.length();i++){


	          JSONObject json_data = result.getJSONObject(i);
	          Log.i("log_tag","id"+json_data.getInt("account")+
	           ", nombre"+json_data.getString("name")+
	           ", email"+json_data.getString("number")+
	           ", tlf"+json_data.getString("url")
	          );
	          

	         }*//*Log.i("log_tag","id"+ jsonArray.getString(0)+
	      	           ", nombre"+jsonArray.getString(1)+
	      	           ", email"+jsonArray.getString(2)+
	      	           ", tlf"+jsonArray.getString(3)
	      	          );
	             /*Log.i(EnviarData.class.getName(),
	                 "Number of entries " + jsonArray.length());
	             for (int i = 0; i < jsonArray.length(); i++) {
	               JSONObject jsonObject = jsonArray.getJSONObject(i);
	               Log.i(EnviarData.class.getName(), jsonObject.getString("text"));
	             }
	 ContentResolver r = getContentResolver();
        	    InputStream in = r.openInputStream(Uri.parse("content://150.186.92.201/dato.php"));
        	    ByteArrayOutputStream out = new ByteArrayOutputStream();
        	    byte[] buffer = new byte[4096];
        	    int n = in.read(buffer);
        	    while (n >= 0) {
        		out.write(buffer, 0, n);
        		n = in.read(buffer);
        	    }
        	    in.close();
		try {
			  			   
			   URL url = new URL(urll);
			   
			   HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			   
			   //byte[] bytes = resul.getBytes("UTF-8");
			   byte[] data = new byte[256];
					   
			   urlConn.setRequestProperty("Content-Length", String.valueOf(bytes.length));
			   
			   urlConn.setRequestMethod("POST");
			   
			   urlConn.setDoOutput(true);
			   
			   urlConn.connect();
			   
			   os = urlConn.getOutputStream();
			   
			   os.write(bytes);
			   
			   
		
		     } finally {
		       if(is!= null)
		          is.close();
		         if(os != null)
		           os.close();
		     if(httpConn != null)
		           httpConn.close();
		   }		
			/*String response = "";

			try {
				StreamConnection s = (StreamConnection)Connector.open(url);

				InputStream input = s.openInputStream();

				byte[] data = new byte[256];
				int len = 0;
				StringBuffer raw = new StringBuffer();

				while( -1 != (len = input.read(data))) {
					raw.append(new String(data, 0, len));
				}

				response = raw.toString();

				input.close();
				s.close();
			} catch(Exception e) { }

			return response;
		
		  
     	}*/
		//******************************************************************/
	//}




//-------------------------------------------------------------------
/*
String data = URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode("10.5885", "UTF-8");
data += "&" + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode("-62.5885", "UTF-8");

URL url = new URL(urll);
HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
//URLConnection conn = url.openConnection();
urlConn.setDoOutput(true);
OutputStreamWriter wr = new OutputStreamWriter(urlConn.getOutputStream());
wr.write(data);
wr.close();
	
	os.close();
	*/
//-------------------
/* InputStream in = null;
String queryResult = "";
try {
     URL url = new URL(urll);
     HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
     HttpURLConnection httpCon = (HttpURLConnection) urlConn;
     httpCon.setAllowUserInteraction(false);
     httpCon.connect();
     in = httpCon.getInputStream();
     BufferedInputStream bis = new BufferedInputStream(in);
     ByteArrayBuffer baf = new ByteArrayBuffer(50);
     int read = 0;
     int bufSize = 512;
     byte[] buffer = new byte[bufSize];
     while(true){
          read = bis.read(buffer);
          if(read==-1){
               break;
          }
          baf.append(buffer, 0, read);
     }
     queryResult = new String(baf.toByteArray());
     } catch (MalformedURLException e) {
          // DEBUG
          Log.e("DEBUG: ", e.toString());
     } catch (IOException e) {
          // DEBUG
          Log.e("DEBUG: ", e.toString());
     }*/

 /*StringBuilder postDataBuilder.append("lat=").append(URLEncoder.encode("10.5665", UTF8));
//StringBuilder postDataBuilder.setlat();
 byte[] postData = null;
 postData = postDataBuilder.toString().getBytes();


 URL urll = new URL("http://" + url + ":" + String.valueOf(80));
 HttpURLConnection httpCon = (HttpURLConnection) urll.openConnection();

 httpCon.setDoOutput(true);
 httpCon.setRequestMethod("POST");
 httpCon.setRequestProperty("Content-Length", Integer.toString(postData.length));
 httpCon.setUseCaches(false);

 OutputStream out = httpCon.getOutputStream();
 out.write(postData);
 out.close();

 int responseCode = httpCon.getResponseCode();*/



/*HttpClient httpclient = new DefaultHttpClient();
//URL url = new URL("http://www.example.com/resource");
HttpURLConnection httpCon = (HttpURLConnection)  url.openConnection();

httpConn = (HttpConnection) Connector.open(url,Connector.READ_WRITE);
connectionSuccess = true;
httpConn.setRequestMethod(HttpConnection.POST);*/

//-------------------------------------------------------------------------------
/*
 URL url = new URL(urll);
	
	HttpURLConnection httpConn1 = (HttpURLConnection) url.openConnection();
	        	        	
	
	httpConn1.setReadTimeout(10000);
	
	httpConn1.setConnectTimeout(15000);
	
	httpConn1.setRequestMethod("POST");
	
	httpConn1.setDoInput(true);
	
	httpConn1.setDoOutput(true);
	
	httpConn1.connect();
	
	//httpConn1.setChunkedStreamingMode(0);

	os = new BufferedOutputStream(httpConn1.getOutputStream());
	
 //writeStream(os);
	
	
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	
	params.add(new BasicNameValuePair("usuario", usuario));
	
	params.add(new BasicNameValuePair("lat", lat));
	
	params.add(new BasicNameValuePair("lon", lon));
	
	params.add(new BasicNameValuePair("codigo", codigo));
	
	//String resul = getQuery(params);
	
	//os.write(resul);
	
	//os = httpConn1.getOutputStream();
	
	
	BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
	
	//String resul = "dato.php?" + getQuery(params);
	
	String resul = getQuery(params);
	
	writer.write(resul);
	
	writer.close();
	
	os.close();

	*/
//--------------------------------------------------------------------------------------------------------------
	
//create an output stream to upload data
//OutputStream oStream = conn.openOutputStream();
/*
 * oStream.write( m_data.getXml().getBytes() );
 * oStream.flush();oStream.write( m_data.getXml().getBytes() );
	oStream.flush();
	// create an input stream to show the result
	DataInputStream diStream = new DataInputStream( conn.
	openInputStream() );
	int availableBytes = diStream.available();
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// loop until no more bytes to read
	while (0 < availableBytes)
	{
	byte[] wsResponse = new byte[availableBytes];
	diStream.read( wsResponse );
	// add incoming bytes to buffer
	baos.write( wsResponse );
	// find out how many more there are
	availableBytes = diStream.available();
	}
	// all bytes are now available in the baos
	// time to check the response
	// since the response is in XML, create an
	// XML Document object to see it
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	DocumentBuilder db = dbf.newDocumentBuilder();
	// change the array of bytes just retrieved
	// into an input stream
	Document doc = db.parse( new ByteArrayInputStream( baos.
	toByteArray() ) );
	// now check the response
	this.checkResponseDocument( doc );*/
/*

httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

URLEncodedPostData encPostData = new URLEncodedPostData("UTF-8", false);
encPostData.append("usuario", usuario);
//encPostData.append("codigo", String.valueOf(4));
encPostData.append("lat", lat);
encPostData.append("lon", lon);
encPostData.append("codigo", codigo);
byte[] postData = encPostData.toString().getBytes("UTF-8");

httpConn.setRequestProperty("Content-Length", String.valueOf(postData.length));

os = httpConn.openOutputStream();

os.write(postData);

os.flush();*/

/*  os = httpConn.openOutputStream();
// Open an HTTP Connection object
httpConn = (HttpConnection)Connector.open(url);
// Setup HTTP Request to POST
httpConn.setRequestMethod(HttpConnection.POST);

httpConn.setRequestProperty("User-Agent", "BlackBerry");
// httpConn.setRequestProperty("Accept_Language","en-US");
//Content-Type is must to pass parameters in POST Request
httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

// This function retrieves the information of this connection
// getConnectionInformation(httpConn);

String params;
params = "name=" + name;

os.write(params.getBytes());

/**Caution: os.flush() is controversial. It may create unexpected behavior
    on certain mobile devices. Try it out for your mobile device **/

//os.flush();

// Read Response from the Server

/* StringBuffer sb = new StringBuffer();
is = httpConn.openDataInputStream();
int chr;
while ((chr = is.read()) != -1)
sb.append((char) chr);

// Web Server just returns the birthday in mm/dd/yy format.
System.out.println(name+"'s Birthday is " + sb.toString());*/
