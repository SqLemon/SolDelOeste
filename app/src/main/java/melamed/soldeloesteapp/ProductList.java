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
    private List<Producto> list;

    /*public Producto getById(int id){
        for(Producto p : list) if(p.getId() == id) return p;
        return null;
    }*/

    Producto getByNombreMarca(String nombre, String marca) {
        for (Producto p : list)
            if (p.getNombre().equals(nombre) && p.getMarca().equals(marca)) return p;
        return null;
    }

    List<String> getNombres() {
        List<String> ls = new ArrayList<>();
        for (Producto p : list) ls.add(p.getNombre());
        Set<String> hs = new HashSet<>();
        hs.addAll(ls);
        ls.clear();
        ls.addAll(hs);
        return ls;
    }

    List<String> getNombres(String marca) {
        List<String> ls = new ArrayList<>();
        for (Producto p : list) if (p.getMarca().equals(marca)) ls.add(p.getNombre());
        Set<String> hs = new HashSet<>();
        hs.addAll(ls);
        ls.clear();
        ls.addAll(hs);
        return ls;
    }

    List<String> getMarcas() {
        List<String> ls = new ArrayList<>();
        for (Producto p : list) ls.add(p.getMarca());
        Set<String> hs = new HashSet<>();
        hs.addAll(ls);
        ls.clear();
        ls.addAll(hs);
        return ls;
    }

    List<String> getMarcas(String nombre) {
        List<String> ls = new ArrayList<>();
        for (Producto p : list) if (p.getNombre().equals(nombre)) ls.add(p.getMarca());
        Set<String> hs = new HashSet<>();
        hs.addAll(ls);
        ls.clear();
        ls.addAll(hs);
        return ls;
    }

    ProductList() {
        list = new ArrayList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @NonNull
    @Override
    public Iterator<Producto> iterator() {
        return list.iterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(Producto producto) {
        if(list.contains(producto)) return false;
        return list.add(producto);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends Producto> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NonNull Collection<? extends Producto> c) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public Producto get(int index) {
        return list.get(index);
    }

    @Override
    public Producto set(int index, Producto element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, Producto element) {
        list.add(index, element);
    }

    @Override
    public Producto remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Producto> listIterator() {
        return list.listIterator();
    }

    @NonNull
    @Override
    public ListIterator<Producto> listIterator(int index) {
        return list.listIterator(index);
    }

    @NonNull
    @Override
    public List<Producto> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    ProductList(Parcel in) {
        if (in.readByte() == 0x01) {
            list = new ArrayList<>();
            in.readList(list, Producto.class.getClassLoader());
        } else {
            list = null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (list == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(list);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ProductList> CREATOR = new Parcelable.Creator<ProductList>() {
        @Override
        public ProductList createFromParcel(Parcel in) {
            return new ProductList(in);
        }

        @Override
        public ProductList[] newArray(int size) {
            return new ProductList[size];
        }
    };
}