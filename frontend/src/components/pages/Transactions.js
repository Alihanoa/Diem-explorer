import React, { useState, useEffect, useRef, useCallback } from "react";
import TableTransactions from "../TableTransactions";

export default function Transactions(props) {

    return (
        <div class="main-wrapper">
                <h1>Transactions</h1>
                <TableTransactions/>
        </div>
    );
}
