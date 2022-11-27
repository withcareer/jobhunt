import logo from './logo.svg';
import './App.css';
import React, {useState, useEffect} from 'react';
import axios from "axios";

function App() {
  const [message, setMessage]=useState('');

    function callback(str) {
        setMessage(str);
    }

    useEffect(
        () => {
            axios.get('/home')
            .then((res) => {
                console.log(res.data);
                callback(res.data);
            })
        }, []
    );

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
          <ul>
              {message}
          </ul>
      </header>
    </div>
  );
}

export default App;
