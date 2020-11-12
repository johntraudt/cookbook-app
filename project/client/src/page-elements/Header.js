import React from 'react';
import { Link } from 'react-router-dom';
import SearchBar from './SearchBar';

function Header() {
    return (
        <header>
            <div className="container">
                <div className="row">
                    <div className="row mr-auto ml-3">
                        <Link to='/' >
                            <h1>BUILD A COOKBOOK</h1>
                        </Link>
                    </div>
                    
                    <div className="row ml-auto mr-2">
                        <div className="p-2">
                            {SearchBar()}
                        </div>
                        
                        <div className="p-2 mt-1">
                            <Link to='/login'>
                                <p className="right">Login</p>
                            </Link>
                        </div>
                    </div>
                </div>
            </div>
            <div className="subtitle">
                <div className="container">
                    <div className="d-flex justify-content-around">
                        <div>MyCookbooks</div>
                        <div>Recipe Of The Day</div>
                        <div>Show Me The Money</div>
                    </div>
                </div>
            </div>
        </header>
    )
}

export default Header;