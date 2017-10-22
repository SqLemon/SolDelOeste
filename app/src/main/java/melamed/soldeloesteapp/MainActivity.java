package melamed.soldeloesteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    TextInputLayout tilUser;
    TextInputLayout tilPass;
    EditText txbUser;
    EditText txbPass;
    Button btnLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = this.getPreferences(Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        txbUser = (EditText) findViewById(R.id.txbUser);
        txbPass = (EditText) findViewById(R.id.txbPass);
        tilUser = (TextInputLayout) findViewById(R.id.dummyLayout1);
        tilPass = (TextInputLayout) findViewById(R.id.dummyLayout2);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        tryGetCreds();
    }

    void tryGetCreds() {
        tilUser.setEnabled(false);
        tilPass.setEnabled(false);
        btnLogin.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        String u = preferences.getString("user", null);
        String p = preferences.getString("pass", null);
        String m = preferences.getString("mail", null);
        int t = preferences.getInt("tipo", -1);
        if (u == null || p == null || m == null || t < 0) {
            progressBar.setVisibility(View.INVISIBLE);
            tilUser.setEnabled(true);
            tilPass.setEnabled(true);
            btnLogin.setEnabled(true);
            return;
        }
        loggedIn(u, p, m, t);
    }

    void loggedIn(String user, String pass, String mail, int tipo) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user", user);
        editor.putString("pass", pass);
        editor.putString("mail", mail);
        editor.putInt("tipo", tipo);
        editor.apply();

        Class c = (tipo > 0 ? VentaActivity.class : Clientes2.class);
        Intent i = new Intent(getApplicationContext(), c)
                .putExtra("user", user)
                .putExtra("pass", pass)
                .putExtra("mail", mail)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(i);
    }

    void login() {
        LoginClass clase = new LoginClass();
        clase.setOnTaskCompletedListener(new LoginClass.onTaskCompletedListener() {
            @Override
            public void onTaskCompleted(LoginClass.Result result) {
                if (result == null) {
                    tilUser.setError("Usuario o contraseña incorrectos");
                    tilPass.setError("Usuario o contraseña incorrectos");
                    progressBar.setVisibility(View.INVISIBLE);
                    tilUser.setEnabled(true);
                    tilPass.setEnabled(true);
                    btnLogin.setEnabled(true);
                    return;
                }
                loggedIn(result.getUser(), result.getPass(), result.getMail(), result.getTipo());
            }
        });
        try {
            tilUser.setEnabled(false);
            tilPass.setEnabled(false);
            btnLogin.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            clase.login(txbUser.getText().toString(), txbPass.getText().toString());
        } catch (java.net.MalformedURLException e) {
            Log.e(e.getClass().toString(), e.getMessage());
        }

    }
}
