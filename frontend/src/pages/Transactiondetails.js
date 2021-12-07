import React from "react";

class Transactiondetails extends React.Component{
    constructor(props){
        super(props);
    }

    async componentDidMount() {
        let data = await this.readData();
        let table = this.createTable(data);
        document.getElementById("transaction").innerHTML = table;
        console.log(table)
    }

    // Data gets fetched from the backend
    async readData(props) {
        let data = await fetch("http://localhost:8888/rest/transaction?version=" + this.props.match.params.version).then(result => result.json());
        return data;
    }

    // create table row for each object within the data array
    createTable(data) {

        let table = [];
        table += "<tr><td>From</td><td>" + data[0][0].sender_id + 
        "</td></tr><tr><td>Public Key</td><td>" + data[0][0].public_key + 
        "</td></tr><tr><td>To</td><td>" + data[0][0].receiver_id +
        "</td></tr><tr><td>Amount</td><td>" + data[0][0].amount + data[0][0].currency + 
        "</td></tr><tr><td>Gas Amount</td><td>" + data[0][0].gas_used + data[0][0].gas_currency + 
        "</td></tr><tr><td>Gas Unit Price</td><td>" + data[0][0].gas_unit_price +
        "</td></tr><tr><td>Maximal Gas Amount</td><td>" + data[0][0].max_gas_amount +
        "</td></tr><tr><td>Date</td><td>" + data[0][0].date +
        "</td></tr><tr><td>Type</td><td>" + data[0][0].type +
        "</td></tr><tr><td>Chain ID</td><td>" + data[1][0].chain_id +
        "</td></tr><tr><td>Hash</td><td>" + data[1][0].hash +
        "</td></tr><tr><td>Metadata</td><td>" + data[1][0].metadata +
        "</td></tr><tr><td>Metadata Signature</td><td>" + data[1][0].metadata_signature +
        "</td></tr><tr><td>Script Hash</td><td>" + data[1][0].script_hash +
        "</td></tr><tr><td>Abort Code</td><td>" + data[1][0].abort_code +
        "</td></tr><tr><td>Category</td><td>" + data[1][0].category +
        "</td></tr><tr><td>Category Description</td><td>" + data[1][0].category_description +
        "</td></tr><tr><td>Reason</td><td>" + data[1][0].reason +
        "</td></tr><tr><td>Reason Description</td><td>" + data[1][0].reason_description +
        "</td></tr><tr><td>Location</td><td>" + data[1][0].location +
        "</td></tr><tr><td>Expiration Date</td><td>" + data[1][0].expiration_date +
        "</td></tr>";
        {/*
        "</td></tr><tr><td>Signature Scheme</td><td>" + data[1][0].expiration_date +
        "</td></tr><tr><td>Secondary Signers</td><td>" + data[1][0].expiration_date +
        "</td></tr><tr><td>Secondary Signature Schemes</td><td>" + data[1][0].expiration_date +
        "</td></tr><tr><td>Secondary Signatures</td><td>" + data[1][0].expiration_date +
        "</td></tr><tr><td>Secondary Public Keys</td><td>" + data[1][0].expiration_date +
        */}
        

        console.log(data);
        return table;
    }

    render(){

        return(
            <div>
                <h1 id="main_title">Transaction Details</h1>
                <h2>Transaction Version {this.props.match.params.version}</h2>
                <table>
                    <thead></thead>
                    <tbody id="transaction"></tbody>
                </table>
            </div>
        );
    }
}
export default Transactiondetails;
