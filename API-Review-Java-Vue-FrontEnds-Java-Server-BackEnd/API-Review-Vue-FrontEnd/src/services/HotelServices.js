import axios from 'axios';        // Access axios API call framework

const hotelAPI = axios.create({    // Instantiate an axios object with the base url for the server
  baseURL: "http://localhost:8080" // URL for our Java server (MUST be started before app runs)
});

export default {

  getHotels() {                     // Retrieve all the hotels from the API server
    return hotelAPI.get('/hotels'); // Issue an HTTP GET with the path that return all hotels
  },

  // retrieve a single hotel from the API server
  // API path to retrieve a single hotel: "/hotels/{id}"
  hotelGetterMcHoHotelwad(theHotelId) {  // receive the id for the hotel we want as a parameter
    // Use tick marks to generate the path string since we want to substitute the value in variable
    return hotelAPI.get(`/hotels/${theHotelId}`) // issue an HTTP GET to the API server path
                                                 //       and return the response to the caller
  }
}
