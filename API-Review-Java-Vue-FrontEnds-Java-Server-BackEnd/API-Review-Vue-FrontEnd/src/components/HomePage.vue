<template>
  <div id="sideNav">
    <h1>Squirrel Hotel System</h1>
    <div class="hotels">
      <div class="status-message error" v-show="errorMsg !== ''">{{errorMsg}}</div>
      <div class="loading" v-if="isLoading">
        <img src="../assets/animated-squirrel.gif" />
      </div>
      <div>
        <!-- Define a click-able link to a router path named "HotelDetail"                            -->
        <!--    with a parameter called id with the value of the current hotel's hotelID              -->
        <!-- one of these divs will be generated for each hotel in the hotels array in the data store -->
        <!-- tag="div" tells Vue to generate the link as a div rather than an anchor tag              -->
        <router-link v-bind:to="{ name: 'HotelDetail', params: { id: hotel.id } }"
            class="hotel"
            v-for="hotel in this.$store.state.hotels"
            v-bind:key="hotel.id"
            tag="div"
      >
        {{ hotel.name }}
      </router-link>
      </div>
    </div>
  </div>
</template>

<script>
import HotelService from '../services/HotelServices';

export default {
  name: "HomePage",
  data() {
    return {
      isLoading: true,
      hotels: [],  // Hold information for Hotels being displayed 

     /* Start of temporary data for testing purposes */
      hotel : {  
        hotelID: 31952,
        name: "The Squirrel Den"
      },
     /* End of temporary data for testing purposes   */ 
      
       errorMsg: ''
      }   // End of return
  },  // End of data()
  // created() runs before Vue generates the HTML for the page
  created() {
    this.retrieveBoards();   // call a method to retrieve the hotels
    this.isLoading = false;
  },
  methods: {
    retrieveBoards() {    // Get the hotels from an API server
      HotelService.getHotels()  // Use the getHotels() method in the HotelService
      .then(response => {       // Wait for API call call to finish (so we have data) - wait on promise
                         this.$store.commit("SET_HOTELS", response.data);  // Store the data in the data store
                                                                           // using the SET_HOTELS mutation
                         this.isLoading = false;                           // indicate we are done loading the page
                        })
      .catch (error => {  // if there was an error in the API call
        if (error.response) { // if it was a response error - request completed, but with an error
          this.errorMsg = "Error submitting new board. Response received was '" + error.response.statusText + "'.";

        } else if (error.request) {  // if it was a request error - request did not complete, may not have even got to server
          this.errorMsg = "Error submitting new board. Server could not be reached.";

        } else {  // if any other type of error occured (which it shouldn't)
          this.errorMsg = "Error submitting new board. Request could not be created.";
        }
        this.isLoading = false;  // indicate we have not loaded the data for the page
      });
    }
  }
}
</script>

<style scoped>
div#sideNav {
  height: 100%;
  width: 20%;
  position: fixed;
  z-index: 1;
  left: 0;
  margin: 10px;;
  top: 0;
  padding-top: 20px;
  padding-bottom: 20px;
  overflow-x: hidden;
  border-right: solid lightgrey 1px;
}

h1 {
  text-align: center;
}

.boards {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
}
/* .board {
  color: #f7fafc;
  border-radius: 10px;
  padding: 40px;
  flex: 1;
  margin: 10px;
  text-align: center;
  cursor: pointer;
  width: 60%;
}
.addBoard {
  color: #f7fafc;
  border-radius: 10px;
  background-color: #28a745;
  font-size: 16px;
  width: 60%;
  margin: 10px;
  padding: 20px;
  cursor: pointer;
}
.form-control {
  margin-bottom: 10px;
}
.btn {margin-bottom: 35px;}
.loading {
  flex: 3;
}
.board:hover:not(.router-link-active), .addBoard:hover {
  font-weight: bold;
}
.router-link-active {
  font-weight: bold;
  border: solid blue 5px;
}
*/
</style>