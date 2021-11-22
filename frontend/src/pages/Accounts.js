import React from "react";

class Accounts extends React.Component{
    constructor(props){
        super(props);
    }

    async componentDidMount() {
        let data = await this.readData();
        let table = this.createTable(data);
        document.getElementById("accounts").innerHTML = table;
        console.log(table)
    }

    // Data gets fetched from the backend
    async readData() {
        let data = await fetch('http://localhost:8888/rest/accounts').then(result => result.json());
        return data;
    }

    // create table row for each object within the data array
    createTable(data) {

        let table = [];
        for (let i = 0; i < data.length; i++) { 
            // let children = [];
            table += "<tr> <td><a href=Accountdetails/" + data[i].address + ">" + data[i].address + "</a></td> <td>" 
                + data[i].authentication_key + "</td>  <td>"
                + data[i].human_name + "</td>  <td>" + data[i].sequence_number + "</td>  <td> " + data[i].is_frozen + "</td> </tr>";
        }
        console.log(data);
        return table;
    }

    render(){

        return(
            <div>
            <h1 id="main_title">Accounts</h1>
            
            <table>
                <caption>Accounts</caption>
                <thead>
                    <tr>
                        <th>Address</th>
                        <th>Authentication Key</th>
                        <th>Sequence Number</th>
                        <th>Frozen</th>
                        <th>Human Name</th>
                    </tr>
                </thead>
                <tbody id="accounts">

                </tbody>
            </table>
            <br></br>
            <br></br>
            <br></br>
            </div>
        );
    }
}
export default Accounts;
