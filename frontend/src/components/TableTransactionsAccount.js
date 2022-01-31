import React, { useState, useEffect, useRef, useCallback } from "react";
import axios from "axios";

export default function TableTransactionsAccount(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    // const [showTransactions, setShowTransactions] = useState(true);
    // const [showSmartContracts, setShowSmartContracts] = useState(false);
    // const [showBlockmetadata, setShowBlockmetadata] = useState(false);
    const [dataTable, setDataTable] = useState([]);
    const [lastRowVersion, setLastRowVersion] = useState(0);
    const [counter, setCounter] = useState(0);
    const observer = useRef();


    const options = {
        threshhold: 1
    }

    const lastElement = useCallback(() => {

        if (observer.current) observer.current.disconnect();

        observer.current = new IntersectionObserver(async (entries) => {

            if (entries[0].isIntersecting && counter > 0) {
                let newDataTransactions = await axios.get(serverAddress + "/rest/nexttentransactionsofaccount?address=" + props.address + "&version=" + lastRowVersion);

                let combinedData = [].concat(dataTable).concat(newDataTransactions.data);
                setLastRowVersion(newDataTransactions.data[newDataTransactions.data.length - 1].version);
                setDataTable(combinedData);
            }
        }, options)
        observer.current.observe(document.getElementById("last"));
    });

    useEffect(async () => {

        if (counter === 0) {
            let dataTransactions = await fetch(serverAddress + "/rest/lasttentransactionsofaccount?address=" + props.address).then(result => result.json());
            setDataTable(dataTransactions);
            setLastRowVersion(dataTransactions[dataTransactions.length - 1].version);
            setCounter(prevCounter => prevCounter + 1);
        }

        // updateTable();

    }, []);
    // }, [showTransactions, showSmartContracts, showBlockmetadata]);

    // async function updateTable() {

    //     console.log("updateTable wird ausgeführt");

    //     if (showTransactions && !showSmartContracts && !showBlockmetadata) {
    //         console.log("Case T1S0B0 wird ausgeführt");
    //         document.getElementById("transactions").checked = true;
    //         setDataTable(await fetch(serverAddress + "/rest/lasttenreal").then(result => result.json()));
    //     } else if (showTransactions && showSmartContracts && !showBlockmetadata) {
    //         // Has to be changed after backend-method is implemented!
    //         console.log("Case T1S1B0 wird ausgeführt");
    //         setDataTable(await fetch(serverAddress + "/rest/lastten").then(result => result.json()));
    //     } else if (showTransactions && showSmartContracts && showBlockmetadata) {
    //         console.log("Case T1S1B1 wird ausgeführt");
    //         setDataTable(await fetch(serverAddress + "/rest/lastten").then(result => result.json()));
    //     } else if (showTransactions && !showSmartContracts && showBlockmetadata) {
    //         // Has to be changed after backend-method is implemented!
    //         console.log("Case T1S0B1 wird ausgeführt");
    //         setDataTable(await fetch(serverAddress + "/rest/lastten").then(result => result.json()));
    //     } else if (!showTransactions && showSmartContracts && !showBlockmetadata) {
    //         console.log("Case T0S1B0 wird ausgeführt");
    //         setDataTable(await fetch(serverAddress + "/rest/lasttensmartcontracts").then(result => result.json()));
    //     } else if (!showTransactions && showSmartContracts && showBlockmetadata) {
    //         // Has to be changed after backend-method is implemented!
    //         console.log("Case T0S1B1 wird ausgeführt");
    //         setDataTable(await fetch(serverAddress + "/rest/lastten").then(result => result.json()));
    //     } else if (!showTransactions && !showSmartContracts && showBlockmetadata) {
    //         console.log("Case T0S0B1 wird ausgeführt");
    //         setDataTable(await fetch(serverAddress + "/rest/lasttenBlock").then(result => result.json()));
    //     } else if (!showTransactions && !showSmartContracts && !showBlockmetadata) {
    //         console.log("Case T0S0B0 wird ausgeführt");
    //         // document.getElementById("transactions").checked = true;
    //         // document.getElementById("transactions").disabled = "disabled";
    //         // setDataTable(await fetch(serverAddress + "/rest/lasttenreal").then(result => result.json()));
    //         setDataTable([]);
    //     }
    // };

    return (
        <>
            <div id="table-caption-wrapper">
                <caption id="table-caption">Latest&nbsp;Transactions</caption>
            </div>
            {/* <div id="table-checkboxes-wrapper">
                <input type="checkbox" id="transactions" name="Show Transactions" onClick={(e) => { setShowTransactions(e.target.checked) }} defaultChecked />
                <label for="transactions">Show Transactions</label>
                <input type="checkbox" id="smartContracts" name="Show Smart Contracts" onClick={(e) => { setShowSmartContracts(e.target.checked) }} />
                <label for="smartContract">Show Smart Contracts</label>
                <input type="checkbox" id="blockMetadata" name="Show Blockmetadata" onClick={(e) => { setShowBlockmetadata(e.target.checked) }} />
                <label for="blockMetadata">Show Blockmetadata</label>
            </div> */}
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
                                <td>{entry.public_key}</td>
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