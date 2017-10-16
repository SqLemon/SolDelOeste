package com.melamed.soldeloesteapp;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GetProductosClass extends AsyncTask<Void, Void, ProductList> {
    private URL url;
    private onTaskCompletedListener listener;
    void setOnTaskCompletedListener(onTaskCompletedListener listener){ this.listener = listener;}
    public interface onTaskCompletedListener{ void onTaskCompleted(ProductList result);}

    //TODO: SI QUEREMOS ENVIAR UN PASS DE CONFIRMACION (post versión beta) descomentar todo lo comentado
    //private static final String pass = whatever; private String query;

    void getProductos() throws MalformedURLException {
        //query = new Uri.Builder().appendQueryParameter("pass", pass).build().getEncodedQuery();
        url = new URL("http://soldeloeste.tk/getProductos.php");
        execute();
    }

    @Override protected void onPostExecute(ProductList result){
        super.onPostExecute(result);
        listener.onTaskCompleted(result);
    }

    @Override protected ProductList doInBackground(Void... params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setDoInput(true);
            //conn.setDoOutput(true);
            //conn.setRequestMethod("POST");
            conn.connect();
            /*OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();*/
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) sb.append(line + "\n");

            ProductList result = new ProductList();
            JSONArray arr = new JSONArray(sb.toString());
            for(int i = 0; i < arr.length(); i++) {
                JSONObject object = arr.getJSONObject(i);
                int id = object.getInt("id");
                String nombre = object.getString("nombre");
                String marca = object.getString("marca");
                Double precio = object.getDouble("precio");
                Producto p = new Producto(id, nombre, marca, precio);
                result.add(p);
            }
            return result;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}