import React from "react";

class Contact extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        return (
            <body>
                <h1 id="main_title">Contact</h1>

                <div class="contact-wrapper">
                    <div class="contact-left">
                        <label>If you have any question or feedback, feel free to contact us!</label>
                        <form>
                            <br />
                            <input type="text" name="name" id="name" placeholder="Name" />
                            <br />
                            <input type="email" placeholder="E-Mail Address" name="mail" id="mail" />
                            <br />
                            <textarea name="message" id="message" placeholder="Message" rows="10" />
                            <br />
                            <input type="submit" name="send" id="submit-button" value="Submit"
                                onClick={
                                    function sendMail() {

                                        var link = "mailto:tevfikkantar@hotmail.de" +
                                            "?cc=Tevfik.Kantar@studmail.w-hs.de" +
                                            "&subject=Diem-Explorer Contact-Encuiry" +
                                            "&body=" + document.getElementById('message').value;

                                        window.location.href = link;
                                    }
                                }
                            />
                        </form>
                    </div>
                    <div class="contact-right">
                        <div class="person">
                            <figure>
                                <img src="Avatar.jpg" alt="Avatar"/>
                                <figcaption>Alihan Türk</figcaption>
                            </figure>
                        </div>
                        <div class="person">
                            <figure>
                                <img src="Avatar.jpg" alt="Avatar" />
                                <figcaption>Daniel Mextorf</figcaption>
                            </figure>
                        </div>
                        <div class="person">
                            <figure>
                                <img src="Avatar.jpg" alt="Avatar" />
                                <figcaption>Tevfik Kantar</figcaption>
                            </figure>
                        </div>
                        <div class="person">
                            <figure>
                                <img src="Avatar.jpg" alt="Avatar" />
                                <figcaption>Tim Niesrath</figcaption>
                            </figure>
                        </div>
                    </div>
                </div>
            </body>
        );
    }
}
export default Contact;
