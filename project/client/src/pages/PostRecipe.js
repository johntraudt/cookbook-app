import React, {useState, useEffect} from 'react';

export default function PostRecipe() {
    const today = new Date();
    const [categories, setCategories] = useState([]);
    const [ingredients, setIngredients] = useState([{
        name:'',
        quantity:'',
        measurementType:'',
        id:1,
    }]);
    const [directions, setDirections] = useState([{
        direction:'',
        id:1,
    }]);
    const [title, setTitle] =useState('');
    const [prepTime, setPrepTime] = useState(0);
    const [cookTime, setCookTime] = useState(0);
    const [servings, setServings] = useState(0);
    const [calories, setCalories] = useState(0);
    const [selectedCategories, setSelectedCategories] = useState([]);
    // const [recipe, setRecipe] = useState({
    //     userId: 0,
    //     name:'',
    //     prepTimeInMinutes:0, 
    //     cookTimeInMinutes:0, 
    //     servings: 0, 
    //     date: `${today.getFullYear()}-${today.getMonth()}-${today.getDate()}`, 
    //     wasUpdated: 1, 
    //     isFeatured: 0, 
    //     calories: 0, 
    //     imageLink: '',
    //     directions: [],
    //     ingredients: [], 
    // })

    useEffect(()=>{
        const getCategories = () => {
            fetch('http://localhost:8080/api/recipe-tag') 
                .then(response => response.json())
                .then((data) => {
                    setCategories(data);
                    console.log(data)
                });
        };
        getCategories();
        // setRecipe();
    },[]);

    const changeIngredientName = (event, id) =>  {
        let tempIngredients=[];
        for (let i=0; i < ingredients.length; i++) {
            tempIngredients.push(ingredients[i]);
        };
        tempIngredients[id-1].name = event.target.value;

        setIngredients(tempIngredients);
    }

    const changeIngredientQuantity = (event, id) => {
        let tempIngredients=[];
        for (let i=0; i < ingredients.length; i++) {
            tempIngredients.push(ingredients[i]);
        };
        tempIngredients[id-1].quantity = event.target.value;

        setIngredients(tempIngredients);
    }

    
    const changeDirection = (event, id) => {
        let tempDirections=[];
        for (let i=0; i < directions.length; i++) {
            tempDirections.push(directions[i]);
        };
        tempDirections[id-1].direction = event.target.value;

        setIngredients(tempDirections);
        console.log(directions)
    }

    const handleAddIngredient = () => {
        let tempIngredients=[];
        for (let i=0; i < ingredients.length; i++) {
            tempIngredients.push(ingredients[i]);
        };
        
        tempIngredients.push({
            name:'',
            quantity:'',
            measurementType:'',
            id:0,
        })

        for (let i=1; i <= tempIngredients.length; i++) {
            tempIngredients[i-1].id=i;
        };
        setIngredients(tempIngredients);
        console.log(ingredients)
    }

    const handleDeleteIngredient = (id) => {
        console.log(id);
        let tempIngredients=[];
        for (let i=0; i < ingredients.length; i++) {
            tempIngredients.push(ingredients[i]);
        };

        tempIngredients.splice(id-1, 1)

        for (let i=1; i <= tempIngredients.length; i++) {
            tempIngredients[i-1].id=i;
        };
        setIngredients(tempIngredients);
        console.log(ingredients)
    }

    const handleAddDirection = () => {
        let tempDirections=[];
        for (let i=0; i < directions.length; i++) {
            tempDirections.push(directions[i]);
        };
        
        tempDirections.push({
            name:'',
            quantity:'',
            measurementType:'',
            id:0,
        })

        for (let i=1; i <= tempDirections.length; i++) {
            tempDirections[i-1].id=i;
        };
        setDirections(tempDirections);
    }

    const handleDeleteDirection = (id) => {
        console.log(id);
        let tempDirections=[];
        for (let i=0; i < directions.length; i++) {
            tempDirections.push(directions[i]);
        };

        tempDirections.splice(id-1, 1)

        for (let i=1; i <= tempDirections.length; i++) {
            tempDirections[i-1].id=i;
        };
        setDirections(tempDirections);
        console.log(tempDirections)
    }

    // const toggleCategory = (category) => {
    //     let tempCategories = [];
    //     let checked = false; 
    //     for (let i=0; i < selectedCategories.length; i++) {
    //         if (selectedCategories[i].name != category.name) {
    //             tempCategories.push(selectedCategories[i])
    //         } else {
    //             checked = true;
    //         }
    //     };
    //     if (checked === false) {
    //         tempCategories.push(category)
    //     }
    //     setSelectedCategories(tempCategories)
    //     console.log(selectedCategories);
    // }

    return (
        <div className="container full-body mt-5 text-center">
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
            </div>

            <div>
                <h4 className="mt-5">Categories</h4>
                <div className="d-flex flex-wrap justify-content-center">
                    {
                        categories.map(category => (
                            <div className="ml-3 mr-3">
                                <input className="mr-2" type="checkbox" id={category.recipeTagId} value={category.name} />
                                <label for={category.recipeTagId}>{category.name}</label>
                            </div>
                        ))
                    }
                </div>
            </div>

            <div>
                <h4 className="mt-5">Ingredients</h4>
                <table className="table">
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
                                    <td>{ingredient.id}</td>
                                    <td><input value={ingredient.name} className="expand" onChange={event => changeIngredientName(event, ingredient.id)} type="text" placeholder={`Ingredient ${ingredient.id} here...`}></input></td>
                                    <td><input value={ingredient.quantity} onChange={event => changeIngredientQuantity(event, ingredient.id)} type="text" placeholder="Quantity here..."></input></td>
                                    <td>
                                        <select>
                                            <option>--  --</option>
                                            <option>tsp</option>
                                            <option>Cup</option>
                                            <option>Tbsp</option>
                                            <option>Ounces</option>
                                            <option>lbs</option>
                                        </select>
                                    </td>
                                    <td><button onClick={() => handleDeleteIngredient(ingredient.id)} className="btn btn-danger pt-1 pb-1">X</button></td>
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
                {/* <div className="text-left">
                    <ul>
                        <li>Enter direcions separated into numbered steps below.</li>
                        <li>Typing the pound sign ('#') around the the number of a corresponding ingredient with autofill ingredient information into your posted recipe page.</li>
                    </ul>
                    <div className="row">
                        <div className="col-1"></div>
                        <p className="col-10">
                            e.g. <br/>
                            "1. Boil #2# in water for 5 minutes until thoroughly cooked." <br/>
                            <br/>
                            ...will display as...<br/>
                            <br/>
                            "1. Boil 1 Box of Spaghetti in water for 5 minutes until thoroughly cooked."
                        </p>
                        <div className="col-1"></div>
                    </div>
                </div> */}
                <table className="table">

                    {
                        directions.map(direction => (
                            <tr>
                                <td>{direction.id}</td>
                                <td><textarea value={direction.direction} onChange={event => changeDirection(event, direction.id)} className="expand" placeholder={`Direction ${direction.id} here...`}></textarea></td>
                                <td><button onClick={() => handleDeleteDirection(direction.id)} className="btn btn-danger pt-1 pb-1">X</button></td>
                            </tr>
                        ))
                    }

                </table>
                <button onClick={() => handleAddDirection()} className="btn btn-secondary mb-1">Add Another Direction</button>
            </div>
            <button className="btn btn-secondary btn-lg mt-3 mb-5">Submit Recipe</button>

        </div>
    );
}