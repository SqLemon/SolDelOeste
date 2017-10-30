package melamed.soldeloesteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
	private EditText txbUser;
	private EditText txbPass;
	private Button btnLogin;
	private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    this.preferences = this.getPreferences(Context.MODE_PRIVATE);
	    super.onCreate(savedInstanceState);
	    this.setContentView(layout.activity_login);
	    this.progressBar = this.findViewById(id.progressbar);
	    this.txbUser = this.findViewById(id.txbUser);
	    this.txbPass = this.findViewById(id.txbPass);
	    this.tilUser = this.findViewById(id.dummyLayout1);
	    this.tilPass = this.findViewById(id.dummyLayout2);
	    this.btnLogin = this.findViewById(id.btnLogin);
	    this.btnLogin.setOnClickListener(new OnClickListener(){
		    @Override
            public void onClick(View v) {
			    LoginActivity.this.login();
		    }
        });
	    this.tryGetCreds();
    }
	
	private void tryGetCreds(){
		this.tilUser.setEnabled(false);
		this.tilPass.setEnabled(false);
		this.btnLogin.setEnabled(false);
		this.progressBar.setVisibility(View.VISIBLE);
		this.u = this.preferences.getString("user", null);
		this.p = this.preferences.getString("pass", null);
		this.m = this.preferences.getString("mail", null);
		this.t = this.preferences.getInt("tipo", -1);
		this.r = this.preferences.getBoolean("recordar", false);
		if(this.u != null && this.p != null && this.m != null && this.t > -1 && this.r)
			this.loggedIn(this.u, this.p, this.m, this.t);
		this.progressBar.setVisibility(View.INVISIBLE);
		this.tilUser.setEnabled(true);
		this.tilPass.setEnabled(true);
		this.btnLogin.setEnabled(true);
	}
	
	private void loggedIn(String user, String pass, String mail, int tipo){
		Editor editor = this.preferences.edit();
		editor.putString("user", user);
		editor.putString("pass", pass);
        editor.putString("mail", mail);
        editor.putInt("tipo", tipo);
		editor.putBoolean("recordar", ((CheckBox) this.findViewById(id.chbRecordarme)).isChecked());
		editor.apply();
		
		Class c = tipo > 0 ? VentaActivity.class : CarritoActivity.class;
		Intent i = new Intent(this.getApplicationContext(), c)
		    .putExtra("user", user)
                .putExtra("pass", pass)
                .putExtra("mail", mail)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.getApplicationContext().startActivity(i);
		this.finish();
	}
	
	private void login(){
		if(this.m != null && this.t > -1 && this.txbUser.getText().toString().equals(this.u) && this.txbPass.getText().toString().equals(this.p))
			this.loggedIn(this.u, this.p, this.m, this.t);
		LoginClass clase = new LoginClass();
		clase.setOnTaskCompletedListener(new onTaskCompletedListener(){
			@Override
			public void onTaskCompleted(Result result){
				if(result != null)
					LoginActivity.this.loggedIn(result.getUser(), result.getPass(), result.getMail(), result.getTipo());
				LoginActivity.this.tilUser.setError("Usuario o contraseña incorrectos");
				LoginActivity.this.tilPass.setError("Usuario o contraseña incorrectos");
				LoginActivity.this.progressBar.setVisibility(View.INVISIBLE);
				LoginActivity.this.tilUser.setEnabled(true);
				LoginActivity.this.tilPass.setEnabled(true);
				LoginActivity.this.btnLogin.setEnabled(true);
				
			}
        });
        try {
	        this.tilUser.setEnabled(false);
	        this.tilPass.setEnabled(false);
	        this.btnLogin.setEnabled(false);
	        this.progressBar.setVisibility(View.VISIBLE);
	        clase.login(this.txbUser.getText().toString(), this.txbPass.getText().toString());
        } catch(MalformedURLException e) {
            Log.e(e.getClass().toString(), e.getMessage());
        }

    }
}
