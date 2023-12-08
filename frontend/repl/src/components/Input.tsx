import React, { useEffect, useState, Dispatch, SetStateAction } from "react";
import { ControlledInput } from "./ControlledInput";

interface InputProps {
  history: string[];
  setHistory: Dispatch<SetStateAction<string[]>>;
  setCommandString: Dispatch<SetStateAction<string>>; // Add setCommandString prop
}

export function Input(props: InputProps) {
  const [commandString, setCommandString] = useState<string>("");

  const handleCraftMealPlan = () => {
    // Log the input for calories when submitting the form
    console.log("Input for calories:", commandString);
    props.setCommandString(commandString);

    // Here, you can perform any further actions related to crafting the meal plan
  };
  

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
      <button id="submit" onClick={handleCraftMealPlan}>
        craft meal plan
      </button>
    </div>
  );
}