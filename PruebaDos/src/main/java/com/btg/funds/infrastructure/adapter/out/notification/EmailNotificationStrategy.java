package com.btg.funds.infrastructure.adapter.out.notification;

import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.PreferenciaNotificacion;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationStrategy implements NotificacionStrategy {
    private final JavaMailSender mailSender;

    public EmailNotificationStrategy(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public PreferenciaNotificacion tipo() {
        return PreferenciaNotificacion.EMAIL;
    }

    @Override
    public void enviar(Cliente cliente, String mensaje) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(cliente.getEmail());
        mailMessage.setSubject("BTG Pactual - Suscripción a fondo");
        mailMessage.setText(mensaje);
        mailSender.send(mailMessage);
    }
}
