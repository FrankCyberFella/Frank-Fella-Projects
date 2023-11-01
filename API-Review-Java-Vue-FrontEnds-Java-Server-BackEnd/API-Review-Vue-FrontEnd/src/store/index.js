/* This is the Vuex Data Store fr the App */
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    hotels: []  // Hold the hotels from the API call
  },
  mutations: {
    SET_HOTELS(state, data) {
      state.hotels = data;
    },
  },
  actions: {},
  modules: {}
});
