import React, {useContext} from 'react';
import Rating from './Rating'
import { Link, useHistory } from 'react-router-dom';
import AuthContext from './AuthContext';

function CookbookCardTemp({cookbookId, recipe}) {

    const history = useHistory();

    const auth = useContext(AuthContext)

    const removeRecipe = () => {
        
        fetch(`${process.env.REACT_APP_URL}/api/cookbook/${cookbookId}/${recipe.recipeId}`, {
            method: 'delete',
            headers: {
                'Content-Type':'application/json',
                "Authorization": "Bearer " + auth.user.token
            }
        })
            
            .then((response) => {
                if (response.status >= 400) {
                    history.push("/notfound");
                } 
            });
    }

    return (
        <div className="card shadow custom-card border-white mt-5 mb-5">
            <Link to={`/recipe/${recipe.recipeId}`}>
                <div className="image">
                    <img className="card-img-top" src={recipe.imageLink} alt={recipe.name}></img>
                </div>
            </Link>

            <div className="card-body">
                <Link to={`/recipe/${recipe.recipeId}`}>
                    <p className="card-title text-dark">{recipe.name}</p>
                    <p className="card-text text-dark">Posted By: {recipe.user ? recipe.user.userName : ""}</p>
                
                    <div>
                        <Rating detailed={true} reviews={recipe.reviews}/>
                    </div>
                </Link>

                <div className="text-right">
                    <button className="btn btn-danger" onClick={(recipe) => removeRecipe(recipe)}>X</button>
                </div>
            </div>
        </div>
    )
}

export default CookbookCardTemp;