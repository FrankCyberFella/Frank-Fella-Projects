package com.frank.server.dao;

import com.frank.server.models.Hotel;

import java.util.List;

public interface HotelDAO {

    List<Hotel> list();

    void create(Hotel hotel);

    Hotel get(int id);

}