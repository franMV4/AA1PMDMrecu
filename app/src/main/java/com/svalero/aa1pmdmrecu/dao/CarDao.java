package com.svalero.aa1pmdmrecu.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.aa1pmdmrecu.domain.Car;


import java.util.List;

@Dao
public interface CarDao {



    @Query("SELECT * FROM `car`")
    List<Car> getAll();

    @Insert
    void insert(Car car);

    @Update
    void update(Car car);

    @Delete
    void delete(Car car);
}
