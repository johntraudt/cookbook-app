import React, {useState, useEffect} from 'react';
import { Link } from 'react-router-dom';
import banner from '../resources/home-banner.jpg';
import SearchBar from '../page-elements/SearchBar';
import SquareCard from '../page-elements/SquareCard';

function Home() {

    useEffect(() => {
        featuredRecipes()
    },[]);

    const featuredRecipes = async () => {
        fetch('http://localhost:8080/recipes/featured')
            .then(response => response.json())
            .then(data => {
            })
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
            <div className="card-grid">
                <div className="row mt-4 mb-4">
                    <div className="col-4"><SquareCard/></div>
                    <div className="col-4"><SquareCard/></div>
                    <div className="col-4"><SquareCard/></div>
                </div>
                <div className="row mt-4 mb-4">
                    <div className="col-4"><SquareCard/></div>
                    <div className="col-4"><SquareCard/></div>
                    <div className="col-4"><SquareCard/></div>
                </div>
            </div>

        </div>
    );
}
  
export default Home;