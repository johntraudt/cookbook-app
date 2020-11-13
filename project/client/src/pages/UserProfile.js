import React, {useState} from 'react';

export default function UserProfile() {
    // const [states, setStates] = useState([]);

    // const acc = document.getElementsByClassName("accordion");

    let test = "accordion"

    

    return (
        <div className="container full-body">
            <h1 className="text-center m-4">UserName Profile</h1>
            <div className="row mt-5">
                <div className='col-4'>
                    
                    <table className="table">
                        <thead>
                            <th colspan="2" className="text-center">
                                <h3>User Information</h3>
                            </th>
                        </thead>
                        <tr>
                            <th>UserName</th>
                            <td>Insert userName</td>
                        </tr>
                        <tr>
                            <th>Email</th>
                            <td>Insert email</td>
                        </tr>
                        <tr>
                            <th>First Name</th>
                            <td>Insert firstName</td>
                        </tr>
                        <tr>
                            <th>Last Name</th>
                            <td>Insert lastName</td>
                        </tr>
                        <tr>
                            <th>Status</th>
                            <td>Insert role.name</td>
                        </tr>
                        <tr>
                            <td colspan="2" className="text-center">
                                <button className="btn btn-secondary">Edit</button>
                            </td>
                        </tr>
                    </table>
                </div>

                <div className='col-8'>




                    <button className={test} onclick={test = 'block'}>Section 1</button>
                    <div className="panel">
                        <p>Lorem ipsum...</p>
                    </div>

                    <button className="accordion">Section 2</button>
                    <div className="panel">
                        <p>Lorem ipsum...</p>
                    </div>

                    <button className="accordion">Section 3</button>
                    <div className="panel">
                        <p>Lorem ipsum...</p>
                    </div>


                </div>
            </div>
        </div>    
    );
}