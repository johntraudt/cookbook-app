import React from 'react';
import { Link } from 'react-router-dom';

function SquareCard({category}) {
    return (
        // <Link to={`/results/${category}`}>
            <div className="card custom-card border-white mb-5">
                <div className="image">
                <img className="card-img-top rounded-circle" src={category.imageLink} alt={category.name}></img>
                </div>
                <div className="card-body">
                    <div className="card-title text-center p-2"><h3>{category.name}</h3></div>
                </div>
            </div>
        // </Link>
    )
}

export default SquareCard;