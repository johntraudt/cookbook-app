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
                <span className={`fa fa-star ${averageRating >= 0.5 ? 'star-checked' : 'star-unchecked'}`}></span>
                <span className={`fa fa-star ${averageRating >= 1.5 ? 'star-checked' : 'star-unchecked'}`}></span>
                <span className={`fa fa-star ${averageRating >= 2.5 ? 'star-checked' : 'star-unchecked'}`}></span>
                <span className={`fa fa-star ${averageRating >= 3.4 ? 'star-checked' : 'star-unchecked'}`}></span>
                <span className={`fa fa-star ${averageRating >= 4.5 ? 'star-checked' : 'star-unchecked'}`}></span>
            </div>
        )
    } 

    if (detailed) {
        return (
            <div>
                {starRating()}
                <div className="text-secondary">{averageRating} star{(averageRating !== 0) && (averageRating < 1.5)? "" : "s"} from {reviews.length} review{(reviews.length > 1) || (reviews.length === 0) ? "s" : ""}</div>
            </div>
        )
    }

    return (
        <div>
            {starRating()}
        </div>
    )
}
