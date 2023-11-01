package com.frank.server.controllers;

import com.frank.server.dao.HotelDAO;
import com.frank.server.dao.MemoryHotelDAO;
import com.frank.server.dao.MemoryReservationDAO;
import com.frank.server.dao.ReservationDAO;
import com.frank.server.exception.HotelNotFoundException;
import com.frank.server.exception.ReservationNotFoundException;
import com.frank.server.models.Hotel;
import com.frank.server.models.Reservation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@RestController
public class HotelController {

    private HotelDAO hotelDAO;
    private ReservationDAO reservationDAO;

    public HotelController() {
        this.hotelDAO = new MemoryHotelDAO();
        this.reservationDAO = new MemoryReservationDAO(hotelDAO);
    }

    /**
     * Return All Hotels
     *
     * @return a list of all hotels in the system
     */
    @RequestMapping(path = "/hotels", method = RequestMethod.GET)
    public List<Hotel> list() {	
    	logRequest("Request to get all hotels - /hotels");
        return hotelDAO.list();
    }

    /**
     * Return hotel information
     *
     * @param id the id of the hotel
     * @return all info for a given hotel
     */
    @RequestMapping(path = "/hotels/{id}", method = RequestMethod.GET)
    public Hotel get(@PathVariable int id) {
    	logRequest("Request to get specific hotel - /hotels/"+id);
        return hotelDAO.get(id);
    }

    /**
     * Returns all reservations in the system
     *
     * @return all reservations
     */
    @RequestMapping(path = "/reservations", method = RequestMethod.GET)
    public List<Reservation> listReservations() {
    	logRequest("Request to get all reservationss - /reservations");
        return reservationDAO.findAll();
    }

    /**
     * Get a reservation by its id
     *
     * @param id
     * @return a single reservation
     */
    @RequestMapping(path = "reservations/{id}", method = RequestMethod.GET)
    public Reservation getReservation(@PathVariable int id) throws ReservationNotFoundException {
    	logRequest("Request to get a specific reservation - /reservations/"+id);
        return reservationDAO.get(id);
    }

    /**
     * List of reservations by hotel
     *
     * @param hotelID
     * @return all reservations for a given hotel
     */
    @RequestMapping(path = "/hotels/{id}/reservations", method = RequestMethod.GET)
    public List<Reservation> listReservationsByHotel(@PathVariable("id") int hotelID) throws HotelNotFoundException {
    	logRequest("Request to get reservations by hotel - /hotels/"+hotelID+"/reservations");
    	return reservationDAO.findByHotel(hotelID);
    }

    /**
     * Create a new reservation for a given hotel
     *
     * @param reservation
     * @param hotelID
     */
    // The @Valid annotation in the parameter set for the a method
    //     Tells server to valid the incoming data according to validation criteria in the class
    @ResponseStatus(HttpStatus.CREATED)  // Set the HTTP Status for successful execution of this controller method
    @RequestMapping(path = "/hotels/{id}/reservations", method = RequestMethod.POST)
    public Reservation addReservation(@Valid @RequestBody Reservation reservation, @PathVariable("id") int hotelID)
            throws HotelNotFoundException {
    	logRequest("Request to get create a reservation - /hotels/"+hotelID+"/reservations");
        return reservationDAO.create(reservation, hotelID);
    }

    /**
     * /hotels/filter?state=oh&city=cleveland
     *
     * @param state the state to filter by
     * @param city  the city to filter by
     * @return a list of hotels that match the city & state
     */
    @RequestMapping(path = "/hotels/filter", method = RequestMethod.GET)
    public List<Hotel> filterByStateAndCity(@RequestParam String state, @RequestParam(required = false) String city) {

    	logRequest("Request to filter hotels by state and city - /hotels/filter?state="+state+"&city="+city);
    	
        List<Hotel> filteredHotels = new ArrayList<>();
        List<Hotel> hotels = list();

        // return hotels that match state
        for (Hotel hotel : hotels) {

            // if city was passed we don't care about the state filter
            if (city != null) {
                if (hotel.getAddress().getCity().toLowerCase().equals(city.toLowerCase())) {
                    filteredHotels.add(hotel);
                }
            } else {
                if (hotel.getAddress().getState().toLowerCase().equals(state.toLowerCase())) {
                    filteredHotels.add(hotel);
                }

            }
        }
        return filteredHotels;
    }

    /**
     * Update reservation by id
     *
     * @param reservationId
     *
     */
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.PUT)
    public void updateReservationsById(@Valid @RequestBody Reservation reservation, @PathVariable("id") int reservationId) throws ReservationNotFoundException {
        logRequest("Request to uodate reservation- /reservations/"+reservationId);
        reservationDAO.update(reservation, reservationId);
    }


    /**
     * Delete reservation by id
     *
     * @param reservationId
     */
    @RequestMapping(path = "/reservations/{id}", method = RequestMethod.DELETE)
    public void deleteReservationsById(@PathVariable("id") int reservationId) throws ReservationNotFoundException {
        logRequest("Request to delete reservations - /reservations/"+reservationId);
        reservationDAO.delete(reservationId);
    }


/**
 * Log timestamp of request and request
 * @param message
 */
static void logRequest(String message) {
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	 
	System.out.println(timestamp + " - " + message);
}
}
