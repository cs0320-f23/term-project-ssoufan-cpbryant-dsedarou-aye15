import "../styles/main.css";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { ControlledInput } from "./ControlledInput";
//import { m } from "framer-motion";
import React from "react";

interface InputProps {
  history: string[];
  setHistory: Dispatch<SetStateAction<string[]>>;
}

/**
 * A component that allows users to input commands and interact with a REPL (Read-Eval-Print Loop).
 *
 * @param props – The properties and callbacks for configuring the REPLInput component.
 * @returns
 */

export function Input(props: InputProps) {
  const [commandString, setCommandString] = useState<string>("");

  /**
   * Handles accesability buttons. ";" to switch modes, "/" to erase command string,
   * "Enter" to click submit button, and "[" to manually select the input box
   * without a mouse.
   */
  useEffect(() => {
    function keyDown(event: KeyboardEvent) {
      if (event.key === "/") {
        setCommandString("");
      } else if (event.key === "Enter") {
        const submitButton = document.getElementById("submit");
        if (submitButton) {
          submitButton.click();
        }
      } else if (event.key === "[") {
        const replHistory = document.getElementById("inputBox");
        if (replHistory) {
          replHistory.focus();
        }
      }
    }
    window.addEventListener("keydown", keyDown);

    return () => {
      window.removeEventListener("keydown", keyDown);
    };
  }, []);

  return (
    <div className="Input">
      <fieldset>
        <legend id="25"> </legend>
        <ControlledInput
          value={commandString}
          setValue={setCommandString}
          ariaLabel={"Command input"}
        />
      </fieldset>
      <button id="submit">craft meal plan</button>
    </div>
  );
}
