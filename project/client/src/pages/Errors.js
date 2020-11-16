import React from 'react';

export default function Errors({ errors }) {
  return (
    <>
      {errors.length > 0 && (
        <div className="alert alert-danger" role="alert">
          {errors.map((error) => (
            <p key={error}>{error}</p>
          ))}
        </div>
      )}
      </>
  );
}