import React from 'react';
import './index.css';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';
import { Redirect } from 'react-router';
import Mainpage from './components/pages/Mainpage';
import Transactions from './components/pages/Transactions';
import Transaction from './components/pages/Transactiondetails';
import Accounts from './components/pages/Accounts';
import Account from './components/pages/Accountdetails';
import Statistics from './components/pages/Statistics';
import Contact from './components/pages/Contact';
import Transactiondetails from './components/pages/Transactiondetails';
import Accountdetails from './components/pages/Accountdetails';
import Header from './components/Header';
import Footer from './components/Footer';

class App extends React.Component {

    constructor(props) {
        super(props);
    }
    state = {
        address: "http://testnet.diem.com/v1"
    }
    async checkNetworkAvailability() {

        const options = {
            method: 'POST', // *GET, POST, PUT, DELETE, etc.
            headers: {
                'Accept': 'application/json',
                "Content-Type": "application/json",
            },
            body: JSON.stringify(
                {
                    "jsonrpc": "2.0",
                    "method": "get_account",
                    "params": ["000000000000000000000000000000dd"],
                    "id": 1
                })
        };

        return await fetch(this.state.address, options, { mode: "no-cors" }).then(response => response.status).catch(function(error){return  404;})
    }

    componentDidMount(){

    //    this.checkNetworkAvailability(this.state.address).then(function(status){
    //         if(status ===200){
    //             document.getElementById("status").style.color= "green"
    //             document.getElementById("status").innerHTML = "Testnet"
    //         }
    //         else if(status === 404){
    //             document.getElementById("status").style.color= "red"
    //             document.getElementById("status").innerHTML = "Testnet"
    //         }
    //     })
    }



    render() {
        
        return (
            <Router>
                <div id="site">
                    <div id="wrapper">
                        <Header/>
                        <Switch>
                            <Route path="/" exact component={Mainpage} />
                            <Route path={["/Transactions/:input/:method", "/Transactions/"]} exact component={Transactions} />
                            <Route path="/Accounts" exact component={Accounts} />
                            <Route path="/Statistics" exact component={Statistics} />
                            <Route path="/Transactiondetails/:version" exact component={Transactiondetails} />
                            <Route path="/Accountdetails/:address" exact component={Accountdetails} />
                            <Route path="/Contact" exact component={Contact} />
                        </Switch>



                    </div>

                    <Footer/>
                </div>
            </Router>
        );
    }
}
export default App;
