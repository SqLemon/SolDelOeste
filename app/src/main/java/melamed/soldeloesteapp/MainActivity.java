package melamed.soldeloesteapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class  MainActivity extends AppCompatActivity {

    //SharedPreferences preferences = getSharedPreferences();
    TextInputLayout tilUser;
    TextInputLayout tilPass;
    EditText txbUser;
    EditText txbPass;
    Button btnLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            Intent i;
            if (result.getTipo() > 0) {
                i = new Intent(getApplicationContext(), VentaActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } else {
                i = new Intent(getApplicationContext(), Clientes2.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            getApplicationContext().startActivity(i);
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
