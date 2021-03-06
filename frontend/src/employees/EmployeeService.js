import axios from 'axios';
import FormFileInput from "react-bootstrap/FormFileInput";

export class EmployeeService {

    baseURL = "/api/employees";
    getAll(){
        return axios.get(this.baseURL).then(res => res.data);
    }

    save = async (employee) => {
        if (employee.id){
            return axios.put(this.baseURL +"/"+ `${employee.id}`, employee).then(res => res.data);
        }else {
            return axios.post(this.baseURL+"/", employee).then(res => res.data);
        }
    };


    delete(id) {
        return axios.delete(this.baseURL+"/" + id).then(res => res.data);
    }

    async getAllEmployeesBySalaryAsc() {
        let res = await axios.get("/api/employees=by-salary-asc");
        return res.data;
    }

    async getAllEmployeesBySalaryDesc() {
        let res = await axios.get("/api/employees=by-salary-desc");
        return res.data;
    }

    upload(file, onUploadProgress) {
        let fileInput = new FormFileInput();
        let formData = new FormData();
        console.log(file);
        formData.append("file", fileInput.files[file]);

        return axios.post("/api/employees/save-from-csv", formData, {
            onUploadProgress,
        }).then(res => {console.log(res)});
    }

    static async getFiles() {
        let data = await axios.get("/files");
        return data.data;
    }
}