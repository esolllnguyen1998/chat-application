import React, { Component } from 'react';
import './App.css';

import { MuiThemeProvider } from '@material-ui/core/styles';

import Login from './Auth/login';
import ChatScreen from './Chat/ChatScreen';
import Register from './Auth/register';
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

class App extends Component {
  constructor() {
    super();
  }

  render() {
    return (
      <MuiThemeProvider>
        <div className="App">
          <ToastContainer />
          <Router>
            <Route path='/register' exact={true} component={Register} />
            <Route path='/login' exact={true} component={Login} />
            <Route path='/chat' exact={true} component={ChatScreen} />
            <Route path='/' exact={true} component={Login} />
          </Router>
        </div >
      </MuiThemeProvider >
    );
  }
}

export default App;
