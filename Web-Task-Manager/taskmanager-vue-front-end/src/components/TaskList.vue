<template>
    <div class="task-list">
      <h1>My Tasks</h1>
       
      <span id="taskStatusFilters">
        <span>
          <input  id="allFilter"      type="radio" v-model="completeStatusFilter" value="ALL" />
          <label for="allFilter">Show all tasks</label>
        </span>
        <span>
          <input  id="completeFilter" type="radio" v-model="completeStatusFilter" value="COMPLETE" />
          <label for="completedFilter">Show Completed Tasks Only</label>
        </span> 
        <span>
          <input  id="incompletefilter" type="radio"  v-model="completeStatusFilter" value="INCOMPLETE" />       
          <label for="incompleteFilter">Show Incomplete Tasks Only</label>
         </span>
       </span>   

        <input class="filter" id="duedateFilter"      type="text" v-model="duedateFilterText"     placeholder="Filter by Date" />
        <input class="filter" id="descriptionFilter"  type="text" v-model="descriptionFilterText" placeholder="Filter Description" />
      <p id="sortInstructions">(Click heading to sort)</p>
      <table id="tasktable">
        <tr>
            <th v-on:click="sortByComplete">Done</th>
            <th v-on:click="sortByDate">Due Date</th>
            <th v-on:click="sortByDescription">Description</th>
        </tr>
      
        <tr
          v-for="task in filteredTasks"
          v-bind:key="task.description"
          v-bind:class="{ finished: task.complete }"
        >
          <td> <input type="checkbox" id="completed" v-model="task.complete" v-on:change="updateDataSource(task)" /></td>
          <td> <span id="duedate"> {{ task.duedate }} </span> </td>
          <td> {{ task.description }}</td>
          <td><img src="../assets/trashcan.png" v-on:click="deleteTask(task)"/> </td>
        </tr>
    </table>
    <a id="showAddTaskButton"
       href="#"
       v-on:click.prevent="showAddTaskForm = true"
       v-if="showAddTaskForm === false">
       Add New Task
    </a>
      <form v-on:submit.prevent="createTask" v-if="showAddTaskForm === true">
        <input  type="date" v-model="newTask.duedate"/>
        <input  type="text" v-model="newTask.description" placeholder="Description" />
        <button type="submit" class="btn save">Save</button>
        <button type="reset"  class="btn cancel" v-on:click.prevent="cancelNewTask">Cancel</button>
      </form>
      <p class="errorMessage" v-if="newTaskErrorMessage">{{ newTaskErrorMessage }}</p>
    </div>
  </template>
  
  <script>
  import taskManagerService from '../services/TaskManagerService'
  
  export default {
    data() {
      return {
        completeStatusFilter: "ALL",
        duedateFilterText: "",
        descriptionFilterText: "",
        showAddTaskForm : false,
        newTask: {
                  taskId : 0,
                  complete : false,
                  duedate : "",
                  description: ""
        },
        tasks: [],
        sortCompleteAsc   : true,
        sortDueDateAsc    : true,
        sortDescriptionAsc: true,
        newTaskErrorMessage : "",
        httpSuccessStatusCodes : [200, 201, 202, 203, 204],
      };
    },
    methods: {  
      updateDataSource(task) {
        taskManagerService.updateTask(task)
        .then((response) => {
                                 if(this.httpSuccessStatusCodes.includes(response.status) ) {
                                      return
                                  }
                         } )
      .catch(error => {
                          if (error.request) {                       
                          alert("Error updating task in data source. "
                               +"\n\nDisplayed data may be inconsistent with data source "
                               +"\n\nPlease exit and contact your data source administrator")
                          }
                          else {            
                               if (error.response)  {
                                   alert("Error accessing data source"
                                        +"\n\nDisplayed data may be inconsistent with data source "
                                        +"\n\nPlease exit and contact your data source administrator")
                              }
                           }

                     })
        },   
      createTask() {
        if(!this.newTask.duedate && !this.newTask.description) {  
          this.newTaskErrorMessage = "Missing Date and Description, please enter both or click cancel"
          return;
        }
        if(!this.newTask.duedate) {  
          this.newTaskErrorMessage = "Missing Date please enter/choose date or click cancel"
          return;
        }
        if (!this.newTask.description) {  
          this.newTaskErrorMessage = "Missing Description please enter a description or click cancel"
          return;
        }
        taskManagerService.addATask(this.newTask)
        .then ((response) => {
             if(this.httpSuccessStatusCodes.includes(response.status) ) {
                this.newTask = response.data
                this.$store.commit('ADD_NEW_TASK', this.newTask);
                this.resetTask();
                this.newTaskErrorMessage = ""
                this.showAddTaskForm = false;
             }
            })   
         .catch(error => {
              if (error.request) {                       
                           alert("Error adding new task to data source. "
                                +"\n\nDisplayed data may be inconsistent with data source "
                                +"\n\nPlease exit and contact your data source administrator")
                          }
              else {            
                    if (error.response)  {
                           alert("Error accessing data source"
                                +"\n\nDisplayed data may be inconsistent with data source "
                                +"\n\nPlease exit and contact your data source administrator")
                     }
               }
               this.showAddTaskForm = false;
              })
      },
      cancelNewTask() {
        this.resetTask;
        this.newTaskErrorMessage=""
        this.showAddTaskForm = false;
      },
      deleteTask(task) {
        taskManagerService.deleteTask(task.taskId)
            .then((response) => {
              if(this.httpSuccessStatusCodes.includes(response.status) ) {
                 this.$store.commit('REMOVE_TASK', task)
              }
            }
            )
            .catch(error => {
              if (error.request) {                       
                           alert("Error deleting task from data source. "
                                +"\n\nDisplayed data may be inconsistent with data source "
                                +"\n\nPlease exit and contact your data source administrator")
                          }
              else {            
                    if (error.response)  {
                           alert("Error accessing data source"
                                +"\n\nDisplayed data may be inconsistent with data source "
                                +"\n\nPlease exit and contact your data source administrator")
                     }
               }

              })
      },
      sortByComplete() {
        this.$store.commit('SORT_BY_COMPLETE', this.sortCompleteAsc)
        this.sortCompleteAsc = !this.sortCompleteAsc;
      },
      sortByDescription() {
        this.$store.commit('SORT_BY_DESCRIPTION', this.sortDescriptionAsc)
        this.sortDescriptionAsc = !this.sortDescriptionAsc;
      },
      sortByDate() {
        this.$store.commit('SORT_BY_DATE', this.sortDueDateAsc)
        this.sortDueDateAsc = !this.sortDueDateAsc;
      },
      resetTask() {
        this.newTask = {
                        taskId : 0,
                        complete : false,
                        duedate : "",
                        description: "" 
        }
      }
    },
    computed: {
      filteredTasks() {
        if(this.$store.state.tasks.length == 0) return;
        let  filteredResult =  this.$store.state.tasks.filter((task) => {
                                                             return task.description.includes(this.descriptionFilterText);
                                                           });
        filteredResult =  filteredResult.filter((task) => {
                                                             return task.duedate.includes(this.duedateFilterText);
                                                          }); 

        switch (this.completeStatusFilter) {
            case "COMPLETE" :
                filteredResult =  filteredResult.filter((task) => {
                                                                   return task.complete === true;
                                                                  });
                break;                                                  
                                                 
            case "INCOMPLETE" :
                filteredResult =  filteredResult.filter((task) => {
                                                                  return task.complete === false;
                                                                  });     
                break;                                                                                         
           
        }                                                   
        return filteredResult
      }
    },
    created() {
      taskManagerService.getAllTasks() 
                        .then(response => {
                          if(this.httpSuccessStatusCodes.includes(response.status) ) {
                          this.$store.commit('ADD_ALL_TASKS',response.data)
                          }
                        })
                        .catch(error => {
                          if (error.request) {
                            alert("Error accessing tasks on data source. "
                                 +"\n\nPlease exit and contact your data source administrator")
                          }
                          else
                          if (error.response) {
                             alert("Error accessing data source"
                                  +"\n\nPlease exit and contact your data source administrator")
                          }
                        })
    }
  };
  </script>
  
  <style>
  .task-list {
    width: 1000px;
    background: #fff;
    margin: 50px auto;
    font-family: "Roboto Condensed", sans-serif;
    border-radius: 10px;
  }
  #duedate {
   color: green;
   font-size: .8em;
  }

  #taskStatusFilters {
    display: flex;
    font-size: 16pt;
    justify-content:space-around;
    margin: 50px auto; 
}
  #sortInstructions {
    display: inline-block;  
    font-size: .8em;
    font-style: italic;
    margin-left: 150px;
    margin-bottom: -30px;
}

  h1 {
    background: rgb(81, 81, 144);
    color: white;
    padding: 10px;
    font-size: 24px;
    text-transform: uppercase;
    text-align: center;
    margin-bottom: 20px;
    border-top-left-radius: 10px;
    border-top-right-radius: 10px;
  }

  #tasktable {
    background-color: lightyellow;
    margin-left: 50px;
    margin-bottom: 50px;
    padding: 0px;
    margin: 10px auto;
    width: 75%;
  }
  #tasktable tr {
    font-size: 24px;
    border-bottom: 5px solid green;
    padding: 40px;
  }
  #tasktable th {
    font-size: 14px;
    border-bottom: 1px solid black;
  }
  input[type="checkbox"] {
    margin-left: 20px;
   }

  #showAddTaskButton {
    display: inline-block;  
    margin-top: 20px;
    margin-bottom: 15px;
    margin-left: 120px;
    font-size: 1.3em;
    }

  .errorMessage {
    display: block;
    margin-top: 5px;
    margin-left: 120px;
    padding-bottom: 5px;
    font-size: 1.5em;
    color: red;
    }
  

  .finished {
    text-decoration: line-through;
  }
  input[type="text"] {
    font-size: 1.2em;
    display: block;
    padding: 8px;
  }

  input[type="date"] {
    font-size : 1.5em;
    width: auto;
  }

  .filter {
    margin: 10px auto;
    width: 30%;
  }
  
  form {
    display: flex;
    padding: 20px;
  }
  form input {
    width: 80%;
    margin-right: 5px !important;
  }
  form .btn.save {
    font-size: 1em;
  }
  form .btn.cancel {
    font-size: 1em;
  }
  </style>
  