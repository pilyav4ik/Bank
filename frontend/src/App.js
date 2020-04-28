import React, {Component} from 'react';
import Department from "./Department";
import Home from "./Home";
import {Route, BrowserRouter as Router, Switch} from "react-router-dom";

class App extends Component{
  state = { };
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home} />
            <Route path='/departments' exact={true} component={Department} />
          </Switch>
        </Router>
    );
  }

}

export default App;
