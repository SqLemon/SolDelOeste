package melamed.soldeloesteapp;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import melamed.soldeloesteapp.R.id;
import melamed.soldeloesteapp.R.layout;
import melamed.soldeloesteapp.RVAdapter.ProductHolder;

class RVAdapter extends Adapter<ProductHolder>{
    private final Carrito c;

    RVAdapter(Carrito c) {
	    c.clean();
	    this.c = c;
    }

    @Override
    public RVAdapter.ProductHolder onCreateViewHolder(ViewGroup parent, int viewType){
	    View v = LayoutInflater.from(parent.getContext()).inflate(layout.product_card, parent, false);
	    return new RVAdapter.ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(final RVAdapter.ProductHolder holder, final int position){
	    holder.lblNombre.setText(this.c.get(position).getNombre());
	    holder.lblMarca.setText(this.c.get(position).getMarca());
	    int cantidad = this.c.getCantidad(this.c.get(position));
	    double precio = this.c.get(position).getPrecio() * cantidad;
	    holder.lblPrecio.setText("$" + String.valueOf(precio));
	    holder.lblCantidad.setText(String.valueOf(cantidad));
	    holder.btnMinusOne.setOnClickListener(new OnClickListener(){
		    @Override
		    public void onClick(View v) {
			    RVAdapter.this.c.setCantidad(RVAdapter.this.c.get(position), RVAdapter.this.c.getCantidad(RVAdapter.this.c.get(position)) - 1);
			    RVAdapter.this.onBindViewHolder(holder, position);
		    }
        });
	    holder.btnPlusOne.setOnClickListener(new OnClickListener(){
		    @Override
		    public void onClick(View v) {
			    RVAdapter.this.c.setCantidad(RVAdapter.this.c.get(position), RVAdapter.this.c.getCantidad(RVAdapter.this.c.get(position)) + 1);
			    RVAdapter.this.onBindViewHolder(holder, position);
		    }
        });
    }

    @Override
    public int getItemCount(){
	    return this.c.size();
    }
	
	static class ProductHolder extends ViewHolder{
		final TextView lblNombre;
		final TextView lblMarca;
        final TextView lblCantidad;
        final TextView lblPrecio;
        final ImageButton btnPlusOne;
        final ImageButton btnMinusOne;

        ProductHolder(View itemView) {
            super(itemView);
	        this.lblNombre = itemView.findViewById(id.lblNombre);
	        this.lblMarca = itemView.findViewById(id.lblMarca);
	        this.lblCantidad = itemView.findViewById(id.lblCantidad);
	        this.lblPrecio = itemView.findViewById(id.lblPrecio);
	        this.btnMinusOne = itemView.findViewById(id.btnMinusOne);
	        this.btnPlusOne = itemView.findViewById(id.btnPlusOne);
        }
    }
}