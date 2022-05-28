import React from "react";


const EditableRow = ({ editFormData, handleEditFormChange, handleCancelClick }) => {
  return (
    <tr>
      {Object.entries(editFormData).map(([key, value]) => (
        <td key={key}>
          <input
            type="text"
            required="required"
            placeholder={"Enter a ".concat({key})}
            name={key}
            value={value}
            onChange={handleEditFormChange}
          ></input>
        </td>
      ))}
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
