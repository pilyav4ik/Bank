import React, { Component } from 'react';
import {EmployeeInfoService} from "./EmployeeInfoService";
import {Panel} from "primereact/panel";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import {Button} from "primereact/button";
import {Dialog} from "primereact/dialog";
import {InputText} from "primereact/inputtext";
import {Growl} from "primereact/growl";
import {Menubar} from "primereact/menubar";

class EmployeeInfo extends Component{

    constructor() {
        super();
        this.state = {
            visible: false,
            employeeInfo: {
                id: '',
                city: '',
                street : '',
                bankName : '',
                cardNumber: '',
                employee_id: ''
            },
            selectedEmployeeInfo : {

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
        this.footer = (
            <div>
                <Button label="Apply" icon="pi pi-check" onClick={this.save} />
            </div>
        );
    }

    componentDidMount(){
        this.employeeInfoService.gatAll().then(dataAll => this.setState({employeeInfo : dataAll}));
    }

    save() {
        this.employeeInfoService.save(this.state.employeeInfo).then(data => {
            this.setState({
                visible : false,
                employeeInfo: {
                    city: null,
                    street : null,
                    bankName : null,
                    cardNumber: null,
                    employee_id: null
                }
            });
            this.growl.show({severity: 'success', summary: 'Success!', detail: 'Text'});
            this.employeeInfoService.gatAll().then(dataAll => this.setState({employeeInfo : dataAll}));
        })
    }
    update(){
        this.employeeInfoService.update(this.state.employeeInfoService).then(data => {
            this.setState({
                visible: false,
                employeeInfo: {
                    employee: [],
                    city: null,
                    street : null,
                    bankName : null,
                    cardNumber: null

                }
            });
            this.growl.show({severity: 'success', summary: "Success"});
            this.employeeInfoService.getAll().then(data => this.setState({employees: data}));
        })
    }

    render(){
        return(
            <div style={{width:'80%', margin: '0 auto', marginTop: '20px'}}>
                <Menubar model={this.items}/>
            <Panel header="Employee full info">
                <DataTable value={this.state.employeeInfo} selectionMode="single">
                    <Column field="id" header="ID"/>
                    <Column field="employee.name" header="name"/>
                    <Column field="employee.salary" header="salary"/>
                    <Column field="city" header="City"/>
                    <Column field="street" header="street"/>
                    <Column field="bankName" header="Bank"/>
                    <Column field="cardNumber" header="Card number"/>
                    <Column field="employee.department_id" header="Department"/>
                </DataTable>
            </Panel>


                <Dialog header="Edit employee" visible={this.state.visible} style={{width: '400px'}} footer={this.footer} modal={true} onHide={() => this.setState({visible: false})}>
                    <form id="employee-info-form">
                           <span className="p-float-label">
                <InputText value={this.state.employeeInfo.employee_id} style={{width : '100%'}} id="city" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employeeInfo);
                        employee.employee_id = val;

                        return { employeeInfo: employee };
                    })}
                } />
                <label htmlFor="bankName">Employee id</label>
              </span>
                        <br/>
                        <span className="p-float-label">
                <InputText value={this.state.employeeInfo.city} style={{width : '100%'}} id="city" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employeeInfo);
                        employee.city = val;

                        return { employeeInfo: employee };
                    })}
                } />
                <label htmlFor="bankName">City</label>
              </span>
                        <br/>
                        <span className="p-float-label">
                <InputText value={this.state.employeeInfo.street} style={{width : '100%'}} id="street" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employeeInfo);
                        employee.street = val;

                        return { employeeInfo: employee };
                    })}
                } />
                <label htmlFor="street">Street</label>
              </span>
                        <br/>
                        <span className="p-float-label">
                <InputText value={this.state.employeeInfo.bankName} style={{width : '100%'}} id="bank_name" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employeeInfo);
                        employee.bankName = val;

                        return { employeeInfo: employee };
                    })}
                } />
                <label htmlFor="bankName">Bank</label>
              </span>
                        <br/>
                        <span className="p-float-label">
                <InputText value={this.state.employeeInfo.cardNumber} style={{width : '100%'}} id="card_number" onChange={(e) => {
                    let val = e.target.value;
                    this.setState(prevState => {
                        let employee = Object.assign({}, prevState.employeeInfo);
                        employee.cardNumber = val;

                        return { employeeInfo: employee };
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
            employeeInfo : {

                city: null,
                street : null,
                bankName : null,
                cardNumber: null
            }
        });
    }

    showEditDialog() {
        this.setState({
            visible : true,
            employeeInfo : {

                city: this.state.selectedEmployeeInfo.city,
                street : this.state.selectedEmployeeInfo.street,
                bankName : this.state.selectedEmployeeInfo.bankName,
                cardNumber: this.state.selectedEmployeeInfo.cardNumber
            }
        })
    }

}
export default EmployeeInfo;