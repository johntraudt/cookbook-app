import React from 'react';
import { Link } from 'react-router-dom';

function SquareCard() {
    return (
        // <Link to={`/results/${category}`}>
            <div className="card custom-card border-white mb-5">
                <img className="card-img-top rounded-circle" src="https://www.alphafoodie.com/wp-content/uploads/2019/05/Thai-Green-Curry.jpeg" alt="Curry"></img>
                <div className="card-title text-center p-2"><h3>Curry</h3></div>
            </div>
        // </Link>
    )
}

export default SquareCard;