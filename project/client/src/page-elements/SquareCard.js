import React from 'react';
import Rating from './Rating'
import { Link } from 'react-router-dom';


function SquareCard({recipe}) {
    console.log(recipe)
    return (
        <Link to={`/recipe/${recipe.recipeId}`}>
            <div className="card custom-card border-white mt-5 mb-5">
                <div className="image">
                    <img className="card-img-top" src={recipe.imageLink} alt={recipe.name}></img>

                </div>
                <div className="card-body">
                    <p className="card-title text-dark">{recipe.name}</p>
                    <p className="card-text text-dark">Posted By: userName Placeholder</p>
                    <div>
                        <Rating/>
                    </div>
                </div>
            </div>
        </Link>
    )

    // return (
    //     <Link to='/recipe'>
    //         <div className="card custom-card border-white">
    //             <img className="card-img-top" src="https://www.alphafoodie.com/wp-content/uploads/2019/05/Thai-Green-Curry.jpeg" alt="Curry"></img>
    //             <div className="card-body">
    //                 <p className="card-title text-dark">Thai Curry</p>
    //                 <p className="card-text text-dark">Posted By: Noah Mitchelson</p>
    //                 <div>
    //                     <Rating/>
    //                 </div>
    //             </div>
    //         </div>
    //     </Link>
    // )
}

export default SquareCard;