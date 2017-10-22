package melamed.soldeloesteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Clientes2 extends AppCompatActivity {
    Carrito carrito;
    ProductList listaEntera;
    RecyclerView rv;
    RVAdapter rvAdapter;
    static final int ADD_REQUEST = 1;
    boolean a = false, b = false;
    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes2);
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        getProductos();
        carrito = new Carrito();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_clientes);
        setSupportActionBar(toolbar);
        rvAdapter = new RVAdapter(carrito);
        rv = (RecyclerView) findViewById(R.id.recView);
        RecyclerView.LayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(llm);
        attachHelper();
        rv.setAdapter(rvAdapter);
    }

    void attachHelper() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final Producto p = carrito.get(position);
                carrito.remove(p);
                refresh();
                Snackbar
                    .make(findViewById(R.id.coordinator), "Eliminado", Snackbar.LENGTH_SHORT)
                    .setAction(
                        "Deshacer",
                        new View.OnClickListener() {
                            @Override public void onClick(View v) {
                                carrito.add(p);
                                refresh();
                            }
                        })
                    .show();
            }
        }).attachToRecyclerView(rv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        a = true;
        if(a && b){
            getMenuInflater().inflate(R.menu.toolbar_cart_menu, menu);
        } else mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                Intent intent = new Intent(Clientes2.this, AddToCart.class);
                intent.putExtra("cart", carrito);
                intent.putExtra("list", listaEntera);
                startActivityForResult(intent, ADD_REQUEST);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ADD_REQUEST) {
            carrito = data.getParcelableExtra("data");
            refresh();
        }
    }

    void refresh() {
        if (carrito.size() == 0) {
            rv.setVisibility(View.GONE);
            findViewById(R.id.dummyText).setVisibility(View.VISIBLE);
        } else {
            rv.setVisibility(View.VISIBLE);
            rvAdapter = new RVAdapter(carrito);
            rv.setAdapter(rvAdapter);
            rv.invalidate();
            findViewById(R.id.dummyText).setVisibility(View.GONE);
        }
    }

    void getProductos() {
        GetProductosClass clase = new GetProductosClass();
        clase.setOnTaskCompletedListener(new GetProductosClass.onTaskCompletedListener() {
            @Override
            public void onTaskCompleted(ProductList result) {
                if (result.size() < 1) {
                    Snackbar.make(findViewById(R.id.coordinator), "Error en la descarga de datos.", Snackbar.LENGTH_LONG);
                    return;
                }
                listaEntera = result;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.progress_bar).setVisibility(View.GONE);
                        refresh();
                    }
                });
                b = true;
                if(a && b){
                    getMenuInflater().inflate(R.menu.toolbar_cart_menu, mMenu);
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
