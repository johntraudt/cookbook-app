import React, {useState, useEffect} from 'react';
import SquareCard from '../page-elements/SquareCard';

export default function Results() {
    const [recipes, setRecipes] = useState([]);

    const featuredRecipes = () => {
        fetch('http://localhost:8080/api/recipe') 
            .then(response => response.json())
            .then((data) => {
                setRecipes(data);
            });
    }
    
    useEffect(() => {
        featuredRecipes();
    }, []);

    return (
        <div className="container full-body">
            <h1 className="text-center m-4">Results for: Pizza</h1>
            <div className="d-flex flex-wrap justify-content-center">
            {recipes.map(recipe => (
                <div className="col-4" id={recipe.recipeId}>
                    <SquareCard recipe={recipe} />
                </div>
            ))}
            </div>
        </div>    
    );
}