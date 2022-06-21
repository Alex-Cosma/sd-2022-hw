package com.project.clinic.mail;

import com.project.clinic.appointment.model.dto.AppointmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender emailSender;

    public void sendAppointmentEmail(String username, AppointmentDTO dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("zaraantonia@yahoo.com");
        message.setTo(username + "@gmail.com");
        message.setSubject("Your new appointment!");
        message.setText(composeEmail(username, dto));
        emailSender.send(message);
    }

    public void setEmailSender(JavaMailSender emailSender){
        this.emailSender = emailSender;
    }

    private String composeEmail(String username, AppointmentDTO dto){
        StringBuilder sb = new StringBuilder("");
        sb.append("Hello, " + username + "!\n\n");
        sb.append("You made a new appointment with ");
        sb.append(dto.getDermatologistUsername());
        sb.append(" for the treatment of ");
        sb.append(dto.getTreatmentTitle());
        sb.append(" on ");
        sb.append(dto.getDate());
        sb.append(".\n\nWe are waiting for you!");

        return sb.toString();
    }
}
