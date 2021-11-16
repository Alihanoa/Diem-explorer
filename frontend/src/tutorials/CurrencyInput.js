import React from "react";

class CurrencyInput extends React.Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }
    handleChange(e) {
        this.props.onCurrencyChange(e.target.value);
    }
    render() {
        const currency = this.props.currency;
        const value = this.props.value;
        return (
            <fieldset>
                <legend>Gib die Menge in {currency} an:</legend>
                <input value={value} onChange={this.handleChange} />
            </fieldset>
        );
    }
}
export default CurrencyInput;
