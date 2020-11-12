import React from 'react';
import Rating from '../page-elements/Rating'

export default function Recipe() {
    return (
        <div className="container full-body">
            <div className="text-center"> 
                <h2 className="display-3">Thai Panang</h2>
                <Rating detailed/>
                <div className="row">
                    <div className="col-2"></div>
                    <div className="col-8">
                        <div className="row justify-content-md-center">
                            <div className="mr-2 ml-2">User: Noah Mitchelson</div>
                            <div className="mr-2 ml-2">Updated: 11/9/2020</div>
                            <div className="mr-2 ml-2">Prep Time: 30 minutes</div>
                            <div className="mr-2 ml-2">Servings: 4</div>
                            <div className="mr-2 ml-2">Categories: Thai</div>
                            <div className="card border-white">
                                <img className="card-img-top p-2" src="https://www.alphafoodie.com/wp-content/uploads/2019/05/Thai-Green-Curry.jpeg" alt="Curry"></img>
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
                                        <div className="col">
                                            <lable className="text-secondary">Desired Servings: </lable>
                                            <input type="number"  placeholder="Set Serving Size #..."></input>
                                        </div>
                                    </form>
                                    <div className="text-left">
                                        <h4>Ingredients</h4>
                                        <ul>
                                            <li>1 tablespoon olive oil</li>
                                            <li>1 small white onion, chopped</li>
                                            <li>1 tsp of salt</li>
                                            <li>1 red bell pepper, sliced into thin (¼” wide) strips</li>
                                            <li>1 green bell pepper sliced into thin strips</li>
                                            <li>3 carrots sliced </li>
                                        </ul>
                                        <h4>Instructions</h4>
                                        <ol>
                                            <li>If you’d like to serve rice with your curry (optional): Bring a large pot of water to boil. Add the rinsed rice and continue boiling for 30 minutes, reducing heat as necessary to prevent overflow. Remove from heat, drain the rice and return the rice to pot. Cover and let the rice rest for 10 minutes or longer, until you’re ready to serve. Just before serving, season the rice to taste with salt and fluff it with a fork.</li>
                                            <li>To make the curry, warm a large skillet with deep sides over medium heat. Once it’s hot, add the 1 tablespoon olive oil. Add the 1 small white onion, chopped and 1 tsp of salt and cook, stirring often, until the onion has softened and is turning translucent, about 5 minutes.</li>
                                            <li>Add the 1 red bell pepper, sliced into thin (¼” wide) strips and 3 carrots sliced . Cook until the bell peppers are easily pierced through by a fork, 3 to 5 more minutes, stirring occasionally. Add the 2 cloves garlic, pressed or minced and 2 tablespoons panang curry paste and cook, while stirring, for 1 minute.</li>
                                            <li>Add the 14 ounces regular coconut milk and ½ cup water, and stir to combine. Bring the mixture to a simmer over medium heat. Reduce heat as necessary to maintain a gentle simmer and cook until the peppers and carrots have softened to your liking, about 5 to 10 minutes, stirring occasionally. If you’re adding crispy tofu, stir it in now.</li>
                                            <li>Remove the pot from the heat. Stir in the 2 tablespoons peanut butter, 1 tablespoon soy sauce, 1 ½ teaspoons brown sugar and 2 teaspoons fresh lime juice. Add salt, to taste (I usually add a pinch or two). If the curry needs a little more punch, add ½ teaspoon more tamari, or for more acidity, add ½ teaspoon more lime juice.</li>
                                            <li>Divide rice and curry into bowls and garnish with fresh basil, if using. If you love spicy curries, serve with sriracha or chili garlic sauce on the side.</li>
                                        </ol>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="col-2"></div>
                </div>
            </div>
        </div>
    )
}