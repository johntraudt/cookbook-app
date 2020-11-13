import React, {useState, useEffect} from 'react';
import CicleCard from '../page-elements/CircleCard'

export default function Categories() {
    return (
        <div className="container full-body">
            <h1 className="text-center m-4">Categories</h1>
            <div className="d-flex flex-wrap justify-content-center mt-5 mb-5">
                <div className="col-4">
                    <CicleCard />
                </div>
                <div className="col-4">
                    <CicleCard />
                </div>
                <div className="col-4">
                    <CicleCard />
                </div>
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
    );
}