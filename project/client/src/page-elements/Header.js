import React, {useContext} from 'react';
import { Link, useLocation, useHistory } from 'react-router-dom';
import SearchBar from './SearchBar';
import dino from '../resources/dino.jpg';
import AuthContext from './AuthContext';

function Header() {
    const location = useLocation();

    const history = useHistory();
    const auth = useContext(AuthContext);

    let randomInt = 0;

    const getRandomId = () => {
        fetch('http://localhost:8080/api/recipe/random') 
            .then(response => response.json())
            .then((data) => {
                randomInt = data;
                console.log(data);
            })
            // .then(() => console.log(randomInt))
            .then(() => history.push(`/recipe/${randomInt}`));
    };

    const logout = () => {
        auth.logout();
        history.push('/');
    }

    return (
        <header className="shadow">
            <div className="container">
                <div className="d-flex flex-wrap justify-content-center p-1">
                    <div className={location.pathname !== '/' ? "row mr-auto ml-3": "row mr-auto ml-3"} >
                        <Link to='/' >
                            <div className="float-left">
                                <img height="20px" width="20px" src={dino} alt="dino logo"></img>
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
                            {
                                !auth.user ? 
                                    <Link to='/login'><div className="right btn btn-outline-secondary p-2 pr-4 pl-4">LOG IN</div></Link> : 
                                    <button className="right btn btn-outline-secondary p-2 pr-4 pl-4" onClick={() => logout()}>Sign Out</button>
                            }
                        </div>
                    </div>
                </div>
            </div>
            <div className="subtitle shadow">
                <div className="container">
                    <div className="d-flex justify-content-around">
                        {auth.user && (
                            <>
                                <Link className="dark mr-auto ml-auto" to='/user'>
                                    <div className='ml-5 mr-5'>My Profile</div>
                                </Link>
                                <Link className="dark mr-auto ml-auto" to='/post'>
                                    <div className='ml-5 mr-5'>Post a Recipe</div>
                                </Link>
                            </>
                        )}
                        <div className="hand mr-auto ml-auto ml-5 mr-5" onClick={() => getRandomId()}>
                            Show Me The Money
                        </div>

                    </div>
                </div>
            </div>
        </header>
    )
}

export default Header;