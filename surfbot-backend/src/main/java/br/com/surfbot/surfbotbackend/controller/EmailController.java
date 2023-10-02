package br.com.surfbot.surfbotbackend.controller;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class EmailController {

    @PostMapping("/matricula/{email}")
    public ResponseEntity<Void> sendEmailMatricula(@PathVariable final String email) {
        enviarEmail(email);
        return ResponseEntity.ok().build();
    }

    private void enviarEmail(final String email) {
        final Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        final Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                final PasswordAuthentication passwordAuthentication = super.getPasswordAuthentication();
                return new PasswordAuthentication("surfbot9@gmail.com","");}});

        /** Ativa Debug para sessão */
        session.setDebug(true);
        try {
            final Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            //Remetente

            final Address[] toUser = InternetAddress.parse(email);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Matrícula na Surfbot");
            final String htmlMessage = "<p>teste</p>";
            final Multipart multipart = new MimeMultipart();

            //criando a primeira parte da mensagem
            final MimeBodyPart attachment0 = new MimeBodyPart();
            //configurando o htmlMessage com o mime type
            attachment0.setContent(htmlMessage,"text/html; charset=UTF-8");
            multipart.addBodyPart(attachment0);
            message.setContent(multipart);
            /**Método para enviar a mensagem criada*/
            Transport.send(message);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
