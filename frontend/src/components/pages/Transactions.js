import React, { useState, useEffect, useRef, useCallback } from "react";
import ReactDom from "react-dom";
import { ReactDOM } from "react";
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import axios from "axios";
import TransactionsTable from "../TransactionsTable";

export default function Transactions(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    const [rows, setRows] = useState([]);
    const [lastRowVersion, setLastRowVersion] = useState([]);
    const [counter, setCounter] = useState(0);
    const observer = useRef();

    const options = {
        threshhold: 1
    }

    const lastElement = useCallback(() => {
        if (observer.current) observer.current.disconnect();
        observer.current = new IntersectionObserver(async (entries) => {
            if (entries[0].isIntersecting) {
                // console.log('Last version: ' + lastRowVersion)
                let newData = await axios.get(serverAddress + "/rest/getnextten?lastVersionNumber=" + lastRowVersion);
                newData.data = newData.data.reverse()

                let combinedData = [].concat(rows).concat(newData.data);
                // console.log(combinedData)
                setRows(combinedData);
                setLastRowVersion(newData.data[newData.data.length - 1].version)
            }
        }, options)

        observer.current.observe(document.getElementById("last"))
    })

    useEffect(() => {
        const loadRows = (async () => {
            let data = await fetch(serverAddress + "/rest/getlast50").then(res => res.json());
            setRows(data);

            setLastRowVersion(data[data.length - 1].version);
            setCounter(number => number + 1);
        })

        if (counter === 0) {
            loadRows();
        }

    }, [])

    return (
        <>
            <div>
                <h1 id="main_title">Transactions</h1>
                <TransactionsTable id="unique" data={rows} />

            </div>
            <div>
                <p ref={lastElement} id="last"></p>
            </div>
        </>

    );
}
