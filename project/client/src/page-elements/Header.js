import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import SearchBar from './SearchBar';

function Header() {

    const location = useLocation();



    return (
        <header className="shadow">
            <div className="container">
                <div className="row p-1">
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
            <div className="subtitle shadow">
                <div className="container">
                    <div className="d-flex justify-content-around">
                        <Link className="dark" to='/notfound'>
                            <div>MyCookbooks</div>
                        </Link>
                        <Link className="dark" to='/notfound'>
                            <div>Recipe Of The Day</div>
                        </Link>
                        <Link className="dark" to='/random'>
                            <div>Show Me The Money</div>
                        </Link>
                    </div>
                </div>
            </div>
        </header>
    )
}

export default Header;