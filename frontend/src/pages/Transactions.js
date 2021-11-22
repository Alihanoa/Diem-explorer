import React from "react";
import Transactiondetails from "./Transactiondetails";
import Transaction from "./Transactiondetails";
import reactDom from "react-dom";
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';

class Transactions extends React.Component {
    constructor(props) {
        super(props);
    }

    async componentDidMount() {
        let data = await this.readData();
        let table = this.createTable(data);
        document.getElementById("transactions").innerHTML = table;
        console.log(table)
    }

    // Data gets fetched from the backend
    async readData() {
        let data = await fetch('http://localhost:8888/rest/transactions').then(result => result.json());
        return data;
    }

    // create table row for each object within the data array
    createTable(data) {

        let table = [];
        for (let i = data.length - 1; i >= 0; i--) {
            // let children = [];
            table += "<tr> <td><a href=Transactiondetails/" + data[i].version + ">" + data[i].version + "</a></td> <td>" 
                + data[i].sender_id + "</td>  <td>" + data[i].public_key + "</td> <td>"
                + data[i].receiver_id + "</td>  <td>" + data[i].amount + " " + data[i].currency + "</td> <td>"
                + data[i].gas_used + " " + data[i].gas_currency + "</td> <td>" + data[i].date + "</td> <td>" + data[i].type + "</td> </tr>";
        }
        console.log(data);
        return table;
    }

    render() {

        return (

            <div><h1 id="main_title">Transactions</h1>
                <table>
                    <caption>Latest Transactions</caption>
                    <thead>
                        <tr>
                            <th>Version</th>
                            <th>From</th>
                            <th>Public Key Sender</th>
                            <th>To</th>
                            <th>Amount</th>
                            <th>Gas Amount</th>
                            <th>Date</th>
                            <th>Type</th>
                        </tr>
                    </thead>
                    <tbody id="transactions"></tbody>
                </table>
                <br></br>
                <br></br>
            </div>
        );
    }
}
export default Transactions;
