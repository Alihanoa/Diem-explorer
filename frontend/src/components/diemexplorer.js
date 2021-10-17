import React, {Component} from 'react';


class Diemexplorer extends Component {
    constructor(props){
        super(props);

    }

    componentWillMount() {
        console.log("Component will mount.");
    }

    componentDidMount() {
        console.log("Component did mount.");
    }
     render(){
        return (
                <h1>Diem Explorer!</h1>
                );
        
    }
}

export default Diemexplorer;