import React, { useState, useEffect } from "react";
import { Bar, Doughnut } from 'react-chartjs-2';
import { Line } from 'react-chartjs-2';

export default function Statistics(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const serverAddress = "https://diemexplorer.internet-sicherheit.de:8888";
    const serverAddress = "http://localhost:8888";

    const [dataBarChart, setDataBarChart] = useState([]);
    const [label1BarChart, setLabel1BarChart] = useState("Loading...");
    const [label2BarChart, setLabel2BarChart] = useState("Loading...");
    const [label3BarChart, setLabel3BarChart] = useState("Loading...");

    const [dataLineChart, setDataLineChart] = useState([]);
    const [labelsLineChart, setLabelsLineChart] = useState(["Loading...", "Loading...", "Loading...", "Loading..."]);
    const [selectedCurrency, setSelectedCurrency] = useState("XUS");
    // const [dataDoughnutChart, setDataDoughnutChart] = useState([]);

    useEffect(async () => {

        setDataBarChart([]);
        setLabel1BarChart("Loading...");
        setLabel2BarChart("Loading...");
        setLabel3BarChart("Loading...");

        let rawData = await fetch(serverAddress + "/rest/balances").then(result => result.json());
        let data = computeDataBarChart(rawData);

        setLabel1BarChart("Accounts with Amount smaller than 1 XUS Coin");
        setLabel2BarChart("Accounts with Amount equal to 1 XUS Coin");
        setLabel3BarChart("Accounts with Amount bigger than 1 XUS Coin");
        setDataBarChart(data);

        updateLineChart();

        // let dataForDoughnut = await fetch(serverAddress + "/rest/doughnutchart").then(res => res.json());
        // document.getElementById("chart").innerhtml = (<div>{chart}</div>);
        // console.log(dataForDoughnut, rawData);
        // setDataDoughnutChart(dataForDoughnut);
    }, [selectedCurrency]);

    async function updateLineChart() {

        if (selectedCurrency === "XUS") {
            computeRawChartdata(await fetch(serverAddress + "/rest/handelsvol30").then(result => result.json()));
        } else {
            computeRawChartdata(await fetch(serverAddress + "/rest/handelsvol30xdx").then(result => result.json()));
        }
    }

    function computeRawChartdata(rawChartdata) {

        let computedLabelsChart = [];
        let computedDataChart = [];

        for (let i = 0; i < rawChartdata.length; i++) {
            computedLabelsChart[i] = rawChartdata[i][0];
            computedDataChart[i] = rawChartdata[i][1];
        }

        setLabelsLineChart(computedLabelsChart);
        setDataLineChart(computedDataChart);
    }

    // function addData(chart, data) {

    //     chart.data.forEach((dataset) => {
    //         dataset.data.push(data);
    //     });
    //     chart.update();
    // }

    function computeDataBarChart(data) {
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
                        labels: [label1BarChart,
                                 label2BarChart,
                                 label3BarChart],
                        datasets: [{
                            data: dataBarChart,
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

            <br></br>
            <br></br>
            <br></br>
            <br></br>
            <br></br>

            <div class="caption-wrapper">
                <caption>Trading&nbsp;Volume&nbsp;last&nbsp;30&nbsp;Days</caption>
            </div>
            <div>
                <input type="radio" name="currency" value="xus" onChange={(e) => { setSelectedCurrency(e.target.value) }} checked></input>
                <label for="xus">XUS</label>
                <input type="radio" name="currency" value="xdx" onChange={(e) => { setSelectedCurrency(e.target.value) }}></input>
                <label for="xdx">XDX</label>
            </div>
            <div class="chart-wrapper">
                <Line
                    data={{
                        labels: labelsLineChart,
                        datasets: [
                            {
                                data: dataLineChart,
                                fill: true,
                                lineTension: 0.1,
                                backgroundColor: '#F0F3F2',
                                borderColor: '#42318C',
                                borderWidth: 2,
                                pointRadius: 0,
                                hitRadius: 15,
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

            {/* <div id="chart2">
                <Doughnut id="doughnutChart"
                    data={{
                        Labels: ["Blockmetadata", "Transactions", "Smart Contracts"],
                        datasets: [{
                            label: '',
                            dataset: dataDoughnutChart,
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
            </div> */}
        </div>
    );
}
