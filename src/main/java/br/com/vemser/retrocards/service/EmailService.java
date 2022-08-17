package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.email.EmailCreateDTO;
import br.com.vemser.retrocards.dto.email.EmailDTO;
import br.com.vemser.retrocards.dto.page.ItemRetrospective.ItemRetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveEmailDTO;
import br.com.vemser.retrocards.entity.EmailEntity;
import br.com.vemser.retrocards.entity.ItemRetrospectiveEntity;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.enums.ItemType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.EmailRepository;
import br.com.vemser.retrocards.repository.ItemRetrospectiveRepository;
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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

    private final ItemRetrospectiveRepository itemRetrospectiveRepository;

    private String message;

    public EmailDTO createEmail(EmailCreateDTO emailCreateDTO, Integer idRetrospective) throws MessagingException, TemplateException, IOException {
        EmailEntity emailEntity = createToEntity(emailCreateDTO);
        emailEntity = emailRepository.save(emailEntity);
        EmailDTO emailDTO = entityToDTO(emailEntity);

        emailDTO.setRetrospectiveEmailDTO(retrospectiveEntityToDTO(retrospectiveRepository.findById(idRetrospective).get()));
        sendEmailFinishedRetrospective(emailDTO);
        return emailDTO;
    }

    //TODO RESOLVER ESTE MÉTODO AINDA!
    public EmailDTO sendEmailFinishedRetrospective(EmailDTO emailDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();

        List<ItemRetrospectiveDTO> itemsWorked = emailDTO.getRetrospectiveEmailDTO().getItemList().stream()
                .filter(itemRetrospectiveDTO -> itemRetrospectiveDTO.getType().equals(ItemType.WORKED)).toList();

        List<ItemRetrospectiveDTO> itemsImprove = emailDTO.getRetrospectiveEmailDTO().getItemList().stream()
                .filter(itemRetrospectiveDTO -> itemRetrospectiveDTO.getType().equals(ItemType.IMPROVE)).toList();

        List<ItemRetrospectiveDTO> itemsNext = emailDTO.getRetrospectiveEmailDTO().getItemList().stream()
                .filter(itemRetrospectiveDTO -> itemRetrospectiveDTO.getType().equals(ItemType.NEXT)).toList();

        emailDTO.getReceiver().forEach(receiver -> {
            try {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setSubject(emailDTO.getSubject());
                mimeMessageHelper.setText(getContentFromTemplateRetrospective(emailDTO, itemsWorked, itemsImprove, itemsNext));
                mimeMessageHelper.setTo((InternetAddress) emailDTO.getReceiver());
                emailSender.send(mimeMessageHelper.getMimeMessage());
            } catch (MessagingException | IOException | TemplateException e) {
                e.printStackTrace();
            }
        });
        return emailDTO;
    }

    public String getContentFromTemplateRetrospective(EmailDTO emailDTO,
                                                      List<ItemRetrospectiveDTO> itemsWorked,
                                                      List<ItemRetrospectiveDTO> itemsImprove,
                                                      List<ItemRetrospectiveDTO> itemsNext) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", emailDTO.getReceiver());
        dados.put("email", from);
        dados.put("itemsWorked", itemsWorked);
        dados.put("itemsImprove", itemsImprove);
        dados.put("itemsNext", itemsNext);

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

    public RetrospectiveEmailDTO retrospectiveEntityToDTO(RetrospectiveEntity retrospectiveEntity) {
        RetrospectiveEmailDTO retrospectiveEmailDTO = objectMapper.convertValue(retrospectiveEntity, RetrospectiveEmailDTO.class);

        List<ItemRetrospectiveEntity> listEntiTy = itemRetrospectiveRepository.findAllByRetrospective_IdRetrospective(retrospectiveEmailDTO.getIdRetrospective());

        retrospectiveEmailDTO.setItemList(listEntiTy.stream()
                .map(this::itemRetrospectiveEntityToDTO)
                .toList());

        return retrospectiveEmailDTO;
    }

    public ItemRetrospectiveDTO itemRetrospectiveEntityToDTO(ItemRetrospectiveEntity itemRetrospectiveEntity) {
        return objectMapper.convertValue(itemRetrospectiveEntity, ItemRetrospectiveDTO.class);
    }

    public String getMessage() {
        return message;
    }
}
