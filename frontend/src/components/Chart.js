import React, { useState, useEffect } from "react";
import { Line } from 'react-chartjs-2'

export default function Chart() {

    const [interval, setInterval] = useState("365days");
    const [dataChart, setDataChart] = useState([]);
    const [labelsChart, setLabelsChart] = useState([]);
    
    useEffect(async () => {updateChart()}, [interval]);

    async function updateChart() {

        console.log("updateChartInterval wird ausgeführt");
        console.log("interval: " + interval);

        switch (interval) {
            case "365days":
                console.log("case 365days wird ausgeführt");
                // let chartdataTransactions365d = await fetch("http://localhost:8888/rest/datalast365days").then(result => result.json());
                let chartdataTransactions365d = [
                    ["04/01/2021 12:00", "1746"], ["05/01/2021 12:00", "36829"],
                    ["06/01/2021 12:00", "17362"], ["07/01/2021 12:00", "67362"],
                    ["08/01/2021 12:00", "97362"], ["09/01/2021 12:00", "27362"]
                ];
                computeRawChartdata(chartdataTransactions365d);
                break;
            case "30days":
                console.log("case 30days wird ausgeführt");
                // let chartdataTransactions30d = await fetch("http://localhost:8888/rest/datalastMonth").then(result => result.json());
                let chartdataTransactions30d = [
                    ["04/01/2021 12:00", "1746"], ["05/01/2021 12:00", "36829"],
                    ["06/01/2021 12:00", "17362"]
                ];
                computeRawChartdata(chartdataTransactions30d);
                break;
        }
        console.log("Labels (asynchron): " + labelsChart);
    }

    function computeRawChartdata(rawChartdata) {

        console.log("computeRawChartdata wird ausgeführt");
        console.log("rawChartdata.length: " + rawChartdata.length);

        let computedLabelsChart = [];
        let computedDataChart = [];

        for (let i = 0; i < rawChartdata.length; i++) {
            computedLabelsChart[i] = rawChartdata[i][0];
            computedDataChart[i] = rawChartdata[i][1];
        }
        
        setLabelsChart(computedLabelsChart);
        setDataChart(computedDataChart);

    }

    return (
        <>
            <div id="chart-wrapper">
                <caption>Developments</caption>
                <select id="chartdata">
                    <option value="transactions" selected>Transactions</option>
                    <option value="accounts">Accounts</option>
                    <option value="gasPrice">Gas Price</option>
                </select>
                <select id="chartinterval" onChange={(e) => { setInterval(e.target.value) }}>
                    <option value="totalTime">Total time</option>
                    <option value="365days" selected>Last 365 days</option>
                    <option value="30days">Last 30 days</option>
                    <option value="7days">Last 7 days</option>
                    <option value="24hours">Last 24 hours</option>
                    <option value="60minutes">Last 60 minutes</option>
                    <option value="60seconds">Last 60 seconds</option>
                </select>
                <Line
                    data={{
                        labels: labelsChart,
                        datasets: [
                            {
                                data: dataChart,
                                fill: true,
                                lineTension: 0.1,
                                backgroundColor: '#F0F3F2',
                                borderColor: '#42318C',
                                borderWidth: 2,
                                pointRadius: 0,
                                hitRadius: 2,
                                hoverRadius: 5
                            }
                        ]
                    }}
                    options={{
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                display: false
                            }
                        },
                        tooltips: {
                            enabled: false
                        }
                    }}
                />
            </div>
        </>
    )
}