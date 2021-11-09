import React from "react";

{/*import {React, useEffect, useState} from "react";
export const Transactions = () => {

    const [team, setTeam] = useState();

    useEffect(() => {
        const fetchMatches = async () => {
        const response = await fetch ('http://localhost:8888/transactions');
        const data = await response.json();
        console.log(data);
    };
    fetchMatches();
    }, []
};
*/}

class Transactions extends React.Component{
    constructor(props){
        super(props);
    }

    render(){

        return(
            <table border="5">
                <caption>Transaktionen</caption>
                <tr>
                    <th>Version</th>
                    <th>Sender_id</th>
                    <th>Receiver_id</th>
                    <th>Amount</th>
                    <th>Currency</th>
                    <th>Gas-Amount</th>
                    <th>Gas-Currency</th>
                    <th>Expiration</th>
                    <th>Sequence-Number</th>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        );
    }
}
export default Transactions;
