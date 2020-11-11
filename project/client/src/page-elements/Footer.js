import { Link } from 'react-router-dom';
import React from 'react';

function Footer() {
    return (
        <div>
            <footer>
                <div className="container">
                    <div className="text-center">
                        <Link to='/' >
                            <h2 className="row-center">BUILD A COOKBOOK</h2>
                        </Link>
                        <div className="row-center">
                            <a className="m-5">About Us</a>
                            <a className="m-5">Privacy Policy</a>
                            <a className="m-5">Download Our App</a>
                            <a className="m-5">Join Our Weekly Newsletter</a>
                        </div>
                    </div>
                </div>
            </footer>
        </div>

    )
}

export default Footer;