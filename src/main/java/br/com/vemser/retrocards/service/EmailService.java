//package br.com.vemser.retrocards.service;
//
//import br.com.vemser.retrocards.dto.user.UserDTO;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//@RequiredArgsConstructor
//public class EmailService {
//
//    private final freemarker.template.Configuration fmConfiguration;
//
//    @Value("${spring.mail.username}")
//    private String from;
//
//    private final JavaMailSender emailSender;
//
//    private String message;
//
//    public void sendEmailFinishedRetrospective(UserDTO userDTO) {
//        MimeMessage mimeMessage = emailSender.createMimeMessage();
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setFrom(from);
//            mimeMessageHelper.setTo(userDTO.getEmail());
//            mimeMessageHelper.setSubject("Retrospectiva concluída com sucesso");
//            mimeMessageHelper.setText(getContentFromTemplateRetrospective(userDTO));
//            emailSender.send(mimeMessageHelper.getMimeMessage());
//        } catch (MessagingException | IOException | TemplateException e) {
//            System.out.println("Erro ao enviar email");
//            message = "Erro ao enviar email para o usuário";
//        }
//    }
//
//    public String getContentFromTemplateRetrospective(UserDTO userDTO) throws IOException,TemplateException {
//        Map<String, Object> dados = new HashMap<>();
//        dados.put("nome", userDTO.getName());
//        dados.put("email", from);
//
//        Template template = null;
//        template = fmConfiguration.getTemplate("emailFinishedRetrospective-template.ftl");
//        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
//        return html;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//}
