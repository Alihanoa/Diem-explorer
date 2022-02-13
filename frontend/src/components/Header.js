import React from 'react';
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';

export default function Header(props) {

    return (
        <header>
            <div id="header-wrapper">
                <div id="header-left-side">
                    <Link to="/">Home</Link>
                    <p id="status"></p>
                </div>
                <div id="header-right-side">
                    <Link to="/Transactions">Transactions</Link>
                    <Link to="/Accounts">Accounts</Link>
                    <Link to="/Statistics">Statistics</Link>
                    <Link to="/Contact">Contact</Link>
                </div>
            </div>
        </header>
    );
}
