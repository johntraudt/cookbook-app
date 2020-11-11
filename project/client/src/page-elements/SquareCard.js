import React from 'react';

function SquareCard() {
    return (
        <div class="card custom-card">
            <img class="card-img-top" src="https://www.alphafoodie.com/wp-content/uploads/2019/05/Thai-Green-Curry.jpeg" alt="Curry"></img>
            <div class="card-body">
                <p class="card-title">Thai Curry</p>
                <p class="card-text">Posted By: Noah Mitchelson</p>
                
                <div>
                    <span class="fa fa-star star-checked"></span>
                    <span class="fa fa-star star-checked"></span>
                    <span class="fa fa-star star-checked"></span>
                    <span class="fa fa-star star-checked"></span>
                    <span class="fa fa-star star-unchecked"></span>
                </div>
            </div>
        </div>
    )
}

export default SquareCard;