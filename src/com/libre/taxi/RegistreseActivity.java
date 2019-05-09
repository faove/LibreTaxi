package com.libre.taxi;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

	
public class RegistreseActivity extends Activity {		
		JSONArray jsonUsuario = null;
		public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

		
		private UserLoginTask mAuthTask = null;
		//dATA DE RETURN
		public String SesionUsuario;
		// Values for email and password at the time of the login attempt.
		private String mEmail;
		private String mPassword;
		private String mConfirPassword;
		private String mTelefono;

		// UI references.
		private EditText mEmailView;
		private EditText mPasswordView;
		private EditText mConfirPasswordView;
		private EditText mTelefonoView;
		private View mLoginFormView;
		private View mLoginStatusView;
		private TextView mLoginStatusMessageView;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			setContentView(R.layout.activity_registrese);

			// Set up the login form.
			mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
			mEmailView = (EditText) findViewById(R.id.email);
			mEmailView.setText(mEmail);

			mPasswordView = (EditText) findViewById(R.id.password);
			mPasswordView
					.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView textView, int id,
								KeyEvent keyEvent) {
							if (id == R.id.login || id == EditorInfo.IME_NULL) {
								attemptLogin();
								return true;
							}
							return false;
						}
					});
			
			
			mConfirPasswordView = (EditText) findViewById(R.id.confirpassword);
			mConfirPasswordView
					.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView textView, int id,
								KeyEvent keyEvent) {
							if (id == R.id.login || id == EditorInfo.IME_NULL) {
								attemptLogin();
								return true;
							}
							return false;
						}
					});
			
			mTelefonoView = (EditText) findViewById(R.id.telefono);
			mTelefonoView
					.setOnEditorActionListener(new TextView.OnEditorActionListener() {
						@Override
						public boolean onEditorAction(TextView textView, int id,
								KeyEvent keyEvent) {
							if (id == R.id.login || id == EditorInfo.IME_NULL) {
								attemptLogin();
								return true;
							}
							return false;
						}
					});
			
			//Boton Crear
			
			mLoginFormView = findViewById(R.id.login_form);
			mLoginStatusView = findViewById(R.id.login_status);
			mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

			findViewById(R.id.sign_in_button).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							attemptLogin();
							/*Intent intent = new Intent(RegistreseActivity.this, BuscarTaxiActivity.class);
							intent.putExtra("SesionUsuario",SesionUsuario);
							//intent.getExtras();
							//setResult(RESULT_OK,intent);     
							//finish();
		            		//startActivityForResult
		            		//tartActivityForResult(intent,REQUEST_CODE);
		            		startActivity(intent);*/
						}
					});
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			super.onCreateOptionsMenu(menu);
			getMenuInflater().inflate(R.menu.login, menu);
			return true;
		}

		
		public void attemptLogin() {
			if (mAuthTask != null) {
				return;
			}

			// Reset errors.
			
			mEmailView.setError("");
			mPasswordView.setError("");
			mConfirPasswordView.setError("");
			mTelefonoView.setError("");
			
			//mPasswordView.setError(null);

			// Store values at the time of the login attempt.
			mEmail = mEmailView.getText().toString();
			mPassword = mPasswordView.getText().toString();
			mConfirPassword = mConfirPasswordView.getText().toString();
			mTelefono = mTelefonoView.getText().toString();
			
			boolean cancel = false;
			View focusView = null;
			// Chequea mTelefono.
			if (TextUtils.isEmpty(mTelefono)) {
				mTelefonoView.setError(getString(R.string.error_field_required));
				focusView = mTelefonoView;
				cancel = true;
			} else if (mTelefono.length() < 4) {
				mTelefonoView.setError(getString(R.string.error_invalid_password));
				focusView = mTelefonoView;
				cancel = true;
			}
			// Chequea Confirma password.
			if (TextUtils.isEmpty(mConfirPassword)) {
				mConfirPasswordView.setError(getString(R.string.error_field_required));
				focusView = mConfirPasswordView;
				cancel = true;
			} else if (mConfirPassword.length() < 4) {
				mConfirPasswordView.setError(getString(R.string.error_invalid_password));
				focusView = mConfirPasswordView;
				cancel = true;
			}
			// Check for a valid password.
			if (TextUtils.isEmpty(mPassword)) {
				mPasswordView.setError(getString(R.string.error_field_required));
				focusView = mPasswordView;
				cancel = true;
			} else if (mPassword.length() < 4) {
				mPasswordView.setError(getString(R.string.error_invalid_password));
				focusView = mPasswordView;
				cancel = true;
				Toast.makeText(getApplicationContext(),getString(R.string.error_invalid_password),Toast.LENGTH_LONG).show();
			}
			
			//Verifico si los password son iguales
			if (!TextUtils.isEmpty(mPassword)){
				
			 	if (!mConfirPassword.equals(mPassword)) {
					//error_equal_password
					mPasswordView.setError(getString(R.string.error_equal_password));
					focusView = mPasswordView;
					cancel = true;
					Toast.makeText(getApplicationContext(),getString(R.string.error_equal_password),Toast.LENGTH_LONG).show();
				
			 	}	
			} 	
			// Check for a valid email address. R.string.error_field_required
			if (TextUtils.isEmpty(mEmail)) {
				mEmailView.setError("Este campo es requerido");
				focusView = mEmailView;
				cancel = true;
			} else if (!mEmail.contains("@")) {
				mEmailView.setError(getString(R.string.error_invalid_email));
				focusView = mEmailView;
				cancel = true;
				Toast.makeText(getApplicationContext(),getString(R.string.error_invalid_email),Toast.LENGTH_LONG).show();
			}

			if (cancel) {
				// There was an error; don't attempt login and focus the first
				// form field with an error.
				focusView.requestFocus();
			} else {
				SesionUsuario=mEmail;
				// Show a progress spinner, and kick off a background task to
				// perform the user login attempt.
				mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
				showProgress(true);
				mAuthTask = new UserLoginTask();
				mAuthTask.execute((Void) null);
			}
		}

		/**
		 * Shows the progress UI and hides the login form.
		 */
		
		@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
		private void showProgress(final boolean show) {
			// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
			// for very easy animations. If available, use these APIs to fade-in
			// the progress spinner.
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
				int shortAnimTime = getResources().getInteger(
						android.R.integer.config_shortAnimTime);

				mLoginStatusView.setVisibility(View.VISIBLE);
				mLoginStatusView.animate().setDuration(shortAnimTime)
						.alpha(show ? 1 : 0)
						.setListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator animation) {
								mLoginStatusView.setVisibility(show ? View.VISIBLE
										: View.GONE);
							}
						});

				mLoginFormView.setVisibility(View.VISIBLE);
				mLoginFormView.animate().setDuration(shortAnimTime)
						.alpha(show ? 0 : 1)
						.setListener(new AnimatorListenerAdapter() {
							@Override
							public void onAnimationEnd(Animator animation) {
								mLoginFormView.setVisibility(show ? View.GONE
										: View.VISIBLE);
							}
						});
			} else {
				// The ViewPropertyAnimator APIs are not available, so simply show
				// and hide the relevant UI components.
				mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
				mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			}
		}

		/**
		 * Represents an asynchronous login/registration task used to authenticate
		 * the user.
		 */
		public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
			JSONArray json;
			@Override
			protected Boolean doInBackground(Void... params) {
				// TODO: attempt authentication against a network service.

				
					// Simulate network access.
					//Thread.sleep(2000);
					try {
						json=CrearUsuario();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				

				/*for (String credential : DUMMY_CREDENTIALS) {
					String[] pieces = credential.split(":");
					if (pieces[0].equals(mEmail)) {
						// Account exists, return true if the password matches.
						return pieces[1].equals(mPassword);
					}
				}*/

				// TODO: register the new account here.
				return true;
			}

			@Override
			protected void onPostExecute(final Boolean success) {
				mAuthTask = null;
				showProgress(false);

				if (success) {
					Intent intent = new Intent(RegistreseActivity.this, BuscarTaxiActivity.class);
					intent.putExtra("SesionUsuario",SesionUsuario);
					//intent.getExtras();
					//setResult(RESULT_OK,intent);     
					//finish();
            		//startActivityForResult
            		//tartActivityForResult(intent,REQUEST_CODE);
            		startActivity(intent);
					finish();
				} else {
					mPasswordView
							.setError(getString(R.string.error_incorrect_password));
					mPasswordView.requestFocus();
				}
			}

			@Override
			protected void onCancelled() {
				mAuthTask = null;
				showProgress(false);
			}
		}
		private JSONArray CrearUsuario() throws IOException {
			
			//String action(){
		   // br = new BroadcastReceiver() {
		    	
		    String usuario="", password="";  
		    
		    int w=0;
		    
			//@Override
			//public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			//Toast.makeText(arg0, "Rise and Shine!", Toast.LENGTH_LONG).show();
			String hora = null,fecha = null;
			
			EnviarData enviardata = new EnviarData();
			
			//enviardata.name="Francisco Alvarez";
			
			enviardata.usuario=mEmail;
			
			enviardata.password=mPassword;
			
			enviardata.telefono=mTelefono;
			
			Calendar c = Calendar.getInstance();
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    
		    String formattedDate = df.format(c.getTime());
		    
			enviardata.fecha=formattedDate;
			
			
			
			//enviardata.taxijson = "http://www.dissoft.info/datotaxi.json";
			
			//enviardata.taxijson = "http://150.186.92.201/datosismo.json";
			//enviardata.urljson = "http://www.libretaxi.com/datocsudo.json";
			
			//
			//ConectarData
			//
			
			//enviardata.url = "http://www.libretaxi.com/login/loginregistrese.php";
			enviardata.url = "http://www.dissoft.info/libretaxi.com/login/loginregistrese.php";
			enviardata.ConectarLogin(enviardata.httpConn, enviardata.url, enviardata.name, enviardata.usuario,enviardata.password,enviardata.telefono,enviardata.fecha);
			//ObtenerJSON
			//Aqui verifico que el login y password exista en la base de datos
			//enviardata.urljson = "http://www.libretaxi.com/login/loginregistrese.json";
			enviardata.urljson = "http://www.dissoft.info/libretaxi.com/login/loginregistrese.json";
			jsonUsuario=enviardata.ObtenerJSON(enviardata.url);
			
			 try {
				 //result = new JSONArray(result);
			     
			     //arraydata = new String[jsonArray.length()];
			     for (int i = 0; i < jsonUsuario.length(); i++) {
			         
			    	 JSONObject row = jsonUsuario.getJSONObject(i);
			         
			    	 usuario = row.getString("usuario");
			         
			         password = row.getString("password");
			         
			       //Crea en la memoria inerna un archivo 
			         //con los datos del usuario
			         try
			         {
			             OutputStreamWriter fout=
			                 new OutputStreamWriter(
			                     openFileOutput("libretaxi.json", Context.MODE_PRIVATE));
			          
			             fout.write(jsonUsuario.toString());
			             fout.close();
			         }
			         catch (Exception ex)
			         {
			             Log.e("Ficheros", "Error al escribir fichero a memoria interna");
			         }
			         
			     }
			    
			   } catch (Exception e) {
			     e.printStackTrace();
			   }
			
				return jsonUsuario;  
		}
	}
