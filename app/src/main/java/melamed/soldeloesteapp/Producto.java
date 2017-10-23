package melamed.soldeloesteapp;

import android.os.Parcel;
import android.os.Parcelable;

class Producto implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Producto> CREATOR = new Parcelable.Creator<Producto>(){
        @Override
        public Producto createFromParcel(Parcel in){
            return new Producto(in);
        }
        
        @Override
        public Producto[] newArray(int size){
            return new Producto[size];
        }
    };
    private final int id;
    private final double precio;
    private final String nombre;
    private final String marca;
    
    Producto(int id, String nombre, String marca, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
    }

    private Producto(Parcel in){
        id = in.readInt();
        precio = in.readDouble();
        nombre = in.readString();
        marca = in.readString();
    }

    int getId() {
        return id;
    }

    String getNombre() {
        return nombre;
    }

    String getMarca() {
        return marca;
    }

    double getPrecio() {
        return precio;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(precio);
        dest.writeString(nombre);
        dest.writeString(marca);
    }
}