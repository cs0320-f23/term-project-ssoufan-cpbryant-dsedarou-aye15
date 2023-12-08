import React from "react";
import "../styles/main.css";
import { Dispatch, ReactElement, SetStateAction, useState } from "react";

/**
 * Here, we set up and display the command history though React components
 * stored in an array called history. These components are created in
 * CommunityInput.
 */

interface CommunityHistoryProps {
  history: string[];
}

/**
 * This function displays each React component kept in the history array
 * in the REPL history box.
 * @param props
 * @returns
 */

export function CommunityHistory(props: CommunityHistoryProps) {
  return (
    <div
      id="CommunityHistoryId"
      className="community-history"
      aria-live="polite"
      aria-atomic="true"
      aria-a
      aria-label="Past commands"
    >
      {props.history.map((command, index) => (
        <p>{command}</p>
      ))}
    </div>
  );
}
