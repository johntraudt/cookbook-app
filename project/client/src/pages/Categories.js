import React, {useState, useEffect} from 'react';
import CicleCard from '../page-elements/CircleCard'

function Categories() {
    return (
        <div className="container full-body">
            <h1 className="text-center m-4">Categories</h1>
            <div className="card-grid">
                <div className="row mt-4 mb-4">
                    <div className="col-4">
                        <CicleCard />
                    </div>
                    <div className="col-4">
                        <CicleCard />
                    </div>
                    <div className="col-4">
                        <CicleCard />
                    </div>
                </div>
                <div className="row mt-4 mb-4">
                    <div className="col-4">
                        <CicleCard />
                    </div>
                    <div className="col-4">
                        <CicleCard />
                    </div>
                    <div className="col-4">
                        <CicleCard />
                    </div>
                </div>
                <div className="row mt-4 mb-4">
                    <div className="col-4">
                        <CicleCard />
                    </div>
                    <div className="col-4">
                        <CicleCard />
                    </div>
                    <div className="col-4">
                        <CicleCard />
                    </div>
                </div>
            </div>
            
        </div>

        
    );
}
  
{/* <div className="card-grid">
<div className="row mt-4 mb-4">
    <div className="col-4"><SquareCard/></div>
    <div className="col-4"><SquareCard/></div>
    <div className="col-4"><SquareCard/></div>
</div>
<div className="row mt-4 mb-4">
    <div className="col-4"><SquareCard/></div>
    <div className="col-4"><SquareCard/></div>
    <div className="col-4"><SquareCard/></div>
</div>
</div> */}

export default Categories;