import React, { Component } from 'react';
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


 class Employees extends Component{
     constructor(){
         super();
         this.state = {
             visible : false,
             employee: {
                 id: null,
                 name: null,
                 salary: null,
                 department_id: null
             },
             selectedPersona : {

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
         this.footer = (
             <div>
                 <Button label="Apply" icon="pi pi-check" onClick={this.save} />
             </div>
         );
     }

     componentDidMount(){
         this.employeeService.getAll().then(data => this.setState({employees: data}))
     }

     save() {
         this.employeeService.save(this.state.employee).then(data => {
             this.setState({
                 visible : false,
                 employee: {
                     id: null,
                     name: null,
                     salary: null,
                     department_id: null
                 }
             });
             this.growl.show({severity: 'success', summary: 'Success!', detail: 'Text'});
             this.employeeService.getAll().then(data => this.setState({employees: data}))
         })
     }

     delete() {
         if(window.confirm("Confirm please")) {
             this.employeeService.delete(this.state.selectedPersona.id).then(data => {
                 this.growl.show({severity: 'success', summary: 'Deleted!', detail: 'Text.'});
                 this.employeeService.getAll().then(data => this.setState({employees: data}));
             });
         }
     }

     render(){
         return (
             <div style={{width:'80%', margin: '0 auto', marginTop: '20px'}}>
                 <Menubar model={this.items}/>
                 <br/>
                 <Panel header="React CRUD App">
                     <DataTable value={this.state.employees} paginator={true} rows="4" selectionMode="single" selection={this.state.selectedEmployee} onSelectionChange={e => this.setState({selectedEmployee: e.value})}>
                         <Column field="id" header="ID"></Column>
                         <Column field="name" header="Name"></Column>
                         <Column field="salary" header="Salary"></Column>
                         <Column field="department_id" header="Department"></Column>
                     </DataTable>
                 </Panel>
                 <Dialog header="Crear employee" visible={this.state.visible} style={{width: '400px'}} footer={this.footer} modal={true} onHide={() => this.setState({visible: false})}>
                     <form id="employee-form">
              <span className="p-float-label">
                <InputText value={this.state.employee.name} style={{width : '100%'}} id="name" onChange={(e) => {
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
                <InputText value={this.state.employee.salary} style={{width : '100%'}} id="salary" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.salary = val

                        return { employee };
                    })}
                } />
                <label htmlFor="salary">Salary</label>
              </span>
                         <br/>
                         <span className="p-float-label">
                <InputText value={this.state.employee.department_id} style={{width : '100%'}} id="department_id" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.department_id = val

                        return { employee };
                    })}
                } />
                <label htmlFor="department_id">Department</label>
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
                 department_id: null
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
                 department_id: this.state.selectedEmployee.department_id
             }
         })
     }
}
export default Employees;