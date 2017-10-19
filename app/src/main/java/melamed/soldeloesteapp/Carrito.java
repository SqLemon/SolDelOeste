package melamed.soldeloesteapp;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class Carrito extends ProductList implements Parcelable {
    HashMap<Integer, Integer> carro = new HashMap<>();

    Carrito(){
        super();
    }
    int getCantidad(Producto p){
        return carro.get(p.getId());
    }

    boolean add(Producto p, int cantidad){
        if(super.contains(p)) return false;
        carro.put(p.getId(), cantidad);
        return super.add(p);
    }

    boolean setCantidad(Producto p, int cantidad){
        if(!carro.containsKey(p.getId())) return false;
        carro.put(p.getId(), cantidad);
        return true;
    }

    protected Carrito(Parcel in) {
        super(in);
        carro = in.readHashMap(HashMap.class.getClassLoader());
    }
    @Override public int describeContents() {
        return 0;
    }
    @Override public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeMap(carro);
    }

    @SuppressWarnings("unused") public static final Parcelable.Creator<Carrito> CREATOR = new Parcelable.Creator<Carrito>() {
        @Override public Carrito createFromParcel(Parcel in) {
            return new Carrito(in);
        }
        @Override public Carrito[] newArray(int size) {
            return new Carrito[size];
        }
    };
}
