import React from "react";
import "../styles/main.css";
import { Dispatch, ReactElement, SetStateAction, useState } from "react";

function Popup(props) {
  return props.trigger ? (
    <div className="popup">
      <div className="popup-inner">
        <h2>Specialty Items For Each Day</h2>
        <table>
          <tr>
            <th>Sunday</th>
            <th>Monday</th>
            <th>Tuesday</th>
            <th>Wednesday</th>
            <th>Thursday</th>
            <th>Friday</th>
            <th>Saturday</th>
          </tr>
          <tr>
            <td>Ratty Blueberry French Toast Casserole Scrambled Eggs</td>
            <td>Ratty Cranberry Coffee Cake</td>
            <td>VDub Tex Mex Tofu Scramble</td>
            <td>Vdub Applewood Smoked Bacon</td>
            <td>Vdub Vegan Paprika Breakfast Potatoes</td>
            <td>Ratty Lentils W/ Roasted Vegetables</td>
            <td>Ratty Jelly Donut</td>
          </tr>
          <tr>
            <td>Ratty Chipotle Shrimp</td>
            <td>Ratty Balsamic Marinated Chicken</td>
            <td>Ratty Pesto Pasta Shrimp</td>
            <td>Vdub Rotisserie Style Pesto Chicken</td>
            <td>Vdub Chicken Broccoli Pasta Alfredo</td>
            <td>Ratty Stir Fried Bok Choy</td>
            <td>Ratty Ratatouille</td>
          </tr>
          <tr>
            <td>Ratty Beef Burgundy</td>
            <td>Ratty Spicy Cuban Beef Stir-fry</td>
            <td>Vdub Sausage And Broccoli Rabe Pasta</td>
            <td>Ratty Udon Mushroom Noodles</td>
            <td>Vdub Ropa Vieja</td>
            <td>Ratty Gnocchi W/ chunky Marinara Sauce</td>
            <td>Ratty Scallop Scampi</td>
          </tr>
        </table>
        <button
          className="close-button"
          onClick={() => props.setTrigger(false)}
        >
          Close
        </button>
        {props.children}
      </div>
    </div>
  ) : (
    ""
  );
}

export default Popup;
