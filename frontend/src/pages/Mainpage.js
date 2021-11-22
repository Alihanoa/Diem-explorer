import React from "react";

class Mainpage extends React.Component {
    constructor(props) {
        super(props);
    }

    async componentDidMount() {
        let data = await this.readData();
        let table = this.createTable(data);
        document.getElementById("transactions").innerHTML = table;
        console.log(table)
    }

    // Data gets fetched from the backend
    async readData() {
        let data = await fetch('http://localhost:8888/rest/transactions').then(result => result.json());
        return data;
    }

    // create table row for each object within the data array
    createTable(data) {

        let table = [];
        for (let i = data.length - 1; i >= data.length - 10; i--) { 
            //let children = [];
            table += "<tr> <td><a href=Transactiondetails/" + data[i].version + ">" + data[i].version + "</a></td> <td>" 
                + data[i].sender_id + "</td>  <td>" + data[i].public_key + "</td> <td>"
                + data[i].receiver_id + "</td>  <td>" + data[i].amount + " " + data[i].currency + "</td> <td>"
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
                            <td>Average gas price: </td>
                            <td>Transaction per hour: </td>
                            <td>Hash rate: </td>
                            <td>Market capacity:  Diem USD</td>
                        </tr>
                    </thead>
                </table>
                <br></br>
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
