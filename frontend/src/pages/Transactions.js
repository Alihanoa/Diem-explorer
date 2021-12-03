import React from "react";
import Transactiondetails from "./Transactiondetails";
import Transaction from "./Transactiondetails";
import ReactDom from "react-dom";
import { ReactDOM } from "react";
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { data } from "jquery";

class Transactions extends React.Component {
    constructor(props) {
        super(props);
    }

    async componentDidMount() {
        let data = await this.readData();
        let table = this.createTable(data);
        document.getElementById("transactions").innerHTML = table;
        // ReactDOM.findDOMNode(this.refs.tabelle).addEventListener('scroll', this.listenScrollEvent);
        console.log(table)
    }

    // componentWillUnmount() {
    //     ReactDOM.findDOMNode(this.refs.table).removeEventListener('scroll', this.listenScrollEvent);
    // }

    // listenScrollEvent() {
    //     console.log('Scroll event detected!');
    // }

    // Data gets fetched from the backend
    async readData() {
        let data = await fetch('http://localhost:8888/rest/transactions').then(result => result.json());
        return data;
    }

    // create table row for each object within the data array
    createTable(data) {

        let table = [];
        for (let i = data.length - 1; i >= data.length - 100; i--) {
            // let children = [];
            table += "<tr> <td><a href=Transactiondetails/" + data[i].version + ">" + data[i].version + "</a></td> <td><a href=Accountdetails/" + data[i].sender_id + ">"
            + data[i].sender_id + "</a></td>  <td>" + data[i].public_key + "</td> <td><a href=Accountdetails/" + data[i].receiver_id + ">"
            + data[i].receiver_id + "</a></td>  <td>" + data[i].amount + " " + data[i].currency + "</td> <td>"
                + data[i].gas_used + " " + data[i].gas_currency + "</td> <td>" + data[i].date + "</td> <td>" + data[i].type + "</td> </tr>";
        }
        console.log(data);
        return table;
    }

    render() {

        return (

            <div><h1 id="main_title">Transactions</h1>
                <table>
                    {/* ref="tabelle" onScroll={this.listenScrollEvent.bind(this)} */}
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
                    <tbody id="transactions">

                    </tbody>
                </table>
            </div>
        );
    }
}
export default Transactions;
