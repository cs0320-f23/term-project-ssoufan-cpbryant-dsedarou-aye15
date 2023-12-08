import { useState } from "react";
import "../styles/main.css";
import { History } from "./History";
import { Input } from "./Input";
import Calendar from "./Calendar"; // Import the Calendar component
import React from "react";
import { Selector, searchCommandFunction } from "./Selector";

/**
 * The main component.
 *
 * This component represents a simple interface that displays a history of commands and their results.
 * It consists of an input section where users can enter commands, a calendar for displaying a weekly meal plan,
 * and a history section that displays the executed commands and their outcomes.
 *
 * @returns - A React JSX element representing the REPL component.
 */
export default function Display() {
  const [history, setHistory] = useState<string[]>([]);
  const [mealPlan, setMealPlan] = useState<string[]>([]);

  // Function to add a meal to the meal plan
  const addMeal = (meal: string) => {
    setMealPlan([...mealPlan, meal]);
  };

  // Function to remove a meal from the meal plan
  const removeMeal = (index: number) => {
    const updatedMealPlan = [...mealPlan];
    updatedMealPlan.splice(index, 1);
    setMealPlan(updatedMealPlan);
  };

const handleSelect = async (selectedOption: string) => {
    console.log("Selected option:", selectedOption);
    const result = await searchCommandFunction(selectedOption);
  };
  
  return (
    <div className="Display">
      {/* <History history={history} />
      <hr></hr> */}
      <p className="limit">Enter your daily calorie limit:</p>
      <Input history={history} setHistory={setHistory} />
      {/* <p>Select any restrictions:</p> */}
      <Selector onSelect={handleSelect} />
      <Calendar mealPlan={mealPlan} removeMeal={removeMeal} />
    </div>
  );
}
