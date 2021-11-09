import React from "react";

class Mainpage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            "version": "1", 
            "sender_ID": "Doe",
            "receiver_ID": "Boss",
            "Amount": 23
        }
    }

    createTable(){
        // fetch('http://localhost:8888/rest/transactions')
        // .then(result => result.json())
        // .then(json => {
        //             console.log(json)
        // }

        let table = [];
        for(let i = 0; i < 10; i++) {
            //let children = [];
            table.push(<tr>
                <td>{this.state.version}</td>
                <td>{this.state.sender_ID}</td>
                <td>{this.state.receiver_ID}</td>
                <td>{this.state.Amount}</td>
                </tr>);
        }
        return table;
    }

    render() {

        // var json_object = this.getData();
        // console.log(json_object);

        return (
            <body>
                <h1 id="main_title">Diem Explorer</h1>

                <form>
                    <input type="search" name="search_bar" id="search_bar"></input>
                </form>

                <table border="3">
                    <caption>Latest Transactions</caption>
                    <tr>
                        <th>Version</th>
                        <th>Sender_ID</th>
                        <th>Receiver_ID</th>
                        <th>Amount</th>
                        <th>Currency</th>
                        <th>Gas-Amount</th>
                        <th>Gas-Currency</th>
                        <th>Expiration</th>
                        <th>Sequence-Number</th>
                    </tr>
                    
                        {this.createTable()}

                        </table>
                        </body>
                            );
    }
}
                            export default Mainpage;
