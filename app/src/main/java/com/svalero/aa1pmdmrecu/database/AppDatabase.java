package com.svalero.aa1pmdmrecu.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.aa1pmdmrecu.dao.CarDao;
import com.svalero.aa1pmdmrecu.dao.ClientDao;
import com.svalero.aa1pmdmrecu.dao.OrderDao;
import com.svalero.aa1pmdmrecu.domain.Car;
import com.svalero.aa1pmdmrecu.domain.Client;
import com.svalero.aa1pmdmrecu.domain.Order;


@Database(entities = {Car.class, Client.class, Order.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {


    public abstract CarDao carDao();

    public abstract ClientDao clientDao();

    public abstract OrderDao orderDao();


}
