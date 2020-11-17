import React from 'react';
import Loading from '../resources/loading-2.gif'

export default function Success({message}) {

    console.log(message);

    return (
        <div className="text-center">
            <h3>{message}</h3>
            <img src={Loading}></img>
        </div>
    )
}