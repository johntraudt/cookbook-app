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
                        <div className="d-flex flex-wrap justify-content-center">
                            <Link to='/about'>
                                <div className="ml-5 mr-5">About Us</div>
                            </Link>
                            <Link to='/privacy'>
                                <div className="ml-5 mr-5">Privacy Policy</div>
                            </Link>
                            <Link to='/notfound'>
                                <div className="ml-5 mr-5">Join Our Weekly Newsletter</div>
                            </Link>
                        </div>
                    </div>
                </div>
            </footer>
        </div>

    )
}

export default Footer;