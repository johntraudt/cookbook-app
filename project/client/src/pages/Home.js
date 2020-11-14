import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import banner from '../resources/home-banner.jpg';
import SearchBar from '../page-elements/SearchBar';
import SquareCard from '../page-elements/SquareCard';

function Home() {

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

    if(!recipes) {
        return (<h1 className="container text-center">Data Not Found</h1>);
    }

    return (
        <div className="container full-body">
            <div className="banner">
                <div className="text-center">
                    <div className="center-overlay">
                        <div className="row">
                            <div className="col-3"></div>
                            <div className="col-6">
                                
                                <SearchBar className="home-search"/>
                                <Link to='/categories'>
                                    <button className="btn btn-light btn-lg mt-1">Categories</button>
                                </Link>
                            </div>
                            <div className="col-3"></div>
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
                <div className="col-4" id={recipe.recipeId}>
                    <SquareCard recipe={recipe} />
                </div>
                )
            ))}
            </div>
        </div>
    );
}
  
export default Home;