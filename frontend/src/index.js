import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import Home from './Home'
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js.map';
import Department from "./Department";
ReactDOM.render(
  <React.StrictMode>
    <App />
    <Home />
  </React.StrictMode>,
  document.getElementById('root')
);

serviceWorker.unregister();
