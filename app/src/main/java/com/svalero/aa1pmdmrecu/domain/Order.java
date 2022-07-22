package com.svalero.aa1pmdmrecu.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private int clientId;
    @ColumnInfo
    private int bikeId;
    @ColumnInfo
    private String description;

    public Order(int id, int clientId, int bikeId, String description) {
        this.id = id;
        this.clientId = clientId;
        this.bikeId = bikeId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getBikeId() {
        return bikeId;
    }

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
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
