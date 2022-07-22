package com.svalero.aa1pmdmrecu.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.aa1pmdmrecu.domain.Client;


import java.util.List;

@Dao
public interface ClientDao {

    @Query("SELECT * FROM `client`")
    List<Client> getAll();

    @Insert
    void insert(Client client);

    @Update
    void update(Client client);

    @Delete
    void delete(Client client);

}
