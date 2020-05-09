import React, { Component } from 'react';
import "react-datepicker/dist/react-datepicker.css";
import './App.css';
import { Table,Container,Input,Button,Label, FormGroup, Form} from 'reactstrap';
import {Link} from 'react-router-dom';
import { Tabs, TabList, Tab, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';

class Employees extends Component {

    constructor(props){
        super(props);
        this.state = {
            isLoading :true,
            Employees : [],
            Departments : [],
            tabIndex: 0
        };

        this.handleSubmit= this.handleSubmit.bind(this);
        this.handleChange= this.handleChange.bind(this);
        this.handleClick= this.handleClick.bind(this);

    }


    componentDidMount = async () => {
        const response= await fetch('/api/employees');
        const body=  await response.json();


        const responseDep= await fetch('/api/departments');
        const bodyDep=  await responseDep.json();
        this.setState({Employees : body, Departments : bodyDep , isLoading :false});
    };

    async handleSubmit(event){
        const item = this.state.item;

        await fetch(`/api/employees`, {
            method : 'POST',
            headers : {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body : JSON.stringify(item),
        });

        event.preventDefault();
        this.setState({item: ''})
    }


    handleChange(event){
        const target= event.target;
        const value= target.value;
        const name = target.name;
        const departmentName = target.type;
        const salary = target.type;
        let item={...this.state.item};
        item[name] = value;
        item[departmentName] = value;
        item[salary] = salary;
        this.setState({item});
        //console.log(item);
    }

    handleClick(){
        this.setState(state => ({
            isToggleOn: !state.isToggleOn
        }));
    }

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
        const {Employees, Departments,isLoading} = this.state;

        if (isLoading)
            return(<div>Loading....</div>);


        let departmentSelect = Departments.map(department =>
            <option key={department.departmentName}>{department.departmentName}</option>);

        const unsorted =
                Employees.map( employee =>
                    <tr key={employee.id}>
                        <td>{employee.id}</td>
                        <td>{employee.name}</td>
                        <td>{employee.department.departmentName}</td>
                        <td>{employee.salary}</td>
                        <td><Button size="sm" color="danger" onClick={() => this.remove(employee.id)}>Delete</Button></td>

                    </tr>);


        const sortBySalary = Employees.sort((a, b) => (a.salary > b.salary) ?  1 : -1).map( employee =>
                <tr key={employee.id}>
                    <td>{employee.id}</td>
                    <td>{employee.name}</td>
                    <td>{employee.department.departmentName}</td>
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

                    <Form onSubmit={this.handleSubmit} >
                        <FormGroup>
                            <Label for="department_id">Department</Label>
                            <select>{departmentSelect}</select>
                        </FormGroup>
                        <FormGroup>
                            <Label for="name">Employee name</Label>
                            <Input type="text" name="name" id="name"
                                   onChange={this.handleChange}/>

                        </FormGroup>

                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>{' '}
                            <Button color="secondary" tag={Link} to="/">Cancel</Button>
                        </FormGroup>
                    </Form>
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