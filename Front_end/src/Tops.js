import React from 'react';

function Tops({title, poster, rating}){
    return <div Name = "tops__movie">
        <img src={poster} alt={title} title={title}/>
        <div className="tops__data">
            <h4 className="tops__title">{title}</h4>
            <h5 className="tops__rating">{rating}</h5>
        </div>
    </div>
}

export default Tops;