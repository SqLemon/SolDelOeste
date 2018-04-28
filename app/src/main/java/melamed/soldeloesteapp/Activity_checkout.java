package melamed.soldeloesteapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Activity_checkout extends AppCompatActivity {
    public String emailBody = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Intent i = getIntent();
        emailBody = i.getStringExtra("eBody");
        ((TextView) findViewById(R.id.txtVistaPedido)).setText(emailBody);

        findViewById(R.id.btnCheckOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    sendMail(emailBody, "damimelamed@gmail.com");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    void sendMail(String body, String toEmail) throws UnsupportedEncodingException, MessagingException {
        Properties emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

        Session mailSession = Session.getDefaultInstance(emailProperties, null);
        MimeMessage emailMessage = new MimeMessage(mailSession);
        emailMessage.setFrom(new InternetAddress("ventas.sdo.app.1@gmail.com", "ventas.sdo.app.1@gmail.com"));
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        emailMessage.setSubject("Pedido");
        emailMessage.setText(body);
        Transport transport = mailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", "ventas.sdo.app.1@gmail.com", "appsdo123");
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
    }


}
