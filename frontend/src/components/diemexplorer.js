import React, {Component} from 'react';
import $ from 'jquery';

class Diemexplorer extends Component {
    constructor(props){
        super(props);
        this.state = {
            items: [],
            isLoaded: false
        }

    }

    componentWillMount() {
        console.log("Component will mount.");
    }

    componentDidMount() {
       // const data = this.restapicall();
        fetch('http://localhost:8888/rest/transactions')
        .then(result => result.json())
        .then(json => {
                    console.log(json)
                })
        console.log("Component did mount.");
    }



       
    
     render(){
        var { isLoaded, items} = this.state;

        if(isLoaded){
            return <div>
                    Die Daten sollten geladen sein!
            </div>
        }
        else{ 
            return (

            <h1>Diem Explorer!</h1>
            
            );}

        
    }



    }


export default Diemexplorer;