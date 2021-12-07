import React from "react";
import Transactiondetails from "./Transactiondetails";
import Transaction from "./Transactiondetails";
import ReactDom from "react-dom";
import { ReactDOM } from "react";
import { BrowserRouter as Router, Route, Link, Switch } from 'react-router-dom';
import { data } from "jquery";

class Transactions extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            rows: 10,
            loadingState: false
        };
    }

    async componentDidMount() {
        let data = await fetch('http://localhost:8888/rest/transactions').then(result => result.json());
        this.setState({ rows: data });
        let table = this.createTable(data);
        document.getElementById("transactions").innerHTML = table;
        console.log(table);

        //Ab hier Code für Scrollen:
        this.refs.iScroll.addEventListener("scroll", () => {
            if (this.refs.iScroll.scrollTop + this.refs.iScroll.clientHeight >= this.refs.iScroll.scrollHeight - 20) {
                this.loadMoreRows();
            }
        });
    }

    loadMoreRows() {
        if (this.state.loadingState) {
            return;
        }
        this.setState({ loadingState: true });
        setTimeout(() => {
            this.setState({ rows: this.state.rows + 10, loadingState: false });
        }, 1000);
    }

    // scrollState(scroll) {
    //     var visibleStart = Math.floor(scroll / this.state.recordHeight);
    //     var visibleEnd = Math.min(visibleStart + this.state.recordsPerBody, this.state.total - 1);

    //     var displayStart = Math.max(0, Math.floor(scroll / this.state.recordHeight) - this.state.recordsPerBody * 1.5);
    //     var displayEnd = Math.min(displayStart + 4 * this.state.recordsPerBody, this.state.total - 1);

    //     this.setState({
    //         visibleStart: visibleStart,
    //         visibleEnd: visibleEnd,
    //         displayStart: displayStart,
    //         displayEnd: displayEnd,
    //         scroll: scroll
    //     });
    // }

    // listenScrollEvent() {
    //     console.log('Scroll event detected!');
    // }

    // create table row for each object within the data array
    createTable(data) {

        let table = [];
        for (let i = data.length - 1; i >= data.length - 100; i--) {
            // let children = [];
            table += "<tr> <td><a href=Transactiondetails/" + data[i].version + ">" + data[i].version + "</a></td> <td><a href=Accountdetails/" + data[i].sender_id + ">"
                + data[i].sender_id + "</a></td>  <td>" + data[i].public_key + "</td> <td><a href=Accountdetails/" + data[i].receiver_id + ">"
                + data[i].receiver_id + "</a></td>  <td>" + data[i].amount + " " + data[i].currency + "</td> <td>"
                + data[i].gas_used + " " + data[i].gas_currency + "</td> <td>" + data[i].date + "</td> <td>" + data[i].type + "</td> </tr>";
        }
        console.log(data);
        return table;
    }

    render() {

        return (

            <div ref = "iScroll">
                <h1 id="main_title">Transactions</h1>
                
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
                    <tbody id="transactions">
                        
                    </tbody>
                </table>
                {/* {this.state.loadingState ? <p className="loading"> loading More Rows..</p> : ""} */}
            </div>
        );
    }
}
export default Transactions;
