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

import java.net.MalformedURLException;

public class CarritoActivity extends AppCompatActivity{
	private static final int ADD_REQUEST = 1;
	private Carrito carrito;
	private ProductList listaEntera;
	private RecyclerView rv;
	private boolean a = false;
	private boolean b = false;
	private Menu mMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carrito);
		findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
		getProductos();
		carrito = new Carrito();
		Toolbar toolbar = findViewById(R.id.toolbar_clientes);
		setSupportActionBar(toolbar);
		rv = findViewById(R.id.recView);
		RecyclerView.LayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		rv.setLayoutManager(llm);
		attachHelper();
		rv.setVisibility(View.GONE);
		findViewById(R.id.dummyText).setVisibility(View.GONE);
	}
	
	private void attachHelper(){
		new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.START | ItemTouchHelper.END){
			@Override
			public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target){
				return false;
			}
			
			@Override
			public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction){
				int position = viewHolder.getAdapterPosition();
				final Producto p = carrito.get(position);
				carrito.remove(p);
				refresh();
				Snackbar
					.make(findViewById(R.id.coordinator), "Eliminado", Snackbar.LENGTH_SHORT)
					.setAction(
						"Deshacer",
						new View.OnClickListener(){
							@Override
							public void onClick(View v){
								carrito.add(p);
								refresh();
							}
						})
					.show();
			}
		}).attachToRecyclerView(rv);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		if(b){
			getMenuInflater().inflate(R.menu.toolbar_cart_menu, menu);
		} else {
			mMenu = menu;
			a = true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		switch(id){
			case R.id.action_add:
				Intent intent = new Intent(CarritoActivity.this, AddToCartActivity.class);
				intent.putExtra("cart", carrito);
				intent.putExtra("list", listaEntera);
				startActivityForResult(intent, ADD_REQUEST);
				return true;
			
			case R.id.action_delete:
				carrito = new Carrito();
				refresh();
				return true;
			
			case R.id.action_logoff:
				logoff();
				break;
			
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void logoff(){
		getPreferences(MODE_PRIVATE).edit().remove("recordar").apply();
		Intent i = new Intent(getApplicationContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(i);
		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode == RESULT_OK && requestCode == ADD_REQUEST){
			carrito = data.getParcelableExtra("data");
			refresh();
		}
	}
	
	private void refresh(){
		if(carrito.size() == 0){
			rv.setVisibility(View.GONE);
			findViewById(R.id.dummyText).setVisibility(View.VISIBLE);
		} else {
			rv.setVisibility(View.VISIBLE);
			findViewById(R.id.dummyText).setVisibility(View.GONE);
		}
		RVAdapter rvAdapter = new RVAdapter(carrito);
		rv.setAdapter(rvAdapter);
		rv.invalidate();
	}
	
	private void getProductos(){
		GetProductosClass clase = new GetProductosClass();
		clase.setOnTaskCompletedListener(new GetProductosClass.onTaskCompletedListener(){
			@Override
			public void onTaskCompleted(ProductList result){
				if(result.size() < 1){
					Snackbar.make(findViewById(R.id.coordinator), "Error en la descarga de datos.", Snackbar.LENGTH_LONG);
					return;
				}
				listaEntera = result;
				runOnUiThread(new Runnable(){
					@Override
					public void run(){
						findViewById(R.id.progress_bar).setVisibility(View.GONE);
						refresh();
					}
				});
				if(a){
					getMenuInflater().inflate(R.menu.toolbar_cart_menu, mMenu);
				} else b = true;
				rv.setVisibility(View.VISIBLE);
				findViewById(R.id.dummyText).setVisibility(View.VISIBLE);
			}
		});
		try{
			clase.getProductos();
		} catch(MalformedURLException e) {
			Log.e(e.getClass().toString(), e.getMessage());
		}
	}
	
	
}
