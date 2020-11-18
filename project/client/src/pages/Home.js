import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import banner from '../resources/home-banner.jpg';
import SearchBar from '../page-elements/SearchBar';
import SquareCard from '../page-elements/SquareCard';

export default function Home() {

    const [recipes, setRecipes] = useState([]);

    const featuredRecipes = () => {
        fetch(`${process.env.REACT_APP_URL}/api/recipe`) 
            .then(response => response.json())
            .then((data) => {
                setRecipes(data);
            });
    }
    
    useEffect(() => {
        featuredRecipes();
    }, []);

    if(!recipes[0]) {
        return (<h1 className="container full-body text-center mt-3">Data Not Found</h1>);
    }

    return (
        <div className="container full-body">
            <div className="mt-4">
                <div className="banner">
                    <div className="text-center">
                        <div className="center-overlay">
                            <div className="d-flex flex-wrap justify-content-center">
                                <div className="col-lg-6 col-md-6 col-sm-10">
                                    
                                    <SearchBar className="home-search"/>
                                    <Link to='/categories'>
                                        <button className="btn btn-light btn-lg mt-1">Categories</button>
                                    </Link>
                                </div>
                            </div>
                        </div>
                    </div>
                    <img className="img-fluid" src={banner} alt="Homepage banner with various fresh ingredients"/>
                </div>

                <div className="subtitle text-center">
                    This Week's Featured Recipes:
                </div>
                
                <div className="d-flex flex-wrap justify-content-center">
                {recipes.map(recipe => (
                    recipe.featured === true && (
                    <div className="col-lg-4 col-md-6 col-sm-12" id={recipe.recipeId}>
                        <SquareCard recipe={recipe} />
                    </div>
                    )
                ))}
                </div>
            </div>
        </div>
    );
}