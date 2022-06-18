import React from "react";


const EditableRowShift = ({ id, rowObject, handleEditFormChange, handleSaveClick, handleCancelClick }) => {
  return (
    <tr>
      <td key="starting_date">
        <input
          type="datetime-local"
          required="required"
          placeholder="Enter starting_date..."
          name="starting_date"
          value={rowObject["starting_date"]}
          onChange={handleEditFormChange}
        ></input>
      </td>
      <td key="ending_date">
        <input
          type="datetime-local"
          required="required"
          placeholder="Enter ending_date..."
          name="ending_date"
          value={rowObject["ending_date"]}
          onChange={handleEditFormChange}
        ></input>
      </td>
      <td key="status">
        <select
          type="text"
          required="required"
          placeholder="Enter status..."
          name="status"
          value={rowObject["status"]}
          onChange={handleEditFormChange}
        >
          <option value="Planned">Planned</option>
          <option value="In progress">In progress</option>
          <option value="Completed">Completed</option>
        </select>
      </td>
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

export default EditableRowShift;
