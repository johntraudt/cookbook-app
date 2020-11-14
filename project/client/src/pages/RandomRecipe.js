import React from 'react';
import Recipe from './Recipe';

export default function RandomRecipe() {

    return (
        <div className="container full-body mt-5 ">
            <h3>
                Complete this when Database can send a random recipe's JSON.  
            </h3>
            <p>
                Consider:
            </p>
            <ol>
                <li>request a random recipe, but have it link to existing 'recipe' page instead of here</li>
                <li>then delete this page</li>
            </ol>
        </div>
    );
}