import React from "react";

class Head extends React.Component{
    constructor(props){
        super(props);
    }

    render(){

        return(
            <header>
                <div id="header_bar">
                    <a href="/">Home</a>
                    Testnet
                    <b/>
                    <a href="/Transactions">Transactions</a>
                    <a href="/Accounts">Accounts</a>
                    <a href="/Statistics">Statistics</a>
                    <a href="/Contact">Contact</a>
                </div>
            </header>
        );
    }
}
export default Head;