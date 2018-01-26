package melamed.soldeloesteapp;

import android.os.Parcel;
import android.os.Parcelable;

class Producto implements Parcelable {
    @SuppressWarnings("unused")
    public static final Creator<Producto> CREATOR = new Creator<Producto>(){
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
	    this.id = in.readInt();
	    this.precio = in.readDouble();
	    this.nombre = in.readString();
	    this.marca = in.readString();
    }

    int getId() {
	    return this.id;
    }

    String getNombre() {
	    return this.nombre;
    }

    String getMarca() {
	    return this.marca;
    }

    double getPrecio() {
	    return this.precio;
    }

    @Override
    public int describeContents() {
	    return this.hashCode();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
	    dest.writeInt(this.id);
	    dest.writeDouble(this.precio);
	    dest.writeString(this.nombre);
	    dest.writeString(this.marca);
    }
}