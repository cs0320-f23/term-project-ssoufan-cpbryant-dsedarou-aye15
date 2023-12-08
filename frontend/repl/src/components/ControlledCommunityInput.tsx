import React from "react";
import "../styles/main.css";
import { Dispatch, SetStateAction } from "react";

/**
 * Interface defining properties for the ControlledCommunityInput component.
 * value: The current value of the input.
 * setValue:  A function to update the input value.
 * ariaLabel: A label for accessibility.
 */
interface ControlledCommunityInputProps {
  value: string;
  setValue: Dispatch<SetStateAction<string>>;
  ariaLabel: string;
}

/**
 * A controlled community input component.
 *
 * This component renders an HTML input element that is controlled by its properties. It allows you to set its value, handle changes, and provide an aria label for accessibility.
 *
 * @param {ControlledCommunityInputProps} props - The properties for the ControlledInput component.
 * @returns - A React JSX element representing the controlled input.
 */
export function ControlledCommunityInput({
  value,
  setValue,
  ariaLabel,
}: ControlledCommunityInputProps) {
  return (
    <input
      type="text"
      className="command-box"
      value={value}
      placeholder="(Enter your review here)"
      id="inputBox"
      onChange={(ev) => setValue(ev.target.value)}
      aria-label={ariaLabel}
    ></input>
  );
}
