import React from "react";

class Accountdetails extends React.Component{
    constructor(props){
        super(props);
    }

    async componentDidMount() {
        let data = await this.readData();
        let data_transactions = await this.readData_2();
        let table = this.createTable(data);
        let transactions_table = this.createTransactionsTable(data_transactions);
        document.getElementById("account").innerHTML = table;
        document.getElementById("transactions").innerHTML = transactions_table;
        console.log(table, transactions_table)
    }

    // Data gets fetched from the backend
    async readData(props) {
        let data = await fetch("http://localhost:8888/rest/account?address=" + this.props.match.params.address).then(result => result.json());
        return data;
    }

    async readData_2(props) {
        let data_transactions = await fetch('http://localhost:8888/rest/transactions').then(result => result.json());
        return data_transactions;
    }

    //create table row for each object within the data array
    createTable(data) {

        let table = [];
        table += "<tr><td>Authentication Key</td><td>" + data[0].authentication_key + 
        "</td></tr><tr><td>Sequence Number</td><td>" + data[0].human_name + 
        "</td></tr><tr><td>Frozen</td><td>" + data[0].sequence_number + 
        "</td></tr><tr><td>Human Name</td><td>" + data[0].is_frozen + 
        "</td></tr><tr><td>Balance</td><td>" + data[2].amount + 
        "</td></tr><tr><td>Sent Events Keys</td><td>" + data[1].sent_events_key + 
        "</td></tr><tr><td>Receive Event Keys</td><td>" + data[1].receive_events_key + 
        "</td></tr><tr><td>Role Type</td><td>" + data[1].rtype + 
        "</td></tr><tr><td>Parent Vasp Name</td><td>" + data[1].parent_vasp_name + 
        "</td></tr><tr><td>Base URL</td><td>" + data[1].base_url + 
        "</td></tr><tr><td>Expiration Time</td><td>" + data[1].expiration_time + 
        "</td></tr><tr><td>Compliance Key</td><td>" + data[1].compliance_key + 
        "</td></tr><tr><td>Received Mint Events Key</td><td>" + data[1].received_mint_events_key + 
        "</td></tr><tr><td>Compliance Key Rotation Events Key</td><td>" + data[1].compliance_key_rotation_events_key + 
        "</td></tr><tr><td>Base URL Rotation Events Key</td><td>" + data[1].base_url_rotation_events_key + 
        // "</td></tr><tr><td>Preburn Balance USD</td><td>" + data[1].preburn_balancexus + 
        // "</td></tr><tr><td>First Seen</td><td>" + data[1].amount + 
        // "</td></tr><tr><td>Last Seen</td><td>" + data[1].amount + 
        // "</td></tr><tr><td>Blockchain Version</td><td>" + data[1].amount + 
        "</td></tr>";

        console.log(data);
        return table;
    }

    createTransactionsTable(data) {

        let table = [];
        for (let i = data.length - 1; i >= 0; i--) {
            if(data[i].sender_id == this.props.match.params.address || data[i].receiver_id == this.props.match.params.address){
                table += "<tr> <td><a href=Transactiondetails/" + data[i].version + ">" + data[i].version + "</a></td> <td>"
                + data[i].sender_id + "</td>  <td>" + data[i].public_key + "</td> <td>"
                + data[i].receiver_id + "</td>  <td>" + data[i].amount + " " + data[i].currency + "</td> <td>"
                + data[i].gas_used + " " + data[i].gas_currency + "</td> <td>" + data[i].date + "</td> <td>" + data[i].type + "</td> </tr>";
            }
        }
        console.log(data);
        return table;
    }

    render(){

        return(
            <div>
                <h1>Account Details</h1>
                <h2>Account Address {this.props.match.params.address}</h2>
                <table>
                    <thead>

                    </thead>

                    <tbody id="account">
                        
                    </tbody>
                </table>

                <p/>

                <table>
                    <caption>Transactions of this Account</caption>
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
}
export default Accountdetails;
