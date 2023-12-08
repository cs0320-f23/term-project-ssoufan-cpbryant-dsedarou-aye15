import React, { useEffect, useState } from "react";
import "../styles/main.css";
interface SelectorProps {
  onSelect: (selectedOption: string) => void;
  commandString: string; // Add commandString prop
}

export interface SelectorFunction {
  (args: Array<string>): Promise<string>;
}


export function searchCommandFunction(selectedOption: string, commandString: string): Promise<string> {
  return new Promise(async (resolve) => {
    try {
      const response = await fetch(
        `http://localhost:2023/menu?restriction=` + selectedOption
      );
      const json = await response.json();
      console.log(json);
      console.log(commandString);
      processSearchResult(commandString, json);
      // if (json.result === "success") {
      //   processSearchResult(commandString, json);
      //   resolve(json);
      // } else {
      //   resolve(
      //     "An error occurred while trying to search. The correct syntax is: search <keyword> <header/index>"
      //   );
      // }
    } catch (error) {
      resolve("An error occurred: " + error);
    }
  });
}

export function processSearchResult(commandString: string, searchData: string | object): void {
  try {
    console.log("Command String:", commandString);

    // Check if the searchData is already an object
    const parsedData = typeof searchData === 'string' ? JSON.parse(searchData) : searchData;

    // Check if the parsedData is an object
    if (typeof parsedData === 'object' && !Array.isArray(parsedData)) {
      Object.keys(parsedData).forEach((key) => {
        const item = parsedData[key];
        const calories = item.Calories;
        console.log(`Calories: ${calories}`);
        // You can use the 'calories' variable for further processing if needed
      });
    } else {
      console.error('Parsed data is not an object.');
    }

    // Perform any other processing based on your requirements
    // ...
  } catch (error) {
    console.error("Error processing search result:", error);
  }
}






export function Selector(props: SelectorProps) {
  const [selectedOption, setSelectedOption] = useState<string>("");

  // useEffect(() => {
  //   // This will log the commandString whenever it changes
  //   console.log("Command String:", props.commandString);
  // }, [props.commandString]);

  const handleOptionChange = async (selectedValue: string) => {
    setSelectedOption(selectedValue);
    props.onSelect(selectedValue);
    const result = await searchCommandFunction(selectedValue, props.commandString);
    // Use the result as needed
  };


  return (
    <div>
      <p className="restriction">Select an allergy:</p>
      <div className="radio-container">
        <input
          type="radio"
          id="Eggs"
          name="restrictions"
          value="Eggs"
          onChange={() => handleOptionChange("Eggs")}
        />
        <label htmlFor="Eggs">Eggs</label>

        <input
          type="radio"
          id="Soy"
          name="restrictions"
          value="Soy"
          onChange={() => handleOptionChange("Soy")}
        />
        <label htmlFor="Soy">Soy</label>

        <input
          type="radio"
          id="Milk"
          name="restrictions"
          value="Milk"
          onChange={() => handleOptionChange("Milk")}
        />
        <label htmlFor="Milk">Milk</label>

        <input
          type="radio"
          id="Wheat"
          name="restrictions"
          value="Wheat"
          onChange={() => handleOptionChange("Wheat")}
        />
        <label htmlFor="Wheat">Wheat</label>

        <input
          type="radio"
          id="Gluten"
          name="restrictions"
          value="Gluten"
          onChange={() => handleOptionChange("Gluten")}
        />
        <label htmlFor="Gluten">Gluten</label>

        <input
          type="radio"
          id="Alcohol"
          name="restrictions"
          value="Alcohol"
          onChange={() => handleOptionChange("Alcohol")}
        />
        <label htmlFor="Alcohol">Alcohol</label>

        <input
          type="radio"
          id="Shellfish"
          name="restrictions"
          value="Shellfish"
          onChange={() => handleOptionChange("Shellfish")}
        />
        <label htmlFor="Shellfish">Shellfish</label>
      </div>
    </div>
  );
}