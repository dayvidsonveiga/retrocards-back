package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.email.EmailCreateDTO;
import br.com.vemser.retrocards.dto.email.EmailDTO;
import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveEmailDTO;
import br.com.vemser.retrocards.entity.EmailEntity;
import br.com.vemser.retrocards.entity.ItemRetrospectiveEntity;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.enums.ItemType;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.EmailRepository;
import br.com.vemser.retrocards.repository.ItemRetrospectiveRepository;
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
import java.time.LocalDate;
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

    private final RetrospectiveService retrospectiveService;

    private final ItemRetrospectiveRepository itemRetrospectiveRepository;

    public String createEmail(EmailCreateDTO emailCreateDTO, Integer idRetrospective) throws NegociationRulesException {
        EmailEntity emailEntity = createToEntity(emailCreateDTO);

        RetrospectiveEmailDTO retrospectiveEmailDTO = retrospectiveEntityToDTO(retrospectiveService.findById(idRetrospective));

        emailEntity.setSubject("[RetroCards - Retrospectiva concluída!] <" +
                retrospectiveEmailDTO.getIdRetrospective() + "> - <" +
                retrospectiveEmailDTO.getTitle() + ">");

        EmailDTO emailDTO = entityToDTO(emailRepository.save(emailEntity));

        emailDTO.setRetrospectiveEmailDTO(retrospectiveEmailDTO);

        sendEmailFinishedRetrospective(emailDTO);

        return "Email enviado com sucesso!";
    }

    public EmailDTO sendEmailFinishedRetrospective(EmailDTO emailDTO) throws NegociationRulesException {

        List<ItemRetrospectiveDTO> itemsWorked = emailDTO.getRetrospectiveEmailDTO().getItemList().stream()
                .filter(itemRetrospectiveDTO -> itemRetrospectiveDTO.getType().equals(ItemType.WORKED.name())).toList();

        List<ItemRetrospectiveDTO> itemsImprove = emailDTO.getRetrospectiveEmailDTO().getItemList().stream()
                .filter(itemRetrospectiveDTO -> itemRetrospectiveDTO.getType().equals(ItemType.IMPROVE.name())).toList();

        List<ItemRetrospectiveDTO> itemsNext = emailDTO.getRetrospectiveEmailDTO().getItemList().stream()
                .filter(itemRetrospectiveDTO -> itemRetrospectiveDTO.getType().equals(ItemType.NEXT.name())).toList();

        MimeMessage mimeMessage = emailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(emailDTO.getReceiver().split(";"));
            mimeMessageHelper.setSubject(emailDTO.getSubject());
            mimeMessageHelper.setText(getContentFromTemplateRetrospective(emailDTO, itemsWorked, itemsImprove, itemsNext), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new NegociationRulesException("Falha ao enviar email");
        }

        return emailDTO;
    }

    public String getContentFromTemplateRetrospective(EmailDTO emailDTO,
                                                      List<ItemRetrospectiveDTO> itemsWorked,
                                                      List<ItemRetrospectiveDTO> itemsImprove,
                                                      List<ItemRetrospectiveDTO> itemsNext) throws IOException, TemplateException {
        Map<String, Object> dados = new HashMap<>();
        dados.put("nome", emailDTO.getReceiver());
        dados.put("email", from);
        dados.put("id", emailDTO.getRetrospectiveEmailDTO().getIdRetrospective());
        dados.put("title", emailDTO.getRetrospectiveEmailDTO().getTitle());
        dados.put("itemsWorked", itemsWorked);
        dados.put("itemsImprove", itemsImprove);
        dados.put("itemsNext", itemsNext);

        Template template = fmConfiguration.getTemplate("finishedRetrospective-template.ftl");

        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    public EmailDTO entityToDTO(EmailEntity emailEntity) {
        return objectMapper.convertValue(emailEntity, EmailDTO.class);
    }

    public EmailEntity createToEntity(EmailCreateDTO emailCreateDTO) {
        String listEmail = String.join(";", emailCreateDTO.getReceiver());

        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setSendDate(LocalDate.now());
        emailEntity.setReceiver(listEmail);

        return emailEntity;
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
}
