import React, { Component } from 'react';
import "react-datepicker/dist/react-datepicker.css";
import './App.css';
import { Table,Container,Input,Button,Label, FormGroup, Form} from 'reactstrap';
import {Link} from 'react-router-dom';
import Departments from "./Departments";

class Employees extends Component {

    constructor(props){
        super(props);
        this.state = {
            isLoading :true,
            Employees : []
        };

        this.handleSubmit= this.handleSubmit.bind(this);
        this.handleChange= this.handleChange.bind(this);

    }


    componentDidMount = async () => {
        const response= await fetch('/api/employees');
        const body=  await response.json();
        this.setState({Employees : body , isLoading :false});
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
        let item={...this.state.item};
        item[name] = value;
        item[departmentName] = value;
        this.setState({item});
        //console.log(item);
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
        const {Employees,isLoading} = this.state;

        if (isLoading)
            return(<div>Loading....</div>);

        let rows=
            Employees.map( employee =>
                <tr key={employee.id}>
                    <td>{employee.id}</td>
                    <td>{employee.name}</td>
                    <td>{employee.department.departmentName}</td>
                    <td><Button size="sm" color="danger" onClick={() => this.remove(employee.id)}>Delete</Button></td>

                </tr>);


        return (
            <div>
                <Container>
                    {title}

                    <Form onSubmit={this.handleSubmit} >
                        <FormGroup>
                            <Label for="department_id">Department</Label>
                            <select name="department_id" id="department_id" value={this.state.department} onChange={this.handleChange}>
                                <option value={"1"}>development</option>
                                <option value={"2"}>test</option>
                            </select>
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
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width={"5%"}>ID</th>
                            <th>Name</th>
                            <th>Department</th>
                            <th width="10%">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {rows}
                        </tbody>

                    </Table>
                </Container>

            </div>

        );
    }
}

export default Employees;