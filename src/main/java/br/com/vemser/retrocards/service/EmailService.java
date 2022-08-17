package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.email.EmailCreateDTO;
import br.com.vemser.retrocards.dto.email.EmailDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.entity.EmailEntity;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.repository.EmailRepository;
import br.com.vemser.retrocards.repository.RetrospectiveRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    private final ObjectMapper objectMapper;

    private final EmailRepository emailRepository;

    private final RetrospectiveRepository retrospectiveRepository;

    private String message;

    public EmailDTO createEmail(EmailCreateDTO emailCreateDTO, Integer idRetrospective) {
        EmailEntity emailEntity = createToEntity(emailCreateDTO);
        emailEntity = emailRepository.save(emailEntity);
        EmailDTO emailDTO = entityToDTO(emailEntity);

        emailDTO.setRetrospectiveDTO(retrospectiveEntityToDTO(retrospectiveRepository.findById(idRetrospective).get()));
        sendEmailFinishedRetrospective(emailDTO);
        return emailDTO;
    }

    //TODO RESOLVER ESTE MÉTODO AINDA!
    public EmailDTO sendEmailFinishedRetrospective(EmailDTO emailDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject("Relatório de retrospectiva");
            mimeMessageHelper.setText(getContentFromTemplateRetrospective(emailDTO));
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            System.out.println("Erro ao enviar email");
            message = "Erro ao enviar email para o usuário";
        }
        return emailDTO;
    }

    public String getContentFromTemplateRetrospective(EmailDTO emailDTO) throws IOException,TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", emailDTO.getReceiver());
        dados.put("email", from);

        Template template = null;
        template = fmConfiguration.getTemplate("emailFinishedRetrospective-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    public EmailDTO entityToDTO(EmailEntity emailEntity) {
        return objectMapper.convertValue(emailEntity, EmailDTO.class);
    }

    public EmailEntity createToEntity(EmailCreateDTO emailCreateDTO) {
        return objectMapper.convertValue(emailCreateDTO, EmailEntity.class);
    }

    public RetrospectiveDTO retrospectiveEntityToDTO(RetrospectiveEntity retrospectiveEntity) {
        return objectMapper.convertValue(retrospectiveEntity, RetrospectiveDTO.class);
    }

    public String getMessage() {
        return message;
    }
}
