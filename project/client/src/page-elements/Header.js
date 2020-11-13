import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import SearchBar from './SearchBar';

function Header() {

    const location = useLocation();



    return (
        <header>
            <div className="container">
                <div className="row">
                    <div className={location.pathname !== '/' ? "row mr-auto ml-3": "row mr-auto ml-3"} >
                        <Link to='/' >
                            <h1>BUILD A COOKBOOK</h1>
                        </Link>
                    </div>
                    
                    <div className="row ml-auto mr-2">
                        {
                            location.pathname !== "/" && (
                                <div className="p-2">
                                    {SearchBar()}
                                </div>
                            )
                        }
                        
                        <div className="p-2">
                            <Link to='/login'>
                                <div className="right btn-secondary p-2 pr-4 pl-4">Login</div>
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