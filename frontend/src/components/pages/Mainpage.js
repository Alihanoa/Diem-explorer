import React, { useState, useEffect } from "react";
import { Line } from 'react-chartjs-2'
import Searchbar from '../Searchbar'
import TransactionsTable from "../TransactionsTable";
import Chart from "../Chart";

export default function Mainpage(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    const [transactionData, setTransactionData] = useState([]);


    useEffect(async () => {

        let transactions_last_minute = await fetch(serverAddress + "/rest/transactionstodate?date=28/04/2021").then(result => result.json());
        let tradingvolume = await fetch(serverAddress + "/rest/tradingvolume?date=28/04/2021").then(result => result.json());
        let market_capacity = await fetch(serverAddress + "/rest/sumbalances").then(result => result.json());

        // let data = await fetch(serverAddress + "/rest/transactions").then(result => result.json());
        // let lasttenanything = await fetch(serverAddress + "/rest/lastten").then(result  => result.json());

        // let data = await fetch(serverAddress + "/rest/transactions").then(result => result.json());
        let data = await fetch(serverAddress + "/rest/lastten").then(result => result.json());
        setTransactionData(data);
        // let lasttensmartcontracts = await fetch(serverAddress + "/rest/lasttensmartcontracts").then(result => result.json());
        // let data = await fetch(serverAddress + "/rest/lasttenreal").then(result => result.json());

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
            <Searchbar/>
            <br></br>
            <br></br>
            {/* <table id="general_information">
                <caption>General Information</caption>
                <thead>
                    <tr>
                        <td>Average gas unit price: 0</td>
                        <td>Transactions in the last minute: <div id="transactions_last_minute"></div></td>
                        <td>Trading volume today: <div id="tradingvolume"/></td>
                        <td>Market capacity:  <div id="market_capacity"/></td>
                    </tr>
                </thead>
            </table> */}
            <table>
            <caption>General Information</caption>
                <thead>
                    <tr>
                        <th>Average Gas Unit Price</th>
                        <th>Transactions in the last Minute</th>
                        <th>Trading Volume today</th>
                        <th>Market Capacity</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>0</td>
                        <td><div id="transactions_last_minute"></div></td>
                        <td><div id="tradingvolume"/></td>
                        <td><div id="market_capacity"/></td>
                    </tr>
                </tbody>
            </table>
            <br></br>
            <br></br>
            <Chart/>        
            <br></br>
            <br></br>
            <TransactionsTable data={transactionData} />
            <br></br>
            <br></br>
        </div>
    );
}
