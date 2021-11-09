import React from "react";
import './magicButton.scss';

class MagicButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = { render: true };
    }

    del() {
        this.setState({
            render: false
        });
    }

    render() {
        if (this.state.render) {
            return (
                <button onClick={(e) => this.del(e)}>
                    Delete Me
                </button>
            );
        } else {
            return null;
        }
    }
}
export default MagicButton;
