import React, { useState, useEffect } from "react";

export default function TableTransactionsMainpage(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    const [showTransactions, setShowTransactions] = useState(true);
    const [showSmartContracts, setShowSmartContracts] = useState(false);
    const [showBlockmetadata, setShowBlockmetadata] = useState(false);
    const [dataTable, setDataTable] = useState([]);

    useEffect(async () => { updateTable() }, [showTransactions, showSmartContracts, showBlockmetadata]);

    async function updateTable() {

        console.log("updateTable wird ausgeführt");

        if (showTransactions || showSmartContracts || showBlockmetadata) {
            console.log("Show Transactions: " + showTransactions
                        + ", Show Smart Contracts: " + showSmartContracts 
                        + ", Show Blockmetadata: " + showBlockmetadata);
            setDataTable(await fetch(serverAddress + "/rest/combinedtransactionslatestten?"
                        + "realtransactions=" + showTransactions
                        + "&" + "smartcontracts=" + showSmartContracts
                        + "&" + "blockmetadata=" + showBlockmetadata).then(result => result.json()));
        } else {
            setDataTable([]);
        }
    }

    return (
        <>
            <div class="caption-wrapper">
                <caption>Latest&nbsp;Transactions</caption>
            </div>
            <div id="table-checkboxes-wrapper">
                <input type="checkbox" id="transactions" name="Show Transactions" onClick={(e) => { setShowTransactions(e.target.checked) }} defaultChecked />
                <label for="transactions">Show Transactions</label>
                <input type="checkbox" id="smartContracts" name="Show Smart Contracts" onClick={(e) => { setShowSmartContracts(e.target.checked) }} />
                <label for="smartContract">Show Smart Contracts</label>
                <input type="checkbox" id="blockMetadata" name="Show Blockmetadata" onClick={(e) => { setShowBlockmetadata(e.target.checked) }} />
                <label for="blockMetadata">Show Blockmetadata</label>
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
                                <td>{entry.dateshort}</td> <td>{entry.type}</td>
                            </tr>
                        )
                    })
                }</tbody>
            </table>
        </>
    )
}