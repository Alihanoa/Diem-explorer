import React, { useState, useEffect } from "react";
import Avatar from './Avatar.jpg';

export default function Contact(props) {

    function Formular(props) {

        const [name, setName] = useState('');
        const [email, setEmail] = useState('');
        const [message, setMessage] = useState('');

        function handleChange(event) {
            if (event.target.name == "name") {
                setName(event.target.value);
            } else if (event.target.name == "email") {
                setEmail(event.target.value);
            } else if (event.target.name == 'message') {
                setMessage(event.target.value);
            }
            console.log(name, email, message);
        }

        function handleSubmit() {
            var link = "mailto:tevfikkantar@hotmail.de" +
                "?cc=Tevfik.Kantar@studmail.w-hs.de" +
                "&subject=Diem-Explorer Contact-Encuiry" +
                "&body=" + message;

            window.location.href = link;
            window.alert("Your contact request was succesfully!\nThank you");
        }

        return (
            <form>
                <label>If you have any questions or feedback, feel free to contact us!</label>

                <input type="text" name="name" class="element contact-input" placeholder="Name" value={name} onChange={handleChange}/>
                
                <input type="email" placeholder="E-Mail Address" name="email" class="element contact-input" value={email} onChange={handleChange}/>
                
                <textarea name="message" placeholder="Message" rows="10" class="element contact-input" value={message} onChange={handleChange}/>
                
                <input type="submit" name="send" id="submit-button" value="Submit" class="element" onClick={handleSubmit}/>
            </form>
        )
    }

    function Person(props) {
        return (
            <div class="person">
                <figure>
                    <img srcSet={Avatar} alt="Avatar" />
                    <figcaption>{props}</figcaption>
                </figure>
            </div>
        )
    }

    return (
        <div class="main-wrapper">
            <h1>Contact</h1>

            <div id="contact-wrapper">
                <div id="contact-left-side">
                    {Formular()}
                </div>
                <div id="contact-right-side">
                    {Person("Alihan Türk")}{Person("Daniel Mextorf")}{Person("Tevfik Kantar")}{Person("Tim Niestrath")}
                </div>
            </div>
        </div>
    );

}
