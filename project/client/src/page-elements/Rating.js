import React from 'react';



export default function Rating ({detailed}) {

    function starRating() {
        return (
            <div>
                <span className="fa fa-star star-checked"></span>
                <span className="fa fa-star star-checked"></span>
                <span className="fa fa-star star-checked"></span>
                <span className="fa fa-star star-checked"></span>
                <span className="fa fa-star star-unchecked"></span>
            </div>
        )
    } 

    if (detailed) {
        return (
            <div>
                {starRating()}
                <div className="text-secondary">4.8 from 65 reviews</div>
            </div>
        )
    }

    return (
        <div>
            {starRating()}
        </div>
    )
}
