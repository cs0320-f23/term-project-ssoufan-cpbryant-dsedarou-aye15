import React from "react";
import "../styles/main.css";
/**
 * The properties for configuring the History component.
 *
 * @property {string[]} history - An array of strings representing the history of commands and their outputs.
 * @property {"brief" | "verbose"} mode - The mode in which the  history is displayed, which can be either "brief" or "verbose."
 */
interface HistoryProps {
  history: string[];
}

/**
 * A React component for displaying the history of commands and their outputs in a interface.
 *
 * @param {HistoryProps} props - The properties to configure the component.
 * @returns - A React element representing the history.
 */
export function History(props: HistoryProps) {
  return (
    <div className="history" tabIndex={0}>
      {props.history.map((item, index) => {
        try {
          const parsedItem = JSON.parse(item);
          {
            return (
              <div key={index}>
                <p>{`${parsedItem.command}`}</p>
              </div>
            );
          }
        } catch (error) {
          // If it's not a serialized command, just render it as a regular string
          return (
            <p aria-label="load" key={index}>
              {item}
            </p>
          );
        }
      })}
    </div>
  );
}
