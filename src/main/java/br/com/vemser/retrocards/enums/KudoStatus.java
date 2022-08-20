package br.com.vemser.retrocards.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KudoStatus {

    CREATE("CREATE"),
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");

    String status;

    KudoStatus(String status) {
        this.status = status;
    }
}
