import React, { useState, useEffect } from "react";
import { Line } from 'react-chartjs-2'

export default function Chart() {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    const [interval, setInterval] = useState("365days");
    const [dataChart, setDataChart] = useState([]);
    const [labelsChart, setLabelsChart] = useState([]);

    useEffect(async () => { updateChart() }, [interval]);

    async function updateChart() {

        console.log("updateChartInterval wird ausgeführt");
        console.log("interval: " + interval);

        switch (interval) {
            case "365days":
                console.log("case 365days wird ausgeführt");
                // let chartdataTransactions365d = await fetch(serverAddress  + "/rest/datalast365days").then(result => result.json());
                let chartdataTransactions365d = [
                    ["20/01/2021 12:00", "13746"], ["04/02/2021 12:00", "15829"],
                    ["20/02/2021 12:00", "26062"], ["04/03/2021 12:00", "36262"],
                    ["20/03/2021 12:00", "47462"], ["04/04/2021 12:00", "39463"],
                    ["20/04/2021 12:00", "18446"], ["04/05/2021 12:00", "16529"],
                    ["20/05/2021 12:00", "16462"], ["04/06/2021 12:00", "14562"],
                    ["20/06/2021 12:00", "11332"], ["04/07/2021 12:00", "19382"],
                    ["20/07/2021 12:00", "15446"], ["04/08/2021 12:00", "16521"],
                    ["20/08/2021 12:00", "17462"], ["04/09/2021 12:00", "16362"],
                    ["20/09/2021 12:00", "20762"], ["04/10/2021 12:00", "23652"],
                    ["20/10/2021 12:00", "27346"], ["04/11/2021 12:00", "33629"],
                    ["20/11/2021 12:00", "53762"], ["04/12/2021 12:00", "63462"],
                    ["20/12/2021 12:00", "56762"], ["04/01/2022 12:00", "52362"],
                    ["20/01/2022 12:00", "61362"]
                ];
                computeRawChartdata(chartdataTransactions365d);
                break;
            case "30days":
                console.log("case 30days wird ausgeführt");
                // let chartdataTransactions30d = await fetch(serverAddress  + "/rest/datalastMonth").then(result => result.json());
                let chartdataTransactions30d = [
                    ["20/12/2021 12:00", "56762"], ["21/12/2021 12:00", "55332"],
                    ["22/12/2021 12:00", "55117"], ["23/12/2021 12:00", "55131"],
                    ["24/12/2021 12:00", "55222"], ["25/12/2021 12:00", "55245"],
                    ["26/12/2021 12:00", "54903"], ["27/12/2021 12:00", "53762"],
                    ["28/12/2021 12:00", "53285"], ["29/12/2021 12:00", "53614"],
                    ["30/12/2021 12:00", "53234"], ["31/12/2021 12:00", "52400"],
                    ["01/01/2022 12:00", "52527"], ["02/01/2022 12:00", "52591"],
                    ["03/01/2022 12:00", "52411"], ["04/01/2022 12:00", "52362"],
                    ["05/01/2022 12:00", "52370"], ["06/01/2022 12:00", "53054"],
                    ["07/01/2022 12:00", "53482"], ["08/01/2022 12:00", "53289"],
                    ["09/01/2022 12:00", "53835"], ["10/01/2022 12:00", "54129"],
                    ["11/01/2022 12:00", "55423"], ["12/01/2022 12:00", "56503"],
                    ["13/01/2022 12:00", "58841"], ["14/01/2022 12:00", "59921"],
                    ["15/01/2022 12:00", "59743"], ["16/01/2022 12:00", "60031"],
                    ["17/01/2022 12:00", "61528"], ["18/01/2022 12:00", "60937"],
                    ["19/01/2022 12:00", "61119"], ["20/01/2022 12:00", "61362"]
                ];
                computeRawChartdata(chartdataTransactions30d);
                break;
            case "7days":
                console.log("case 7days wird ausgeführt");
                let chartdataTransactions7d = await fetch(serverAddress  + "/rest/datalastWeek").then(result => result.json());
                computeRawChartdata(chartdataTransactions7d);
                break;
            case "24hours":
                console.log("case 24hours wird ausgeführt");
                let chartdataTransactions24h = await fetch(serverAddress  + "/rest/datalastDay").then(result => result.json());
                computeRawChartdata(chartdataTransactions24h);
                break;
            case "60minutes":
                console.log("case 60minutes wird ausgeführt");
                let chartdataTransactions60m = await fetch(serverAddress  + "/rest/datalastHour").then(result => result.json());
                computeRawChartdata(chartdataTransactions60m);
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
            <div id="chart-caption-wrapper">
                <caption id="chart-caption">Developments</caption>
            </div>
            <div id="chart-bar">
                <select id="select-chartdata">
                    <option value="transactions" selected>Transactions</option>
                    <option value="accounts">Accounts</option>
                    <option value="gasPrice">Gas Price</option>
                </select>
                <select id="select-chartinterval" onChange={(e) => { setInterval(e.target.value) }}>
                    <option value="totalTime">Total time</option>
                    <option value="365days" selected>Last 365 Days</option>
                    <option value="30days">Last 30 Days</option>
                    <option value="7days">Last 7 Days</option>
                    <option value="24hours">Last 24 Hours</option>
                    <option value="60minutes">Last 60 Minutes</option>
                    <option value="60seconds">Last 60 Seconds</option>
                </select>
            </div>
            <div id="chart-wrapper">
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
        </>
    )
}