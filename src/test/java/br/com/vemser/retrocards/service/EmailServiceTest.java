package br.com.vemser.retrocards.service;

import br.com.vemser.retrocards.dto.ItemRetrospective.ItemRetrospectiveDTO;
import br.com.vemser.retrocards.dto.email.EmailCreateDTO;
import br.com.vemser.retrocards.dto.email.EmailDTO;
import br.com.vemser.retrocards.dto.retrospective.RetrospectiveEmailDTO;
import br.com.vemser.retrocards.entity.EmailEntity;
import br.com.vemser.retrocards.entity.ItemRetrospectiveEntity;
import br.com.vemser.retrocards.entity.RetrospectiveEntity;
import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.ItemType;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.exceptions.NegociationRulesException;
import br.com.vemser.retrocards.repository.EmailRepository;
import br.com.vemser.retrocards.repository.ItemRetrospectiveRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private freemarker.template.Configuration fmConfiguration;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private RetrospectiveService retrospectiveService;

    @Mock
    private ItemRetrospectiveRepository itemRetrospectiveRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(emailService, "objectMapper", objectMapper);
    }

    @Test
    public void shouldTestCreateEmail() throws NegociationRulesException, MessagingException, IOException {
        EmailEntity emailEntity = getEmailEntity();
        EmailCreateDTO emailCreateDTO = getEmailCreateDTO();
        RetrospectiveEntity retrospectiveEntity = getRetrospectiveEntity();
        EmailDTO emailDTO = getEmailDTO();
        mimeMessage.setSubject(emailDTO.getSubject());

        when(retrospectiveService.findById(anyInt())).thenReturn(retrospectiveEntity);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        ReflectionTestUtils.setField(emailService, "from", "test@gmail.com");
        doNothing().when(javaMailSender).send(any(MimeMessage.class));
        when(fmConfiguration.getTemplate(anyString())).thenReturn(new Template("test", "test", new Configuration()));
        when(emailRepository.save(any(EmailEntity.class))).thenReturn(emailEntity);

        String message = emailService.createEmail(emailCreateDTO, 1);

        assertNotNull(message);
        assertEquals("Email enviado com sucesso!", message);
    }

    @Test
    public void shouldTestCreateEmailWithoutSuccess() throws IOException, MessagingException, NegociationRulesException {

        EmailDTO emailDTO = getEmailDTO();

        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        ReflectionTestUtils.setField(emailService, "from", null);

        EmailDTO emailDTO1 = emailService.sendEmailFinishedRetrospective(emailDTO);

        verify(javaMailSender, times(0)).send(any(MimeMessage.class));

        assertNotNull(emailDTO1);
    }

    private static EmailEntity getEmailEntity() {
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setIdEmail(1);
        emailEntity.setSubject("Subject email");
        emailEntity.setReceiver("Dayvidson");
        emailEntity.setSendDate(LocalDate.of(2022, 8, 19));
        return emailEntity;
    }

    private static EmailDTO getEmailDTO() {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setIdEmail(1);

        RetrospectiveEmailDTO retrospectiveEmailDTO = new RetrospectiveEmailDTO();
        retrospectiveEmailDTO.setIdRetrospective(1);
        retrospectiveEmailDTO.setStatus(RetrospectiveStatus.IN_PROGRESS);
        retrospectiveEmailDTO.setTitle("Retrospective email dto title");
        retrospectiveEmailDTO.setOccurredDate(LocalDateTime.of(2022, 8, 25, 19, 18));

        ItemRetrospectiveDTO itemRetrospectiveDTO = new ItemRetrospectiveDTO();
        itemRetrospectiveDTO.setIdRetrospective(1);
        itemRetrospectiveDTO.setIdItemRetrospective(1);
        itemRetrospectiveDTO.setTitle("Title item retrospective");
        itemRetrospectiveDTO.setDescription("Description item retrospective");
        itemRetrospectiveDTO.setType(ItemType.WORKED);

        retrospectiveEmailDTO.setItemList(List.of(itemRetrospectiveDTO));
        emailDTO.setRetrospectiveEmailDTO(retrospectiveEmailDTO);
        emailDTO.setReceiver("Dayvidson");
        emailDTO.setSubject("Subject email dto");
        emailDTO.setSendDate(LocalDate.of(2022, 8, 19));
        return emailDTO;
    }

    private static EmailCreateDTO getEmailCreateDTO() {
        EmailCreateDTO emailCreateDTO = new EmailCreateDTO();
        List<String> receiver = new ArrayList<>();
        receiver.add("Dayvidson");
        emailCreateDTO.setReceiver(receiver);
        return emailCreateDTO;
    }

    private static RetrospectiveEntity getRetrospectiveEntity() {
        RetrospectiveEntity retrospectiveEntity = new RetrospectiveEntity();
        retrospectiveEntity.setIdRetrospective(1);
        retrospectiveEntity.setTitle("test");
        retrospectiveEntity.setStatus(RetrospectiveStatus.IN_PROGRESS);
        retrospectiveEntity.setOccurredDate(LocalDateTime.of(2022, 8, 18, 10, 10, 10));
        SprintEntity sprintEntity = new SprintEntity();

        RetrospectiveEntity retrospective = new RetrospectiveEntity();
        retrospective.setStatus(RetrospectiveStatus.IN_PROGRESS);
        sprintEntity.setRetrospectives(Set.of(retrospective));
        retrospectiveEntity.setSprint(sprintEntity);

        ItemRetrospectiveEntity itemRetrospectiveEntity = new ItemRetrospectiveEntity();
        itemRetrospectiveEntity.setIdItemRetrospective(1);
        itemRetrospectiveEntity.setTitle("Title item retrospective");
        itemRetrospectiveEntity.setDescription("Description item retrospective");
        itemRetrospectiveEntity.setType(ItemType.WORKED);

        retrospectiveEntity.setItems(Set.of(itemRetrospectiveEntity));
        return retrospectiveEntity;
    }

    private static RetrospectiveEmailDTO getRetrospectiveEmailDTO() {
        RetrospectiveEmailDTO retrospectiveEmailDTO = new RetrospectiveEmailDTO();
        retrospectiveEmailDTO.setIdRetrospective(1);
        retrospectiveEmailDTO.setTitle("Title retrospective email");
        retrospectiveEmailDTO.setOccurredDate(LocalDateTime.of(2022, 8, 19, 20, 30));

        ItemRetrospectiveDTO itemRetrospectiveDTO = new ItemRetrospectiveDTO();
        itemRetrospectiveDTO.setIdRetrospective(1);
        itemRetrospectiveDTO.setIdItemRetrospective(1);
        itemRetrospectiveDTO.setTitle("Title item retrospective");
        itemRetrospectiveDTO.setType(ItemType.WORKED);
        itemRetrospectiveDTO.setDescription("Description item retrospective");

        retrospectiveEmailDTO.setItemList(List.of(itemRetrospectiveDTO));
        return retrospectiveEmailDTO;
    }

    private static ItemRetrospectiveDTO getItemRetrospectiveDTO() {
        ItemRetrospectiveDTO itemRetrospectiveDTO = new ItemRetrospectiveDTO();
        itemRetrospectiveDTO.setIdItemRetrospective(1);
        itemRetrospectiveDTO.setIdRetrospective(1);
        itemRetrospectiveDTO.setTitle("Title item retrospective");
        itemRetrospectiveDTO.setDescription("Description item retrospective");
        itemRetrospectiveDTO.setType(ItemType.WORKED);
        return itemRetrospectiveDTO;
    }
}
