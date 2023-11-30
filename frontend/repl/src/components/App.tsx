import React from "react";
import "../styles/App.css";
import Display from "./Display";

/**
 * This is the highest level component!
 */
function App() {
  return (
    <div className="App">
      <p className="App-header">
        <h1>Brown Bites</h1>
      </p>
      <Display />
    </div>
  );
}

export default App;
