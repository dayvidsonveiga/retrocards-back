package br.com.vemser.retrocards.controller.enums;

public enum ItemType {

    WORKED("WORKED"),
    IMPROVE("IMPROVE"),
    NEXT("NEXT");

    String type;

    ItemType(String type) {
        this.type = type;
    }
}
