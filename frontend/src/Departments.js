import React, { Component } from 'react';
import "react-datepicker/dist/react-datepicker.css";
import './App.css';
import { Table,Container,Input,Button,Label, FormGroup, Form} from 'reactstrap';
import {Link} from 'react-router-dom';

class Departments extends Component {

    constructor(props){
        super(props);
        this.state = {
            isLoading :true,
            Departments : []
        };

        this.handleSubmit= this.handleSubmit.bind(this);
        this.handleChange= this.handleChange.bind(this);

    }


    componentDidMount = async () => {
        const response= await fetch('/api/departments');
        const body=  await response.json();
        this.setState({Departments : body , isLoading :false});
    };

    async handleSubmit(event){
        const item = this.state.item;

        await fetch(`/api/departments`, {
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
        let item={...this.state.item};
        item[name] = value;
        this.setState({item});
        //console.log(item);
    }


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
        const {Departments,isLoading} = this.state;

        if (isLoading)
            return(<div>Loading....</div>);

        let rows=
            Departments.map( department =>
                <tr key={department.id}>
                    <td>{department.id}</td>
                    <td>{department.name}</td>
                    <td><Button size="sm" color="danger" onClick={() => this.remove(department.id)}>Delete</Button></td>

                </tr>);


        return (
            <div>
                <Container>
                    {title}

                    <Form onSubmit={this.handleSubmit} >
                        <FormGroup>
                            <Label for="name">Title</Label>
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
                    <h3>Department List</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width={"5%"}>ID</th>
                            <th>Name</th>
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

export default Departments;