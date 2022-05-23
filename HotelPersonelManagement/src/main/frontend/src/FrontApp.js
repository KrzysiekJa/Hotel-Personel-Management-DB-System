import React, {useState, Fragment} from "react";
import "./FrontApp.css";
import ContactMainHandler from "./types/Contact";


const FrontApp = () => {
  const [controlValue, setControlValue] = useState(0);

  return (
    <div className="app-container">
      <div class="buttons">
        <div class="buttons">
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
              <ContactMainHandler/>
            </Fragment>
        }
        {controlValue === 2 &&
            <h3>
              Api!!!!
            </h3>
        }
        {controlValue === 3 &&
            <h3>
              Is!!!!
            </h3>
        }
        {controlValue === 4 &&
            <h3>
              Working!!!!
            </h3>
        }
        {controlValue === 5 &&
            <h3>
              :D
            </h3>
        }
    </div>
  );
};

export default FrontApp;