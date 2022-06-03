import React, { useState, Fragment } from "react";
import "./FrontApp.css";
import ContactMainHandler from "./types/Contact";
import HotelMainHandler from "./types/Hotel";
import Hotel2MainHandler from './types/Hotel2';


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
          <h3>
            Is!!!!
          </h3>
        }
        {controlValue === 2 &&
          <Fragment>
            <HotelMainHandler/>
          </Fragment>
        }
        {controlValue === 3 &&
          <Fragment>
            <ContactMainHandler/>
          </Fragment>
        }
        {controlValue === 4 &&
          <h3>
            Working!!!!
          </h3>
        }
        {controlValue === 5 &&
          <Fragment>
            <Hotel2MainHandler/>
          </Fragment>
        }
    </div>
  );
};

export default FrontApp;