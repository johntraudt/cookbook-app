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
                            <div className="float-left">
                                <img height="20px" width="20px" src="dino.jpg" alt="dino logo"></img>
                            </div>
                            <div className="float-right">
                                <h1>BUILD A COOKBOOK</h1>
                            </div>
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
                                <div className="right btn btn-outline-secondary p-2 pr-4 pl-4">LOG IN</div>
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
                        <Link className="dark"  to='/recipe/random' onClick={() => window.location.reload()}>
                            <div>Show Me The Money</div>
                        </Link>
                    </div>
                </div>
            </div>
        </header>
    )
}

export default Header;