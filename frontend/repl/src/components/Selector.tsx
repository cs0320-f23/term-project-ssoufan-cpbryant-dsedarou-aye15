import React from "react";
import "../styles/main.css";

interface SelectorProps {
  onSelect: (selectedOption: string) => void;
}

export function Selector(props: SelectorProps) {
  const handleOptionChange = (selectedOption: string) => {
    props.onSelect(selectedOption);
  };

  return (
    <div>
      <p className="restriction">Select a restriction:</p>
      <div className="radio-container">
        <input
          type="radio"
          id="vegan"
          name="restrictions"
          value="Vegan"
          onChange={() => handleOptionChange("Vegan")}
        />
        <label htmlFor="vegan">Vegan</label>

        <input
          type="radio"
          id="halal"
          name="restrictions"
          value="Halal"
          onChange={() => handleOptionChange("Halal")}
        />
        <label htmlFor="halal">Halal</label>

        <input
          type="radio"
          id="kosher"
          name="restrictions"
          value="Kosher"
          onChange={() => handleOptionChange("Kosher")}
        />
        <label htmlFor="kosher">Kosher</label>

        <input
          type="radio"
          id="allergen-free"
          name="restrictions"
          value="Allergen-Free"
          onChange={() => handleOptionChange("Allergen-Free")}
        />
        <label htmlFor="Allergen-free">Allergen-Free</label>

        <input
          type="radio"
          id="vegetarian"
          name="restrictions"
          value="Vegetarian"
          onChange={() => handleOptionChange("Vegetarian")}
        />
        <label htmlFor="Vegetarian">Vegetarian</label>
      </div>
    </div>
  );
}
