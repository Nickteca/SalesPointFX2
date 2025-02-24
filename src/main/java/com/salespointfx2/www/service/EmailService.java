package com.salespointfx2.www.service;

import java.io.File;
import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Service
public class EmailService {

	private final String username = "elreydelpollosistemas@gmail.com"; // Tu correo de Gmail
	private final String password = "zocd swzp pwdz ewrk"; // La contraseña de aplicación de Gmail (si tienes 2FA activado)

	public void sendEmail(String to, String subject, String text, String attachmentPath) throws MessagingException {
		try {

			// Configuración de las propiedades SMTP
			Properties properties = new Properties();
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587"); // Usamos el puerto 587 para STARTTLS
			properties.put("mail.smtp.auth", "true"); // Habilitamos la autenticación SMTP
			properties.put("mail.smtp.starttls.enable", "true"); // Habilitamos STARTTLS

			// Autenticación
			Session session = Session.getInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			// Crear el mensaje
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);

			// Crear el contenido del mensaje
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(text, "utf-8");

			// Crear el adjunto (archivo PDF)
			MimeBodyPart attachmentPart = new MimeBodyPart();
			FileDataSource source = new FileDataSource(new File(attachmentPath));
			attachmentPart.setDataHandler(new DataHandler(source));
			attachmentPart.setFileName(new File(attachmentPath).getName());

			// Crear el contenedor del mensaje
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(textPart);
			multipart.addBodyPart(attachmentPart);

			// Establecer el contenido del mensaje
			message.setContent(multipart);

			// Enviar el correo
			Transport.send(message);
			System.out.println("Correo enviado exitosamente!");

			/*
			 * MimeMessage message = mailSender.createMimeMessage(); MimeMessageHelper
			 * helper = new MimeMessageHelper(message, true);
			 * 
			 * // Configura el remitente, destinatario, asunto y cuerpo del mensaje
			 * helper.setFrom("elreydelpollosistemas@gmail.com"); helper.setTo(to);
			 * helper.setSubject(subject); helper.setText(text, true); // El segundo
			 * parámetro "true" indica que el contenido es HTML // Adjuntar el archivo PDF
			 * File file = new File(attachmentPath); helper.addAttachment(file.getName(),
			 * file);
			 * 
			 * // Envía el correo mailSender.send(message);
			 */
		} catch (Exception e) {
			Alert infoAlert = new Alert(AlertType.ERROR);
			infoAlert.setTitle("error con el mail");
			infoAlert.setHeaderText("parece que no se envio el mail");
			infoAlert.setContentText(e.getMessage() + " " + e.getCause());
			infoAlert.showAndWait();
		}
	}
}
