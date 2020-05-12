import React, { Component } from 'react';
import "react-datepicker/dist/react-datepicker.css";
import './App.css';
import { Container,Button, FormGroup} from 'reactstrap';
import {Link} from 'react-router-dom';
import { Tabs, TabList, Tab, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import {Row, Col} from "react-bootstrap";
import  axios from  'axios';


class Departments extends Component {

    constructor(props){
        super(props);
        this.state = {
            isLoading :true,
            Departments : [],
            departmentName: '',
            tabIndex: 0
        };
    }

    changeHandler = e => {
        this.setState({[e.target.name]: e.target.value})
    };



    componentDidMount = async () => {
        const response= await fetch('/api/departments');
        const body=  await response.json();
        this.setState({Departments : body , isLoading :false});
    };

    submitHandler = (e) => {
        e.preventDefault();
        const form = document.forms[0];
        console.log(this.state);
        axios.post('/api/departments', this.state)
            .then(response => {
                const{departments} = this.state;
                console.log(response);
                this.setState({departments});
                form.reset();
            })
            .catch(error => {
                console.log(error)
            })
    };

    async remove(id){
        await fetch(`/api/departments/${id}` , {
            method: 'DELETE' ,
            headers : {
                'Accept' : 'application/json',
                'Content-Type' : 'application/json'
            }

        }).then(() => {
            let updatedDepartments = [...this.state.Departments].filter(i => i.id !== id);
            this.setState({Departments : updatedDepartments});
        });

    }

    render() {
        const title =<h3>Add Department</h3>;
        const {Departments,isLoading, department_name} = this.state;

        if (isLoading)
            return(<div>Loading....</div>);

        const formSubmitDepartment = (
            <form onSubmit={this.submitHandler}>
                <FormGroup className={"mb-2"}>
                    <Row>
                        <Col><input  type="text" placeholder={"name"}
                                     name="departmentName"
                                     value={department_name}
                                     onChange={this.changeHandler}/></Col>
                        <Col><button type={'submit'}>Save</button>{' '}
                            <Button className={"btn"} tag={Link} to="/">Cancel</Button></Col>
                    </Row>
                </FormGroup>


            </form>
        );

        const departmentsList =
            Departments.map( department =>
                <tr key={department.id}>
                    <td>{department.id}</td>
                    <td>{department.departmentName}</td>
                    <td><Button size="sm" color="danger" onClick={() => this.remove(department.id)}>Delete</Button></td>

                </tr>);

        const tableWithDepartments = (
            <Tabs>
                <TabList>
                    <Tab>Departments List</Tab>
                </TabList>
                <TabPanel>
                    <table  class="table">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Department</th>
                        </tr>
                        </thead>
                        <tbody>{departmentsList}</tbody>
                    </table>
                </TabPanel>
            </Tabs>
        );

        return (
            <div>
                <Container>
                    {title}
                    {formSubmitDepartment}
                </Container>

                {''}
                <Container>
                    {tableWithDepartments}
                </Container>
                {' '}
            </div>

        );
    }
}

export default Departments;