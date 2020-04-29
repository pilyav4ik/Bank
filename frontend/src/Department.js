import React, { Component } from 'react';

import { Table,Container,Button,} from 'reactstrap';

class Department extends Component {


    constructor(props){
        super(props);

        this.state = {
            isLoading :false,
            Departments : []
        };


        this.handleChange= this.handleChange.bind(this);
        this.handleDateChange= this.handleDateChange.bind(this);

    }



    handleChange(event){
        const target= event.target;
        const value= target.value;
        const name = target.name;
        let item={...this.state.item};
        item[name] = value;
        this.setState({item});
        console.log(item);
    }


    handleDateChange(date){
        let item={...this.state.item};
        item.expensedate= date;
        this.setState({item});

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


    async componentDidMount() {

        const response= await fetch('/api/departments');
        const body = await response.json();
        this.setState({Departments : body , isLoading :false});
        console.log(body);
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

                </tr>
            );


        return (
            <div>

                {''}
                <Container>
                    <h3>Departments</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="10%">Id</th>
                            <th width="80%">Name</th>
                            <th width="10%">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {rows}
                        </tbody>

                    </Table>
                </Container>

                }

            </div>

        );
    }
}

export default Department;