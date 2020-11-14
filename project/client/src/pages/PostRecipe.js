import React from 'react';

export default function PostRecipe() {
    return (
        <div className="container full-body mt-5 text-center">
            <h3 className="mb-5">
                Design Your Own Recipe!
            </h3>
            <label for="title">Recipe Title</label>
            <input id="title" type="text" placeholder="Title here..."></input>
            <label for="time">Prep Time</label>
            <input id="time" type="number" placeholder="Prep Time in minutes..."></input>
            <label for="time">Cook Time</label>
            <input id="time" type="number" placeholder="Cook Time in minutes..."></input>
            <label for="servings">Servings</label>
            <input id="servings" type="number" placeholder="Servings count here..."></input>


            {/* {
                categories.map(category => {
                    return (
                        <input type="checkbox" id={category.id} value={category.name}>{category.name}</input>
                        <label for={category.id}>{category.name}</label>
                    )
                })
            } */}
            <div>
                <h4 className="mt-5">Categories</h4>
                <div>
                    <input className="m-2" type="checkbox" value='Pasta'/>
                    <label>Pasta</label>
                    <input className="m-2" type="checkbox" value='Bread'/>
                    <label className="m-2">Bread</label>
                    <input className="m-2" type="checkbox" value='Soup'/>
                    <label className="m-2">Soup</label>
                    <input className="m-2" type="checkbox" value='Salad'/>
                    <label className="m-2">Salad</label>
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
                        <tr>
                            <td>1.</td>
                            <td><input className="expand" type="text" placeholder="Ingredient #1"></input></td>
                            <td><input type="text" placeholder="Ingredient #1"></input></td>
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
                            <td><button className="btn btn-danger pt-1 pb-1">X</button></td>
                        </tr>
                    </tbody>
                </table>
                <div >
                    <button className="btn btn-secondary">Add Another Ingredient</button>
                </div>
                <h4 className="mt-5">Directions</h4>
                <div className="text-left">
                    <ul>
                        <li>Enter direcions separated into numbered steps below.</li>
                        <li>Typing the pound sign ('#'), followed by the number of a corresponding ingredient with autofill ingredient information into your posted recipe page.</li>
                    </ul>
                    <div className="row">
                        <div className="col-1"></div>
                        <p className="col-10">
                            e.g. <br/>
                            "1. Boil #2 in water for 5 minutes until thoroughly cooked." <br/>
                            <br/>
                            ...will display as...<br/>
                            <br/>
                            "1. Boil 1 Box of Spaghetti in water for 5 minutes until thoroughly cooked."
                        </p>
                        <div className="col-1"></div>
                    </div>
                </div>
                <table className="table">
                    <tbody>
                        <tr>
                            <td>1.</td>
                            <td><textarea className="expand" placeholder="Direction #1"></textarea></td>
                            <td><button className="btn btn-danger pt-1 pb-1">X</button></td>
                        </tr>
                    </tbody>
                </table>
                <button className="btn btn-secondary mb-1">Add Another Direction</button>
            </div>
            <button className="btn btn-secondary btn-lg mt-3 mb-5">Add Another Direction</button>

        </div>
    );
}