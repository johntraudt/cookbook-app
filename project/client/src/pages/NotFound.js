import React from 'react';

export default function NotFound() {
    return (
        <div className="container full-body text-center">
            <div className="mt-4">
                <h3>
                    Whoops, something went wrong. 
                </h3>
                <p>
                    We couldn't find the page you requested.  Please the attempted URL path or try again later.
                </p>
            </div>
        </div>
    );
}