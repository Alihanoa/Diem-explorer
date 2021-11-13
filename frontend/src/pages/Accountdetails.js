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
        table += "<tr><th>Address</th><td>" + data[1].address + "</td></tr>";

        console.log(data);
        return table;
    }

    render(){

        return(
            <div>
                <h1>Account Details</h1>
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
