import React, { useState, useEffect } from "react";
import Searchbar from '../Searchbar'
import Chart from "../Chart";
import TableTransactionsMainpage from "../TableTransactionsMainpage";

export default function Mainpage(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    useEffect(async () => {

        let transactions_last_minute = await fetch(serverAddress + "/rest/transactionstodate?date=28/04/2021").then(result => result.json());
        let tradingvolume = await fetch(serverAddress + "/rest/tradingvolume?date=28/04/2021").then(result => result.json());
        let market_capacity = await fetch(serverAddress + "/rest/sumbalances").then(result => result.json());

        document.getElementById("transactions_last_minute").innerHTML = transactions_last_minute.toLocaleString();
        document.getElementById("tradingvolume").innerHTML = tradingvolume.toLocaleString() + " XUS";
        document.getElementById("market_capacity").innerHTML = market_capacity.toLocaleString() + " XUS";

    }, []);

    return (
        <div>
            <h1 id="main_title">Diem Explorer</h1>
            <Searchbar/>
            <br></br>
            <br></br>
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
            <TableTransactionsMainpage/>
            <br></br>
        </div>
    );
}
