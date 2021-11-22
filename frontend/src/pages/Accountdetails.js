import React from "react";

class Accountdetails extends React.Component{
    constructor(props){
        super(props);
    }

    async componentDidMount() {
        let data = await this.readData();
        let table = this.createTable(data);
        document.getElementById("account").innerHTML = table;
        console.log(table)
    }

    // Data gets fetched from the backend
    async readData(props) {
        let data = await fetch("http://localhost:8888/rest/account?address=" + this.props.match.params.address).then(result => result.json());
        return data;
    }

    //create table row for each object within the data array
    createTable(data) {

        let table = [];
        table += "<tr><td>Address</td><td>" + data[0].address + 
        "</td></tr><tr><td>Authentication Key</td><td>" + data[0].authentication_key + 
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
        "</td></tr><tr><td>Preburn Balance USD</td><td>" + data[1].preburn_balancexus + 
        // "</td></tr><tr><td>First Seen</td><td>" + data[1].amount + 
        // "</td></tr><tr><td>Last Seen</td><td>" + data[1].amount + 
        // "</td></tr><tr><td>Blockchain Version</td><td>" + data[1].amount + 
        "</td></tr>";

        console.log(data);
        return table;
    }

    render(){

        return(
            <div>
                <h1 id="main_title">Account Details</h1>
                <table>
                    <thead>

                    </thead>

                    <tbody id="account">
                        
                    </tbody>
                </table>
                <br></br>
                <br></br>
            </div>
        );
    }
}
export default Accountdetails;
