import React, { Fragment } from "react";


const ReadOnlyRowEmployee = ({ container, keysList, handleEditClick, handleDeleteClick, positions }) => {
  
  // container =/= keysList
  const renderRow = (container, keysList) => {
    let tdList = [];
    for(const key of Object.keys(keysList)){
      if(key === 'position_ID'){
        tdList.push(
          <td key={key}>
            {positions.filter(obj => {
              return obj.position_ID === container[key]; }
            )[0].name}
          </td>
        );
      } else {
        tdList.push(
          <td key={key}>{ container[key] }</td>
        );
      }
    }
    return tdList;
  };

  return (
    <tr>
      <Fragment>
        {renderRow(container, keysList)}
      </Fragment>
      <td>
        <button type="button" className="button-8" onClick={(event) => handleEditClick(event, container)}>
          Edit
        </button>
        <button type="button" className="button-8" onClick={() => handleDeleteClick(container)}>
          Delete
        </button>
      </td>
    </tr>
  );
};

export default ReadOnlyRowEmployee;
