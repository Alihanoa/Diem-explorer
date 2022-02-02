import React, { useState } from "react";
import searchIcon from './search-icon.png';

export default function Searchbar(props) {

    // CHANGE FOR LOCAL-SERVER/IFIS-SERVER
    // const [serverAddress, setServerAddress] = useState("https://diemexplorer.internet-sicherheit.de:8888");
    const [serverAddress, setServerAddress] = useState("http://localhost:8888");

    const [searchfieldvalue, setSearchfieldvalue] = useState('');
    const [choiceboxvalue, setChoiceboxvalue] = useState('Account Address');

    function handleChange(e){
        setSearchfieldvalue(e.target.value); console.log(searchfieldvalue)
    }

    function handleSelect(e) {
        setChoiceboxvalue(e.target.value);
    }

    function handleClick(e) {
        e.preventDefault();
        e.nativeEvent.stopPropagation();
        e.nativeEvent.stopImmediatePropagation();
        if (choiceboxvalue === "Account Address" && searchfieldvalue != "") {
            window.location.href = "/Accountdetails/" + searchfieldvalue;
        }
        else if (choiceboxvalue === "Transaction Version" && searchfieldvalue != "") {
            window.location.href = "/Transactiondetails/" + searchfieldvalue;
        }
        // else if (choiceboxvalue === "Date" && searchfieldvalue != "") {
        //     window.location.href = "/Transactions/" + searchfieldvalue + "/" + choiceboxvalue;
        // }
        // else if (choiceboxvalue === "Amount greater than" && searchfieldvalue != "") {
        //     window.location.href = "/Transactions/" + searchfieldvalue;
        // }
        // else if (choiceboxvalue === "Amount less than" && searchfieldvalue != "") {
        //     // window.location.href = "http://localhost:3099/Transactions/" + searchfieldvalue;
        //     window.location.href = "/Transactions/" + searchfieldvalue;
        // }
    }

    return (
        <form class="search">
            <input type="search" placeholder="Search..." name="search_bar" id="search_bar" onChange={(e) => {handleChange(e)}}/>
            <select id="searchbox" onChange={(e) => { handleSelect(e) }}>
                <option>Account Address</option>
                <option>Transaction Version</option>
                {/* <option>Amount greater than</option>
                <option>Amount less than</option>
                <option>Date</option> */}
            </select>
            <button name="search_button" id="search_button" onClick={(e) => {handleClick(e)}}>
                <img id="search_icon" srcSet={searchIcon} />
            </button>
        </form>
    );
}
