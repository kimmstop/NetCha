import React, { useEffect, useState } from 'react';

function Main(){
    const [message, setMessage] = useState("")
    useEffect(()=> {
        fetch("/netcha")
        .then(response => response.text())
        .then(m =>setMessage(m))
    }, [])
    return (
        <div>
            <p>
            {message}
            </p>
            <a
                className="App-link"
                href="https://reactjs.org"
                target="_blank"
                rel="noopener noreferrer"
            >
            Learn React
            </a>
        </div>
        
        
    )
}

export default Main;