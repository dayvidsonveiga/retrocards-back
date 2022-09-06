package br.com.vemser.retrocards.util;

import br.com.vemser.retrocards.exceptions.NegotiationRulesException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class CheckDate {

    public void checkDateIsValid(LocalDateTime sprintEndDate, LocalDate checkDate) throws NegotiationRulesException {
        if (checkDate.isAfter(sprintEndDate.toLocalDate())) {
            throw new NegotiationRulesException("A data deve ser inferior a data de encerramento da sprint.");
        }
    }
}
