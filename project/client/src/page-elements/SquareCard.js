import React from 'react';
import Rating from './Rating'
import { Link } from 'react-router-dom';

function SquareCard({recipe}) {
    console.log(recipe)
    return (

        <Link to={`/recipe/${recipe.recipeId}`}>
            <div className="card shadow custom-card border-white mt-5 mb-5">
                <div className="image">
                    <img className="card-img-top" src={recipe.imageLink} alt={recipe.name}></img>
                </div>
                <div className="card-body">
                    <p className="card-title text-dark">{recipe.name}</p>
                    <p className="card-text text-dark">Posted By: {recipe.user.userName}</p>
                    <div>
                        <Rating reviews={recipe.reviews}/>
                    </div>
                </div>
            </div>
        </Link>
    )
}

export default SquareCard;