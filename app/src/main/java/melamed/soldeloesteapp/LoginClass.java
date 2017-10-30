package melamed.soldeloesteapp;

import android.net.Uri.Builder;
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

import melamed.soldeloesteapp.LoginClass.Result;

class LoginClass extends AsyncTask<Void, Void, Result>{
	private LoginClass.Result values;
	private URL url;
	private String query;
	private LoginClass.onTaskCompletedListener listener;
	
	void setOnTaskCompletedListener(LoginClass.onTaskCompletedListener listener){
		this.listener = listener;
	}

    void login(String user, String pass) throws MalformedURLException {
	    this.query = new Builder()
		    .appendQueryParameter("user", user)
		    .appendQueryParameter("pass", pass)
                .build().getEncodedQuery();
	    this.url = new URL("http://soldeloeste.tk/login.php");
	    this.execute();
    }

    @Override
    protected LoginClass.Result doInBackground(Void... params) {
        try {
	        HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
	        conn.setReadTimeout(10000);
	        conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
	        writer.write(this.query);
	        writer.flush();
	        writer.close();
            os.close();


            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();

            JSONObject object = new JSONObject(result);
	        this.values = new LoginClass.Result(object.getString("username"), object.getString("password"), object.getString("email"), object.getInt("tipo"));
	        return null;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
	    this.values = null;
	    return null;
    }

    @Override
    protected void onPostExecute(LoginClass.Result v){
        super.onPostExecute(v);
	    this.listener.onTaskCompleted(this.values);
    }

    interface onTaskCompletedListener{
	    void onTaskCompleted(LoginClass.Result result);
    }

    class Result{
        private final String user;
        private final String mail;
        private final String pass;
        private final int tipo;

        Result(String user, String pass, String mail, int tipo){
            this.user = user;
            this.pass = pass;
            this.mail = mail;
            this.tipo = tipo;
        }

        String getUser(){
	        return user;
        }

        String getPass(){
	        return pass;
        }

        String getMail(){
	        return mail;
        }

        int getTipo(){
	        return tipo;
        }
    }
}

