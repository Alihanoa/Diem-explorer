import React, { useState, useEffect } from "react";
import Transactiondetails from "./Transactiondetails";
import Transaction from "./Transactiondetails";
import ReactDom from "react-dom";
import { ReactDOM } from "react";
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { data } from "jquery";
import axios from "axios";
import TransactionsTable from "../TransactionsTable";

export default function Transactions(props) {

    const [rows, setRows] = useState([]);
    const [loadingState, setLoadingState] = useState(false);
    const [lastRow, setLastRow] = useState(null);
    const [lastRowVersion, setLastRowVersion] = useState(0);
    const [table, setTable] = useState(null);

    useEffect(async () => {
        console.log("useeffect wurde aufgerufen")
        let data = await fetch('http://localhost:8888/rest/getlast50').then(result => result.json());
        setRows(data);
        
        // let generatedTable = createTable(data);
        // document.getElementById("transactions").innerHTML = generatedTable;
        // console.log(table);

        let stack = document.getElementById("transactions");
        // let generatedLastRow = stack.rows[stack.rows.length - 1];
        console.log(stack);
        // setLastRow(generatedLastRow);
        // let element = generatedLastRow.innerHTML
        // console.log(element);
        console.log(lastRow);
    },[]);

    //     let observer = new IntersectionObserver(rows => {
    //         rows.forEach(async (row) => {
    //             if (row.isIntersecting) {
    //                 let newData = await axios.get("http://localhost:8888/rest/getnextten?lastVersionNumber=" + lastRowVersion);
    //                 newData = newData.data.reverse();
    //                 newData = createTable(newData);
    //                 console.log(newData);
    //                 generatedTable += newData;
    //                 document.getElementById("transactions").innerHTML = table;
    //                 let foo = document.getElementById("transactions");
    //                 setLastRow(foo.rows[foo.rows.length - 1]);
    //                 if (observer.current) {
    //                     observer.disconnect();
    //                 }
    //                 observer.observe(lastRow);
    //                 console.log(lastRow)
    //             }

    //         })
    //     }, {
    //         threshhold: 1
    //     });
    //     if (observer.current) {
    //         observer.disconnect();
    //     }
    //     observer.observe(lastRow);
    // }, lastRow);

    console.log(rows)
    return (

        <div>
            <h1 id="main_title">Transactions</h1>
                    <TransactionsTable  id="unique" data={rows} />
                
        </div>
    );
}
