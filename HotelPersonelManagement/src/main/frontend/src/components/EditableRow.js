import React, { Fragment } from "react";


const EditableRow = ({ editFormData, handleEditFormChange, handleCancelClick }) => {
  return (
    <tr>
      <Fragment>
        {Object.entries(editFormData).map(([key, value]) => (
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
        <button type="button" className="button-8">
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
