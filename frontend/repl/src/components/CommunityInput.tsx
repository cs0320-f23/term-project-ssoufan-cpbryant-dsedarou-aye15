import "../styles/main.css";
import { Dispatch, ReactElement, SetStateAction, useState } from "react";
import React from "react";
import { ControlledCommunityInput } from "./ControlledCommunityInput";
/**
 *imports the props made in our higher level Display class so they can be access in input.
 */
interface CommunityInputProps {
  history: string[];
  setHistory: Dispatch<SetStateAction<string[]>>;
}

/**
 * This function is a higher level function which handles all of CommunityInput's functionality
 * @param props - props which need to be accessed to produce proper functionality
 * @returns - command string which can take in an input and pass it over to the history
 */
export function CommunityInput(props: CommunityInputProps) {
  // Manages the contents of the input box
  const [commandString, setCommandString] = useState<string>("");

  function handleSubmit(): void {
    props.setHistory(props.history.concat(commandString));
    setCommandString("");
  }

  return (
    <div className="community-input">
      <fieldset>
        <legend>Discuss how your dining experience was:</legend>
        <ControlledCommunityInput
          value={commandString}
          setValue={setCommandString}
          ariaLabel={"Command input"}
        />
      </fieldset>
      <div className="button-container">
        <button className="centered-button" onClick={handleSubmit}>
          Submit!
        </button>
      </div>
    </div>
  );
}
