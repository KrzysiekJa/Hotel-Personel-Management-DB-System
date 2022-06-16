import React, { useState, Fragment } from "react";
import "./FrontApp.css";
import EmployeeMainHandler from "./types/Employee";
import HotelMainHandler from "./types/Hotel";
import PositionMainHandler from './types/Position';
import SkillMainHandler from './types/Skill';
import ShiftMainHandler from './types/Shift';



const FrontApp = () => {
  const [controlValue, setControlValue] = useState(0);

  return (
    <div className="app-container">
      <div className="buttons">
        <div className="buttons">
          <button type="button" className="button-8" onClick={() => setControlValue(1)}>
            Employees
          </button>
          <button type="button" className="button-8" onClick={() => setControlValue(2)}>
            Hotels
          </button>
          <button type="button" className="button-8" onClick={() => setControlValue(3)}>
            Positions
          </button>
          <button type="button" className="button-8" onClick={() => setControlValue(4)}>
            Skills
          </button>
          <button type="button" className="button-8" onClick={() => setControlValue(5)}>
            WorkPlan
          </button>
        </div>
      </div>
        {controlValue === 1 &&
          <Fragment>
            <EmployeeMainHandler/>
          </Fragment>
        }
        {controlValue === 2 &&
          <Fragment>
            <HotelMainHandler/>
          </Fragment>
        }
        {controlValue === 3 &&
          <Fragment>
            <PositionMainHandler/>
          </Fragment>
        }
        {controlValue === 4 &&
          <Fragment>
            <SkillMainHandler/>
          </Fragment>
        }
        {controlValue === 5 &&
          <Fragment>
            <ShiftMainHandler/>
          </Fragment>
        }
    </div>
  );
};

export default FrontApp;