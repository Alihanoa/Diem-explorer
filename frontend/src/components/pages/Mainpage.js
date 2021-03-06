import React, { useState, useEffect } from "react";
import Searchbar from "../Searchbar";
import Chart from "../Chart";
import TableTransactionsMainpage from "../TableTransactionsMainpage";

export default function Mainpage(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const serverAddress = "https://diemexplorer.internet-sicherheit.de:8888";
    const serverAddress = "http://localhost:8888";

    const averageGasUnitPrice = 0;
    const [transactionsToday, setTransactionstoday] = useState("Loading...");
    const [tradingvolumeToday, setTradingvolumeToday] = useState("Loading...");
    const [marketCapacity, setMarketCapacity] = useState("Loading...");

    useEffect(async () => {

        let dataTransactionsToday = await fetch(serverAddress + "/rest/transactionstodate?date=28/04/2021").then(result => result.json());
        let dataTradingvolumeToday = await fetch(serverAddress + "/rest/tradingvolume?date=28/04/2021").then(result => result.json());
        let dataMarketCapacity = await fetch(serverAddress + "/rest/sumbalances").then(result => result.json());

        setTransactionstoday(dataTransactionsToday.toLocaleString());
        setTradingvolumeToday(dataTradingvolumeToday.toLocaleString() + " XUS");
        setMarketCapacity(dataMarketCapacity.toLocaleString() + " XUS");

    }, []);

    return (
        <div class="main-wrapper">
            <h1>Diem Explorer</h1>
            <Searchbar/>
            <br></br>
            <br></br>
            <div class="caption-wrapper">
                <caption>General&nbsp;Information</caption>
            </div>
            <table id="general-information">
                <thead>
                    <tr>
                        <th>Average Gas Unit Price</th>
                        <th>Transactions today</th>
                        <th>Trading Volume today</th>
                        <th>Market Capacity</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><div id="averageGasUnitPrice">{averageGasUnitPrice}</div></td>
                        <td><div id="transactionsToday">{transactionsToday}</div></td>
                        <td><div id="tradingvolumeToday"/>{tradingvolumeToday}</td>
                        <td><div id="marketCapacity"/>{marketCapacity}</td>
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
