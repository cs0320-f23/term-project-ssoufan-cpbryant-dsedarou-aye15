import { useState } from "react";
import "../styles/main.css";
import { History } from "./History";
import { Input } from "./Input";
import React from "react";
import { Selector } from "./Selector";

/**
 * The main component.
 *
 * This component represents a simple interface that displays a history of commands and their results. It consists of an input section where users can enter commands and a history section that displays the executed commands and their outcomes.
 *
 * @returns - A React JSX element representing the REPL component.
 */
export default function Display() {
  const [history, setHistory] = useState<string[]>([]);
  return (
    <div className="Display">
      {/* <History history={history} />
      <hr></hr> */}
      <p>Enter your daily calorie limit:</p>
      <Input history={history} setHistory={setHistory} />
      {/* <p>Select any restrictions:</p> */}
      <Selector onSelect={function (selectedOption: string): void {
        throw new Error("Function not implemented.");
      } }/>
    </div>
  );
}
