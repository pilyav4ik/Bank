import axios from "axios";

export class DepartmentService {

    baseURL = "/api/departments";
    getAllDepartments(){
        return axios.get(this.baseURL).then(res => res.data, count => count.length);
    }


    save(department){
        return axios.post(this.baseURL, department).then(res => res.data);
    }

    edit(id) {
        return axios.post(this.baseURL + id).then(res => res.data);
    }

    delete(id) {
        return axios.delete(this.baseURL + id).then(res => res.data);
    }
}