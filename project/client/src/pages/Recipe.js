import React, {useState, useEffect} from 'react';
import Rating from '../page-elements/Rating'
import { useLocation } from 'react-router-dom'

export default function Recipe() {

    const [recipe, setRecipe] = useState({
        cookTimeInMinutes: 0,
        date: "",
        directions: [],
        featured: false,
        imageLink: "",
        ingredients: [],
        name: "",
        prepTimeInMinutes: 0,
        recipeId: 0,
        reviews: [],
        servings: 0,
        tags: null,
        user: {
            userId: 1,
            userName: "",
            email: "",
            passwordHash: null,
            firstName: "",
            lastName: "",
            passwordHash: null,
            role: {
                userRoleId: 0,
                name: ""
            },
            userId: 0,
            userName: "",
            userRoleId: 0},
        userId: 0,
        wasUpdated: false
    });

    const today = new Date();

    const [review, setReview] = useState({
        reviewId: 0,
        recipeId: 0,
        userId: 0,
        comment: "",
        rating: 5,
        date:  `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`
    }) 
    
    const location = useLocation();

    
    useEffect(() => {
        const getRecipe = () => {
            fetch(`http://localhost:8080/api${location.pathname}`) 
                .then(response => response.json())
                .then((data) => {
                    setRecipe(data);
                    console.log(data);
                });
        }
        getRecipe()
    }, [location.pathname]);

    if(!recipe) {
        return null;
    }

    const addReview = (event) => {
        event.preventDefault();
    };

    const setComment = (text) => {
        const tempReview = review;
        setReview(tempReview.comment = text);
        console.log(review.comment);
    }

    return (
        <div className="container full-body text-center">
            <div className="mt-4">
                <h2 className="display-3">{recipe.name}</h2>
                <Rating detailed={true} reviews={recipe.reviews}/>
                <div className="row">
                    <div className="col-2"></div>
                    <div className="col-8">
                        <div className="row justify-content-md-center">
                            <div className="mr-2 ml-2">User: {`${recipe.user.firstName} ${recipe.user.lastName}`}</div>
                            <div className="mr-2 ml-2">{recipe.wasUpdated ? 'Posted': 'Edited'}: {recipe.date}</div>
                            <div className="mr-2 ml-2">Prep Time: {recipe.prepTimeInMinutes}</div>
                            <div className="mr-2 ml-2">Cook Time: {recipe.cookTimeInMinutes}</div>
                            <div className="mr-2 ml-2">Servings: {recipe.servings}</div>

                            {
                            recipe.tags !== null && (
                                <div className="mr-2 ml-2">{`Tags:
                                    ${
                                        recipe.tags.map((tag) => {return (` ${tag.name}`).toLowerCase()})
                                    }`}
                                </div>
                            )}
                        </div>

                        <div className="card border-white">
                            <img className="card-img-top p-2" src={recipe.imageLink} alt={`${recipe.name}`}></img>
                            <div className="card-body p-0">
                                <form className="m-2">
                                    <div className="col m-2">
                                        <select className="p-1" name="myCookbooks" id="myCookbooks">
                                            <option value="">--Add To Cookbook--</option>
                                            <option value="Dessert Book">Dessert Book</option>
                                            <option value="Mexican Food">Mexican Food</option>
                                        </select>
                                        {/* <button className="btn btn-light btn-outline-dark btn-sm">Add Recipe</button> */}
                                    </div>
                                    {/* <div className="col">
                                        <lable className="text-secondary">Desired Servings: </lable>
                                        <input type="number"  placeholder="Set Serving Size #..."></input>
                                    </div> */}
                                </form>
                                <div className="text-left">
                                    <h4>Ingredients</h4>
                                    <ul>
                                        {
                                            recipe.ingredients.map((ingredient) => {
                                                return <li>{ingredient.quantity} {ingredient.measurementUnit.name!==null ? `${ingredient.measurementUnit.name}`.toLowerCase() : ''} {ingredient.ingredient.name}</li>
                                            })
                                        }
                                    </ul>
                                    <h4>Directions</h4>
                                    <ol>
                                        {
                                            recipe.directions.map((direction) => {
                                                return <li>{direction.text}</li>
                                            })
                                        }
                                    </ol>
                                </div>
                            </div>
                        </div>
                        <div>
                            <table className="table text-left mt-4">
                                <tr className="text-center">
                                    <th>Comments</th>
                                </tr>
                                <tr><td>
                                    <form className="text-center" onSubmit={(event) => addReview(event)}>
                                        <textarea className="expand" onChange={(event) => setComment(event.target.value)} value={review.comment}>
                                            Hello
                                        </textarea>
                                        <button className="btn" type="submit">Write a Review</button>
                                    </form>
                                </td></tr>
                                <table className="table">
                                    {recipe.reviews.map((review) => {
                                        return <div><th> {} usename === null ? '' : usesanem </th>
                                            <tr><div className="pl-5 pb-3">{review.comment}</div></tr></div>
                                    })}
                                </table>
                            </table>
                        </div>
                    </div>
                </div>
                <div className="col-2"></div>
            </div>
        </div>
    )
}