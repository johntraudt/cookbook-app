import React, {useState, useEffect} from 'react';
import { Link, useHistory } from 'react-router-dom';
import banner from '../resources/home-banner.jpg';
import SearchBar from '../page-elements/SearchBar';
import SquareCard from '../page-elements/SquareCard';

export default function Cookbook() {

    const [cookbook, setCookbook] = useState([]);

    const history = useHistory(); 

    const cookbookId = history.location.pathname.replace('/cookbook/','');

    const featuredRecipes = () => {
        fetch(`http://localhost:8080/api/cookbook/${cookbookId}`) 
            .then(response => response.json())
            .then((data) => {
                setCookbook(data);
            });
    }
    
    useEffect(() => {
        featuredRecipes();
    }, []);

    if(!cookbook) {
        return (<h1 className="container text-center">Data Not Found</h1>);
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
                {cookbook.recipes.map(recipe => (
                    <div className="col-lg-4 col-md-6 col-sm-12" id={recipe.recipeId}>
                        <SquareCard recipe={recipe} />
                    </div>
                ))}
                </div>
            </div>
        </div>
    );
}














