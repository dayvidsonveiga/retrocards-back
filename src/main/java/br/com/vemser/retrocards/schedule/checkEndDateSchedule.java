package br.com.vemser.retrocards.schedule;

import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.repository.KudoBoxRepository;
import br.com.vemser.retrocards.repository.RetrospectiveRepository;
import br.com.vemser.retrocards.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class checkEndDateSchedule {

    private final SprintRepository sprintRepository;
    private final KudoBoxRepository kudoBoxRepository;
    private final RetrospectiveRepository retrospectiveRepository;


    //  Busca todas sprints do banco e caso o dia atual seja posterior a data de encerramento da sprint seta as retrospectivas e kudo box  para finalizado
    @Scheduled(cron = "0 1 00 * * *")
    @Transactional
    public void checkEndDateSprint() {
        LocalDate today = LocalDate.now();

        List<SprintEntity> sprintCheckedList = sprintRepository.findAll().stream().filter(
                sprintEntity -> sprintEntity.getEndDate().isBefore(today.atStartOfDay())
        ).toList();

        sprintCheckedList.forEach(sprint -> {
            sprint.getKudoboxs().forEach(kudobox -> {
                kudobox.setStatus(KudoStatus.FINISHED);
                kudoBoxRepository.save(kudobox);
            });
            sprint.getRetrospectives().forEach(retrospective -> {
                retrospective.setStatus(RetrospectiveStatus.FINISHED);
                retrospectiveRepository.save(retrospective);
            });
        });
    }
}
