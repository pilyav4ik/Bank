import axios from "axios";

export class EmployeeInfoService {

    baseURL = "/api/employees-add-info/";
    getAllURL = "/api/employees/";

    getAll(){
        return axios.get(this.getAllURL).then(res => res.data);
    }


    save = async (employee) => {
        if (employee.id){
            return axios.put(this.baseURL + `${employee.id}`, employee).then(res => res.data);
        }else {
            return axios.post(this.baseURL, employee).then(res => res.data);
        }
    };

    async getAllEmployeesBySalaryAsc() {
        let res = await axios.get("/api/employees=by-salary-asc");
        return res.data;
    }

    async getAllEmployeesBySalaryDesc() {
        let res = await axios.get("/api/employees=by-salary-desc");
        return res.data;
    }
}