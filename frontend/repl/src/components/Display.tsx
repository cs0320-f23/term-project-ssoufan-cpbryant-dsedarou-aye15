import { ReactElement, useState } from "react";
import "../styles/main.css";
import { History } from "./History";
import { Input } from "./Input";
import Calendar from "./Calendar";
import React from "react";
import { Selector, searchCommandFunction } from "./Selector";
import { CommunityHistory } from "./CommunityHistory";
import { CommunityInput } from "./CommunityInput";
import { Generate } from "./Generate";
import { MenuButton } from "./MenuButton";

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
  const [communityHistory, setCommunityHistory] = useState<string[]>([]);
  const [commandString, setCommandString] = useState<string>(""); // Initialize commandString state
  const [itemsProp, setSelectedItems] = useState<any[]>([]); // New state for selected items

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
    setCommandString(selectedOption);
    const result = await searchCommandFunction(
      selectedOption,
      commandString,
      setSelectedItems
    );
  };

  return (
    <div className="Display">
      <MenuButton />
      <p className="limit">Enter your daily calorie limit:</p>
      <Input
        history={history}
        setHistory={setHistory}
        setCommandString={setCommandString}
      />
      <Selector onSelect={handleSelect} commandString={commandString} />
      <Generate />
      <Calendar
        mealPlan={mealPlan}
        removeMeal={removeMeal}
        itemsProp={itemsProp}
      />
      <div className="repl">
        <hr></hr>
        <p className="App-header">
          <h1>Community Section!</h1>
        </p>
        <CommunityHistory history={communityHistory} />
        <CommunityInput
          history={communityHistory}
          setHistory={setCommunityHistory}
        />
      </div>
    </div>
  );
}
