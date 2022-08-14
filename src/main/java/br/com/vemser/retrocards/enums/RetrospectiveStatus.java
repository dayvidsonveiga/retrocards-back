package br.com.vemser.retrocards.enums;

public enum RetrospectiveStatus {

    FINISHED("FINISHED"),
    IN_PROGRESS("IN_PROGRESS");

    String status;

    RetrospectiveStatus(String status) {
        this.status = status;
    }
}
