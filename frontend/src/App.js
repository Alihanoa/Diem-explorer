import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Redirect } from 'react-router';
import Mainpage from './pages/Mainpage';
import Transactions from './pages/Transactions';
import Transaction from './pages/Transactiondetails';
import Accounts from './pages/Accounts';
import Account from './pages/Accountdetails';
import Search from './pages/Search';
import Statistics from './pages/Statistics';
import Contact from './pages/Contact';
import Head from './pages/Head';
import Others from './tutorials/Others';
import Transactiondetails from './pages/Transactiondetails';
import Accountdetails from './pages/Accountdetails';

function App() {
    return(
    <div>
        <Router>
            <Route path="/">
                <Head />
            </Route>
            {/*exact sorgt dafür, dass nur bei genau dem Pfad die Komponente angezeigt wird.*/}
            <Route path="/" exact component={Mainpage} />
            <Route path="/Transactions" component={Transactions} />
            <Route path="/Accounts" component={Accounts}/>
            <Route path="/Statistics" component={Statistics}/>
            <Route path="/Transactiondetails/:version" exact component={Transactiondetails}/>
            <Route path="/Accountdetails/:address" component={Accountdetails}/>
            <Route path="/Contact" component={Contact} />
            {/*Hier führen mehrere Pfade auf dieselbe Seite.*/}
            <Route path={["/Others", 'Sonstiges']}>
                {/*name ist ein Property. Properties sind read-only!*/}
                <Others name="Kantar" isBig={true}/>
            </Route>
            {/*
            <Route path='/'>
            {42 == 42 ? <Redirect to="/CurrencyCalculator"/> : <Clock/>}
            </Route>
            */}
        </Router>

        <footer>
            <a target="_blank" href="https://developers.diem.com/docs/welcome-to-diem/">Diem Developers</a>
            <a href="./Contact">Contact</a>
        </footer>

        {/* Standard von React
        <div className="App">
            <header className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <p>
                Edit <code>src/App.js</code> and save to reload.
            </p>
            <a
                className="App-link"
                href="https://reactjs.org"
                target="_blank"
                rel="noopener noreferrer"
            >
                Learn React
            </a>
            </header>
        </div>
        */}
    </div>
    );
}

export default App;
