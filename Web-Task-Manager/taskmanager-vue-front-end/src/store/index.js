import Vue  from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    tasks: []
  },
  mutations: {
    ADD_ALL_TASKS(state, theTasks) {
      state.tasks = theTasks;
    },
    ADD_NEW_TASK(state, aTask) {
      state.tasks.push(aTask)
    },
    REMOVE_TASK(state, task) {
      state.tasks.splice(state.tasks.indexOf(task), 1)  
   },
    SORT_BY_COMPLETE(state, sortByCompleteAsc) {
      (sortByCompleteAsc) ?  state.tasks.sort((oneElem, otherElem) => (oneElem.complete > otherElem.complete) ? 1 : -1)
                          :  state.tasks.sort((otherElem, oneElem) => (oneElem.complete > otherElem.complete) ? 1 : -1)
     },
    SORT_BY_DATE(state, sortByDateAsc) {
      (sortByDateAsc) ?  state.tasks.sort((oneElem, otherElem) => (oneElem.duedate > otherElem.duedate) ? 1 : -1)
                      :  state.tasks.sort((otherElem, oneElem) => (oneElem.duedate> otherElem.duedate) ? 1 : -1)
     },
    SORT_BY_DESCRIPTION(state, sortByDescAsc) {
      (sortByDescAsc) ?  state.tasks.sort((oneElem, otherElem) => (oneElem.description > otherElem.description) ? 1 : -1)
                      :  state.tasks.sort((otherElem, oneElem) => (oneElem.description > otherElem.description) ? 1 : -1)
  },
  }
})
