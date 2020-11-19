import React, {useState, useEffect, useContext} from 'react';
import Rating from '../page-elements/Rating'
import { useLocation, useHistory } from 'react-router-dom'
import DropdownButton from 'react-bootstrap/DropdownButton';
import Dropdown from 'react-bootstrap/Dropdown'
import AuthContext from '../page-elements/AuthContext';
import Errors from './Errors';


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
    const [cookbookId, setCookbookId] = useState(0)
    const [cookbooks, setCookbooks] = useState([])

    const today = new Date();
    const [review, setReview] = useState({
        reviewId: 0,
        recipeId: 0,
        userId: 0,
        userName: "",
        comment: "",
        rating: 5,
        date:  `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`
    }) 

    const [recipeAdded, setRecipeAdded] = useState(false);

    const [errors, setErrors] = useState([]);

    const auth = useContext(AuthContext);
    const history = useHistory();
    const location = useLocation();

    const getRecipe = () => {
        fetch(`${process.env.REACT_APP_URL}/api${location.pathname}`)
            .then((response) => {
                if (response.status >= 400) {
                    history.push("/notfound");
                } else {
                    response.json()
                        .then((data) => setRecipe(data));
                }
            })
    }

    const getCookbooks = () => {
        if (!auth.user) {
            return null;
        }
        fetch(`${process.env.REACT_APP_URL}/api/cookbook/user/${auth.user.userId}/all`) 
            .then(response => response.json())
            .then((data) => {
                setCookbooks(data);
            });
    };



    const addRecipeToCookbook = (event) => {
        event.preventDefault();
        setRecipeAdded(false);

        if (cookbookId > 0) {
            fetch(`${process.env.REACT_APP_URL}/api/cookbook/${cookbookId}/${recipe.recipeId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type':'application/json',
                    "Authorization": "Bearer " + auth.user.token
                }
            })
            .then(response => {
                if (response.status < 400) {
                    console.log('success!');
                    setRecipeAdded(true);
                } else if (response.status >= 400) {
                    response.json()
                        .then((data) => {
                            setErrors(data);
                            console.log(data)
                        }
                    );
                }
            })
        }
    }
    
    useEffect(() => {
        getCookbooks();
        getRecipe();
    }, []);
    
    if (!auth) {
        return null;
    }

    if(!recipe) {
        return null;
    }

    const addReview = () => {

        fetch(`${process.env.REACT_APP_URL}/api/review`,  {
            method: 'post',
            headers: {
                'Content-Type':'application/json',
                "Authorization": "Bearer " + auth.user.token
            },
            body: JSON.stringify({
                rating: review.rating,
                comment: review.comment,
                date: review.date,
                userId: `${auth.user.userId}`,
                recipeId: recipe.recipeId
            })
        }).then((response) => {
            if (response.status >= 400) {
                setErrors(['You have already reviewed this recipe']);
            } else {
                getRecipe();
            }
        })
        setComment('');
    };

    const setComment = (text) => {
        const tempReview = {
            reviewId: review.reviewId,
            recipeId: review.recipeId,
            userId: review.userId,
            userName: review.userName,
            comment: text,
            rating: review.rating,
            date:  `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`
        }
        setReview(tempReview);
    }

    const setRating = (score) => {
        const tempReview = {
            reviewId: review.reviewId,
            recipeId: review.recipeId,
            userId: review.userId,
            userName: review.userName,
            comment: review.comment,
            rating: score,
            date:  `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`
        }
        setReview(tempReview);
    }

    return (
        <div className="container full-body text-center">
            <div className="mt-4">
                <h2 className="display-3">{recipe.name}</h2>
                <Rating detailed={true} reviews={recipe.reviews}/>
                <div className="row">
                    <div className="col-lg-2 col-sm-0"></div>
                    <div className="col-lg-8 col-sm-12">
                        <div className="row justify-content-md-center m-2">
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
                                    {auth.user && (
                                    <div className="col m-2">

                                        <select onChange={(event) => {setCookbookId(event.target.value)}} className="p-1 mr-3">
                                            <option value={0}>--Add To Cookbook--</option>
                                            {
                                                cookbooks.map((cookbook) => (
                                                    <option value={cookbook.cookbookId}>{cookbook.title}</option>
                                                ))
                                            }
                                        </select>

                                        <button onClick={(event) => {addRecipeToCookbook(event)}} className="btn btn-light btn-outline-dark btn-sm">Add Recipe</button>
                                        {
                                            recipeAdded ? <div>{recipe.name} was added to your cookbook</div> : <></>
                                        }
                                    </div>
                                    )}
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
                                                return <li>{ingredient.quantity ? ingredient.quantity : ''} {ingredient.measurementUnit.measurementUnitId !== 0 && ingredient.measurementUnit.measurementUnitId !== 5 ? ingredient.measurementUnit.name : ''} {ingredient.ingredient.name}</li>
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
                                    <th colspan="2">Comments</th>
                                </tr>
                                { auth.user && (
                                    <>
                                        <tr>
                                            <td colspan="2">
                                                <form className="text-center">
                                                    <textarea className="form-control" onChange={(event) => setComment(event.target.value)} value={review.comment}></textarea>
                                                </form>
                                            </td>
                                        </tr>
                                        <tr className="text-center mr-3">
                                            <td>
                                                <DropdownButton alignRight title={"⭐".repeat(review.rating) + "☆".repeat(5-review.rating)} id="dropdown-menu-align-right" onSelect={setRating}>
                                                    <Dropdown.Item eventKey={1}>⭐☆☆☆☆</Dropdown.Item>
                                                    <Dropdown.Item eventKey={2}>⭐⭐☆☆☆</Dropdown.Item>
                                                    <Dropdown.Item eventKey={3}>⭐⭐⭐☆☆</Dropdown.Item>
                                                    <Dropdown.Item eventKey={4}>⭐⭐⭐⭐☆</Dropdown.Item>
                                                    <Dropdown.Item eventKey={5}>⭐⭐⭐⭐⭐</Dropdown.Item>
                                                </DropdownButton>
                                            </td>
                                            <td>
                                                <button className="btn btn-outline-secondary ml-3" onClick={() => addReview()}>Post a Review</button>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colSpan="2" className="text-center">
                                                <div className="row">
                                                    <div className="col-lg-2 col-md-0 col-sm-0"/>
                                                    <div className="col-lg-8 col-md-12 col-sm-12">
                                                        <Errors errors={errors} />
                                                    </div>
                                                    <div className="col-lg-2 col-md-0 col-sm-0"/>
                                                </div>
                                            </td>
                                        </tr>
                                    </>
                                )}
                                <table className="table">
                                    {recipe.reviews.map((review) => {
                                        return <div>
                                            <th>{review.comment === null ? "" : review.user.firstName +  " " + review.user.lastName}</th>
                                            <tr>
                                                <div className="pl-5">{review.comment === null ? "" : review.comment}</div>
                                            </tr>
                                            <tr>
                                                <div className="pl-5 pb-3"> {review.comment === null ? "" : <Rating detailed={false} reviews={[review]}/>}</div>
                                            </tr>
                                        </div>
                                    })}
                                </table>
                            </table>
                        </div>
                    </div>
                </div>
                <div className="col-lg-2 col-sm-0"></div>
            </div>
        </div>
    )
}