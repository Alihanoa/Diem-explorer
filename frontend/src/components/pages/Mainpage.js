import React, { useState, useEffect } from "react";
import { Line } from 'react-chartjs-2'
import Searchbar from '../Searchbar'
import TransactionsTable from "../TransactionsTable";
import Chart from "../Chart";

export default function Mainpage(props) {

    const [serverAdress, setServerAdress] = useState("diemexplorer.internet-sicherheit.de:8888");
    // serverAdress : "diemexplorer.internet-sicherheit.de:8888"

    const [transactionData, setTransactionData] = useState([]);
    

    useEffect(async () => {

        let transactions_last_minute = await fetch("http://" + serverAdress + "/rest/transactionstodate?date=28/04/2021").then(result => result.json());
        let tradingvolume = await fetch("http://" + serverAdress + "/rest/tradingvolume?date=28/04/2021").then(result => result.json());
        let market_capacity = await fetch("http://" + serverAdress + "/rest/sumbalances").then(result => result.json());

        // let data = await fetch('http://localhost:8888/rest/transactions').then(result => result.json());
        // let lasttenanything = await fetch("http://localhost:8888/rest/lastten").then(result  => result.json());

        // let data = await fetch('http://localhost:8888/rest/transactions').then(result => result.json());
        let data = await fetch("http://" + serverAdress + "/rest/lastten").then(result => result.json());
        setTransactionData(data);
        // let lasttensmartcontracts = await fetch("http://localhost:8888/rest/lasttensmartcontracts").then(result => result.json());
        // let data = await fetch("http://localhost:8888/rest/lasttenreal").then(result => result.json());

        // let table = createTable(lasttentransactions);
        // let table = createTable(data);

        document.getElementById("transactions_last_minute").innerHTML = transactions_last_minute.toLocaleString();
        document.getElementById("tradingvolume").innerHTML = tradingvolume.toLocaleString() + " XUS";
        document.getElementById("market_capacity").innerHTML = market_capacity.toLocaleString() + " XUS";

        // document.getElementById("transactions").innerHTML = table;

        // console.log(transactions_last_minute, tradingvolume, market_capacity, table, lasttenanything, lasttensmartcontracts, lasttentransactions);
    }, []);

    return (
        <div>
            <h1 id="main_title">Diem Explorer</h1>

            <Searchbar />
            <br></br>
            <br></br>
            <table id="general_information">
                <caption>General Information</caption>
                <thead>
                    <tr>
                        <td>Average gas unit price: 0</td>
                        <td>Transactions in the last minute: <div id="transactions_last_minute"></div></td>
                        <td>Trading volume today: <div id="tradingvolume" /></td>
                        <td>Market capacity:  <div id="market_capacity" /></td>
                    </tr>
                </thead>
            </table>
            <br></br>

            <Chart />        

            <br></br>
            <br></br>
            <br></br>
            <TransactionsTable data={transactionData}/>
            {/* <input type="checkbox" id="smartContract" name="Include Smart Contracts" defaultChecked />
            <label for="smartContract"> Include Smart Contracts </label>
            <input type="checkbox" id="blockMetadata" name="Include Blockmetadata" />
            <label for="blockMetadata"  > Include Blockmetadata </label>
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
            </table> */}
        </div>
    );
}
