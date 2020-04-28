import React, { Component } from 'react';
class Department extends Component {

    state = {
        isLoading : true,
        Departments : []
    };

    async componentDidMount(){
        const response=await fetch('/api/departments');
        const body = await response.json();
        this.setState({Departments : body , isLoading: false});
    }

    render() {
        const {Departments , isLoading} = this.state;
        if(isLoading)
            return (<div>Loading...</div>);

        return (

            <div className={"col-6"}>

                <h2>Departments</h2>
                {
                    Departments.map( department =>
                        <div id={department.id}>

                            <table className="table">
                                <thead>
                                <tr>
                                    <th scope="col">#</th>
                                    <th scope="col">Name</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th scope="row">{department.id}</th>
                                    <td>{department.name}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    )}

            </div>
        );
    }
}

export default Department;