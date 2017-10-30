package melamed.soldeloesteapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

class ProductList implements List<Producto>, Parcelable {
    @SuppressWarnings("unused")
    public static final Creator<ProductList> CREATOR = new Creator<ProductList>(){
        @Override
        public ProductList createFromParcel(Parcel in){
            return new ProductList(in);
        }

        @Override
        public ProductList[] newArray(int size){
            return new ProductList[size];
        }
    };

    /*public Producto getById(int id){
        for(Producto p : list) if(p.getId() == id) return p;
        return null;
    }*/
    private final List<Producto> list;

    ProductList(){
	    this.list = new ArrayList<>();
    }

    ProductList(Parcel in){
        if(in.readByte() == 0x01){
	        this.list = new ArrayList<>();
	        in.readList(this.list, Producto.class.getClassLoader());
        } else {
	        this.list = null;
        }
    }

    Producto getByNombreMarca(String nombre, String marca) {
	    for(Producto p : this.list)
		    if(p.getNombre().equals(nombre) && p.getMarca().equals(marca)) return p;
	    return null;
    }

    List<String> getNombres() {
        List<String> ls = new ArrayList<>();
	    for(Producto p : this.list) ls.add(p.getNombre());
	    Set<String> hs = new HashSet<>();
	    hs.addAll(ls);
        ls.clear();
        ls.addAll(hs);
        return ls;
    }

    List<String> getNombres(String marca) {
        List<String> ls = new ArrayList<>();
	    for(Producto p : this.list) if(p.getMarca().equals(marca)) ls.add(p.getNombre());
	    Set<String> hs = new HashSet<>();
	    hs.addAll(ls);
        ls.clear();
        ls.addAll(hs);
        return ls;
    }

    List<String> getMarcas() {
        List<String> ls = new ArrayList<>();
	    for(Producto p : this.list) ls.add(p.getMarca());
	    Set<String> hs = new HashSet<>();
	    hs.addAll(ls);
        ls.clear();
        ls.addAll(hs);
        return ls;
    }

    List<String> getMarcas(String nombre) {
        List<String> ls = new ArrayList<>();
	    for(Producto p : this.list) if(p.getNombre().equals(nombre)) ls.add(p.getMarca());
	    Set<String> hs = new HashSet<>();
	    hs.addAll(ls);
        ls.clear();
        ls.addAll(hs);
        return ls;
    }

    @Override
    public int size() {
	    return this.list.size();
    }

    @Override
    public boolean isEmpty() {
	    return this.list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
	    return this.list.contains(o);
    }

    @NonNull
    @Override
    public Iterator<Producto> iterator() {
	    return this.list.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
	    return this.list.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
	    return this.list.toArray(a);
    }

    @Override
    public boolean add(Producto producto) {
	    return !this.list.contains(producto) && this.list.add(producto);
    }

    @Override
    public boolean remove(Object o) {
	    return this.list.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
	    return this.list.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends Producto> c) {
	    return this.list.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends Producto> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
	    return this.list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
	    return this.list.retainAll(c);
    }

    @Override
    public void clear() {
	    this.list.clear();
    }

    @Override
    public Producto get(int index) {
	    return this.list.get(index);
    }

    @Override
    public Producto set(int index, Producto element) {
	    return this.list.set(index, element);
    }

    @Override
    public void add(int index, Producto element) {
	    this.list.add(index, element);
    }

    @Override
    public Producto remove(int index) {
	    return this.list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
	    return this.list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
	    return this.list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Producto> listIterator() {
	    return this.list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<Producto> listIterator(int index) {
	    return this.list.listIterator(index);
    }

    @NonNull
    @Override
    public List<Producto> subList(int fromIndex, int toIndex) {
	    return this.list.subList(fromIndex, toIndex);
    }

    @Override
    public int hashCode(){
	    return this.list.hashCode();
    }

    @Override
    public boolean equals(Object o){
	    return o instanceof ProductList && this.list.equals(o);
    }

    @Override
    public int describeContents(){
	    return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
	    if(this.list == null){
		    dest.writeByte((byte) 0x00);
	    } else {
		    dest.writeByte((byte) 0x01);
		    dest.writeList(this.list);
	    }
    }
}