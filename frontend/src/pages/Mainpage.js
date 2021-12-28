import React from "react";
import { Line } from 'react-chartjs-2'

class Mainpage extends React.Component {
    
    constructor(props) {
        super(props);
        
    }

    state = {
        chartdataTransactions : [],
        // serverAdress : "diemexplorer.internet-sicherheit.de:8888"
        serverAdress : "localhost:8888",
        searchfieldvalue : "",
        choiceboxvalue : "Address",
    }

    
    async componentDidMount() {
        
        let transactions_last_minute = await fetch("http://" + this.state.serverAdress +"/rest/transactionstodate?date=28/04/2021").then(result => result.json());
        let tradingvolume = await fetch("http://" + this.state.serverAdress +"/rest/tradingvolume?date=28/04/2021").then(result => result.json());
        let market_capacity = await fetch("http://" + this.state.serverAdress +"/rest/sumbalances").then(result => result.json());
        

        let rawChartdataTransactions = {}//await fetch("http://" + this.state.serverAdress +"/rest/datalastWeek").then(result => result.json());
        let chartdataTransactions = this.processChartdata(rawChartdataTransactions);
        this.setState({chartdataTransactions : rawChartdataTransactions});
        // this.setState({chartlabelsTransactions : rawChartdataTransactions});

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

        // console.log(transactions_last_minute, tradingvolume, market_capacity, table, lasttenanything, lasttensmartcontracts, lasttentransactions);
    }

    processChartdata(data) {

        let processedData = [];
        for (let i = 0; i < data.length; i++) {
            processedData[i] = data[i][1];
        }
    }


    // addData(chart, data) {

    //     this.chart.data.forEach((dataset) => {
    //         dataset.data.push(data);
    //     });
    //     this.chart.update();
    // }

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
                    <Line
                    data={{
                        // labels: [this.state.chartlabelsTransactions[1], this.state.chartlabelsTransactions[2],
                        //          this.state.chartlabelsTransactions[3], this.state.chartlabelsTransactions[4],
                        //          this.state.chartlabelsTransactions[5], this.state.chartlabelsTransactions[6],
                        //          this.state.chartlabelsTransactions[7], this.state.chartlabelsTransactions[8],
                        //          this.state.chartlabelsTransactions[9], this.state.chartlabelsTransactions[10],
                        //          this.state.chartlabelsTransactions[11], this.state.chartlabelsTransactions[12]],
                        datasets: [
                            {
                                label: 'Transactions',
                                data: this.state.chartdataTransactions,
                                fill: true,
                                lineTension: 0.1,
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
            </div>
        );
    }
}
export default Mainpage;
