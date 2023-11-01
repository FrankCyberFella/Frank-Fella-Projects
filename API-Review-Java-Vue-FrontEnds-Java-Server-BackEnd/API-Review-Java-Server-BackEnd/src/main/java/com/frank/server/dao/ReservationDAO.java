package com.frank.server.dao;

import java.util.List;

import com.frank.server.exception.HotelNotFoundException;
import com.frank.server.exception.ReservationNotFoundException;
import com.frank.server.models.Reservation;

public interface ReservationDAO {

    List<Reservation> findAll();

    List<Reservation> findByHotel(int hotelID) throws HotelNotFoundException;

    Reservation get(int reservationID) throws ReservationNotFoundException;

    Reservation create(Reservation reservation, int hotelID) throws HotelNotFoundException;

    Reservation update(Reservation reservation, int id) throws ReservationNotFoundException;

    void delete(int id) throws ReservationNotFoundException;

}