import React from 'react';
import Rating from './Rating'
import { Link } from 'react-router-dom';


function SquareCard() {

    return (
        <Link to='/recipe'>
            <div class="card custom-card border-white">
                <img class="card-img-top" src="https://www.alphafoodie.com/wp-content/uploads/2019/05/Thai-Green-Curry.jpeg" alt="Curry"></img>
                <div class="card-body">
                    <p class="card-title text-dark">Thai Curry</p>
                    <p class="card-text text-dark">Posted By: Noah Mitchelson</p>
                    <div>
                        <Rating/>
                    </div>
                </div>
            </div>
        </Link>
    )
}

export default SquareCard;