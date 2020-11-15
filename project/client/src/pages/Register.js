import React, { useState, useEffect, useContext} from 'react';
import { Link, useHistory } from 'react-router-dom';

import AuthContext from '../page-elements/AuthContext';


export default function Register() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    
    const auth = useContext(AuthContext);
    const history = useHistory();

    useEffect( () => {
        setPassword();
        setUsername();
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
    
        const response = await fetch('http://localhost:8080/api/authenticate', {
          method: 'POST',
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
            username,
            password
          })
        });
        
        if (response.status === 200) {
          const { jwt_token } = await response.json();
        
          auth.login(jwt_token);
        
          history.goBack(); // Not sure if this will work.  If not history.push('/') will redirect to home instead
        } 
        // else if (response.status === 403) {
        //   setErrors(['Login failed.']);
        // } else {
        //   setErrors(['Unknown error.']);
        // }
      };

    return (

        <div className="container full-body">
            <div className="text-center m-5">
                <h1 className="p-2">Login</h1>
                <form onSubmit={() => handleSubmit()}>
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

                            <div>
                                <button type="submit" className="btn btn-light btn-outline-dark m-2 expand btn-sm">Create an Account</button>
                            </div>
                        </div>

                    </div>
                </form>
                <h4 className="m-5">
                    Build A Cookbook: Your gateway to home cooking
                </h4>  
            </div>
        </div>

    );
}
