package melamed.soldeloesteapp;

import android.net.Uri;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class getProductosClass extends AsyncTask<Void, Void, getProductosClass.Result> {

    private Result values;
    private URL url;
    //private String query;
    private onTaskCompletedListener listener;

    public class Result {
        private String nombre, marca;
        private int id;
        private Double precio;
        Result(int id, String nombre, String marca, Double precio){
            this.id = id;
            this.nombre = nombre;
            this.marca = marca;
            this.precio = precio;
        }

        int getId(){return this.id;}
        String getNombre(){return this.nombre;}
        String getMarca(){return this.marca;}
        Double getPrecio(){ return this.precio;}
    }

    void setOnTaskCompletedListener(onTaskCompletedListener listener){ this.listener = listener;}
    public interface onTaskCompletedListener{ void onTaskCompleted(Result result);}


    void getProductos() throws MalformedURLException {
        //SI QUEREMOS ENVIAR UN PASS DE CONFIRMACION (post versión beta) descomentar.
        //Uri.Builder builder = new Uri.Builder();
                        //.appendQueryParameter("pass", pass);
        //query = builder.build().getEncodedQuery();
        url = new URL("http://soldeloeste.tk/getProductos.php");
        //tipo = LOGIN;
        execute();
    }

    @Override protected void onPostExecute(Result v){
        super.onPostExecute(v);
        listener.onTaskCompleted(values);
    }

    @Override protected getProductosClass.Result doInBackground(Void... params) {
        try {
            //SI QUEREMOS ENVIAR UN PASS DE CONFIRMACION (post versión beta) descomentar.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //conn.setDoOutput(true);
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
            while ((line = reader.readLine()) != null){
                sb.append(line +"\n");
            }
            String result = sb.toString();

            JSONObject object = new JSONObject(result);
            //values = new Result(object.getInt("id"), object.getString("nombre"), object.getString("marca"), object.getDouble("precio"));

            return null;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        values = null;
        return null;
    }
}