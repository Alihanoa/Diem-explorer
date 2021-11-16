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
        table += "<tr><th>Address</th><td>" + data[0].address + 
        "</td></tr><tr><td>Authentication Key</td><td>" + data[0].authentication_key + 
        "</td></tr><tr><td>Human name Key</td><td>" + data[0].human_name + 
        "</td></tr><tr><td>Sequence Number</td><td>" + data[0].sequence_number + 
        "</td></tr><tr><td>Is frozen</td><td>" + data[0].is_frozen + 
        // "</td></tr><tr><td>Amount</td><td>" + data[1].amount + 
        "</td></tr>";

        console.log(data);
        return table;
    }

    render(){

        return(
            <div>
                <h1 id="main_title">Account Details</h1>
                <table border="3">
                    <thead>

                    </thead>

                    <tbody id="account">
                        
                    </tbody>
                </table>
            </div>
        );
    }
}
export default Accountdetails;
