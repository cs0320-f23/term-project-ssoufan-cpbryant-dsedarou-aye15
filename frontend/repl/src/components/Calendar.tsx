import React from "react";

interface CalendarProps {
  mealPlan: string[];
  removeMeal: (index: number) => void;
}

const Calendar: React.FC<CalendarProps> = ({ mealPlan, removeMeal }) => {
  const daysOfWeek = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

  return (
    <div className="calendar">
      <table>
        <thead>
          <tr>
            <th>Time</th>
            {daysOfWeek.map((day) => (
              <th key={day}>{day}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          <tr>
            <td id="col1">Breakfast</td>
            {daysOfWeek.map((day, index) => (
              <td key={index}>
                <MealSlot
                  day={day}
                  mealPlan={mealPlan}
                  removeMeal={removeMeal}
                />
              </td>
            ))}
          </tr>
          <tr>
            <td id="col2">Lunch</td>
            {daysOfWeek.map((day, index) => (
              <td key={index + daysOfWeek.length}>
                <MealSlot
                  day={day}
                  mealPlan={mealPlan}
                  removeMeal={removeMeal}
                />
              </td>
            ))}
          </tr>
          <tr>
            <td id="col3">Dinner</td>
            {daysOfWeek.map((day, index) => (
              <td key={index + 2 * daysOfWeek.length}>
                <MealSlot
                  day={day}
                  mealPlan={mealPlan}
                  removeMeal={removeMeal}
                />
              </td>
            ))}
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
