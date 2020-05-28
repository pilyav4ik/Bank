import axios from "axios";

export class EmployeeInfoService {

    baseURL = "/api/employees/info";

    gatAll(){
        return axios.get(this.baseURL).then(res => res.data);
    }
    get(id){
        return axios.get(this.baseURL + id).then(res => res.data);
    }

    save(employeeInfo){
        return axios.post(this.baseURL, employeeInfo).then(res => res.data);
    }

    edit(id) {
        return axios.put(this.baseURL, id).then(res => res.data);
    }
}