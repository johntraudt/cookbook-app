import React, { useState, useEffect, useContext} from 'react';
import { Link, useHistory } from 'react-router-dom';
import Errors from './Errors';

import AuthContext from '../page-elements/AuthContext';


export default function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errors, setErrors] = useState([]);
    
    const auth = useContext(AuthContext);
    // const history = useHistory();

    useEffect( () => {
        setPassword();
        setUsername();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
    
        const response = await fetch('http://localhost:8080/api/user/authenticate', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                userName: username,
                passwordHash: password,
            })
        });
        console.log(response)

        if (response.status === 200) {
            const { jwt_token } = await response.json();
            console.log(jwt_token)
            auth.login(jwt_token);
        } else if (response.status ===403) {
            setErrors(["Could not find that user name and password combination"]);
            setTimeout(() => {setErrors([])}, 3000);
        } else {
            setErrors(["Unknown Error."]);
        }
    };

    return (
        <div className="container full-body">
            <div>
                <Errors errors={errors}/>
            </div>
            <div className="mt-4">
                <div className="text-center m-5">
                    <h1 className="p-2">Login</h1>
                    <form onSubmit={(event) => handleSubmit(event)}>
                        <div className="center">
                            <div className="p-2">
                                <div>
                                    <label className="pr-3">Username: </label>
                                    <input className="m-1 expand" type='text' placeholder="Username" onChange={event => setUsername(event.target.value)}></input>
                                </div>
                                <div>
                                    <label className="pr-3">Password: </label>
                                    <input className="m-1 expand" type='password' placeholder="Password" onChange={event => setPassword(event.target.value)}></input>
                                </div>
                            </div>

                            <div className="p-4">
                                <div>
                                    <button type="submit" className="btn btn-light btn-outline-dark m-2 expand btn-sm">Login</button>
                                </div>

                                <Link to='/signup'>
                                    <div>
                                        <button type="button" className="btn btn-light btn-outline-dark m-2 expand btn-sm">Create an Account</button>
                                    </div>
                                </Link>
                            </div>

                        </div>
                    </form>
                    <h4 className="m-5">
                        Build A Cookbook: Your gateway to home cooking
                    </h4>  
                </div>
            </div>
        </div>
    );
}