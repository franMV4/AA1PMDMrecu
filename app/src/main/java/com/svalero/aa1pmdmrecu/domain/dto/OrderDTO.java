package com.svalero.aa1pmdmrecu.domain.dto;

import androidx.room.Ignore;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class OrderDTO implements Comparable {


    private int id;
    private LocalDate date;
    private String clientNameSurname;  // Client
    private String carBrandModel;    // Client
    private String carLicensePlate;    // Car
    private byte[] carImageOrder;   // Car
    private String description;     //Order


    public OrderDTO(int id, LocalDate date, String clientNameSurname, String carBrandModel, String carLicensePlate, byte[] carImageOrder, String description) {
        this.id = id;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String getCarLicensePlate() {
        return carLicensePlate;
    }


    @Ignore
    public OrderDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return id == orderDTO.id && date.equals(orderDTO.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }


    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", date=" + date +
                ", clientNameSurname='" + clientNameSurname + '\'' +
                ", carBrandModel='" + carBrandModel + '\'' +
                ", description='" + description + '\'' +
                ", carImageOrder=" + Arrays.toString(carImageOrder) +
                '}';
    }



    @Override
    public int compareTo(Object o) {
        return 0;
    }



}
