package melamed.soldeloesteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import java.net.MalformedURLException;

import melamed.soldeloesteapp.LoginClass.Result;
import melamed.soldeloesteapp.LoginClass.onTaskCompletedListener;
import melamed.soldeloesteapp.R.id;
import melamed.soldeloesteapp.R.layout;

public class LoginActivity extends AppCompatActivity{
	String u, p, m;
	int t;
	boolean r;
	private SharedPreferences preferences;
	private TextInputLayout tilUser;
	private TextInputLayout tilPass;
	private TextInputEditText txbUser;
	private TextInputEditText txbPass;
	private Button btnLogin;
	private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    preferences = getPreferences(Context.MODE_PRIVATE);
	    super.onCreate(savedInstanceState);
	    setContentView(layout.activity_login);
	    progressBar = findViewById(id.progressbar);
	    txbUser = findViewById(id.txbUser);
	    txbPass = findViewById(id.txbPass);
	    tilUser = findViewById(id.dummyLayout1);
	    tilPass = findViewById(id.dummyLayout2);
	    btnLogin = findViewById(id.btnLogin);
	    btnLogin.setOnClickListener(new OnClickListener(){
		    @Override
		    public void onClick(View v){
			    login();
		    }
	    });
	    tryGetCreds();
    }
	
	private void tryGetCreds(){
		tilUser.setEnabled(false);
		tilPass.setEnabled(false);
		btnLogin.setEnabled(false);
		progressBar.setVisibility(View.VISIBLE);
		u = preferences.getString("user", null);
		p = preferences.getString("pass", null);
		m = preferences.getString("mail", null);
		t = preferences.getInt("tipo", -1);
		r = preferences.getBoolean("recordar", false);
		if(u != null && p != null && m != null && t > -1 && r)
			loggedIn(u, p, m, t);
		progressBar.setVisibility(View.INVISIBLE);
		tilUser.setEnabled(true);
		tilPass.setEnabled(true);
		btnLogin.setEnabled(true);
	}
	
	private void loggedIn(String user, String pass, String mail, int tipo){
		Editor editor = preferences.edit();
		editor.putString("user", user);
		editor.putString("pass", pass);
        editor.putString("mail", mail);
        editor.putInt("tipo", tipo);
		editor.putBoolean("recordar", ((CheckBox) findViewById(id.chbRecordarme)).isChecked());
		editor.apply();
		
		Class c = tipo > 0 ? VentaActivity.class : ClientesActivity.class;
		Intent i = new Intent(getApplicationContext(), c)
		    .putExtra("user", user)
                .putExtra("pass", pass)
                .putExtra("mail", mail)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(i);
		finish();
	}
	
	private void login(){
		if(m != null && t > -1 && txbUser.getText().toString().equals(u) && txbPass.getText().toString().equals(p))
			loggedIn(u, p, m, t);
		LoginClass clase = new LoginClass();
		clase.setOnTaskCompletedListener(new onTaskCompletedListener(){
			@Override
			public void onTaskCompleted(Result result){
				if(result != null)
					loggedIn(result.getUser(), result.getPass(), result.getMail(), result.getTipo());
				tilUser.setError("Usuario o contraseña incorrectos");
				tilPass.setError("Usuario o contraseña incorrectos");
				progressBar.setVisibility(View.INVISIBLE);
				tilUser.setEnabled(true);
				tilPass.setEnabled(true);
				btnLogin.setEnabled(true);
				
			}
        });
        try {
	        tilUser.setEnabled(false);
	        tilPass.setEnabled(false);
	        btnLogin.setEnabled(false);
	        progressBar.setVisibility(View.VISIBLE);
	        clase.login(txbUser.getText().toString(), txbPass.getText().toString());
        } catch(MalformedURLException e) {
            Log.e(e.getClass().toString(), e.getMessage());
        }

    }
}
