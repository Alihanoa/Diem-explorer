import React, { useState, useEffect } from "react";

export default function Accounts(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    useEffect(async () => {
        let data = await fetch(serverAddress + "/rest/accounts").then(result => result.json());
        let table = createTable(data);
        document.getElementById("accounts").innerHTML = table;
        console.log(table)
    }, []);

    // create table row for each object within the data array
    function createTable(data) {

        let table = [];
        for (let i = 0; i < data.length; i++) {
            // let children = [];
            table += "<tr> <td><a href=Accountdetails/" + data[i].address + ">" + data[i].address + "</a></td> <td>"
                + data[i].authentication_key + "</td>  <td>"
                + data[i].sequence_number + "</td>  <td>" + data[i].is_frozen + "</td> </tr>";
        }
        console.log(data);
        return table;
    }

    return (
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
                    </tr>
                </thead>
                <tbody id="accounts">

                </tbody>
            </table>
        </div>
    );
}
