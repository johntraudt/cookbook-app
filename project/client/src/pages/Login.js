import React from 'react';

function Login() {
    return (

        <div className="container full-body">
            <div className="text-center m-5">
                <h1 className="p-2">Login</h1>
                <form>
                    <div className="center">
                        <div className="p-2">
                            <div>
                                <label className="pr-3">Username: </label>
                                <input className="m-1 expand" type='text' placeholder="Username"></input>
                            </div>
                            <div>
                                <label className="pr-3">Password: </label>
                                <input className="m-1 expand" type='text' placeholder="Password"></input>
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
                    Build A Cookbook: You gateway to home cooking
                </h4>  
            </div>
        </div>

    );
}
  
export default Login;