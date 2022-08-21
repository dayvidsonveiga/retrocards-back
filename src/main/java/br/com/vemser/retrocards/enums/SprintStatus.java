package br.com.vemser.retrocards.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SprintStatus {

    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");

    String status;

    SprintStatus(String status) {
        this.status = status;
    }
}
