import React from "react";

interface CalendarProps {
  mealPlan: string[];
  removeMeal: (index: number) => void;
  itemsProp: any[];
}
const Calendar: React.FC<CalendarProps> = ({ mealPlan, itemsProp }) => {
  console.log("Calendar Items Length:", itemsProp.length);

  if (itemsProp.length === 0) {
    // Render a message or default content when itemsProp is empty
    return <p>No items available</p>;
  }

  // Continue with the rest of the rendering logic
  return (
    <div className="calendar">
      <table>
        <thead>
          <tr>
            <th>Time</th>
            <th>Meal</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td id="col1">Breakfast</td>
            <td>
              {itemsProp
                .filter((item) => item.meal === "breakfast")
                .map((item, index) => (
                  <div key={index}>
                    {item.item} - {item.calories} calories
                    {/* You can include additional details if needed */}
                  </div>
                ))}
            </td>
          </tr>
          <tr>
            <td id="col2">Lunch</td>
            <td>
              {itemsProp
                .filter((item) => item.meal === "lunch")
                .map((item, index) => (
                  <div key={index}>
                    {item.item} - {item.calories} calories
                    {/* You can include additional details if needed */}
                  </div>
                ))}
            </td>
          </tr>
          <tr>
            <td id="col3">Dinner</td>
            <td>
              {itemsProp
                .filter((item) => item.meal === "dinner")
                .map((item, index) => (
                  <div key={index}>
                    {item.item} - {item.calories} calories
                    {/* You can include additional details if needed */}
                  </div>
                ))}
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};





interface MealSlotProps {
  day: string;
  mealPlan: string[];
  removeMeal: (index: number) => void;
}

const MealSlot: React.FC<MealSlotProps> = ({ day, mealPlan, removeMeal }) => {
  const mealsForDay = mealPlan.filter((meal) => meal.includes(day));
  return (
    <div>
      {mealsForDay.map((meal, index) => (
        <div key={index}>
          {meal}
          <button onClick={() => removeMeal(index)}>Remove</button>
        </div>
      ))}
    </div>
  );
};

export default Calendar;
