package melamed.soldeloesteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class AddToCartActivity extends AppCompatActivity{
    Carrito carrito;
    ProductList listaEntera;
    AutoCompleteTextView txtProdNombre, txtProdMarca;
    EditText numProdCant;
    TextView lblProdPrecio;
	
	@Override
	public void onBackPressed(){
		if(((AutoCompleteTextView) findViewById(R.id.txtProdNombre)).getText().toString().equals("") &&
			((AutoCompleteTextView) findViewById(R.id.txtProdMarca)).getText().toString().equals("") &&
			((EditText) findViewById(R.id.numProdCant)).getText().toString().equals("")){
			setResult(RESULT_CANCELED);
			finish();
			return;
		}
		new AlertDialog.Builder(this)
			.setMessage("¿Deseas descartar los cambios?")
			//.setCancelable(false)
			.setPositiveButton(
				"Descartar",
				new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which){
						setResult(RESULT_CANCELED);
						finish();
					}
				})
			.setNegativeButton(
				"Cancelar",
				new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which){
						dialog.cancel();
					}
				})
			.create()
			.show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);
        carrito = getIntent().getParcelableExtra("cart");
        listaEntera = getIntent().getParcelableExtra("list");
		
		Toolbar toolbar = findViewById(R.id.toolbar_add2cart);
		setSupportActionBar(toolbar);
		
		txtProdNombre = findViewById(R.id.txtProdNombre);
		txtProdMarca = findViewById(R.id.txtProdMarca);
		numProdCant = findViewById(R.id.numProdCant);
		lblProdPrecio = findViewById(R.id.lblProdPrecio);

        txtProdNombre.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listaEntera.getNombres()));
        txtProdMarca.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, listaEntera.getMarcas()));
		
		txtProdNombre.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				txtProdNombre.showDropDown();
			}
		});
		txtProdMarca.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				txtProdMarca.showDropDown();
			}
		});
		
        txtProdNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
	            if(!hasFocus){
		            TextInputLayout j = findViewById(R.id.dummyL1);
		            j.setError(getString(R.string.nonexistent));
		            String t = ((AutoCompleteTextView) v).getText().toString();
		            if(listaEntera.getNombres().contains(t)){
			            j.setError(null);
			            populateBrands(t);
		            }
	            }
            }
        });

        txtProdMarca.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
	            if(!hasFocus){
		            TextInputLayout j = findViewById(R.id.dummyL2);
		            j.setError(getString(R.string.nonexistentbrand));
		            String t = ((AutoCompleteTextView) v).getText().toString();
		            if(listaEntera.getMarcas().contains(t)){
			            j.setError(null);
			            populateNames(t);
		            }
	            }
            }
        });

        numProdCant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int a = 0;
                if (!s.toString().equals("")) a = Integer.parseInt(s.toString());
                Producto p = listaEntera.getByNombreMarca(txtProdNombre.getText().toString(), txtProdMarca.getText().toString());
                if (p == null | a < 1) {
	                lblProdPrecio.setText(R.string.n_a);
	                return;
                }
                double pr = a * p.getPrecio();
                lblProdPrecio.setText(String.valueOf(pr));
            }
	
	        @Override
	        public void afterTextChanged(Editable s){
	        }
        });
		
	}

    void populateNames(String s) {
        List<String> t = listaEntera.getNombres(s);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, t);
        txtProdNombre.setAdapter(adapter);
    }

    void populateBrands(String s) {
        List<String> t = listaEntera.getMarcas(s);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, t);
        txtProdMarca.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_ok:
                trySave();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void trySave() {
        String nombre = ((AutoCompleteTextView) findViewById(R.id.txtProdNombre)).getText().toString();
        String marca = ((AutoCompleteTextView) findViewById(R.id.txtProdMarca)).getText().toString();
        String strcantidad = ((EditText) findViewById(R.id.numProdCant)).getText().toString();
        int cantidad = 0;
        if (!strcantidad.equals("")) cantidad = Integer.parseInt(strcantidad);

        ((TextInputLayout) findViewById(R.id.dummyL1)).setError(null);
        ((TextInputLayout) findViewById(R.id.dummyL2)).setError(null);
        ((TextInputLayout) findViewById(R.id.dummyL3)).setError(null);

        if (!listaEntera.getNombres().contains(nombre))
	        ((TextInputLayout) findViewById(R.id.dummyL1)).setError(getString(R.string.nonexistent));
	    if(!listaEntera.getMarcas().contains(marca))
		    ((TextInputLayout) findViewById(R.id.dummyL2)).setError(getString(R.string.nonexistentbrand));
	    if(nombre.equals(""))
		    ((TextInputLayout) findViewById(R.id.dummyL1)).setError(getString(R.string.requiredfield));
	    if(marca.equals(""))
		    ((TextInputLayout) findViewById(R.id.dummyL2)).setError(getString(R.string.requiredfield));

        if (strcantidad.equals("")) {
	        ((TextInputLayout) findViewById(R.id.dummyL3)).setError(getString(R.string.requiredfield));
        } else if (cantidad < 1) {
	        ((TextInputLayout) findViewById(R.id.dummyL3)).setError(getString(R.string.invalidquantity));
        }

        if ((((TextInputLayout) findViewById(R.id.dummyL1)).getError() != null) |
                (((TextInputLayout) findViewById(R.id.dummyL2)).getError() != null) |
                (((TextInputLayout) findViewById(R.id.dummyL1)).getError() != null)) {
            return;
        }

        Producto p = listaEntera.getByNombreMarca(nombre, marca);
        if (p == null) {
	        Snackbar.make(findViewById(R.id.dummyXX), R.string.nonexistentcombination, Snackbar.LENGTH_LONG);
	        return;
        }

        if(!carrito.add(p, cantidad)){
            new AlertDialog.Builder(this)
	            .setTitle(R.string.error)
	            .setMessage(R.string.alreadyincart)
	            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
		            @Override
		            public void onClick(DialogInterface dialog, int which){
			            setResult(RESULT_CANCELED);
                        finish();
                    }})
                .setCancelable(false)
	            .create()
	            .show();
	        return;
        }
        returnToParent();
    }

    void returnToParent() {
        Intent i = new Intent().putExtra("data", carrito);

        setResult(RESULT_OK, i);
        finish();
    }
}
