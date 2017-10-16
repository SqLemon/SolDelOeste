package com.melamed.soldeloesteapp;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginClass extends AsyncTask<Void, Void, LoginClass.Result> {
    private Result values;
    private URL url;
    private String user;
    private String pass;
    private String query;
    private onTaskCompletedListener listener;

    public class Result {
        private String user, mail, pass;
        private int tipo;
        Result(String user, String pass, String mail, int tipo){
            this.user = user;
            this.pass = pass;
            this.mail = mail;
        }

        String getUser(){return this.user;}
        String getPass(){return this.pass;}
        String getMail(){return this.mail;}
        int getTipo(){ return this.tipo;}
    }

    void setOnTaskCompletedListener(onTaskCompletedListener listener){ this.listener = listener;}
    public interface onTaskCompletedListener{ void onTaskCompleted(Result result);}


    void login(String user, String pass) throws MalformedURLException{
        this.user = user;
        this.pass = pass;
        query = new Uri.Builder()
                .appendQueryParameter("user", user)
                .appendQueryParameter("pass", pass)
                .build().getEncodedQuery();
        url = new URL("http://soldeloeste.tk/login.php");
        execute();
    }

    @Override protected void onPostExecute(Result v){
        super.onPostExecute(v);
        listener.onTaskCompleted(values);
    }

    @Override protected LoginClass.Result doInBackground(Void... params) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(line +"\n");
            }
            String result = sb.toString();

            JSONObject object = new JSONObject(result);
            values = new Result(object.getString("username"), object.getString("password"), object.getString("email"), object.getInt("tipo"));
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

