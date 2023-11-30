import React from "react";
import "../styles/main.css";
import { Dispatch, SetStateAction } from "react";

/**
 * Interface defining properties for the ControlledInput component.
 * value: The current value of the input.
 * setValue:  A function to update the input value.
 * ariaLabel: A label for accessibility.
 */
interface ControlledInputProps {
  value: string;
  setValue: Dispatch<SetStateAction<string>>;
  ariaLabel: string;
}

/**
 * A controlled input component.
 *
 * This component renders an HTML input element that is controlled by its properties. It allows you to set its value, handle changes, and provide an aria label for accessibility.
 *
 * @param {ControlledInputProps} props - The properties for the ControlledInput component.
 * @returns - A React JSX element representing the controlled input.
 */
export function ControlledInput({
  value,
  setValue,
  ariaLabel,
}: ControlledInputProps) {
  return (
    <input
      type="text"
      className="command-box"
      value={value}
      placeholder="Click here to personally add or remove any item from your meal plan!"
      id="inputBox"
      onChange={(ev) => setValue(ev.target.value)}
      aria-label={ariaLabel}
    ></input>
  );
}
