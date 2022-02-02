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
            case "totalTime":
                console.log("case totalTime wird ausgeführt");
                let chartdataTransactionsTotalTime = [
                    ["02/02/2020 24:00", "1904"], ["02/03/2020 24:00", "3802"], 
                    ["02/04/2020 24:00", "5279"], ["02/05/2020 24:00", "7041"], 
                    ["02/06/2020 24:00", "8751"], ["02/07/2020 24:00", "12900"], 
                    ["02/08/2020 24:00", "15395"], ["02/09/2020 24:00", "19348"], 
                    ["02/10/2020 24:00", "23060"], ["02/11/2020 24:00", "26413"], 
                    ["02/12/2020 24:00", "29352"], ["02/01/2021 24:00", "31949"], 
                    ["02/02/2021 24:00", "33746"], ["02/03/2021 24:00", "35062"], 
                    ["02/04/2021 24:00", "39405"], ["02/05/2021 24:00", "42446"], 
                    ["02/06/2021 24:00", "43471"], ["02/07/2021 24:00", "45332"], 
                    ["02/08/2021 24:00", "49446"], ["02/09/2021 24:00", "52431"], 
                    ["02/10/2021 24:00", "54707"], ["02/11/2021 24:00", "56346"], 
                    ["02/12/2021 24:00", "58727"], ["02/01/2022 24:00", "56618"], 
                    ["02/02/2022 24:00", "61362"]
                ];
                computeRawChartdata(chartdataTransactionsTotalTime);
                break;
            case "365days":
                console.log("case 365days wird ausgeführt");
                let chartdataTransactions365d = [
                    ["02/02/2021 24:00", "33746"], ["17/02/2021 24:00", "34829"],
                    ["02/03/2021 24:00", "35062"], ["17/03/2021 24:00", "36245"],
                    ["02/04/2021 24:00", "39405"], ["17/04/2021 24:00", "40463"],
                    ["02/05/2021 24:00", "42446"], ["17/05/2021 24:00", "43529"],
                    ["02/06/2021 24:00", "43471"], ["17/06/2021 24:00", "44586"],
                    ["02/07/2021 24:00", "45332"], ["17/07/2021 24:00", "47382"],
                    ["02/08/2021 24:00", "49446"], ["17/08/2021 24:00", "50521"],
                    ["02/09/2021 24:00", "52431"], ["17/09/2021 24:00", "53347"],
                    ["02/10/2021 24:00", "54707"], ["17/10/2021 24:00", "55652"],
                    ["02/11/2021 24:00", "56346"], ["17/11/2021 24:00", "58629"],
                    ["02/12/2021 24:00", "58727"], ["17/12/2021 24:00", "59071"],
                    ["02/01/2022 24:00", "56618"], ["17/01/2022 24:00", "52314"],
                    ["02/02/2022 24:00", "61362"]
                ];
                computeRawChartdata(chartdataTransactions365d);
                break;
            case "30days":
                console.log("case 30days wird ausgeführt");
                // let chartdataTransactions30d = await fetch(serverAddress  + "/rest/datalastMonth").then(result => result.json());
                let chartdataTransactions30d = [
                    ["02/01/2022 24:00", "56618"],
                    ["04/01/2022 24:00", "55117"], ["05/01/2022 24:00", "55131"],
                    ["06/01/2022 24:00", "55222"], ["07/01/2022 24:00", "55245"],
                    ["08/01/2022 24:00", "54903"], ["09/01/2022 24:00", "53762"],
                    ["10/01/2022 24:00", "53285"], ["11/01/2022 24:00", "53614"],
                    ["12/01/2022 24:00", "53234"], ["13/01/2022 24:00", "52400"],
                    ["14/01/2022 24:00", "52527"], ["15/01/2022 24:00", "52591"],
                    ["16/01/2022 24:00", "52411"], ["17/01/2022 24:00", "52314"],
                    ["18/01/2022 24:00", "52370"], ["19/01/2022 24:00", "53054"],
                    ["20/01/2022 24:00", "53482"], ["21/01/2022 24:00", "53289"],
                    ["22/01/2022 24:00", "53835"], ["23/01/2022 24:00", "54129"],
                    ["24/01/2022 24:00", "55423"], ["25/01/2022 24:00", "56503"],
                    ["26/01/2022 24:00", "58841"], ["27/01/2022 24:00", "59921"],
                    ["28/01/2022 24:00", "59743"], ["29/01/2022 24:00", "60031"],
                    ["30/01/2022 24:00", "61528"], ["31/02/2022 24:00", "60937"],
                    ["01/02/2022 24:00", "61119"], ["02/02/2022 24:00", "61362"]
                ];
                computeRawChartdata(chartdataTransactions30d);
                break;
            case "7days":
                console.log("case 7days wird ausgeführt");
                // let chartdataTransactions7d = await fetch(serverAddress  + "/rest/datalastWeek").then(result => result.json());
                let chartdataTransactions7d = [
                    ["27/01/2022 12:00", "28119"], ["27/01/2022 24:00", "59921"],
                    ["28/01/2022 12:00", "27052"], ["28/01/2022 24:00", "59743"],
                    ["29/01/2022 12:00", "28948"], ["29/01/2022 24:00", "60031"],
                    ["30/01/2022 12:00", "31295"], ["30/01/2022 24:00", "61528"],
                    ["31/02/2022 12:00", "29654"], ["31/02/2022 24:00", "60937"],
                    ["01/02/2022 12:00", "30076"], ["01/02/2022 24:00", "61119"],
                    ["02/02/2022 12:00", "29826"], ["02/02/2022 24:00", "61362"],
                    ["03/02/2022 12:00", "31272"]
                ];
                computeRawChartdata(chartdataTransactions7d);
                break;
            case "24hours":
                console.log("case 24hours wird ausgeführt");
                // let chartdataTransactions24h = await fetch(serverAddress  + "/rest/datalastDay").then(result => result.json());
                let chartdataTransactions24h = [
                    ["02/02/2022 12:00", "30735"], ["02/02/2022 14:00", "34721"],
                    ["02/02/2022 16:00", "39039"], ["02/02/2022 18:00", "44905"],
                    ["02/02/2022 20:00", "50713"], ["02/02/2022 22:00", "56601"],
                    ["02/02/2022 24:00", "61362"], ["03/02/2022 02:00", "1372"],
                    ["03/02/2022 04:00", "1982"], ["03/02/2022 06:00", "4829"],
                    ["03/02/2022 08:00", "18187"], ["03/02/2022 10:00", "26515"],
                    ["03/02/2022 12:00", "31272"]
                ];
                computeRawChartdata(chartdataTransactions24h);
                break;
            case "60minutes":
                console.log("case 60minutes wird ausgeführt");
                // let chartdataTransactions60m = await fetch(serverAddress  + "/rest/datalastHour").then(result => result.json());
                let chartdataTransactions60m = [
                    ["03/02/2022 11:00", "28456"], ["03/02/2022 11:05", "28721"],
                    ["03/02/2022 11:10", "28985"], ["03/02/2022 11:15", "29238"],
                    ["03/02/2022 11:20", "29507"], ["03/02/2022 11:25", "29781"],
                    ["03/02/2022 11:30", "29966"], ["03/02/2022 11:35", "30139"],
                    ["03/02/2022 11:40", "30398"], ["03/02/2022 11:45", "30672"],
                    ["03/02/2022 11:50", "30893"], ["03/02/2022 11:55", "31044"],
                    ["03/02/2022 12:00", "31272"]
                ];
                computeRawChartdata(chartdataTransactions60m);
                break;
            case "60seconds":
                console.log("case 60seconds wird ausgeführt");
                // let chartdataTransactions60m = await fetch(serverAddress  + "/rest/datalastHour").then(result => result.json());
                let chartdataTransactions60s = [
                    ["03/02/2022 11:59:00", "31227"], ["03/02/2022 11:59:05", "31231"],
                    ["03/02/2022 11:59:10", "31235"], ["03/02/2022 11:59:15", "31238"],
                    ["03/02/2022 11:59:20", "31242"], ["03/02/2022 11:59:25", "31246"],
                    ["03/02/2022 11:59:30", "31250"], ["03/02/2022 11:59:35", "31253"],
                    ["03/02/2022 11:59:40", "31257"], ["03/02/2022 11:59:45", "31261"],
                    ["03/02/2022 11:59:50", "31265"], ["03/02/2022 11:59:55", "31268"],
                    ["03/02/2022 12:00:00", "31272"]
                ];
                computeRawChartdata(chartdataTransactions60s);
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