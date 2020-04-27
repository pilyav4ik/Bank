import React, { Component } from "react";
import { Nav, Navbar, NavItem, NavbarBrand, NavLink} from 'react-bootstrap';

class AppNav extends Component{
    state = { };
    render() {
        return (
            <div>
                <Navbar class="navbar navbar-expand-lg navbar-light bg-light">
                    <NavbarBrand href="/">Tracker App</NavbarBrand>
                        <Nav className="mr-auto" navbar>
                            <NavItem>
                                <NavLink href="/home/">Home</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/departments/">Departments</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink href="/expenses/">Expenses</NavLink>
                            </NavItem>
                        </Nav>
                </Navbar>
            </div>
        );
    }
}

export default AppNav;