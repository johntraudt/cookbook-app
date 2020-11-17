import React, {useState, useEffect} from 'react';
import { Link, Redirect, useHistory } from 'react-router-dom';
import banner from '../resources/home-banner.jpg';
import SearchBar from '../page-elements/SearchBar';
import SquareCard from '../page-elements/SquareCard';
import CookbookCardTemp from '../page-elements/CookbookCardTemp';

export default function Cookbook() {

    const [cookbook, setCookbook] = useState([]);

    const history = useHistory(); 

    const cookbookId = history.location.pathname.replace('/cookbook/','');

    const featuredRecipes = () => {
        fetch(`http://localhost:8080/api/cookbook/${cookbookId}`) 
            .then( (response) => {
                if (response.status >= 400) {
                    history.push("/notfound")
                } else {
                    response.json()
                        .then((data) => setCookbook(data))
                }
            });
    }
    
    useEffect(() => {
        featuredRecipes();
    }, []);

    if(!cookbook) {
        return (<h1 className="container text-center">Cookbook Not Found</h1>);
    }

    if(!cookbook.recipes) {
        return (<h1 className="container text-center">Recipes Not Found</h1>);
    }



    return (
        <div className="container full-body">
            <div className="mt-4">

                <div className="subtitle text-center">
                    {cookbook.title} by {cookbook.user ? cookbook.user.userName : ''}
                </div>
                
                <div className="d-flex flex-wrap justify-content-center">
                    {cookbook.recipes.map(recipe => (
                        <div className="col-lg-4 col-md-6 col-sm-12" id={recipe.recipeId}>
                            <CookbookCardTemp cookbookId={cookbookId} recipe={recipe} />
                            {/* <div className="text-center">
                                <button>Press me</button>
                            </div> */}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}














