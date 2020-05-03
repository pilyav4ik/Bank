import React, {Component} from 'react';
import Home from "./Home";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Departments from "./Departments";

class App extends Component{
  state = { };
  render() {
    return (
        <Router>
          <Switch>
              <Route path='/' exact={true} component={Home}/>
              <Route path='/departments' exact={true} component={Departments}/>
          </Switch>
        </Router>
    );
  }

}

export default App;
