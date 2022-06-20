import React, { Fragment } from "react";


const ReadOnlyRowWorkPlanEmployee = ({ container, keysList, handleDeleteClick }) => {
  
  // container =/= keysList
  const renderRow = (container, keysList) => {
    let tdList = [];
    for(const key of Object.keys(keysList)){
      tdList.push(
        <td key={key}>{ container[key] }</td>
      );
    }
    return tdList;
  };

  return (
    <tr>
      <Fragment>
        {renderRow(container, keysList)}
      </Fragment>
      <td>
        <button type="button" className="button-8" onClick={() => handleDeleteClick(container)}>
          Delete
        </button>
      </td>
    </tr>
  );
};

export default ReadOnlyRowWorkPlanEmployee;
