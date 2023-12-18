import "../styles/main.css";
import { Dispatch, ReactElement, SetStateAction, useState } from "react";
import React from "react";
import Popup from "./Popup";

export function MenuButton() {
  const [buttonPopup, setButtonPopup] = useState(false);

  return (
    <div id="MenuButtonDiv">
      <button id="MenuButton" onClick={() => setButtonPopup(true)}>
        Click Here to View the Menu!
      </button>
      <Popup trigger={buttonPopup} setTrigger={setButtonPopup}></Popup>
    </div>
  );
}
