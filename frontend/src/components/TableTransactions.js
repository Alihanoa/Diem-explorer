import React, { useState, useEffect, useRef, useCallback } from "react";
import axios from "axios";

export default function TableTransactions(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    const [dataTable, setDataTable] = useState([]);
    const [lastRowVersion, setLastRowVersion] = useState(0);
    const [counter, setCounter] = useState(0);
    const observer = useRef();

    const options = {
        threshhold: 0
    }

    const lastElement = useCallback(() => {

        if (observer.current) observer.current.disconnect();

        observer.current = new IntersectionObserver(async (entries) => {

            if (entries[0].isIntersecting && counter > 0) {
                let newDataTransactions = await axios.get(serverAddress + "/rest/getnextten?lastVersionNumber=" + lastRowVersion);
                newDataTransactions.data = newDataTransactions.data.reverse();
                let combinedData = [].concat(dataTable).concat(newDataTransactions.data);
                setLastRowVersion(newDataTransactions.data[newDataTransactions.data.length - 1].version);
                setDataTable(combinedData);
            }
        }, options)
        observer.current.observe(document.getElementById("last"));
    });

    useEffect(async () => {

        if (counter === 0) {
            let dataTransactions = await fetch(serverAddress + "/rest/getlast50").then(result => result.json());
            setDataTable(dataTransactions);
            setLastRowVersion(dataTransactions[dataTransactions.length - 1].version);
            setCounter(prevCounter => prevCounter + 1);
        }
    }, []);

    return (
        <>
            <div id="table-caption-wrapper">
                <caption id="table-caption">Latest&nbsp;Transactions</caption>
            </div>
            <table>
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
                <tbody id="transactions">{
                    dataTable.map(entry => {
                        return (
                            <tr>
                                <td><a href={"/Transactiondetails/" + entry.version}> {entry.version} </a></td>
                                <td><a href={"/Accountdetails/" + entry.sender_id}>{entry.sender_id} </a></td>
                                <td>{entry.addressshort}</td>
                                <td><a href={"/Accountdetails/" + entry.receiver_id}>{entry.receiver_id} </a></td>
                                <td>{entry.amount + ' '}{entry.currency}</td>
                                <td>{entry.gas_used + ' '}{entry.gas_currency}</td>
                                <td>{entry.date}</td> <td>{entry.type}</td>
                            </tr>
                        )
                    })
                }</tbody>
            </table>
            <p ref={lastElement} id="last"/>
        </>
    )

}