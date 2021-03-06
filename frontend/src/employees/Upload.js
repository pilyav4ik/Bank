import React, {Component} from 'react';
import {FileUpload} from "primereact/fileupload";
import {EmployeeService} from "./EmployeeService";


class Upload extends Component{

    constructor(props){
        super(props);
        this.state = {
            visible : true,
            csvFile: null
        };
        this.employeeService = new EmployeeService();
        this.upload = this.upload.bind(this);
    }

    componentDidMount(){
        this.employeeService.getAll().then(data => this.setState({employees: data}));
    }

    upload(){
        this.employeeService.upload(this.state.employee).then(data =>{
            this.setState({
                visible: false,
                csvFile: null
            });
            this.toast.show({severity: 'info', summary: 'Success', detail: 'File Uploaded'});
        }).then(this.employeeService.getAll);
    }


    render() {
        return (
            <div>

                <div className="card">

                    <FileUpload mode="basic"
                                name="file"
                                url="/api/employees/save-from-csv"
                                auto={true}
                                chooseLabel={"Upload CSV"}
                                onUpload={this.upload}/>
                </div>
            </div>
        )
    }
}


export default Upload