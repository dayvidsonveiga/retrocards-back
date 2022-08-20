package br.com.vemser.retrocards.schedule;

import br.com.vemser.retrocards.entity.SprintEntity;
import br.com.vemser.retrocards.enums.KudoStatus;
import br.com.vemser.retrocards.enums.RetrospectiveStatus;
import br.com.vemser.retrocards.repository.KudoBoxRepository;
import br.com.vemser.retrocards.repository.RetrospectiveRepository;
import br.com.vemser.retrocards.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class checkEndDate {

    private final SprintRepository sprintRepository;
    private final KudoBoxRepository kudoBoxRepository;
    private final RetrospectiveRepository retrospectiveRepository;


    public void checkEndDateSprint() {
        List<SprintEntity> sprintVerifyList = sprintRepository.findAll();
        LocalDate today = LocalDate.now();

        List<SprintEntity> sprintCheckedList = sprintVerifyList.stream().filter(
                sprintEntity -> sprintEntity.getEndDate().isBefore(today.atStartOfDay())
        ).toList();

        sprintCheckedList.forEach(sprint -> {
            sprint.getKudoboxs().forEach(kudobox -> {
                kudobox.setStatus(KudoStatus.CREATE);
                kudoBoxRepository.save(kudobox);
            });
            sprint.getRetrospectives().forEach(retrospective -> {
                retrospective.setStatus(RetrospectiveStatus.FINISHED);
                retrospectiveRepository.save(retrospective);
            });
            sprintRepository.save(sprint);
        });
    }
}
