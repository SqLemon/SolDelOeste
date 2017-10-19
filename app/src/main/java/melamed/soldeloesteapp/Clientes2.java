package melamed.soldeloesteapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Clientes2 extends AppCompatActivity {
    Carrito carrito;
    ProductList listaEntera;
    RecyclerView rv;
    static final int ADD_REQUEST = 1;
    Menu m;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes2);
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        getProductos();
        carrito = new Carrito();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_clientes);
        setSupportActionBar(toolbar);
        rv = (RecyclerView) findViewById(R.id.recView);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        m = menu;
        getMenuInflater().inflate(R.menu.toolbar_cart_menu, menu);
        menu.findItem(R.id.action_add).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_add:
                Intent intent = new Intent(Clientes2.this, AddToCart.class);
                intent.putExtra("cart", carrito);
                intent.putExtra("list", listaEntera);
                startActivityForResult(intent, ADD_REQUEST);
                return true;

            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADD_REQUEST) {
            carrito = (Carrito) data.getParcelableExtra("data");
            RVAdapter rvAdapter = new RVAdapter(carrito);
            rv.setAdapter(rvAdapter);
        }
    }

    void getProductos(){
        GetProductosClass clase = new GetProductosClass();
        clase.setOnTaskCompletedListener(new GetProductosClass.onTaskCompletedListener() {
            @Override public void onTaskCompleted(ProductList result) {
                if(result.size() < 1) {
                    Snackbar.make(findViewById(R.id.coordinator), "Error en la descarga de datos.", Snackbar.LENGTH_LONG);
                    return;
                }
                listaEntera = result;
                runOnUiThread(new Runnable() {
                    @Override public void run() {
                        m.findItem(R.id.action_add).setVisible(true);
                        findViewById(R.id.progress_bar).setVisibility(View.GONE);
                        RVAdapter rvAdapter = new RVAdapter(carrito);
                        rv.setAdapter(rvAdapter);
                        if(carrito.size() == 0){
                            rv.setVisibility(View.GONE);
                            findViewById(R.id.dummyText).setVisibility(View.VISIBLE);
                        } else {
                            rv.setVisibility(View.VISIBLE);
                            findViewById(R.id.dummyText).setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        try {
            clase.getProductos();
        } catch (java.net.MalformedURLException e) {
            Log.e(e.getClass().toString(), e.getMessage());
        }
    }



}
