import React, { useState } from "react";

export default function Searchbar(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");
    
    const[searchfieldvalue, setSearchfieldvalue] = useState('');
    const[choiceboxvalue, setChoiceboxvalue] = useState('Address');

    return (
        <form class="search">
            <input type="search" placeholder="Search..." name="search_bar" id="search_bar" onChange={(e) => { setSearchfieldvalue(e.target.value); console.log(searchfieldvalue) }
            }>
            </input>
            <button name="search_button" id="search_button" onClick={(e) => {
                e.preventDefault();
                e.nativeEvent.stopPropagation();
                e.nativeEvent.stopImmediatePropagation();
                if (choiceboxvalue === "Address" && searchfieldvalue != "") {
                    window.location.href = "http://" + serverAddress + "/Accountdetails/" + searchfieldvalue;
                }
                else if (choiceboxvalue === "Transactionnumber" && searchfieldvalue != "") {
                    window.location.href = ("/Transactiondetails/" + searchfieldvalue);
                }
                else if (choiceboxvalue === "Date" && searchfieldvalue != "") {
                    window.location.href = "http://" + serverAddress + "/Transactions/" + searchfieldvalue + "/" + choiceboxvalue;
                }
                else if (choiceboxvalue === "Amount greater than" && searchfieldvalue != "") {
                    window.location.href = "http://" + serverAddress + "/Transactions/" + searchfieldvalue;
                }
                else if (choiceboxvalue === "Amount less than" && searchfieldvalue != "") {
                    // window.location.href = "http://localhost:3099/Transactions/" + searchfieldvalue;
                    window.location.href = serverAddress + "/Transactions/" + searchfieldvalue;
                }
            }
            }></button>
            <select onChange={(e) => {
                setChoiceboxvalue(e.target.value); 
                console.log(choiceboxvalue);
            }}>
                <option>Address</option>
                <option>Transactionnumber</option>
                <option>Amount greater than</option>
                <option>Amount less than</option>
                <option>Date</option>
            </select>
        </form>
    );
}
