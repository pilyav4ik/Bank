import React, { Component } from "react";
import { Nav, Navbar, NavItem, NavbarBrand, NavLink} from 'react-bootstrap';
import Container from "react-bootstrap/Container";

class AppNav extends Component{
    state = { };
    render() {
        return (
            <Container>
                <Navbar class="navbar navbar-expand-lg navbar-light bg-light">
                    <NavbarBrand href="/">Tracker App</NavbarBrand>
                        <Nav className="mr-auto" navbar>
                            <NavItem>
                                <NavLink href="/">Home</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/departments/">Departments</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/employees/">Employees</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/upload/">Uploads</NavLink>
                            </NavItem>
                        </Nav>
                </Navbar>
            </Container>
        );
    }
}

export default AppNav;