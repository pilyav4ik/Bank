import axios from 'axios';

export class EmployeeService {

    baseURL = "/api/employees/";
    getAll(){
        return axios.get(this.baseURL).then(res => res.data);
    }

    save = async (employee) => {
        if (employee.id){
            return axios.put(this.baseURL + `${employee.id}`, employee).then(res => res.data);
        }else {
            return axios.post(this.baseURL, employee).then(res => res.data);
        }
    };


    delete(id) {
        return axios.delete(this.baseURL + id).then(res => res.data);
    }
}