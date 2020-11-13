import React from 'react';



export default function Rating ({detailed, reviews}) {

    if (!reviews) {
        return null;
    }

    const averageCalculator = () => {
        let total = 0;
        reviews.map((review) => {return (total += review.rating)});
        return (total/(reviews.length !== 0 ? reviews.length : 1));
    }

    const averageRating = averageCalculator();

    function starRating() {

        return (

            <div>
                <span className={`fa fa-star ${averageRating >= 1 ? 'star-checked' : 'star-unchecked'}`}></span>
                <span className={`fa fa-star ${averageRating >= 2 ? 'star-checked' : 'star-unchecked'}`}></span>
                <span className={`fa fa-star ${averageRating >= 3 ? 'star-checked' : 'star-unchecked'}`}></span>
                <span className={`fa fa-star ${averageRating >= 4 ? 'star-checked' : 'star-unchecked'}`}></span>
                <span className={`fa fa-star ${averageRating >= 5 ? 'star-checked' : 'star-unchecked'}`}></span>
            </div>
        )
    } 

    if (detailed) {
        return (
            <div>
                {starRating()}
                <div className="text-secondary">{averageRating} stars from {reviews.length} reviews</div>
            </div>
        )
    }

    return (
        <div>
            {starRating()}
        </div>
    )
}
