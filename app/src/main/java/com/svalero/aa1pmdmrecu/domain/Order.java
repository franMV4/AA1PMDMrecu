package com.svalero.aa1pmdmrecu.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.svalero.aa1pmdmrecu.database.TimestampConverter;

import java.time.LocalDate;

@Entity
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    @TypeConverters({TimestampConverter.class})
    private LocalDate date;
    @ColumnInfo
    private int clientId;
    @ColumnInfo
    private int carId;
    @ColumnInfo
    private String description;

    public Order(int id, LocalDate date, int clientId, int carId, String description) {
        this.id = id;
        this.date = date;
        this.clientId = clientId;
        this.carId = carId;
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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




    @Ignore
    public Order() {
    }



}
