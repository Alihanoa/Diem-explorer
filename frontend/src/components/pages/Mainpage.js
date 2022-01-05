import React, { useState, useEffect } from "react";
import { Line } from 'react-chartjs-2'
import Searchbar from '../Searchbar'
import TransactionsTable from "../TransactionsTable";

export default function Mainpage(props) {

    const [currentDataset, setCurrentDataset] = useState([]);
    const [serverAdress, setServerAdress] = useState("localhost:8888");
    const [transactionData, setTransactionData] = useState([]);
    // serverAdress : "diemexplorer.internet-sicherheit.de:8888"

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

        initializeChart();

        // console.log(transactions_last_minute, tradingvolume, market_capacity, table, lasttenanything, lasttensmartcontracts, lasttentransactions);
    }, []);

    async function initializeChart() {

        console.log("initializeChart wird ausgeführt")

        // let chartdataTransactions365d = await fetch("http://localhost:8888/rest/datalast365days").then(result => result.json());
        // setState({currentDataset: chartdataTransactions365d});
        let chartdataTransactions365d = [
            ["04/01/2021 12:00", "1746"], ["05/01/2021 12:00", "36829"],
            ["06/01/2021 12:00", "17362"], ["07/01/2021 12:00", "67362"],
            ["08/01/2021 12:00", "97362"], ["09/01/2021 12:00", "27362"]
        ];
        setCurrentDataset(chartdataTransactions365d);

        console.log("currentDataset nach Initialisierung: " + currentDataset);
    }

    async function updateChartinterval(e) {

        console.log("updateChartInterval wird ausgeführt")

        let selectedInterval = e.target.value;
        console.log("selectedInterval: " + selectedInterval);

        switch (selectedInterval) {

            case "365days":
                // console.log("case 365 days wird ausgeführt")
                // let chartdataTransactions365d = await fetch("http://localhost:8888/rest/datalast365days").then(result => result.json());
                // setState({currentDataset: chartdataTransactions365d});
                let chartdataTransactions365d = [
                    ["04/01/2021 12:00", "1746"], ["05/01/2021 12:00", "36829"],
                    ["06/01/2021 12:00", "17362"], ["07/01/2021 12:00", "67362"],
                    ["08/01/2021 12:00", "97362"], ["09/01/2021 12:00", "27362"]
                ];
                setCurrentDataset(chartdataTransactions365d);
                break;

            case "30days":
                // console.log("case 30 days wird ausgeführt")
                // let chartdataTransactions30d = await fetch("http://localhost:8888/rest/datalastMonth").then(result => result.json());
                // setState({currentDataset: chartdataTransactions30d});
                let chartdataTransactions30d = [
                    ["04/01/2021 12:00", "1746"], ["05/01/2021 12:00", "36829"],
                    ["06/01/2021 12:00", "17362"]
                ];
                setCurrentDataset(chartdataTransactions30d);
                break;
        }
        console.log("currentDataset nach Befüllung: " + currentDataset);
    }

    // create table row for each object within the data array
    // function createTable(data) {

    //     let table = [];
    //     for (let i = data.length - 1; i >= data.length - 10; i--) {
    //         //let children = [];
    //         table += "<tr> <td><a href=Transactiondetails/" + data[i].version + ">" + data[i].version +
    //             "</a></td> <td><a href=Accountdetails/" + data[i].sender_id + ">"
    //             + data[i].sender_id + "</a></td>  <td>" + data[i].public_key + "</td> <td><a href=Accountdetails/" + data[i].receiver_id + ">"
    //             + data[i].receiver_id + "</a></td>  <td>" + data[i].amount + " " + data[i].currency + "</td> <td>"
    //             + data[i].gas_used + " " + data[i].gas_currency + "</td> <td>" + data[i].date + "</td> <td>" + data[i].type + "</td> </tr>";
    //     }
    //     console.log(data);
    //     return table;
    // }

    // var json_object = getData();
    // console.log(json_object);

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

            <div id="chart-wrapper">
                <caption>Developments</caption>
                <select id="chartdata">
                    <option value="transactions" selected>Transactions</option>
                    <option value="accounts">Accounts</option>
                    <option value="gasPrice">Gas Price</option>
                </select>
                <select id="chartinterval" onChange={(e) => { updateChartinterval(e) }}>
                    <option value="totalTime">Total time</option>
                    <option value="365days" selected>Last 365 days</option>
                    <option value="30days">Last 30 days</option>
                    <option value="7days">Last 7 days</option>
                    <option value="24hours">Last 24 hours</option>
                    <option value="60minutes">Last 60 minutes</option>
                    <option value="60seconds">Last 60 seconds</option>
                </select>
                <Line
                    data={{
                        datasets: [
                            {
                                data: currentDataset,
                                fill: true,
                                lineTension: 0.1,
                                backgroundColor: '#F0F3F2',
                                borderColor: '#42318C',
                                borderWidth: 2,
                                pointRadius: 0,
                                hitRadius: 2,
                                hoverRadius: 5
                            }
                        ]
                    }}
                    options={{
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                display: false
                            }
                        },
                        tooltips: {
                            enabled: false
                        }
                    }}
                />
            </div>
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
