import React, { Component } from 'react';

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
import {EmployeeInfoService} from "./EmployeeInfoService";


class EmployeeInfo extends Component{
    constructor(){
        super();
        this.state = {
            visible : false,
            employee: {
                id: null,
                name: null,
                salary: null,
                departmentId: null,
                city: null,
                street: null,
                bankName: null,
                cardNumber: null
            },
            selectedEmployee : {

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
        this.employeeInfoService = new EmployeeInfoService();
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
        this.employeeInfoService.getAll().then(data => this.setState({employees: data}));
    }

    save() {
        this.employeeInfoService.save(this.state.employee).then(data => {
            this.setState({
                visible : false,
                employee: {
                    id: null,
                    name: null,
                    salary: null,
                    departmentId: null,
                    city: null,
                    street: null,
                    bankName: null,
                    cardNumber: null
                }
            });
            this.growl.show({severity: 'success', summary: 'Success!', detail: 'Text'});
            this.employeeInfoService.getAll().then(data => this.setState({employees: data}));
        })
    }

    delete() {
        this.employeeInfoService.delete(this.state.selectedEmployee.id).then(data => {
            this.growl.show({severity: 'success', summary: 'Deleted!', detail: 'Text.'});
            this.employeeInfoService.getAll().then(data => this.setState({employees: data}));
        });
    }


    getAllEmployeesBySalaryAsc(){
        this.employeeInfoService.getAllEmployeesBySalaryAsc().then(data =>  this.setState({employees: data}));
    }
    getAllEmployeesBySalaryDesc(){
        this.employeeInfoService.getAllEmployeesBySalaryDesc().then(data =>  this.setState({employees: data}));
    }

    export(){
        this.dt.exportCSV();
    }

    render(){
        let header = <div style={{textAlign:'left'}}>
            <Button type="button" icon="pi pi-external-link" iconPos="left" label="CSV" onClick={this.export}></Button>
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
                        <Column field="city" header="city"/>
                        <Column field="street" header="Street"/>
                        <Column field="bankName" header="Bank"/>
                        <Column field="cardNumber" header="Card"/>
                    </DataTable>
                </Panel>
                <Dialog header="Create Info" visible={this.state.visible}
                        style={{width: '400px'}} footer={this.footer} modal={true}
                        onHide={() => this.setState({visible: false})}>
                    <form id="employee-form-form" onSubmit={this.employeeInfoService}>
              <span className="p-float-label">
                <InputText value={this.state.selectedEmployee.id} style={{width : '100%'}} onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.id = val;

                        return { employee };
                    })}
                } />
                <label htmlFor="name">Name</label>
              </span>
                        <br/>

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
                <InputText value={this.state.employee.departmentId} style={{width : '100%'}} id="departmentId" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.departmentId = val;

                        return { employee };
                    })}
                } />
                <label htmlFor="departmentId">Department</label>
              </span>

                        <br/>
                        <span className="p-float-label">
                <InputText value={this.state.employee.city} style={{width : '100%'}} id="city" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.city = val;

                        return { employee };
                    })}
                } />
                <label htmlFor="city">City</label>
              </span>

                        <br/>
                        <span className="p-float-label">
                <InputText value={this.state.employee.street} style={{width : '100%'}} id="street" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.street = val;

                        return { employee };
                    })}
                } />
                <label htmlFor="street">Street</label>
              </span>
                        <br/>
                        <span className="p-float-label">
                <InputText value={this.state.employee.bankName} style={{width : '100%'}} id="bankName" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.bankName = val;

                        return { employee };
                    })}
                } />
                <label htmlFor="bankName">Bank</label>
              </span>
                        <br/>
                        <span className="p-float-label">
                <InputText value={this.state.employee.cardNumber} style={{width : '100%'}} id="cardNumber" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employee);
                        employee.cardNumber = val;

                        return { employee };
                    })}
                } />
                <label htmlFor="cardNumber">Card number</label>
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
                department_id: null,
                city: null,
                street: null,
                bankName: null,
                cardNumber: null
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
                department_id: this.state.selectedEmployee.department_id,
                city: this.state.selectedEmployee.city,
                street: this.state.selectedEmployee.name,
                bankName: this.state.selectedEmployee.bankName,
                cardNumber: this.state.selectedEmployee.cardNumber
            }
        })
    }
}
export default EmployeeInfo;