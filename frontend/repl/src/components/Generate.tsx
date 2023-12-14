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
    const calories = parseInt(commandString);
    console.log("Command String:", calories);

    // Check if the searchData is already an object
    const parsedData = typeof searchData === 'string' ? JSON.parse(searchData) : searchData;

    // Check if the parsedData is an object
    if (typeof parsedData === 'object' && !Array.isArray(parsedData)) {
      const breakfastArray: { item: string, calories: string, portionSize: string }[] = [];
      const lunchArray: { item: string, calories: string, portionSize: string }[] = [];
      const dinnerArray: { item: string, calories: string, portionSize: string }[] = [];

      Object.keys(parsedData).forEach((key) => {
        const item = parsedData[key];
        const itemInfo = { item: item.Item, calories: item.Calories, portionSize: item['Serving size'] };

        if (item.Meal.toLowerCase() === 'breakfast') {
          breakfastArray.push(itemInfo);
        } else if (item.Meal.toLowerCase() === 'lunch') {
          lunchArray.push(itemInfo);
        } else if (item.Meal.toLowerCase() === 'dinner') {
          dinnerArray.push(itemInfo);
        }
      });

      const selectedItems: { meal: string, item: string, calories: number, portionSize: string }[] = [];
      let totalCalories = 0;

      // Function to check if an item is already selected
      const isItemSelected = (item: string) => selectedItems.some(selected => selected.item === item);

      // Select two items from each meal
      ['breakfast', 'lunch', 'dinner'].forEach(meal => {
        const mealArray = meal === 'breakfast' ? breakfastArray : meal === 'lunch' ? lunchArray : dinnerArray;
        if (mealArray.length > 0) {
          for (const item of mealArray.slice(0, 2)) {
            // Check if the item is not already selected and adding it won't exceed total calories
            if (!isItemSelected(item.item) && totalCalories + parseInt(item.calories) <= calories) {
              selectedItems.push({
                meal,
                item: item.item,
                calories: parseInt(item.calories),
                portionSize: item.portionSize,
              });
              totalCalories += parseInt(item.calories);
            }
          }
        }
      });

      // Calculate the remaining target number of items for each meal
      const remainingTargetItemCount = Math.floor((calories - totalCalories) / 2);

      // Loop through meals in order: breakfast, lunch, dinner
      ['breakfast', 'lunch', 'dinner'].forEach(meal => {
        const mealArray = meal === 'breakfast' ? breakfastArray : meal === 'lunch' ? lunchArray : dinnerArray;

        // Loop through items in the mealArray
        let remainingItemsToAdd = remainingTargetItemCount;
        for (const item of mealArray.slice(2)) {
          // Check if the item is not already selected and adding it won't exceed total calories
          if (!isItemSelected(item.item) && totalCalories + parseInt(item.calories) <= calories) {
            selectedItems.push({
              meal,
              item: item.item,
              calories: parseInt(item.calories),
              portionSize: item.portionSize,
            });
            totalCalories += parseInt(item.calories);
            remainingItemsToAdd--;
          }

          if (remainingItemsToAdd <= 0) {
            break;
          }
        }
      });

      // Log the selected items
      console.log("Selected Items:", selectedItems);
    } else {
      console.error('Parsed data is not an object.');
    }
  } catch (error) {
    console.error("Error processing search result:", error);
  }
}

export function Generate(props: SelectorProps) {
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
      <button className="generate">Generate</button>
    </div>
  );
}