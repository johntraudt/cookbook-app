import React from 'react';
import dino from '../resources/dino.jpg';

export default function AboutUs() {
    return (
        <div className="container full-body text-center">
            <div className="mt-4">
                <h4 className="mt-5 mb-3">
                    A Recipe Book web app to dicover and share your favorite recipes. 
                </h4>
                <div className="col-12">
                    <iframe height="200px" width='200px' src="https://docs.google.com/presentation/d/e/2PACX-1vQ6CpRYUEVaSaYe9vGunlyzZqy25sd1zuAIfhKAN67bkpNyJJK8rQBeU6yPLQHys-Ye-gF6OeKFz_lV/embed?start=false&loop=false&delayms=60000" frameborder="0" width="960" height="569" allowfullscreen="true" mozallowfullscreen="true" webkitallowfullscreen="true"></iframe>
                </div>
                <img className="mt-3" height="300px" width="300px" src={dino} alt="dino logo"></img>
            </div>
        </div>
    );
}