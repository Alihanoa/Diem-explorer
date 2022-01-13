import React, { useState, useEffect, useRef, useCallback } from "react";
import TransactionsTable from "../TransactionsTable";
import axios from "axios";

export default function Accountdetails(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    const[data, setData]= useState([]);
    const[lastRowVersion, setLastRowVersion] = useState(0);
    const[counter, setCounter] = useState(0);
    const observer = useRef();


    const options = {
        threshhold : 1
    }

    const lastElement = useCallback(() => {

        if(observer.current) observer.current.disconnect();
        
        observer.current = new IntersectionObserver(async (entries) => {
            
            if(entries[0].isIntersecting && counter > 0){
                let newData = await axios.get(serverAddress + "/rest/nexttentransactionsofaccount?address=" + props.match.params.address + "&version=" +lastRowVersion)
                console.log(newData.data);
                // console.log(lastRowVersion)
                // console.log(data);

                let concateddata = [].concat(data).concat(newData.data);
                setLastRowVersion(newData.data[newData.data.length -1].version);
                setData(concateddata);
            }
        },options)
        observer.current.observe(document.getElementById("last"));
    });

    useEffect(async () => {

        let data = await fetch(serverAddress + "/rest/account?address=" + props.match.params.address).then(result => result.json());
        if(counter === 0){
        let data_transactions = await fetch(serverAddress + "/rest/lasttentransactionsofaccount?address=" + props.match.params.address).then(result => result.json());
        setData(data_transactions);
        setCounter(prevCounter => prevCounter +1 );
        setLastRowVersion(data_transactions[data_transactions.length -1].version)
        }

        let table = createTable(data);
        
        document.getElementById("account").innerHTML = table;
    },[]);

    //create table row for each object within the data array
    function createTable(data) {

        let table = [];
        table += "<tr><td>Authentication Key</td><td>" + data[0].authentication_key +
            "</td></tr><tr><td>Sequence Number</td><td>" + data[0].sequence_number +
            "</td></tr><tr><td>Frozen</td><td>" + data[0].is_frozen +
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
            // "</td></tr><tr><td>Human Name</td><td>" + data[0].human_name + 
            // "</td></tr><tr><td>Preburn Balance USD</td><td>" + data[1].preburn_balancexus + 
            // "</td></tr><tr><td>First Seen</td><td>" + data[1].amount + 
            // "</td></tr><tr><td>Last Seen</td><td>" + data[1].amount + 
            // "</td></tr><tr><td>Blockchain Version</td><td>" + data[1].amount + 
            "</td></tr>";
        return table;
    }

    return (
        <div>
            <h1>Account Details</h1>
            <h2>Account Address {props.match.params.address}</h2>
            <table>
                <thead>

                </thead>

                <tbody id="account">

                </tbody>
            </table>

            

           <TransactionsTable data={data} />
           <p ref ={lastElement} id="last"></p>
        </div>
    )
}