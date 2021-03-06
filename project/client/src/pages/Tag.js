import React, {useState, useEffect} from 'react';
import { useHistory, useLocation } from 'react-router-dom';
import SquareCard from '../page-elements/SquareCard';


export default function Tag() {

    const [tag, setTag] = useState([]);
    const history = useHistory(); 
    const location = useLocation();


    useEffect(() => {
        const featuredRecipes = () => {
            fetch(`${process.env.REACT_APP_URL}/api${location.pathname}`) 
                .then( (response) => {
                    if (response.status >= 400) {
                        history.push("/notfound")
                    } else {
                        response.json()
                            .then((data) => setTag(data))
                    }
                });
        }
        featuredRecipes();
    }, []);

    if(!tag || !tag.recipes) {
        return (<h1 className="container text-center">Cookbook Not Found</h1>);
    }


    return (
        <div className="container full-body">
            <div className="mt-4">

                <div className="subtitle text-center">
                    {tag.name}
                </div>
                
                <div className="d-flex flex-wrap justify-content-center">
                {tag.recipes.map(recipe => (
                    <div className="col-lg-4 col-md-6 col-sm-12" id={recipe.recipeId}>
                        <SquareCard detailed={true} recipe={recipe} />
                    </div>
                ))}
                </div>
            </div>
        </div>
    );
}



