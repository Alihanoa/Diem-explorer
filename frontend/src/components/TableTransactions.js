import React, { useState, useEffect, useRef, useCallback } from "react";
import axios from "axios";

export default function TableTransactions(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const serverAddress = "https://diemexplorer.internet-sicherheit.de:8888";
    const serverAddress = "http://localhost:8888";

    const [showTransactions, setShowTransactions] = useState(true);
    const [showSmartContracts, setShowSmartContracts] = useState(false);
    const [showBlockmetadata, setShowBlockmetadata] = useState(false);
    const [dataTable, setDataTable] = useState([]);
    const [lastRowVersion, setLastRowVersion] = useState(0);
    const [counter, setCounter] = useState(0);
    const [order, setOrder] = useState("ASC");
    const observer = useRef();
    const page = props.page;

    const sorting = (col) => {
        if (order === 'ASC') {
            const sorted = [...dataTable].sort((a, b) =>
                a[col] > b[col] ? 1 : -1
            );
            setDataTable(sorted);
            setOrder("DSC");
        } else if (order === 'DSC') {
            const sorted = [...dataTable].sort((a, b) =>
                a[col] < b[col] ? 1 : -1
            );
            setDataTable(sorted);
            setOrder("ASC");
        }
    }

    const options = {
        threshhold: 0
    }

    const lastElement = useCallback(() => {

        if (observer.current) observer.current.disconnect();

        observer.current = new IntersectionObserver(async (entries) => {

            if (entries[0].isIntersecting && counter > 0) {
                let newDataTransactions;

                if (page === "transactions") {
                    // Case page = transactions
                    newDataTransactions = await axios.get(serverAddress + "/rest/combinedtransactionsnext30?"
                        + "realtransactions=" + showTransactions
                        + "&smartcontracts=" + showSmartContracts
                        + "&blockmetadata=" + showBlockmetadata
                        + "&version=" + lastRowVersion);
                } else {
                    // Case page = accountdetails
                    // CHANGE WHEN BACKEND-METHOD IS IMPLEMENTED
                    newDataTransactions = await axios.get(serverAddress + "/rest/nexttentransactionsofaccount?address=" + props.address + "&version=" + lastRowVersion);
                }

                let combinedData = [].concat(dataTable).concat(newDataTransactions.data);
                if (newDataTransactions.data.length !== 0) {
                    setLastRowVersion(newDataTransactions.data[newDataTransactions.data.length - 1].version);
                    console.log("lastRowVersion = " + lastRowVersion);
                };
                setDataTable(combinedData);
            }
        }, options);
        observer.current.observe(document.getElementById("last"));
    });

    useEffect(async () => {
        let table = createLoadingTable();
        document.getElementById("transactions").innerHTML = table;
        updateTable();
    }, [showTransactions, showSmartContracts, showBlockmetadata]);

    function createLoadingTable() {
        return ("<tr><td>Loading...</td><td>Loading...</td><td>Loading...</td><td>Loading...</td>"
                + "<td>Loading...</td><td>Loading...</td><td>Loading...</td><td>Loading...</td></tr>");
    }
    
    async function updateTable() {

        console.log("updateTable wird ausgeführt");

        if (showTransactions || showSmartContracts || showBlockmetadata) {
            let dataTransactions;
            if (page === "transactions") {
                // Case page = transactions
                dataTransactions = await axios.get(serverAddress + "/rest/combinedtransactionslatestten?"
                    + "realtransactions=" + showTransactions
                    + "&smartcontracts=" + showSmartContracts
                    + "&blockmetadata=" + showBlockmetadata);
                dataTransactions.data = dataTransactions.data.reverse();
            } else {
                // Case page = accountdetails
                // CHANGE WHEN BACKEND-METHOD IS IMPLEMENTED
                dataTransactions = await axios.get(serverAddress + "/rest/lasttentransactionsofaccount?address=" + props.address);
            }
            setDataTable(dataTransactions.data);
            setLastRowVersion(dataTransactions.data[dataTransactions.data.length - 1].version);
            setCounter(prevCounter => prevCounter + 1);
        } else {
            setDataTable([]);
        }
    }

    return (
        <>
            <div class="caption-wrapper">
                <caption>Latest&nbsp;Transactions</caption>
            </div>
            <div class="table-checkboxes-wrapper">
                <input type="checkbox" id="transactions" name="Show Transactions" onClick={(e) => { setShowTransactions(e.target.checked) }} defaultChecked />
                <label for="transactions">Show Transactions</label>
                <input type="checkbox" id="smartContracts" name="Show Smart Contracts" onClick={(e) => { setShowSmartContracts(e.target.checked) }} />
                <label for="smartContract">Show Smart Contracts</label>
                <input type="checkbox" id="blockMetadata" name="Show Blockmetadata" onClick={(e) => { setShowBlockmetadata(e.target.checked) }} />
                <label for="blockMetadata">Show Blockmetadata</label>
            </div>
            <table  class="normal-table">
                <thead>
                    <tr>
                        <th onClick={() => sorting("version")}>Version</th>
                        <th>From</th>{/* onClick={() => sorting("sender_id")} */}
                        <th>Public Key Sender</th>{/* onClick={() => sorting("addressshort")} */}
                        <th>To</th>{/* onClick={() => sorting("receiver_id")} */}
                        <th onClick={() => sorting("amount")}>Amount</th>
                        <th onClick={() => sorting("gas_used")}>Gas Amount</th>
                        <th onClick={() => sorting("dateshort")}>Date</th>
                        <th onClick={() => sorting("type")}>Type</th>
                    </tr>
                </thead>
                <tbody id="transactions">{
                    dataTable.map(entry => {
                        return (
                            <tr>
                                <td><a href={"/Transactiondetails/" + entry.version}>{entry.version}</a></td>
                                <td><a href={"/Accountdetails/" + entry.sender_id}>{entry.sender_id}</a></td>
                                <td>{entry.addressshort}</td>
                                <td><a href={"/Accountdetails/" + entry.receiver_id}>{entry.receiver_id}</a></td>
                                <td>{entry.amount + ' '}{entry.currency}</td>
                                <td>{entry.gas_used + ' '}{entry.gas_currency}</td>
                                <td>{entry.dateshort}</td>
                                <td>{entry.type}</td>
                            </tr>
                        )
                    })
                }</tbody>
            </table>
            <p ref={lastElement} id="last" />
        </>
    )
}