import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import Home from './Home'
import * as serviceWorker from './serviceWorker';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js.map';


ReactDOM.render(
  <React.StrictMode>
      <Home />
      <App />

  </React.StrictMode>,
  document.getElementById('root')
);

serviceWorker.unregister();
