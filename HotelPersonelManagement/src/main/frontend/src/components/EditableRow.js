import React, { Fragment } from "react";


const EditableRow = ({ id, rowObject, handleEditFormChange, handleSaveClick, handleCancelClick }) => {
  return (
    <tr>
      <Fragment>
        {Object.entries(rowObject).map(([key, value]) => (
          <td key={key}>
            <input
              type="text"
              required="required"
              placeholder={ "Enter ".concat(key, '...') }
              name={key}
              value={value}
              onChange={handleEditFormChange}
            ></input>
          </td>
        ))}
      </Fragment>
      <td>
        <button type="button" className="button-8" onClick={() => handleSaveClick(id)}>
          Save
        </button>
        <button type="button" className="button-8" onClick = {handleCancelClick}>
          Cancel
        </button>
      </td>
    </tr>
  );
};

export default EditableRow;
