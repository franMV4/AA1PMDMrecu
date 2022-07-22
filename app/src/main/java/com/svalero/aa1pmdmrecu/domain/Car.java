package com.svalero.aa1pmdmrecu.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Car {


    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String brand;
    @ColumnInfo
    private String model;
    @ColumnInfo
    private String licensePlate;
    @ColumnInfo
    private int clientId;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] carImage;



    @Ignore
    public Car() {
    }

    @Ignore
    public Car(Car car) {
        this.id = car.getId();
        this.brand = car.getBrand();
        this.model = car.getModel();
        this.licensePlate = car.getLicensePlate();
        this.clientId = car.getClientId();
        this.carImage = car.getCarImage();
    }


    public Car(int id, String brand, String model, String licensePlate, int clientId, byte[] carImage) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.clientId = clientId;
        this.carImage = carImage;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public byte[] getCarImage() {
        return carImage;
    }

    public void setCarImage(byte[] carImage) {
        this.carImage = carImage;
    }



}
