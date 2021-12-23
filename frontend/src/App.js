import React from 'react';
import './index.css';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
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
                <header>
                    <div class="header_wrapper">
                        <div class="header_left">
                            <a id="home" href="/">Home</a>
                            <p>Testnet</p>
                        </div>
                        <>
                            <Router>
                                <div class="header_right">








                                    <nav>
                                        <ul id="nav-links">
                                            <Link to="/Transactions" class="link">
                                                <li>
                                                    Transactions
                                                </li>
                                            </Link>
                                            <Link to="/Accounts" class="link">
                                                <li>
                                                    Accounts
                                                </li>
                                            </Link>
                                            <Link to="/Statistics" class="link">
                                                <li>
                                                    Statistics
                                                </li>
                                            </Link>
                                            <Link to="/Contact" class="link">
                                                <li>
                                                    Contact
                                                </li>
                                            </Link>
                                        </ul>

                                    </nav>
                                    {/* <a href="/Transactions">Transactions</a>
                                <a href="/Accounts">Accounts</a>
                                <a href="/Statistics">Statistics</a>
                                <a href="/Contact">Contact</a> */}
                                </div>

                                {/*exact sorgt dafür, dass nur bei genau dem Pfad die Komponente angezeigt wird.*/}
                                <Switch>
                                    <Route path="/" exact component={Mainpage} />
                                    <Route path="/Transactions" exact component={Transactions} />
                                    <Route path="/Accounts" exact component={Accounts} />
                                    <Route path="/Statistics" exact component={Statistics} />
                                    <Route path="/Transactiondetails/:version" exact component={Transactiondetails} />
                                    <Route path="/Accountdetails/:address" exact component={Accountdetails} />
                                    <Route path="/Contact" exact component={Contact} />
                                </Switch>
                                {/* <Route path='/'>
                    {42 == 42 ? <Redirect to="/CurrencyCalculator"/> : <Clock/>}
                    </Route> */}
                            </Router>
                        </>
                    </div>
                </header>




            </div>

            <footer>
                <a target="_blank" href="https://developers.diem.com/docs/welcome-to-diem/">Diem Developers</a>
                <a href="/Contact">Contact</a>
            </footer>
        </div>
    );
}
export default App;
