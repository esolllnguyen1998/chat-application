import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import Register from './Auth/register';
import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import reducers from './reducers';
import { BrowserRouter as Router, Route } from 'react-router-dom';

const createStoreWithMiddleware = applyMiddleware()(createStore);

ReactDOM.render(
    <Provider store={createStoreWithMiddleware(reducers)} >
        <Router>
            <Route path='/register' exact={true} component={Register} />
            <Route path='' exact={true} component={App} />
        </Router>
    </Provider>
    , document.getElementById('root')
);

registerServiceWorker();

