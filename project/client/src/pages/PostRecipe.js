import React, {useState, useEffect, useContext} from 'react';
import Errors from './Errors';
import AuthContext from '../page-elements/AuthContext';
import {useHistory } from 'react-router-dom'
import Success from '../page-elements/Success'


export default function PostRecipe() {
    const [categories, setCategories] = useState([]);
    const [ingredients, setIngredients] = useState([{
        ingredient:{
            name:'',
        },
        ingredientListIndex:1,
        quantity:'',
        measurementUnitId:5,
    }]);
    const [directions, setDirections] = useState([{
        directionNumber:1,
        text:'',
    }]);
    const [title, setTitle] =useState('');
    const [prepTime, setPrepTime] = useState(0);
    const [cookTime, setCookTime] = useState(0);
    const [servings, setServings] = useState(0);
    const [calories, setCalories] = useState(0);
    const [selectedCategories, setSelectedCategories] = useState([]);
    const [image, setImage] = useState(''); 
    const [measurementUnits, setMeasurementUnits] = useState([]);
    const [errors, setErrors] = useState([]);
    const [isSuccess, setIsSuccess] = useState(false);

    useEffect(()=>{
        const getCategories = () => {
            fetch(`${process.env.REACT_APP_URL}/api/recipe-tag`) 
                .then(response => response.json())
                .then((data) => {
                    setCategories(data);
                    console.log(data)
                })
        };
        const getMeasurementUnits = () => {
            fetch(`${process.env.REACT_APP_URL}/api/measurement-unit`)
                .then(response => response.json())
                .then((data) => {
                    setMeasurementUnits(data);
                    console.log(data)
                })
        };
        getMeasurementUnits();
        getCategories();
        setSelectedCategories();
    },[]);

    const auth = useContext(AuthContext);
    const history = useHistory();
    const today = new Date();

    const SubmitButton = (event) => {
        event.preventDefault();

        fetch(`${process.env.REACT_APP_URL}/api/recipe`, {
            method: 'POST',
            headers: {
                'Content-Type':'application/json',
                "Authorization": "Bearer " + auth.user.token
            },
            body: JSON.stringify({
                recipeId: 0,
                userId: `${auth.user.userId}`,
                name: title,
                prepTimeInMinutes: prepTime, 
                cookTimeInMinutes: cookTime, 
                servings: servings, 
                date: `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`, 
                wasUpdated: false,
                calories: calories,
                imageLink: image,
                ingredients: ingredients,
                directions: directions,
                tags: selectedCategories,
                featured: false,
            })
        })
        .then(response => {
            if (response.status === 201) {
                setIsSuccess(true);
                setTimeout(()=>{history.push('/')}, 2500);
            } else if (response.status >= 400) {
                response.json()
                    .then((data) => {
                        setErrors(data);
                    }
                );
            }
        })
    }

    const changeMeasurementUnitId = (event, ingredientListIndex) => {
        let tempIngredients=[];
        for (let i=0; i < ingredients.length; i++) {
            tempIngredients.push(ingredients[i]);
        };
        tempIngredients[ingredientListIndex-1].measurementUnitId = event.target.value;

        setIngredients(tempIngredients);
    }

    const changeIngredientName = (event, ingredientListIndex) =>  {
        let tempIngredients=[];
        for (let i=0; i < ingredients.length; i++) {
            tempIngredients.push(ingredients[i]);
        };
        tempIngredients[ingredientListIndex-1].ingredient.name = event.target.value;

        setIngredients(tempIngredients);
    }

    const changeIngredientQuantity = (event, ingredientListIndex) => {
        let tempIngredients=[];
        for (let i=0; i < ingredients.length; i++) {
            tempIngredients.push(ingredients[i]);
        };
        tempIngredients[ingredientListIndex-1].quantity = event.target.value;

        setIngredients(tempIngredients);
    }

    const handleAddIngredient = () => {
        let tempIngredients=[];
        for (let i=0; i < ingredients.length; i++) {
            tempIngredients.push(ingredients[i]);
        };
        
        tempIngredients.push({
            ingredient:{
                name:'',
            },
            quantity:'',
            measurementUnitId:5,
            ingredientListIndex:0,
        })

        for (let i=1; i <= tempIngredients.length; i++) {
            tempIngredients[i-1].ingredientListIndex=i;
        };
        setIngredients(tempIngredients);
    }

    const handleDeleteIngredient = (ingredientListIndex) => {
        let tempIngredients=[];
        for (let i=0; i < ingredients.length; i++) {
            tempIngredients.push(ingredients[i]);
        };

        tempIngredients.splice(ingredientListIndex-1, 1)

        for (let i=1; i <= tempIngredients.length; i++) {
            tempIngredients[i-1].ingredientListIndex=i;
        };
        setIngredients(tempIngredients);
    }

    const changeDirection = (event, directionNumber) => {
        let tempDirections=[];
        for (let i=0; i < directions.length; i++) {
            tempDirections.push(directions[i]);
        };
        tempDirections[directionNumber-1].text = event.target.value;

        setDirections(tempDirections);
    }

    const handleAddDirection = () => {
        let tempDirections=[];
        for (let i=0; i < directions.length; i++) {
            tempDirections.push(directions[i]);
        };
        
        tempDirections.push({
            text:'',
            directionNumber:0,
        })

        for (let i=1; i <= tempDirections.length; i++) {
            tempDirections[i-1].directionNumber=i;
        };
        setDirections(tempDirections);

    }

    const handleDeleteDirection = (directionNumber) => {

        let tempDirections=[];
        for (let i=0; i < directions.length; i++) {
            tempDirections.push(directions[i]);
        };

        tempDirections.splice(directionNumber-1, 1)

        for (let i=1; i <= tempDirections.length; i++) {
            tempDirections[i-1].directionNumber=i;
        };
        setDirections(tempDirections);
    }

    const toggleCategory = (category) => {
        let tempCategories = [];
        let checked = true; 
        if(selectedCategories){
            for (let i=0; i < selectedCategories.length; i++) {
                if (selectedCategories[i].name !== category.name) {
                    tempCategories.push(selectedCategories[i])
                } else {
                    checked = false;
                }
            }
        };
        if (checked === true) {
            tempCategories.push(category)
        }
        setSelectedCategories(tempCategories);
    }

    if (!auth || !auth.user) {
        return null;
    }

    if (!auth.user) {
        return null;
    }

    return (
        <div className="container full-body text-center">
            {!isSuccess && (  
            <div className="mt-4">
                <div className="mt-4">
                    <h3 className="mb-3">
                        Design Your Own Recipe!
                    </h3>
                    <div className="d-flex flex-wrap justify-content-center">
                        <div className="ml-3 mr-3 mt-2">
                            <label for="title">Recipe Title: </label>
                            <input value={title} onChange={event => setTitle(event.target.value)} id="title" type="text" placeholder="Title here..."></input>
                        </div>
                        <div className="ml-3 mr-3 mt-2">
                            <label for="time">Prep Time (MINUTES):</label>
                            <input value={prepTime} onChange={event => setPrepTime(event.target.value)} id="time" type="number" placeholder="Prep Time in minutes..."></input>
                        </div>
                        <div className="ml-3 mr-3 mt-2">
                            <label for="time">Cook Time (MINUTES):</label>
                            <input value={cookTime} onChange={event => setCookTime(event.target.value)} id="time" type="number" placeholder="Cook Time in minutes..."></input>
                        </div>
                        <div className="ml-3 mr-3 mt-2">
                            <label for="servings">Servings:</label>
                            <input value={servings} onChange={event => setServings(event.target.value)} id="servings" type="number" placeholder="Servings count here..."></input>
                        </div>
                        <div className="ml-3 mr-3 mt-2">
                            <label for="calories">Calories:</label>
                            <input value={calories} onChange={event => setCalories(event.target.value)} id="calories" type="number" placeholder="Calories here..."></input>
                        </div>
                        <div className="ml-3 mr-3 mt-2">
                            <label for="image">Image Link:</label>
                            <input value={image} onChange={event => setImage(event.target.value)} id="image" type="text" placeholder="Image link here..."></input>
                        </div>
                    </div>

                    <div>
                        <h4 className="mt-5">Categories</h4>
                        <div className="d-flex flex-wrap justify-content-center">
                            {
                                categories.map(category => (
                                    <div className="ml-3 mr-3">
                                        <input onChange={() => toggleCategory(category)} className="mr-2" type="checkbox" id={category.recipeTagId} value={category.name} />
                                        <label for={category.recipeTagId}>{category.name}</label>
                                    </div>
                                ))
                            }
                        </div>
                    </div>

                    <div>
                        <h4 className="mt-5">Ingredients</h4>
                        <table className="table table-responsive">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Ingredient Name</th>
                                    <th>Quantity</th>
                                    <th>Measurement Type</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    ingredients.map(ingredient => (
                                        <tr>
                                            <td>{ingredient.ingredientListIndex}</td>
                                            <td><input value={ingredient.ingredient.name} className="expand" onChange={event => changeIngredientName(event, ingredient.ingredientListIndex)} type="text" placeholder={`Ingredient ${ingredient.ingredientListIndex} here...`}></input></td>
                                            <td><input value={ingredient.quantity} onChange={event => changeIngredientQuantity(event, ingredient.ingredientListIndex)} type="text" placeholder="Quantity here..."></input></td>
                                            
                                            <td>
                                                <select onChange={(event) => {changeMeasurementUnitId(event, ingredient.ingredientListIndex)}}>
                                                {   
                                                    measurementUnits.map((unit) => {
                                                        return <option value={unit.measurementUnitId}>{unit.name}</option>
                                                    })
                                                }
                                                </select>
                                            </td>

                                            <td><button onClick={() => handleDeleteIngredient(ingredient.ingredientListIndex)} className="btn btn-danger pt-1 pb-1">X</button></td>
                                        </tr>
                                    ))
                                }
                            </tbody>
                        </table>
                        <div >
                            <button onClick={() => handleAddIngredient()} className="btn btn-secondary">Add Another Ingredient</button>
                        </div>
                        <h4 className="mt-5">Directions</h4>
                        <div>
                            Enter directions separated into numbered steps below.
                        </div>

                        <table className="table table-responsive">

                            {
                                directions.map(direction => (
                                    <tr>
                                        <td>{direction.directionNumber}</td>
                                        <div>
                                        <td><textarea value={direction.text} onChange={event => changeDirection(event, direction.directionNumber)} className="expand" placeholder={`Direction ${direction.directionNumber} here...`}></textarea></td>
                                        </div>
                                        <td><button onClick={() => handleDeleteDirection(direction.directionNumber)} className="btn btn-danger pt-1 pb-1">X</button></td>
                                    </tr>
                                ))
                            }

                        </table>
                        <button onClick={() => handleAddDirection()} className="btn btn-secondary mb-1">Add Another Direction</button>
                    </div>
                    <div className="row mt-3">
                        <div className="col-lg-4 col-md-2 col-sm-0"></div>
                        <div className="justify-center col-lg-4 col-md-8 col-sm-12">
                            <Errors errors={errors} />
                        </div>
                        <div className="col-lg-4 col-md-2 col-sm-0"></div>
                    </div>
                   
                    <button onClick={(event) => SubmitButton(event)} className="btn btn-secondary btn-lg mt-3 mb-5">Submit Recipe</button>
                </div>
            </div>
            )}
            {isSuccess && (
                <Success message='Your Recipe has been posted! Redirecting....'/>
            )}
        </div>
    );
}