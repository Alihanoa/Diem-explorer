import React from "react";
import { Line } from 'react-chartjs-2'

class Mainpage extends React.Component {

    constructor(props) {
        super(props);
    }

    state = {
        chartdataTransactions : []
    }

    async componentDidMount() {
        let transactions_last_minute = await fetch("http://localhost:8888/rest/transactionstodate?date=28/04/2021").then(result => result.json());
        let tradingvolume = await fetch("http://localhost:8888/rest/tradingvolume?date=28/04/2021").then(result => result.json());
        let market_capacity = await fetch("http://localhost:8888/rest/sumbalances").then(result => result.json());

        // let data = await this.readData();
        
        let data = await fetch('http://localhost:8888/rest/transactions').then(result => result.json());
        // let lasttenanything = await fetch("http://localhost:8888/rest/lastten").then(result  => result.json());
        // let lasttensmartcontracts = await fetch("http://localhost:8888/rest/lasttensmartcontracts").then(result => result.json());
        // let lasttentransactions = await fetch("http://localhost:8888/rest/lasttenreal").then(result => result.json());

        this.setState({chartdataTransactions : data});
        
        // let table = this.createTable(lasttentransactions);
        let table = this.createTable(data);

        document.getElementById("transactions_last_minute").innerHTML = transactions_last_minute;
        document.getElementById("tradingvolume").innerHTML = tradingvolume + " XUS";
        document.getElementById("market_capacity").innerHTML = market_capacity + " XUS";

        document.getElementById("transactions").innerHTML = table;

        // console.log(transactions_last_minute, tradingvolume, market_capacity, table, lasttenanything, lasttensmartcontracts, lasttentransactions);
    }

    addData(chart, data) {

        this.chart.data.forEach((dataset) => {
            dataset.data.push(data);
        });
        this.chart.update();
    }

    // create table row for each object within the data array
    createTable(data) {

        let table = [];
        for (let i = data.length - 1; i >= data.length - 10; i--) { 
            //let children = [];
            table += "<tr> <td><a href=Transactiondetails/" + data[i].version + ">" + data[i].version + 
            "</a></td> <td><a href=Accountdetails/" + data[i].sender_id + ">"
                + data[i].sender_id + "</a></td>  <td>" + data[i].public_key + "</td> <td><a href=Accountdetails/" + data[i].receiver_id + ">"
                + data[i].receiver_id + "</a></td>  <td>" + data[i].amount + " " + data[i].currency + "</td> <td>"
                + data[i].gas_used + " " + data[i].gas_currency + "</td> <td>" + data[i].date + "</td> <td>" + data[i].type + "</td> </tr>";
        }
        console.log(data);
        return table;
    }

    render() {

        // var json_object = this.getData();
        // console.log(json_object);

        return (
            <div>
                <h1 id="main_title">Diem Explorer</h1>

                <form class="search">
                    <input type="search" placeholder="Search..." name="search_bar" id="search_bar"></input>
                    {/* <button type="submit" name="search_button" id="search_button"></button> */}
                </form>
                <br></br>
                <br></br>
                <table id="general_information">
                    <caption>General Information</caption>
                    <thead>
                        <tr>
                            <td>Average gas unit price: 0</td>
                            <td>Transactions in the last minute: <div id="transactions_last_minute"></div></td>
                            <td>Trading volume today: <div id="tradingvolume"/></td>
                            <td>Market capacity:  <div id="market_capacity"/></td>
                        </tr>
                    </thead>
                </table>
                <br></br>

                <div id="chart-wrapper">
                    <caption>Developments</caption>
                    <Line
                    data={{
                        labels: ['January', 'February', 'March', 'April', 'May', 'June',
                                'July', 'August', 'September', 'October', 'November', 'December'],
                        datasets: [
                            {
                                label: 'Transactions',
                                data: this.state.chartdataTransactions,
                                fill: true,
                                lineTension: 0.5,
                                backgroundColor: '#F0F3F2',
                                borderColor: '#42318C',
                                borderWidth: 2
                            },
                            // {
                            //     label: 'Accounts',
                            //     // data: this.state.chartdataAccounts,
                            //     fill: true,
                            //     lineTension: 0.5,
                            //     backgroundColor: '#F0F3F2',
                            //     borderColor: '#42318C',
                            //     borderWidth: 2
                            // },
                            // {
                            //     label: 'Gas price',
                            //     // data: this.state.chartdataGasPrice,
                            //     fill: true,
                            //     lineTension: 0.5,
                            //     backgroundColor: '#F0F3F2',
                            //     borderColor: '#42318C',
                            //     borderWidth: 2
                            // }
                        ]
                    }}
                    options={{
                        maintainAspectRatio: false,
                        options: {
                            // tooltips: {
                            //    mode: 'index',
                            //    intersect: false
                            // },
                            // hover: {
                            //    mode: 'index',
                            //    intersect: false
                            // }
                         }
                    }}
                    />
                </div>
                <br></br>
                <br></br>
                <br></br>

                <input type="checkbox" id="smartContract" name="Include Smart Contracts" defaultChecked/>
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
                </table>
                <br></br>
                <br></br>
            </div>
        );
    }
}
export default Mainpage;
