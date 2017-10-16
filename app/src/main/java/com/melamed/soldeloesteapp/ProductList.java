package com.melamed.soldeloesteapp;

import android.support.annotation.NonNull;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ProductList implements List<Producto> {
    private List<Producto> list;

    public Producto getById(int id){
        for(Producto p : list) if(p.getId() == id) return p;
        return null;
    }

    ProductList(){
        list.clear();
    }
    @Override public int size() {
        return list.size();
    }
    @Override public boolean isEmpty() {
        return list.isEmpty();
    }
    @Override public boolean contains(Object o) {
        return list.contains(o);
    }
    @NonNull @Override public Iterator<Producto> iterator() {
        return list.iterator();
    }
    @NonNull @Override public Object[] toArray() {
        return list.toArray();
    }
    @NonNull @Override public <T> T[] toArray(@NonNull T[] a) {
        return list.toArray(a);
    }
    @Override public boolean add(Producto producto) {
        return list.add(producto);
    }
    @Override public boolean remove(Object o) {
        return list.remove(o);
    }
    @Override public boolean containsAll(@NonNull Collection<?> c) {
        return list.containsAll(c);
    }
    @Override public boolean addAll(@NonNull Collection<? extends Producto> c) {
        return list.addAll(c);
    }
    @Override public boolean addAll(int index, @NonNull Collection<? extends Producto> c) {
        return false;
    }
    @Override public boolean removeAll(@NonNull Collection<?> c) {
        return list.removeAll(c);
    }
    @Override public boolean retainAll(@NonNull Collection<?> c) {
        return retainAll(c);
    }
    @Override public void clear() {
        list.clear();
    }
    @Override public boolean equals(Object o) {
        return list.equals(o);
    }
    @Override public int hashCode() {
        return list.hashCode();
    }
    @Override public Producto get(int index) {
        return list.get(index);
    }
    @Override public Producto set(int index, Producto element) {
        return list.set(index, element);
    }
    @Override public void add(int index, Producto element) {
        list.add(index, element);
    }
    @Override public Producto remove(int index) {
        return list.remove(index);
    }
    @Override public int indexOf(Object o) {
        return list.indexOf(o);
    }
    @Override public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }
    @Override public ListIterator<Producto> listIterator() {
        return list.listIterator();
    }
    @NonNull @Override public ListIterator<Producto> listIterator(int index) {
        return list.listIterator(index);
    }
    @NonNull @Override public List<Producto> subList(int fromIndex, int toIndex) {
        return subList(fromIndex, toIndex);
    }
}