import React from 'react';
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
import Transactiondetails from './pages/Transactiondetails';
import Accountdetails from './pages/Accountdetails';

function App() {

    return (
        <div id="site">
            <div id="wrapper">
                <header id="header_bar">
                    <div id="header_content">
                        <a href="/">Home</a>
                        <a>Testnet</a>
                        <a id="free_room" />
                        <a href="/Transactions">Transactions</a>
                        <a href="/Accounts">Accounts</a>
                        <a href="/Statistics">Statistics</a>
                        <a href="/Contact">Contact</a>
                    </div>
                </header>

                <Router>
                    {/*exact sorgt dafür, dass nur bei genau dem Pfad die Komponente angezeigt wird.*/}
                    <Route path="/" exact component={Mainpage} />
                    <Route path="/Transactions" component={Transactions} />
                    <Route path="/Accounts" component={Accounts} />
                    <Route path="/Statistics" component={Statistics} />
                    <Route path="/Transactiondetails/:version" exact component={Transactiondetails} />
                    <Route path="/Accountdetails/:address" component={Accountdetails} />
                    <Route path="/Contact" component={Contact} />
                    {/* Hier führen mehrere Pfade auf dieselbe Seite.
                    <Route path={["/Others", 'Sonstiges']}>
                    <Others name="Kantar" isBig={true} />
                    </Route>
                    
                    <Route path='/'>
                    {42 == 42 ? <Redirect to="/CurrencyCalculator"/> : <Clock/>}
                    </Route> */}
                </Router>
            </div>

            <footer>
                <a target="_blank" href="https://developers.diem.com/docs/welcome-to-diem/">Diem Developers</a>
                <a href="/Contact">Contact</a>
            </footer>
        </div>
    );
}
export default App;
