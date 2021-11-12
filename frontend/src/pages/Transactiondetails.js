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
        table += "<tr> <td>Version</td><td>" + this.readData.version + "</td> </tr>";

        console.log(data);
        return table;
    }

    render(){

        return(
            <div>
                <h1>Transaction Details</h1>
                <table border="3">
                    <thead>
                        
                    </thead>

                    <tbody id="transaction">
                        {/* <tr>
                            <th>Version</th>
                            <td></td>
                        </tr> */}
                        {/* <tr>
                            <td>Sender_ID</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Receiver_ID</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>public_key</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Amount</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Currency</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Gas-Amount</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Gas-Currency</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Date</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>type</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>chaid-id</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>hash</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>metadata</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>metadata_signature</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>script_hash</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>abort_code</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>category</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>category_description</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>reason</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>reasen_description</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>location</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>VM status type</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>expiration_date</td>
                            <td></td>
                        </tr> */}
                    </tbody>
                </table>
            </div>
        );
    }
}
export default Transactiondetails;
