package melamed.soldeloesteapp;

import android.annotation.SuppressLint;
import android.os.Parcel;

import java.util.HashMap;
import java.util.Iterator;

class Carrito extends ProductList{
    @SuppressWarnings("unused")
    public static final Creator<Carrito> CREATOR = new Creator<Carrito>(){
        @Override
        public Carrito createFromParcel(Parcel in){
            return new Carrito(in);
        }

        @Override
        public Carrito[] newArray(int size){
            return new Carrito[size];
        }
    };
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer, Integer> carro = new HashMap<>();

    Carrito() {}

    private Carrito(Parcel in){
        super(in);
        this.carro = in.readHashMap(HashMap.class.getClassLoader());
    }
    
    double subTotal(){
    	double subtotal = 0;
    	for(Producto p : this) subtotal += p.getPrecio() * getCantidad(p);
    	return subtotal;
    }

    int getCantidad(Producto p) {
        return this.carro.get(p.getId());
    }

    boolean add(Producto p, int cantidad) {
        if(!add(p)) return false;
        this.carro.put(p.getId(), cantidad);
        return true;
    }

    void clean(){
        int j = this.size();
        Iterator<Producto> iterator = this.iterator();
        while(iterator.hasNext()){
            Producto p = iterator.next();
            for(int i = 0; i < j; i++){
                if(i == this.indexOf(p)) continue;
                if(this.get(i).getId() == p.getId()){
                    remove(i);
                    j--;
                    iterator = this.iterator();
                }
            }
        }
    }
    
    void setCantidad(Producto p, int cantidad){
        if(!this.carro.containsKey(p.getId())) return;
        if(cantidad < 1) return;
        this.carro.put(p.getId(), cantidad);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeMap(this.carro);
    }
}
