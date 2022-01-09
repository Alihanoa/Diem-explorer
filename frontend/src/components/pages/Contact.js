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
                <label>If you have any question or feedback, feel free to contact us!</label>
                <br />
                <input type="text" name="name" id="name" placeholder="Name" value={name} onChange={handleChange} />
                <br />
                <input type="email" placeholder="E-Mail Address" name="email" id="email" value={email} onChange={handleChange} />
                <br />
                <textarea name="message" id="message" placeholder="Message" rows="10" value={message} onChange={handleChange} />
                <br />
                <input type="submit" name="send" id="submit-button" value="Submit" onClick={handleSubmit} />
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
        <body>
            <h1 id="main_title">Contact</h1>

            <div class="contact-wrapper">
                <div class="contact-left">
                    {Formular()}
                </div>
                <div class="contact-right">
                    {Person("Alihan Türk")}{Person("Daniel Mextorf")}{Person("Tevfik Kantar")}{Person("Tim Niestrath")}
                </div>
            </div>
        </body>
    );

}
