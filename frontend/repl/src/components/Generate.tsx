import React, { useState } from "react";
import "../styles/main.css";

export function searchCommandFunction(): Promise<string> {
  return new Promise(async (resolve) => {
    try {
      const response = await fetch(`http://localhost:2023/day`);
      const json = await response.json();
      resolve(json.day);
    } catch (error) {
      resolve("An error occurred: " + error);
    }
  });
}

export function Generate() {
  const [selectedOption, setSelectedOption] = useState<string>("");
  const [generatedData, setGeneratedData] = useState<string | null>(null); // State to store the fetched data

  const handleGenerateClick = async () => {
    try {
      const result = await searchCommandFunction();
      setGeneratedData(result); // Set the fetched data in the state
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  return (
    <div>
      <button className="generate" onClick={handleGenerateClick}>
        Generate
      </button>
      {generatedData && (
        <p>Menu Day: {generatedData}</p>
      )}
    </div>
  );
}
