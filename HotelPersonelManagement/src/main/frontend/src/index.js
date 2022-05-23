import React from "react";
import ReactDOM from "react-dom/client";
//import ReactDOM from "react-dom";
import "./index.css";
import FrontApp from "./FrontApp";


//ReactDOM.render(
//  <React.StrictMode>
//    <FrontApp />
//  </React.StrictMode>,
//  document.getElementById("root")
//);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <FrontApp />
  </React.StrictMode>
);
