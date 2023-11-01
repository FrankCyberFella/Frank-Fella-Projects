<template>
    <div>
        <h1>{{ hotel.name }} Details</h1>

        <p> {{hotel.address.city}}</p>
        <p> {{hotel.address.state}}</p>
        <p> Cost per night: ${{hotel.costPerNight}}</p>


        <div>
           <router-link to="/">Return to Home</router-link>
        </div> 
    </div>
         
</template>
<script>
// import the code for any services we use
import  HotelMethods from '@/services/HotelServices'

export default {
    name: "hotel-detail",
       data() {
        return { 
            // define real data to use in the page
            hotel: {}  // Define an empty object to hold the hotel for the page
                       //     Since the API returns the hotel data as an object, this must be an object

            /* Start of temporary data for testing purposes 
            hotel: {
                     hotelID: 1,
                     name: "The Squirrel Den"
                    }
            End of temporary data for testing purposes   */
        }    // end of return() for data()
    },  // end of data()
    // Since we need the hotel data before the page is generated (use it the HTML)
    // We will use a created() hook, so Vue will get the data before it generates the page
    created() {
        // We have two ways to information on a specific hotel
        //
        // 1. We can get it from the Vue Data Store: this.$store.state.hotels.filter()
        //
        //    Pro: We have already have the data from the API server - no API call overhead 
        //    Con: Data in the data store may be "stale" - it could have changed since we got it
        //
        // 2. Call the API server to retrieve it: Write a service to call the API and then we call it
        //
        //    a. Add a Service to make API call
        //    b. Call the service to get the hotel
        //    c. Copy the data from the API response to the hotel object
        //
        //    Pro: We get the most up to date data
        //    Con: Overhead for doing an API call and reliance on the network being up 
        //
        //  After a vote option 2 was selected

        // Call the service to get the data from he API server passing it the of the hotel we want
        // The hotel id to be displayed is in the path with name id: this.$route.params.id
        HotelMethods.hotelGetterMcHoHotelwad(this.$route.params.id)
                .then((response) => {                           // wait for API call to complete so we know we have the data
                                  this.hotel = response.data  // copy the data from rge reponse to our hotel object
                                  }) // End of .then
                 .catch (error => {      // Error handling for API call
                     if (error.response) {
                         this.errorMsg = "Error submitting new board. Response received was '" + error.response.statusText + "'.";
                     } 
                     else 
                        if (error.request) {
                           this.errorMsg = "Error submitting new board. Server could not be reached.";
                        } else {
                    this.errorMsg = "Error submitting new board. Request could not be created.";
              }
        this.isLoading = false;
      });

    }  // end of created()
}  // end of export
</script>
<style scoped>

</style>