import React, { useEffect, useState, Dispatch, SetStateAction } from "react";
import { ControlledInput } from "./ControlledInput";

interface InputProps {
  history: string[];
  setHistory: Dispatch<SetStateAction<string[]>>;
  setCommandString: Dispatch<SetStateAction<string>>;
}

export function Input(props: InputProps) {
  const [commandString, setCommandString] = useState<string>("");

  const handleCraftMealPlan = () => {
    props.setCommandString(commandString);
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
        confirm
      </button>
    </div>
  );
}
