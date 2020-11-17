import React from 'react';
import Rating from './Rating'
import { Link } from 'react-router-dom';

const hello = (event) => {
    alert('hello');
}

function CookbookCardTemp({recipe}) {

    console.log(recipe);

    return (
        <Link to={`/recipe/${recipe.recipeId}`}>
            <div className="card shadow custom-card border-white mt-5 mb-5">
                <div className="image">
                    <img className="card-img-top" src={recipe.imageLink} alt={recipe.name}></img>
                </div>

                <div className="card-body">
                    <p className="card-title text-dark">{recipe.name}</p>
                    <p className="card-text text-dark">Posted By: {recipe.user ? recipe.user.userName : ""}</p>

                    <div>
                        <Rating detailed={true} reviews={recipe.reviews}/>
                    </div>

                    <div className="text-center">
                        <button onclick="hello(event)">Press me!</button>
                    </div>
                </div>
            </div>
        </Link>
    )
}

export default CookbookCardTemp;