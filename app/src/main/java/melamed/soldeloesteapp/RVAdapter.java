package melamed.soldeloesteapp;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import melamed.soldeloesteapp.R.id;
import melamed.soldeloesteapp.R.layout;
import melamed.soldeloesteapp.RVAdapter.ProductHolder;

class RVAdapter extends Adapter<ProductHolder>{
    private Carrito c;
    private onQuantityChangedListener onQuantityChangedListener;
	
	void setOnQuantityChangedListener(RVAdapter.onQuantityChangedListener onQuantityChangedListener){
		this.onQuantityChangedListener = onQuantityChangedListener;
	}
	
	interface onQuantityChangedListener {
		void onQuantityChanged();
	}

    RVAdapter(Carrito c) {
	    c.clean();
	    this.c = c;
	    onQuantityChangedListener = new onQuantityChangedListener(){
		    @Override public void onQuantityChanged(){}
	    };
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType){
	    View v = LayoutInflater.from(parent.getContext()).inflate(layout.product_card, parent, false);
	    ProductHolder p = new ProductHolder(v);
	    return p;
    }
    
    void remove(int position){
    	c.remove(position);
    	notifyItemRemoved(position);
    }
    
    public void add(Producto p, int cant, int pos){
    	c.add(pos, p, cant);
    	notifyItemInserted(pos);
    }
    
    void changeDataSet(Carrito carrito){
    	c = carrito;
    	notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final RVAdapter.ProductHolder holder, final int position){
	    holder.lblNombre.setText(c.get(position).getNombre());
	    holder.lblMarca.setText(c.get(position).getMarca());
	    int cantidad = c.getCantidad(c.get(position));
	    double precio = c.get(position).getPrecio() * cantidad;
	    holder.lblPrecio.setText(CostFormatter.format(precio));
	    holder.lblCantidad.setText(String.valueOf(cantidad));
	    holder.btnMinusOne.setOnClickListener(new OnClickListener(){
		    @Override
		    public void onClick(View v) {
		    c.setCantidad(c.get(position), c.getCantidad(c.get(position)) - 1);
		    onQuantityChangedListener.onQuantityChanged();
		    onBindViewHolder(holder, position);
	    }});
	    holder.btnPlusOne.setOnClickListener(new OnClickListener(){
		    @Override
		    public void onClick(View v) {
		    c.setCantidad(c.get(position), c.getCantidad(c.get(position)) + 1);
		    onQuantityChangedListener.onQuantityChanged();
		    onBindViewHolder(holder, position);
	    }});
    }

    @Override public int getItemCount(){
	    return c.size();
    }
	
	static class ProductHolder extends ViewHolder {
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