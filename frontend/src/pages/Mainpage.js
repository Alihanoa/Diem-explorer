import React from "react";
import { Line } from 'react-chartjs-2'

class Mainpage extends React.Component {
    
    constructor(props) {
        super(props);
        
    }

    state = {
        currentDataset: [],
        // serverAdress : "diemexplorer.internet-sicherheit.de:8888"
        serverAdress : "localhost:8888",
        searchfieldvalue : "",
        choiceboxvalue : "Address",
    }

    
    async componentDidMount() {
        
        let transactions_last_minute = await fetch("http://" + this.state.serverAdress +"/rest/transactionstodate?date=28/04/2021").then(result => result.json());
        let tradingvolume = await fetch("http://" + this.state.serverAdress +"/rest/tradingvolume?date=28/04/2021").then(result => result.json());
        let market_capacity = await fetch("http://" + this.state.serverAdress +"/rest/sumbalances").then(result => result.json());

        // let data = await fetch('http://localhost:8888/rest/transactions').then(result => result.json());
        // let lasttenanything = await fetch("http://localhost:8888/rest/lastten").then(result  => result.json());

        // let data = await fetch('http://localhost:8888/rest/transactions').then(result => result.json());
        let data = await fetch("http://" + this.state.serverAdress +"/rest/lastten").then(result  => result.json());

        // let lasttensmartcontracts = await fetch("http://localhost:8888/rest/lasttensmartcontracts").then(result => result.json());
        // let data = await fetch("http://localhost:8888/rest/lasttenreal").then(result => result.json());

        // let table = this.createTable(lasttentransactions);
        let table = this.createTable(data);

        document.getElementById("transactions_last_minute").innerHTML = transactions_last_minute.toLocaleString();
        document.getElementById("tradingvolume").innerHTML = tradingvolume.toLocaleString() + " XUS";
        document.getElementById("market_capacity").innerHTML = market_capacity.toLocaleString() + " XUS";

        document.getElementById("transactions").innerHTML = table;

        this.initializeChart();

        // console.log(transactions_last_minute, tradingvolume, market_capacity, table, lasttenanything, lasttensmartcontracts, lasttentransactions);
    }

    async initializeChart() {
        
        console.log("initializeChart wird ausgeführt")

        // let chartdataTransactions365d = await fetch("http://localhost:8888/rest/datalast365days").then(result => result.json());
        // this.setState({currentDataset: chartdataTransactions365d});
        let chartdataTransactions365d = [
            ["04/01/2021 12:00", "1746"], ["05/01/2021 12:00", "36829"],
            ["06/01/2021 12:00", "17362"], ["07/01/2021 12:00", "67362"],
            ["08/01/2021 12:00", "97362"], ["09/01/2021 12:00", "27362"]
        ];
        this.setState({currentDataset: chartdataTransactions365d});
                
        console.log("currentDataset nach Initialisierung: " + this.state.currentDataset);
    }

    async updateChartinterval(e) {
        
        console.log("updateChartInterval wird ausgeführt")

        let selectedInterval = e.target.value;
        console.log("selectedInterval: " + selectedInterval);

        switch(this.selectedInterval) {

            case "365days":
                // console.log("case 365 days wird ausgeführt")
                // let chartdataTransactions365d = await fetch("http://localhost:8888/rest/datalast365days").then(result => result.json());
                // this.setState({currentDataset: chartdataTransactions365d});
                let chartdataTransactions365d = [
                    ["04/01/2021 12:00", "1746"], ["05/01/2021 12:00", "36829"],
                    ["06/01/2021 12:00", "17362"], ["07/01/2021 12:00", "67362"],
                    ["08/01/2021 12:00", "97362"], ["09/01/2021 12:00", "27362"]
                ];
                this.setState((state) => {return {currentDataset: chartdataTransactions365d}});
                break;

            case "30days":
                // console.log("case 30 days wird ausgeführt")
                // let chartdataTransactions30d = await fetch("http://localhost:8888/rest/datalastMonth").then(result => result.json());
                // this.setState({currentDataset: chartdataTransactions30d});
                let chartdataTransactions30d = [
                    ["04/01/2021 12:00", "1746"], ["05/01/2021 12:00", "36829"],
                    ["06/01/2021 12:00", "17362"]
                ];
                this.setState((state) => {return {currentDataset: chartdataTransactions30d}});
                break;
        }
        console.log("currentDataset nach Befüllung: " + this.state.currentDataset);
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
                    <input type="search" placeholder="Search..." name="search_bar" id="search_bar" onChange= {(e) =>
                         {this.state.searchfieldvalue = e.target.value; console.log(this.state.searchfieldvalue)}
                         }>
                         </input>
                    <button name="search_button" id="search_button" onClick={(e) =>{
                            e.preventDefault();
                            e.nativeEvent.stopPropagation();
                            e.nativeEvent.stopImmediatePropagation();
                            if(this.state.choiceboxvalue === "Address" && this.state.searchfieldvalue != ""){
                                window.location.href = "http://localhost:3000/Accountdetails/" + this.state.searchfieldvalue;
                            }
                            else if(this.state.choiceboxvalue === "Transactionnumber" && this.state.searchfieldvalue != ""){
                                this.props.history.push("/Transactiondetails/"+this.state.searchfieldvalue);
                            }
                            else if(this.state.choiceboxvalue === "Date" && this.state.searchfieldvalue != ""){
                                window.location.href ="http://localhost:3000/Transactions/" + this.state.searchfieldvalue + "/" +  this.state.choiceboxvalue;
                            }
                            else if(this.state.choiceboxvalue === "Amount greater than" && this.state.searchfieldvalue != ""){
                                window.location.href ="http://localhost:3000/Transactions/" + this.state.searchfieldvalue;
                            }
                            else if(this.state.choiceboxvalue === "Amount less than" && this.state.searchfieldvalue!= ""){
                                window.location.href = "http://localhost:3099/Transactions/" + this.state.searchfieldvalue;
                            }
                        }
                    }></button>
                    <select onChange={(e) => {
                        this.state.choiceboxvalue = e.target.value; console.log(this.state.choiceboxvalue);
                    }}>
                        <option>Address</option>
                        <option>Transactionnumber</option>
                        <option>Amount greater than</option>
                        <option>Amount less than</option>
                        <option>Date</option>
                    </select>
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
                    <select id="chartdata">
                        <option value="transactions" selected>Transactions</option>
                        <option value="accounts">Accounts</option>
                        <option value="gasPrice">Gas Price</option>
                    </select>
                    <select id="chartinterval" onChange={(e)=> {this.updateChartinterval(e)}}>
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
                                data: this.state.currentDataset,
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
            </div>
        );
    }
}
export default Mainpage;
