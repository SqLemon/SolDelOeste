package melamed.soldeloesteapp;


import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class  MainActivity extends AppCompatActivity {

    EditText txbUser;
    EditText txbPass;
    Button btnLogin;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txbUser = (EditText) findViewById(R.id.txbUser);
        txbPass = (EditText) findViewById(R.id.txbPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                login();
            }
        });
    }

    void login(){
        LoginClass clase = new LoginClass();
        clase.setOnTaskCompletedListener(new LoginClass.onTaskCompletedListener() {
            @Override public void onTaskCompleted(LoginClass.Result result) {
                if(result == null) {
                    Snackbar.make(findViewById(R.id.main),"Usuario o contraseña incorrectos.",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                Intent myIntent;
                if (result.getTipo() > 0) {
                    myIntent = new Intent(getApplicationContext(), VentaActivity.class);
                } else {
                    myIntent = new Intent(getApplicationContext(), ClientesActivity.class);
                }
                getApplicationContext().startActivity(myIntent);
            }
        });
        try {
            clase.login(txbUser.getText().toString(), txbPass.getText().toString());
        } catch (java.net.MalformedURLException e) {
            Log.e(e.getClass().toString(), e.getMessage());
        }

    }
}