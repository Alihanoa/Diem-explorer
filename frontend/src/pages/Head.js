import React from "react";

class Head extends React.Component{
    constructor(props){
        super(props);
    }

    render(){

        return(
            <header>
                <table>
                    <thead>
                    <tr>
                        <th><a href="/">Logo</a></th>
                        <td><a href="./Transactions">Transactions</a></td>
                        <td><a href="./Accounts">Accounts</a></td>
                        <td><a href="./Statistics">Statistics</a></td>
                        <td><a href="./Contact">Contact</a></td>
                    </tr>
                    </thead>
                </table>
            </header>
        );
    }
}
export default Head;