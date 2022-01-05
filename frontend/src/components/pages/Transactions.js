import React, { useState, useEffect } from "react";
import Transactiondetails from "./Transactiondetails";
import Transaction from "./Transactiondetails";
import ReactDom from "react-dom";
import { ReactDOM } from "react";
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { data } from "jquery";
import axios from "axios";

export default function Transactions(props) {

    const [rows, setRows] = useState(10);
    const [loadingState, setLoadingState] = useState(false);
    const [lastRow, setLastRow] = useState(null);
    const [lastRowVersion, setLastRowVersion] = useState(0);
    const [table, setTable] = useState(null);

    useEffect(async () => {
        let data = await fetch('http://localhost:8888/rest/getlast50').then(result => result.json());
        setRows(data);
        let generatedTable = createTable(data);
        document.getElementById("transactions").innerHTML = generatedTable;
        // console.log(table);

        let stack = document.getElementById("transactions");
        let generatedLastRow = stack.rows[stack.rows.length - 1];
        console.log(generatedLastRow);
        setLastRow(lastRow => generatedLastRow);
        console.log(stack);
        console.log(lastRow);

        let observer = new IntersectionObserver(rows => {
            rows.forEach(async (row) => {
                if (row.isIntersecting) {
                    let newData = await axios.get("http://localhost:8888/rest/getnextten?lastVersionNumber=" + lastRowVersion);
                    newData = newData.data.reverse();
                    newData = createTable(newData);
                    console.log(newData);
                    generatedTable += newData;
                    document.getElementById("transactions").innerHTML = table;
                    let foo = document.getElementById("transactions");
                    setLastRow(document.getElementById("transactions").rows[foo.rows.length - 1]);
                    if (observer.current) {
                        observer.disconnect();
                    }
                    observer.observe(lastRow);
                }

            })
        }, {
            threshhold: 1
        });
        if (observer.current) {
            observer.disconnect();
        }
        console.log(lastRow);
        observer.observe(lastRow);
    }, []);

    // create table row for each object within the data array
    function createTable(data) {

        let generatedTable = [];
        for (let i = 0; i <= data.length - 1; i++) {
            // let children = [];
            generatedTable += "<tr> <td><a href=Transactiondetails/" + data[i].version + ">" + data[i].version + "</a></td> <td><a href=Accountdetails/" + data[i].sender_id + ">"
                + data[i].sender_id + "</a></td>  <td>" + data[i].public_key + "</td> <td><a href=Accountdetails/" + data[i].receiver_id + ">"
                + data[i].receiver_id + "</a></td>  <td>" + data[i].amount + " " + data[i].currency + "</td> <td>"
                + data[i].gas_used + " " + data[i].gas_currency + "</td> <td>" + data[i].date + "</td> <td>" + data[i].type + "</td> </tr>";

            setLastRowVersion(data[data.length - 1].version);
            console.log(lastRowVersion);
        }
        console.log(data);
        setTable(table + generatedTable);
        return generatedTable;
    }

    return (

        <div>
            <h1 id="main_title">Transactions</h1>

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
                <tbody id="transactions">

                </tbody>
            </table>
        </div>
    );
}
