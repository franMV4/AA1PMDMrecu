package com.svalero.aa1pmdmrecu.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class Client {



    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private String surname;
    @ColumnInfo
    private String dni;
    @ColumnInfo
    private boolean retired;
    @ColumnInfo
    private float latitude;
    @ColumnInfo
    private float longitude;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] clientImage;


    public int getId() {
        return id;
    }


    @Ignore
    public Client() {
    }

    public Client(int id, String name, String surname, String dni, boolean retired, float latitude, float longitude, byte[] clientImage) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.retired = retired;
        this.latitude = latitude;
        this.longitude = longitude;
        this.clientImage = clientImage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public boolean isRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public byte[] getClientImage() {
        return clientImage;
    }

    public void setClientImage(byte[] clientImage) {
        this.clientImage = clientImage;
    }




}
