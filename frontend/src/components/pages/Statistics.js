import React, { useState, useEffect } from "react";
import { Bar, Doughnut } from 'react-chartjs-2'

export default function Statistics(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    const [dataForChart, setDataForChart] = useState([]);
    const [dataDougnutChart, setDataDougnutChart] = useState([]);

    useEffect(async () => {
        let rawdata = await fetch(serverAddress + "/rest/balances").then(result => result.json());
        let data = computeData(rawdata);
        let dataForDoughnut = await fetch(serverAddress + "/rest/doughnutchart").then(res => res.json());
        setDataForChart(data);
        // document.getElementById("chart").innerhtml = (<div>{chart}</div>);
        console.log(dataForDoughnut, rawdata);
        setDataDougnutChart(dataForDoughnut)
    }, []);

    function addData(chart, data) {

        chart.data.forEach((dataset) => {
            dataset.data.push(data);
        });
        chart.update();
    }

    function computeData(data) {
        let equal = 0;
        let more = 0;
        let less = 0;

        for (let i = 0; i < data.length; i++) {
            if (data[i].amount == 1000000) {
                equal++;
            } else if (data[i].amount > 1000000) {
                more++;
            } else {
                less++;
            }
        }
        let countings = [less, equal, more];

        return countings;
    }

    return (
        <div class="main-wrapper">
            <h1>Statistics</h1>
            <div class="chart-wrapper">
                <caption id="chart-caption">Amount&nbsp;of&nbsp;Accounts</caption>
                <br />
                <Bar id="barChart"
                    data={{
                        labels: ['Accounts with Amount smaller than 1 XUS Coin',
                                 'Accounts with Amount equal to 1 XUS Coin',
                                 'Accounts with Amount bigger than 1 XUS Coin'],
                        datasets: [{
                            data: dataForChart,
                                fill: true,
                                lineTension: 0.1,
                                backgroundColor: '#F0F3F2',
                                borderColor: '#42318C',
                                borderWidth: 2,
                                pointRadius: 0,
                                hitRadius: 15,
                                hoverRadius: 5
                        }]
                    }}
                    options={{
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                display: false
                            }
                        }
                    }}
                />
            </div>

            <div id="chart2">
                <Doughnut id="dougnutChart"
                    data={{
                        Labels: ["Blockmetadata", "Transactions", "Smart Contracts"],
                        datasets: [{
                            label: '',
                            dataset: dataDougnutChart,
                            backgroundColor: [
                                'rgba(255,0,0,100)',
                                'rgba(0,255,0,100)',
                                'rgba(0,0,255,100)',
                            ],
                            borderColor: [
                                'rgba(255,0,0,100)',
                                'rgba(0,255,0,100)',
                                'rgba(0,0,255,100)',
                            ],
                            borderWidth: 1

                        }]
                    }}
                    height={400}
                    width={600}
                    options={{
                        maintainAspectRatio: false
                    }}
                />
            </div>
        </div>
    );
}
