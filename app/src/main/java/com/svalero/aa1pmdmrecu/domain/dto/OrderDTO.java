package com.svalero.aa1pmdmrecu.domain.dto;

import androidx.room.Ignore;

public class OrderDTO implements Comparable {


    private int id;
    private String clientNameSurname;  // Client
    private String carBrandModel;    // Client
    private String carLicensePlate;    // Car
    private byte[] carImageOrder;   // Car
    private String description;     //Order


    public OrderDTO(int id, String clientNameSurname, String carBrandModel, String carLicensePlate, byte[] carImageOrder, String description) {
        this.id = id;
        this.clientNameSurname = clientNameSurname;
        this.carBrandModel = carBrandModel;
        this.carLicensePlate = carLicensePlate;
        this.carImageOrder = carImageOrder;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientNameSurname() {
        return clientNameSurname;
    }

    public void setClientNameSurname(String clientNameSurname) {
        this.clientNameSurname = clientNameSurname;
    }

    public String getCarBrandModel() {
        return carBrandModel;
    }

    public void setCarBrandModel(String carBrandModel) {
        this.carBrandModel = carBrandModel;
    }

    public void setCarLicensePlate(String carLicensePlate) {
        this.carLicensePlate = carLicensePlate;
    }

    public byte[] getCarImageOrder() {
        return carImageOrder;
    }

    public void setCarImageOrder(byte[] carImageOrder) {
        this.carImageOrder = carImageOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    @Ignore
    public OrderDTO() {
    }

    public String getCarLicensePlate() {
        return carLicensePlate;
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }



}
