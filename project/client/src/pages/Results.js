import React, {useState, useEffect} from 'react';
import SquareCard from '../page-elements/SquareCard';
import { useHistory, Redirect } from 'react-router-dom';

export default function Results() {
    const [recipes, setRecipes] = useState([]);

    const history = useHistory(); 

    const searchTerm = history.location.pathname.replace('/results/','');



    const featuredRecipes = () => {
        fetch(`http://localhost:8080/api/recipe/search/${searchTerm}`) 
            .then(response => response.json())
            .then((data) => {
                setRecipes(data);
            });
    }
    
    useEffect(() => {
        featuredRecipes();
    }, []);

    if (!searchTerm) {
        return (
            <div>

            </div>
        )
    }

    if (recipes.length === 0) {
        return (
            <div className="container full-body">
                <h1>No results were found for: {searchTerm}</h1>
            </div>
        )
    }

    return (
        <div className="container full-body">
            <h1 className="text-center m-4">Results for: {searchTerm}</h1>
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