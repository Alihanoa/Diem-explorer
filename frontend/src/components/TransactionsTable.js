import React, { useState, useEffect } from "react";

export default function TransactionsTable(props) {

    // function createTable() {
    //     let table = [];
    //     for (let i = props.data.length - 1; i >= props.data.length - 10; i--) {
    //         //let children = [];
    //         table += "<tr> <td><a href=Transactiondetails/" + entry.version + ">" + entry.version +
    //             "</a></td> <td><a href=Accountdetails/" + entry.sender_id + ">"
    //             + entry.sender_id + "</a></td>  <td>" + entry.public_key + "</td> <td><a href=Accountdetails/" + entry.receiver_id + ">"
    //             + entry.receiver_id + "</a></td>  <td>" + entry.amount + " " + entry.currency + "</td> <td>"
    //             + entry.gas_used + " " + entry.gas_currency + "</td> <td>" + entry.date + "</td> <td>" + entry.type + "</td> </tr>";
    //     }
    //     return table;
    // }

    return (
        <>
            <input type="checkbox" id="smartContract" name="Include Smart Contracts" defaultChecked />
            <label for="smartContract"> Include Smart Contracts </label>
            <input type="checkbox" id="blockMetadata" name="Include Blockmetadata" />
            <label for="blockMetadata"  > Include Blockmetadata </label>
            <table>
                <caption>Latest Transactions</caption>
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
                    props.data.map(entry => {
                        return (
                            <tr>
                                <td> {entry.version}
                                </td> <td>
                                    {entry.sender_id} </td>  <td>{entry.public_key}</td> <td>
                                    {entry.receiver_id}</td>  <td>{entry.amount}{entry.currency}</td> <td>
                                    {entry.gas_used}{entry.gas_currency}</td> <td>{entry.date}</td> <td>{entry.type}</td>
                            </tr>
                        )
                    })
                }</tbody>
            </table>
        </>
    )
}