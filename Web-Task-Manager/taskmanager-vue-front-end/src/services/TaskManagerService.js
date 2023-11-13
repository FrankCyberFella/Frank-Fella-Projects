import axios from 'axios'

const APIServer = axios.create({
    baseURL: "http://localhost:8080"
});

export default {
    getAllTasks() {
        return APIServer.get('/tasks')       
    },
    updateTask(updatedTask) {
       return  APIServer.put('/tasks', updatedTask)
    },
    addATask(newTask) {
        return APIServer.post('/tasks', newTask)
    },
    deleteTask(taskId) {
        return  APIServer.delete(`/tasks/${taskId}`)
 
     }

}