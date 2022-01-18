import React, { useState, useEffect } from "react";

export default function TransactionsTable(props) {

    return (
        <>
            <div id="table-caption-wrapper">
                <caption id="table-caption">Latest&nbsp;Transactions</caption>
            </div>
            <div id="table-checkboxes-wrapper">
                <input type="checkbox" id="transactions" name="Show Transactions" defaultChecked/>
                <label for="transactions">Show Transactions</label>
                <input type="checkbox" id="smartContract" name="Show Smart Contracts"/>
                <label for="smartContract">Show Smart Contracts</label>
                <input type="checkbox" id="blockMetadata" name="Show Blockmetadata"/>
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
                    props.data.map(entry => {
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
        </>
    )
}