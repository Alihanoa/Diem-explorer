import React from "react";

class Accountdetails extends React.Component{
    constructor(props){
        super(props);
    }

    render(){

        return(
            <div>
                <h1>Account Details</h1>
                <table border="3">
                    <thead>
                        <tr>
                            <th>Address</th>
                            <td>{this.props.address}</td>
                        </tr>
                    </thead>

                    <tbody>
                        <tr>
                            <td>authentication_key</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Human Name</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>Sequence Number</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>is_frozen</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>sent_events_key</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>receive_events_key</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>rtype</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>parent_vasp_name</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>base_url</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>expiration_time</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>compliance_key</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>received_mint_events_key</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>compliance_key_rotation_events_key</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>base_url_rotation_events_key</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>preburn_balancexus</td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        );
    }
}
export default Accountdetails;
