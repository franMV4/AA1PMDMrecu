package com.svalero.aa1pmdmrecu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.aa1pmdmrecu.domain.Order;


import java.util.List;

@Dao
public interface OrderDao {

    @Query("SELECT * FROM `order`")
    List<Order> getAll();


    @Insert
    void insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);

}
