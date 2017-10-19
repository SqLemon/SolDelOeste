package melamed.soldeloesteapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Map;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ProductHolder>{
    Carrito c;
    RVAdapter(Carrito c){
        super();
        this.c = c;
    }

    @Override public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card, parent, false);
        ProductHolder productHolder = new ProductHolder(v);
        return productHolder;
    }

    @Override public void onBindViewHolder(final ProductHolder holder, final int position) {
        holder.lblNombre.setText(c.get(position).getNombre());
        holder.lblMarca.setText(c.get(position).getMarca());
        int cantidad = c.getCantidad(c.get(position));
        double precio = c.get(position).getPrecio() * cantidad;
        holder.lblCantidad.setText(String.valueOf(cantidad));
        holder.lblPrecio.setText(String.valueOf(precio));
        holder.btnMinusOne.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                c.setCantidad(c.get(position), c.getCantidad(c.get(position)) - 1);
                onBindViewHolder(holder, position);
            }
        });
        holder.btnPlusOne.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                c.setCantidad(c.get(position), c.getCantidad(c.get(position)) + 1);
                onBindViewHolder(holder, position);
            }
        });
    }

    @Override public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @Override public int getItemCount() {
        return c.size();
    }


    public static class ProductHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView lblNombre, lblMarca, lblCantidad, lblPrecio;
        ImageButton btnPlusOne, btnMinusOne;

        ProductHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblMarca = (TextView) itemView.findViewById(R.id.lblMarca);
            lblCantidad = (TextView) itemView.findViewById(R.id.lblCantidad);
            lblPrecio = (TextView) itemView.findViewById(R.id.lblPrecio);
            btnMinusOne = (ImageButton) itemView.findViewById(R.id.btnMinusOne);
            btnPlusOne = (ImageButton) itemView.findViewById(R.id.btnPlusOne);
        }
    }
}