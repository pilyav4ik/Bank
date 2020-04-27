import React, { Component } from 'react';
import AppNav from "./AppNav";
class Department extends Component {

    state = {
        isLoading : true,
        Departments : []
    };

    async componentDidMount(){
        const response=await fetch('http://localhost:8080/api/departments');
        const body = await response.json();
        this.setState({Departments : body , isLoading: false});
    }

    render() {
        const {Departments , isLoading} = this.state;
        if(isLoading)
            return (<div>Loading...</div>);

        return (

            <div className={"col-6"}>
                <AppNav />
                <h2>Departments</h2>
                {
                    Departments.map( department =>
                        <div id={department.id}>
                            <div className={"col-md-5"}>
                                {department.id} -> {department.name}
                            </div>

                        </div>
                    )}

            </div>
        );
    }
}

export default Department;