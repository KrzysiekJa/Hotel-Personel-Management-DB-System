import React, {Fragment } from "react";
import "./FrontApp.css";
import { ContactMainForm, ContactAddFormSubmit } from "./types/Contact";


const FrontApp = () => {

  return (
    <div className="app-container">
      <div class="buttons">
        <div class="buttons">
          <button type="button" class="button-8">Employees</button>
          <button type="button" class="button-8">Hotels</button>
          <button type="button" class="button-8">Positions</button>
          <button type="button" class="button-8">Skills</button>
          <button type="button" class="button-8">WorkPlan</button>
        </div>
      </div>

      <Fragment>
        <ContactMainForm/>
      </Fragment>

      <h3>Add a Contact</h3>
      <Fragment>
        <ContactAddFormSubmit/>
      </Fragment>
    </div>
  );
};


export default FrontApp;
