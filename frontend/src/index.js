import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js.map';
import AppNav from "./AppNav";


ReactDOM.render(
  <React.StrictMode>
      <AppNav />
      <App />

  </React.StrictMode>,
  document.getElementById('root')
);

serviceWorker.unregister();
