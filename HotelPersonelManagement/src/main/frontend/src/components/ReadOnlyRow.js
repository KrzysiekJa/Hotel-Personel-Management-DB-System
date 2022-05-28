import React, { Fragment } from "react";


const ReadOnlyRow = ({ container, editFormData, handleEditClick, handleDeleteClick }) => {
  
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
        {renderRow(container, editFormData)}
      </Fragment>
      <td>
        <button type="button" className="button-8" onClick={(event) => handleEditClick(event, container)}>
          Edit
        </button>
        <button type="button" className="button-8" onClick={() => handleDeleteClick(container.id)}>
          Delete
        </button>
      </td>
    </tr>
  );
};

export default ReadOnlyRow;
