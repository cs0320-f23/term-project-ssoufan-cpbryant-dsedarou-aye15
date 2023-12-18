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
      <Popup trigger={buttonPopup} setTrigger={setButtonPopup}>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipisicing elit. Recusandae
          eum dolore nulla consequuntur perferendis laudantium ratione iure odit
          ipsum quae vitae ullam, quis explicabo, consectetur autem, aliquid
          cupiditate aperiam! Beatae.
        </p>
        <p>
          Lorem ipsum dolor sit amet consectetur, adipisicing elit. Porro,
          voluptatibus, sunt provident sit odit ipsam nisi accusamus amet aut,
          fugiat incidunt reiciendis sequi? Quidem nemo reprehenderit ab culpa.
          Aspernatur, illo!
        </p>
      </Popup>
    </div>
  );
}
