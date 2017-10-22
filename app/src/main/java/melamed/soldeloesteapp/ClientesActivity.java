package melamed.soldeloesteapp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;


public class ClientesActivity extends AppCompatActivity {

    GridView gridProds;
    Button btnList;
    Button btnSend;
    Button btnAdd;
    Button btnBack;
    Button btnDelete;
    boolean delete = false;

    String[] numbers = new String[]{
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        gridProds = (GridView) findViewById(R.id.gridProds);
        btnList = (Button) findViewById(R.id.btnList);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnBack = (Button) findViewById(R.id.btnBack);
        getProductos();

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridProds.setVisibility(View.VISIBLE);
                findViewById(R.id.listLayout).setVisibility(View.VISIBLE);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, numbers);
                gridProds.setAdapter(adapter);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delete = true;
                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridProds.setVisibility(View.GONE);
                findViewById(R.id.listLayout).setVisibility(View.GONE);
            }
        });
    }

    void getProductos() {
        GetProductosClass clase = new GetProductosClass();
        clase.setOnTaskCompletedListener(new GetProductosClass.onTaskCompletedListener() {
            @Override
            public void onTaskCompleted(ProductList result) {
                if (result.size() < 1) {
                    Snackbar.make(findViewById(R.id.clientes), "Error en la conexión.", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                //setear valores del grid


                //procesa productos devueltos, uno por uno
                for (Producto p : result) {
                    //aca tenes los datos de cada producto
                    int id = p.getId();
                    String nombre = p.getNombre();
                    String marca = p.getMarca();
                    double precio = p.getPrecio();
                    //TODO: usar datos, agregar a la lista qsy
                }


            }
        });
        try {
            clase.getProductos();
        } catch (java.net.MalformedURLException e) {
            Log.e(e.getClass().toString(), e.getMessage());
        }

    }


}