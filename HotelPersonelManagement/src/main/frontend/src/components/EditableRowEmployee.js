import React, { Fragment } from "react";


const EditableRowEmployee = ({ id, rowObject, handleEditFormChange, handleSaveClick, handleCancelClick, positions}) => {
  return (
    <tr>
      <select
        type="text"
        name="position_ID"
        required="required"
        onChange={handleEditFormChange}
      >
        <Fragment>
          {Object.entries(positions).map(([key, value]) => (
            <option value={value['name']}>
              {value['name']}
            </option>
          ))}
        </Fragment>
      </select>
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

export default EditableRowEmployee;
