import axios from "axios";

export class DepartmentService {

    baseURL = "http://localhost:8080/api/departments/";
    getAll(){
        return axios.get(this.baseURL).then(res => res.data);
    }
}