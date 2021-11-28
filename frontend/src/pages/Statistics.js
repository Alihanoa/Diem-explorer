import React, { useEffect } from "react";
import { Bar } from 'react-chartjs-2'

class Statistics extends React.Component{

    constructor(props){
        super(props);
    }

    async readData() {
        let data = await fetch('http://localhost:8888/rest/balances').then(result => result.json());
        return data;
    }

    async componentDidMount(){
    let data = await this.readData();
    let dataForChart = this.computeData(data);
    

    console.log(data);

    }

    computeData(data){
        

        let equal = 0;
        let more = 0;
        let less =0;

        for(let i=0; i<data.length; i++){
            if(data[i].amount == 1000000){
                equal++;
            }
            else if(data[i].amount > 1000000){
                more++;
            }
            else {
                less++;
            }
        }
        let countings = [less, equal, more];

        return countings;
    }
     render(props){
        

        return(
            <div>
                <Bar id="barChart"
                    data ={{labels : ['weniger', 'gleich', 'mehr'],
                    datasets: [{
                        label: 'Wieviele Accounts haben mindestens 1 XUS Token',
                        data: [this.props.dataForChart],
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
                    }]}}
                    height={400}
                    width = {600}
                    options = {{
                        maintainAspectRatio: false,
                    }}
                />
                */
            </div>
        );
    }


}
export default Statistics;
