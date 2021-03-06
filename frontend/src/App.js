import React, {Component} from 'react';
import Home from "./Home";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Departments from "./departments/Departments";
import Employees from "./employees/Employees";
import EmployeeInfo from "./employee-info/EmployeeInfo";
import Upload from "./employees/Upload";


export class App extends Component{
    constructor(props){
      super(props);
      this.state = {};
  }
  render() {
    return (
        <Router>
          <Switch>
              <Route path='/' exact={true} component={Home}/>
              <Route path='/departments' exact={true} component={Departments}/>
              <Route path='/employees' exact={true} component={Employees}/>
              <Route path={'/upload'} component={Upload}/>
              <Route path='/employees-info' exact={true} component={EmployeeInfo}/>
          </Switch>
        </Router>
    );
  }

}

export default App;
