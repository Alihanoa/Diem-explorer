import React, { useState, useEffect, useRef, useCallback } from "react";
import TableTransactions from "../TableTransactions";

export default function Transactions(props) {

    return (
        <>
            <div>
                <h1 id="main_title">Transactions</h1>
                {/* <TableTransactions id="unique" data={rows}/> */}
                <TableTransactions id="unique"/>
                {/* <p ref={lastElement} id="last"></p> */}
            </div>
        </>
    );
}
