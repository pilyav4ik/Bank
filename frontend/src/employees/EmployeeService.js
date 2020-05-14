import axios from 'axios';

export class EmployeeService {

    baseURL = "http://localhost:8080/api/employees/";
    getAll(){
        return axios.get(this.baseURL).then(res => res.data);
    }

    save(employee){
        return axios.post(this.baseURL, employee).then(res => res.data);
    }

    edit(id) {
        return axios.post(this.baseURL + id).then(res => res.data);
    }

    delete(id) {
        return axios.delete(this.baseURL + id).then(res => res.data);
    }
}