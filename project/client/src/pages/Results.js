import React, {useState, useEffect} from 'react';
import SquareCard from '../page-elements/SquareCard';
import { useHistory } from 'react-router-dom';

export default function Results() {
    const [recipes, setRecipes] = useState([]);

    const history = useHistory(); 

    const searchTerm = history.location.pathname.replace('/results/','');

    const recipeResults = () => {
        fetch(`${process.env.REACT_APP_URL}/api/recipe/search/${searchTerm}`) 
            .then(response => response.json())
            .then((data) => {
                setRecipes(data);
            });
    }
    
    useEffect(() => {
        recipeResults();
    }, []);

    console.log(recipes)
    console.log(searchTerm)

    if (!searchTerm) {
        return (
            <div>

            </div>
        )
    }

    if (recipes.length === 0) {
        return (
            <div className="container full-body mt-4">
                <h1>No results were found for: {searchTerm}</h1>
            </div>
        )
    }

    return (
        <div className="container full-body">
            <div className="mt-4">
                <h1 className="text-center m-4">Results for: {searchTerm}</h1>
                <div className="d-flex flex-wrap justify-content-center">
                {recipes.map(recipe => (
                    <div className="col-lg-4 col-md-6 col-sm-12" id={recipe.recipeId}>
                        <SquareCard recipe={recipe} />
                    </div>
                ))}
                </div>
            </div>
        </div>    
    );
}