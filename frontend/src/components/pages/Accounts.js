import React, { useState, useEffect } from "react";

export default function Accounts(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const serverAddress = "https://diemexplorer.internet-sicherheit.de:8888";
    const serverAddress = "http://localhost:8888";
    
    const [dataTable, setDataTable] = useState([]);
    const [order, setOrder] = useState("ASC");

    const sorting = (col) =>{
        if(order === 'ASC'){
            const sorted = [...dataTable].sort((a,b)=>
                a[col] > b[col] ? 1 : -1
            );
            setDataTable(sorted);
            setOrder("DSC");
        }else if(order === 'DSC'){
            const sorted = [...dataTable].sort((a,b)=>
                a[col] < b[col] ? 1 : -1
            );
            setDataTable(sorted);
            setOrder("ASC");
        }
    }

    useEffect(async () => {
        let table = createLoadingTable();
        document.getElementById("accounts").innerHTML = table;
        let data = await fetch(serverAddress + "/rest/accounts").then(result => result.json());
        setDataTable(data);
        table = createTable(data);
        document.getElementById("accounts").innerHTML = table;
    }, []);

    function createLoadingTable() {
        return ("<tr><td>Loading...</td><td>Loading...</td><td>Loading...</td><td>Loading...</td></tr>");
    }

    // Create table row for each object within the data array
    function createTable(data) {

        let table = [];
        for (let i = 0; i < data.length; i++) {
            // let children = [];
            table += "<tr> <td><a href=Accountdetails/" + data[i].address + ">" + data[i].address + "</a></td> <td>"
                + data[i].authentication_key + "</td>  <td>"
                + data[i].sequence_number + "</td>  <td>" + data[i].is_frozen + "</td> </tr>";
        }
        return table;
    }

    return (
        <div class="main-wrapper">
            <h1>Accounts</h1>

            <div class="caption-wrapper">
                <caption id="accounts-caption">Accounts</caption>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Address</th>
                        <th>Authentication Key</th>
                        <th onClick={() => sorting("sequence_number")}>Sequence Number</th>
                        <th>Frozen</th>
                    </tr>
                </thead>
                <tbody id="accounts">{
                    dataTable.map(entry => {
                        return (
                            <tr>
                                <td><a href={"/Accountdetails/" + entry.address}>{entry.address}</a></td>
                                <td>{entry.authentication_key}</td>
                                <td>{entry.sequence_number}</td>
                                <td>{entry.is_frozen}</td>
                            </tr>
                        )
                    })
                }</tbody>
            </table>
        </div>
    );
}
