package com.melamed.soldeloesteapp;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Producto {
    private int id;
    private double precio;
    private String nombre, marca;

    Producto(int id, String nombre, String marca, double precio){
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
    }

    int getId(){
        return id;
    }
    String getNombre(){
        return nombre;
    }
    String getMarca(){
        return marca;
    }
    double getPrecio(){
        return precio;
    }
}