package melamed.soldeloesteapp;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class Carrito extends ProductList implements Parcelable {
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Integer> carro = new HashMap<>();

    Carrito() {
        super();
    }

    int getCantidad(Producto p) {
        return carro.get(p.getId());
    }

    boolean add(Producto p, int cantidad) {
        if(!super.add(p)) return false;
        carro.put(p.getId(), cantidad);
        return true;
    }

    void clean(){
        int j = size();
        Iterator<Producto> iterator = iterator();
        while(iterator.hasNext()){
            Producto p = iterator.next();
            for(int i = 0; i < j; i++){
                if(i == indexOf(p)) continue;
                if(get(i).getId() == p.getId()){
                    this.remove(i);
                    j--;
                    iterator = iterator();
                }
            }
        }
    }

    boolean setCantidad(Producto p, int cantidad) {
        if (!carro.containsKey(p.getId())) return false;
        if (cantidad < 1) return false;
        carro.put(p.getId(), cantidad);
        return true;
    }

    private Carrito(Parcel in) {
        super(in);
        carro = in.readHashMap(HashMap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeMap(carro);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Carrito> CREATOR = new Parcelable.Creator<Carrito>() {
        @Override
        public Carrito createFromParcel(Parcel in) {
            return new Carrito(in);
        }

        @Override
        public Carrito[] newArray(int size) {
            return new Carrito[size];
        }
    };
}
