package melamed.soldeloesteapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Activity_checkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        findViewById(R.id.btnCheckOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hay que traer el carrito y mandar el mail. gracias Iakox :)
            }
        });
    }

    void sendMail(Carrito lista, String toEmail, String user) throws UnsupportedEncodingException, MessagingException {
        Properties emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", "587");
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");
        String emailBody = "Usuario: " + user + "\n\n";
        emailBody += "Producto\tMarca\tPrecio Unitario\tPrecio Total\n";
        for(Producto p :  lista){
            emailBody += p.getNombre() + "\t";
            emailBody += p.getMarca() + "\t";
            emailBody += p.getPrecio() + "\t";
            emailBody += lista.getCantidad(p) + "\n";
        }
        Session mailSession = Session.getDefaultInstance(emailProperties, null);
        MimeMessage emailMessage = new MimeMessage(mailSession);
        emailMessage.setFrom(new InternetAddress("ventas.sdo.app.1@gmail.com", "ventas.sdo.app.1@gmail.com"));
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        emailMessage.setSubject("Pedido");
        emailMessage.setText(emailBody);
        Transport transport = mailSession.getTransport("smtp");
        transport.connect("smtp.gmail.com", "ventas.sdo.app.1@gmail.com", "appsdo123");
        transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
        transport.close();
    }


}
