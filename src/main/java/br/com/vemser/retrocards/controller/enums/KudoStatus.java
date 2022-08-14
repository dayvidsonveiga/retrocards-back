package br.com.vemser.retrocards.controller.enums;

public enum KudoStatus {

    CREATE("CREATE"),
    IN_PROGRESS("IN_PROGRESS");

    String status;

    KudoStatus(String status) {
        this.status = status;
    }
}
