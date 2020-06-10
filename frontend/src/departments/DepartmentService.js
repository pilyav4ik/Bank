import axios from "axios";

export class DepartmentService {

    baseURL = "/api/departments/";
    getAllDepartments(){
        return axios.get(this.baseURL).then(res => res.data);
    }


    save = async (department) => {
        if (department.id){
            return axios.put(this.baseURL + `${department.id}`, department).then(res => res.data);
        }else {
            return axios.post(this.baseURL, department).then(res => res.data);
        }
    };


    delete(id) {
        return axios.delete(this.baseURL + "/" + id).then(res => res.data);
    }
}