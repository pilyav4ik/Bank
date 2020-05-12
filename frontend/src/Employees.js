import React, { Component } from 'react';
import "react-datepicker/dist/react-datepicker.css";
import './App.css';
import { Container,Button,FormGroup} from 'reactstrap';
import {Link} from 'react-router-dom';
import { Tabs, TabList, Tab, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import  axios from  'axios';
import {Col, Row} from "react-bootstrap";

class Employees extends Component {

    constructor(props){
        super(props);
        this.state = {
            isLoading :true,
            Employees : [],
            Departments : [],
            name: '',
            department_id: '',
            departmentName: '',
            salary: '',
            tabIndex: 0
        };



    }

    changeHandler = e => {
        this.setState({[e.target.name]: e.target.value})
    };

    componentDidMount = async () => {
        const response= await fetch('/api/employees');
        const body=  await response.json();

        const responseDep= await fetch('/api/departments');
        const bodyDep=  await responseDep.json();
        this.setState({Employees : body, Departments : bodyDep , isLoading :false});
    };

    submitHandler(e) {
        const {Employees, Departments, isLoading} = this.state;
        const form = document.forms[0];
        console.log(this.state);
        axios.post(`/api/employees/${Departments.id}`, this.state)
            .then(response => {
                const{employees} = this.state;
                console.log(response);
                this.setState({employees});
                form.reset();
            })
            .catch(error => {
                console.log(error)
            })
    };


    async remove(id){
        await fetch(`/api/employees/${id}` , {
            method: 'DELETE' ,
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            }

        }).then(() => {
            let updatedEmployees = [...this.state.Employees].filter(i => i.id !== id);
            this.setState({Employees : updatedEmployees});
        });

    }

    render() {
        const title =<h3>Add Employee</h3>;
        const {Employees, Departments, name, salary, departmentId, isLoading} = this.state;

        if (isLoading)
            return(<div>Loading....</div>);


        let departmentSelect = Departments.map(department =>
            <option key={department.id} value={departmentId}>{department.departmentName}</option>);


        const formSubmitEmployee = (
            <form onSubmit={this.submitHandler}>
                <FormGroup className={"mb-2"}>
                    <Row>
                        <Col><select>{departmentSelect}</select></Col>
                        <Col><input  type="text" placeholder={"name"}
                                     name="name"
                                     value={name}
                                     onChange={this.changeHandler}/></Col>
                        <Col><input  type="text" placeholder={"salary"}
                                     name="salary"
                                     value={salary}
                                     onChange={this.changeHandler}/></Col>
                        <Col><button type={'submit'}>Save</button>{' '}
                            <Button className={"btn"} tag={Link} to="/">Cancel</Button></Col>
                    </Row>
                </FormGroup>
            </form>
        );
        const unsorted =
            Employees.map( employee =>
                    <tr key={employee.id}>
                        <td>{employee.id}</td>
                        <td>{employee.name}</td>
                        <td>{employee.department_id}</td>
                        <td>{employee.salary}</td>
                        <td><Button size="sm" color="danger" onClick={() => this.remove(employee.id)}>Delete</Button></td>

                    </tr>);


        const sortBySalary = Employees.sort((a, b) => (a.salary > b.salary) ?  1 : -1).map( employee =>
                <tr key={employee.id}>
                    <td>{employee.id}</td>
                    <td>{employee.name}</td>
                    <td>{employee.department_id}</td>
                    <td>{employee.salary}</td>
                    <td><Button size="sm" color="danger" onClick={() => this.remove(employee.id)}>Delete</Button></td>

                </tr>);

        const component = (
            <Tabs>
                <TabList>
                    <Tab>Unsorted</Tab>
                    <Tab>Sort by salary</Tab>
                </TabList>

                <TabPanel>
                    <table  class="table">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Name</th>
                        <th scope="col">Department</th>
                        <th scope="col">Salary</th>
                    </tr>
                    </thead>
                    <tbody>{unsorted}</tbody>
                    </table>
                </TabPanel>

                <TabPanel>
                    <table  class="table">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Name</th>
                            <th scope="col">Department</th>
                            <th scope="col">Salary</th>
                        </tr>
                        </thead>
                        <tbody>{sortBySalary}</tbody>
                    </table>
                </TabPanel>


            </Tabs>

        );

        return (
            <div>
                <Container>
                    {title}
                    {formSubmitEmployee}
                </Container>


                {''}
                <Container>
                    <h3>Employees List</h3>
                    {component}
                </Container>
                {' '}
            </div>

        );
    }
}

export default Employees;