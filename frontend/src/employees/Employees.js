import React, {Component, useState} from 'react';
import {EmployeeService} from './EmployeeService';
import '../App.css';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import {Panel} from 'primereact/panel';
import {Menubar} from 'primereact/menubar';
import {Dialog} from 'primereact/dialog';
import {InputText} from 'primereact/inputtext';
import {Button} from 'primereact/button';
import {Growl} from 'primereact/growl';


import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import {NavLink} from "react-bootstrap";
import Upload from "./Upload";
import {FileUpload} from "primereact/fileupload";



 class Employees extends Component{
     constructor(props){
         super(props);
         this.state = {
             visible : false,
             employee: {
                 id: null,
                 name: null,
                 salary: null,
                 departmentId: null
             },
             selectedEmployee : {

             },
             fileWithEmployees : {
                 path : null
             }
         };
         this.items = [
             {
                 label : 'New',
                 icon  : 'pi pi-fw pi-plus',
                 command : () => {this.showSaveDialog()}
             },
             {
                 label : 'Edit',
                 icon  : 'pi pi-fw pi-pencil',
                 command : () => {this.showEditDialog()}
             },
             {
                 label : 'Delete',
                 icon  : 'pi pi-fw pi-trash',
                 command : () => {this.delete()}
             }
         ];

         this.employeeService = new EmployeeService();
         this.save = this.save.bind(this);
         this.delete = this.delete.bind(this);
         this.getAllEmployeesBySalaryAsc = this.getAllEmployeesBySalaryAsc.bind(this);
         this.getAllEmployeesBySalaryDesc = this.getAllEmployeesBySalaryDesc.bind(this);
         this.footer = (
             <div>
                 <Button label="Apply" icon="pi pi-check" onClick={this.save} />
             </div>
         );
         this.export = this.export.bind(this);
     }

     componentDidMount(){
         this.employeeService.getAll().then(data => this.setState({employees: data}));
     }

     save() {
         this.employeeService.save(this.state.employee).then(data => {
             this.setState({
                 visible : false,
                 employee: {
                     id: null,
                     name: null,
                     salary: null,
                     departmentId: null
                 }
             });
             this.growl.show({severity: 'success', summary: 'Success!', detail: 'Text'});
             this.employeeService.getAll().then(data => this.setState({employees: data}));
         })
     }

     delete() {
             this.employeeService.delete(this.state.selectedEmployee.id).then(data => {
                 this.growl.show({severity: 'success', summary: 'Deleted!', detail: 'Text.'});
                 this.employeeService.getAll().then(data => this.setState({employees: data}));
             });
     }


     getAllEmployeesBySalaryAsc(){
         this.employeeService.getAllEmployeesBySalaryAsc().then(data =>  this.setState({employees: data}));
     }
     getAllEmployeesBySalaryDesc(){
         this.employeeService.getAllEmployeesBySalaryDesc().then(data =>  this.setState({employees: data}));
     }

     export(){
         this.dt.exportCSV();
     }

     render(){
         let header = <div style={{textAlign:'left'}}>

             <Button type="button" icon="pi pi-external-link" iconPos="left" label="CSV" onClick={this.export}/>
             <Upload/>
             <NavLink type={ "button"} className={"form-check form-check-inline"} href="/employees-info">More info</NavLink>
         </div>;

         let sortBySalaryAsc = <a onClick={this.getAllEmployeesBySalaryAsc}><i className="pi pi-sort-up"/></a>;
         let sortBySalaryDesc = <a  onClick={this.getAllEmployeesBySalaryDesc}><i className="pi pi-sort-down"/></a>;


         return (
             <div style={{width:'80%', margin: '0 auto', marginTop: '20px'}}>
                 <Menubar model={this.items}/>
                 <br/>

                 {header}
                 Sort list by salary: {sortBySalaryAsc} {sortBySalaryDesc}
                 <Panel header="React CRUD App">
                     <DataTable value={this.state.employees} paginator={true} rows="25" selectionMode="single"
                                selection={this.state.selectedEmployee}
                                onSelectionChange={e => this.setState({selectedEmployee: e.value})}>
                         <Column field="id" header="ID"/>
                         <Column field="name" header="Name"/>
                         <Column field="salary" header="Salary"/>
                         <Column field="departmentId" header="Department"/>
                         <Column field="createDateTime" header="Created"/>
                         <Column field="updateDateTime" header="Updated"/>
                     </DataTable>
                 </Panel>


                 <Dialog header="Create employee" visible={this.state.visible}
                         style={{width: '400px'}} footer={this.footer} modal={true}
                         onHide={() => this.setState({visible: false})}>
                     <form id="employee-form" onSubmit={this.employeeService}>
              <span className="p-float-label">
                <InputText style={{width : '100%'}} hidden={true} value={this.state.selectedEmployee.id}
                           onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.id = val;

                        return { employee };
                    })}
                } />
              </span>
                         <br/>

              <span className="p-float-label">
                <InputText id={"name"} value={this.state.employee.name} style={{width : '100%'}} onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.name = val;

                        return { employee };
                    })}
                } />
                <label htmlFor="name">Name</label>
              </span>
                         <br/>
                         <span className="p-float-label">
                <InputText id={"salary"} value={this.state.employee.salary} style={{width : '100%'}}
                           onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.salary = val;

                        return { employee };
                    })}
                } />
                <label htmlFor="salary">Salary</label>
              </span>
                         <br/>
                         <span className="p-float-label">
                <InputText id={"department"} value={this.state.employee.departmentId} style={{width : '100%'}} onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.departmentId = val;

                        return { employee };
                    })}
                } />
                <label htmlFor="departmentId">Department</label>
              </span>
                     </form>
                 </Dialog>
                 <Growl ref={(el) => this.growl = el} />
             </div>
         );
     }

     showSaveDialog(){
         this.setState({
             visible : true,
             employee : {
                 id: null,
                 name: null,
                 salary: null,
                 departmentId: null
             }
         });
     }

     showEditDialog() {
         this.setState({
             visible : true,
             employee : {
                 id: this.state.selectedEmployee.id,
                 name: this.state.selectedEmployee.name,
                 salary: this.state.selectedEmployee.salary,
                 departmentId: this.state.selectedEmployee.departmentId
             }
         })
     }

 }
export default Employees;