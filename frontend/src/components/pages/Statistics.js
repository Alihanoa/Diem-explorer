import React, { useState, useEffect } from "react";
import { Bar, Doughnut } from 'react-chartjs-2'

export default function Statistics(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    const [dataForChart, setDataForChart] = useState([]);
    const [dataDougnutChart, setDataDougnutChart] = useState([]);

    useEffect( async () => {
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

        <>
            <div id="chart">
                <Bar id="barChart"
                    data={{
                        labels: ['weniger', 'gleich', 'mehr'],
                        datasets: [{
                            label: 'Wieviele Accounts haben mindestens 1 XUS Token',
                            data: dataForChart,
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(255, 206, 86, 0.2)'
                                /*                       'rgba(75, 192, 192, 0.2)',
                                            'rgba(153, 102, 255, 0.2)',
                                            'rgba(255, 159, 64, 0.2)' */
                            ],
                            borderColor: [
                                'rgba(255, 99, 132, 1)',
                                'rgba(54, 162, 235, 1)',
                                'rgba(255, 206, 86, 1)' /*
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)' */
                            ],
                            borderWidth: 1
                        }]
                    }}
                    height={400}
                    width={600}
                    options={{
                        maintainAspectRatio: false,
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
        </>
    );
}
