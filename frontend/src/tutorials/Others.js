import React from "react";
import CurrencyInput from "./CurrencyInput";
import MagicButton from "./MagicButton";

class Others extends React.Component {

    static defaultProps = {vorname: "Tevfik"};  //Default-Property

    constructor(props) {
        super(props);
        {/*state´s kann man, im Gegensatz zu properties, verändern. Siehe componentDidMount()*/}
        this.state = {
            date: new Date(),
            currency: "Euro",
            value: "",
            anzahl: 1
        };
        this.handleDollarChange = this.handleDollarChange.bind(this);
        this.handleEuroChange = this.handleEuroChange.bind(this);
    }

    //Wird aufgrufen, bevor render-Methode aufgerufen wird.
    componentWillMount(){

    }

    //Wird nach render aufgerufen.
    componentDidMount() {
        this.ticker = setInterval(() => this.tick(), 1000);
        this.setState({anzahl: this.state.anzahl + 5});     //So kann man state´s verändern.
    }

    componentWillUnmount() {
        clearInterval(this.ticker);
    }

    tick() {
        this.setState({
            date: new Date()
        });
    }

    handleDollarChange(value) {
        this.setState({ currency: "Dollar", value });
    }

    handleEuroChange(value) {
        this.setState({ currency: "Euro", value });
    }

    createTable() {
        let table = [];
        for (let i = 0; i < 10; i++) {
            let children = [];
            table.push(<tr><td><MagicButton/></td></tr>);
        }
        return table;
    }

    onButtonClick(){
        //console.log('Klick');
        this.setState({anzahl: this.state.anzahl + 1});
    }

    //render wird nicht nur einmal aufgerufen, sondern jedes mal wenn ein property oder state geändert wird aufgerufen.
    render() {
        const value = this.state.value;
        const currency = this.state.currency;
        let euro = 0;
        let dollar = 0;
        if (currency === "Euro") {
            euro = value;
            dollar = value * 1.13;
        } else {
            dollar = value;
            euro = value * 0.88;
        }
        return (
            <div>
                <h1>Timestamp: {this.state.date.toLocaleString()}</h1>

                <CurrencyInput currency="Euro" value={euro} onCurrencyChange={this.handleEuroChange} />
                <CurrencyInput currency="Dollar" value={dollar} onCurrencyChange={this.handleDollarChange} />
                {/*
                Currency1: {this.props.match.params.currency}
                <br />
                Currency2: {this.props.match.params.currency}
                */}
                <table>
                    {this.createTable()}
                </table>

                {/*Hier wird mit einem Property gearbeitet. welcome=... ist conditional Rendering.*/}
                <h1><Greeting welcome={true}/> {this.props.vorname} {this.props.name}</h1>
                <h1><Greeting welcome={this.props.isBig}/></h1>
                {this.state.anzahl}<br/>
                <button onClick={this.onButtonClick.bind(this)}>Klick mich!</button>
            </div>
        );
    }
}

{/* Hiermit soll Parent/Child Components geübt werden.*/}
class Greeting extends React.Component{

    render(){
        if(this.props.welcome === true){
            return <span>Willkommen</span>
        }else{
            return <span>Tschau</span>
        }
    }
}
export default Others;
