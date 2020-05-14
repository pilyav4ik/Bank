import React, { Component } from 'react';
import "react-datepicker/dist/react-datepicker.css";
import './App.css';
import {DepartmentService} from "./departments/DepartmentService";
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


class Departments extends Component {

    constructor(){
        super();
        this.state = {
            visible : false,
            department : {
                departmentId: '',
                departmentName: '',
            },
            selectedDepartment : {

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
        this.departmentService = new DepartmentService();
        this.save = this.save.bind(this);
        this.delete = this.delete.bind(this);
        this.footer = (
            <div>
                <Button label="Apply" icon="pi pi-check" onClick={this.save} />
            </div>
        );
    }

    componentDidMount(){
        this.departmentService.getAllDepartments().then(departmentList => this.setState({departments: departmentList}));
    }

    save() {
        this.departmentService.save(this.state.department).then(data => {
            this.setState({
                visible : false,
                department : {
                    id: null,
                    departmentName: null,
                }
            });
            this.growl.show({severity: 'success', summary: 'Success!', detail: 'Text'});
            this.departmentService.getAllDepartments().then(departmentList=> this.setState({departments: departmentList}));
        })
    }

    delete() {
        if(window.confirm("Confirm please")) {
            this.departmentService.delete(this.state.selectedDepartment.id).then(data => {
                this.growl.show({severity: 'success', summary: 'Deleted!', detail: 'Text.'});
                this.departmentService.getAll().then(data => this.setState({departments: data}));
            });
        }
    }

    render(){
        return (
            <div style={{width:'80%', margin: '0 auto', marginTop: '20px'}}>
                <Menubar model={this.items}/>
                <br/>
                <Panel header="React CRUD App">
                    <DataTable value={this.state.departments} paginator={true} rows="4" selectionMode="single" selection={this.state.selectedDepartment} onSelectionChange={e => this.setState({selectedDepartment: e.value})}>
                        <Column field="id" header="ID"/>
                        <Column field="departmentName" header="Name"/>
                    </DataTable>
                </Panel>
                <Dialog header="Crear department" visible={this.state.visible} style={{width: '400px'}} footer={this.footer} modal={true} onHide={() => this.setState({visible: false})}>
                    <form id="department-form">
              <span className="p-float-label">
                <InputText value={this.state.department.departmentName} style={{width : '100%'}} id="departmentName" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let department = Object.assign({}, prevState.department);
                        department.departmentName = val;

                        return { department };
                    })}
                } />
                <label htmlFor="departmentName">Name</label>
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
            department : {
                id: null,
                departmentName: null
            }
        });
    }

    showEditDialog() {
        this.setState({
            visible : true,
            department : {
                id: this.state.selectedDepartment.id,
                departmentName: this.state.selectedDepartment.departmentName,
            }
        })
    }
}

export default Departments;