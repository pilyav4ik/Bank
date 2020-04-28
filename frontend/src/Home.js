import React, { Component } from "react";
import AppNav from "./AppNav";

class Home extends Component{
    state = { };

    async componentDidMount(){
        const response=await fetch('http://localhost:8080/api');
        const body = await response.json();
        this.setState({Departments : body , isLoading: false});
    }

    render() {
        return(
            <AppNav/>
        );
    }
}

export default Home;