package utils;

import entities.*;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    // Use your Gmail account and App Password
    private static final String FROM_EMAIL = "rtakouit7@gmail.com";
    // Generate a new App Password from Google Account settings and put it here
    private static final String APP_PASSWORD = "ggux vtfc sdps slxp"; 

    public static void sendReservationConfirmation(Reservation reservation) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));

            // Set recipient
            String clientEmail = reservation.getClients().get(0).getEmail();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(clientEmail));
            
            // Set email subject
            message.setSubject("Confirmation de réservation - Hôtel");

            // Create email content with better formatting
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            StringBuilder content = new StringBuilder();
            content.append("Bonjour ").append(reservation.getClients().get(0).getNom()).append(",\n\n");
            content.append("Votre réservation a été confirmée avec succès.\n\n");
            content.append("Détails de la réservation :\n");
            content.append("--------------------------------\n");
            content.append("Numéro de réservation : ").append(reservation.getId()).append("\n");
            content.append("Date d'arrivée : ").append(sdf.format(reservation.getDateDebut())).append("\n");
            content.append("Date de départ : ").append(sdf.format(reservation.getDateFin())).append("\n\n");
            
            content.append("Chambre(s) réservée(s) :\n");
            for (Chambre chambre : reservation.getChambres()) {
                content.append("- Chambre n°").append(chambre.getnumChambre())
                       .append(" (").append(chambre.getCategorie()).append(")\n")
                       .append("  Prix : ").append(chambre.getPrix()).append(" €\n");
            }
            
            content.append("\nNous vous remercions de votre confiance et vous souhaitons un excellent séjour !\n\n");
            content.append("Cordialement,\n");
            content.append("L'équipe de l'hôtel");

            message.setText(content.toString());

            // Send message
            Transport.send(message);
            System.out.println("Email sent successfully to: " + clientEmail);
            
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
