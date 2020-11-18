import React, {useState, useEffect} from 'react';
import CicleCard from '../page-elements/CircleCard';
import { Link } from 'react-router-dom';

export default function Categories() {
    const [categories, setCategories] = useState([]);

    const getCategories = () => {
        fetch(`${process.env.REACT_APP_URL}/api/recipe-tag`) 
            .then(response => response.json())
            .then((data) => {
                setCategories(data);
            });
    }
    
    useEffect(() => {
        getCategories();
    }, []);

    if(!categories) {
        return (<h1 className="container text-center">Data Not Found</h1>);
    }

    return (
        <div className="container full-body">
            <div className="mt-4">
                <h1 className="text-center m-4">Categories</h1>
                <div className="d-flex flex-wrap justify-content-center">
                    {categories.map(category => (
                        <div className="col-4" id={category.recipeTagId}>
                            <Link className="dark" to={`/recipe-tag/${category.recipeTagId}`}>
                                <CicleCard category={category} />
                            </Link>
                        </div>
                    ))}
                </div>
            </div>
        </div>    
    );
}