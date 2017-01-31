package com.mavpokit.rseriesalarm.data.model;

/**
 * Created by Alex on 24.01.2017.
 */

public class AlarmObject {
    private String id;
    private String name;
    private String number;
    private String code;

    public AlarmObject(String id, String name, String number, String code) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.code = code;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
