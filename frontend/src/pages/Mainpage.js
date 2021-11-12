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
            table = table + "<tr> <td>" + data[i].version + "</td>  <td>" + data[i].sender_id + "</td>  <td>"
                + data[i].receiver_id + "</td>  <td>" + data[i].amount + "</td>  <td> " + data[i].currency + "</td> <td>"
                + data[i].gas_used + "</td> <td> " + data[i].gas_currency + "</td> <td>" + data[i].date + "</td> <td>" + data[i].type + "</td> </tr>";
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

                <form>
                    <input type="search" name="search_bar" id="search_bar"></input>
                </form>

                <table border="3">
                    <caption>Latest Transactions</caption>
                    <thead>
                        <tr>
                            <th>Version</th>
                            <th>Sender_ID</th>
                            <th>Receiver_ID</th>
                            <th>Amount</th>
                            <th>Currency</th>
                            <th>Gas-Amount</th>
                            <th>Gas-Currency</th>
                            <th>Date</th>
                            <th>type</th>
                        </tr>
                    </thead>
                    <tbody id="transactions">

                    </tbody>
                </table>
            </div>
        );
    }
}
export default Mainpage;
