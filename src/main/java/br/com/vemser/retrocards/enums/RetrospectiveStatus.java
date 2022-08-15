package br.com.vemser.retrocards.enums;

public enum RetrospectiveStatus {

    CREATE("CREATE"),
    IN_PROGRESS("IN_PROGRESS"),
    FINISHED("FINISHED");

    String status;

    RetrospectiveStatus(String status) {
        this.status = status;
    }
}
