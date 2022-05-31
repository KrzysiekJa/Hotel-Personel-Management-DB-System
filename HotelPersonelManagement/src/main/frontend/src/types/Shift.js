import React, { useState , Fragment } from "react";
import "../FrontApp.css";
import data from "../mock-data.json";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";



const ShiftTableBody = () => {

  const [shifts, setShifts] = useState(data);
  const [editFormData, setEditFormData] = useState({
    starting_date: "",
    ending_date: "",
    status: "",
  });
  const [editShiftId, setEditShiftId] = useState(null);

  const handleEditFormChange = (event) => {
    event.preventDefault();

    const fieldName = event.target.getAttribute("name");
    const fieldValue = event.target.value;
    const newFormData = { ...editFormData };
    newFormData[fieldName] = fieldValue;
    setEditFormData(newFormData);
  };

  const handleEditClick = (event, shift) => {
    event.preventDefault();
    setEditShiftId(shift.shift_ID);

    const formValues = {
        starting_date: shift.starting_date,
        ending_date: shift.ending_date,
        status: shift.status,
    };
    setEditFormData(formValues);
  };

  const handleCancelClick = () => {
    setEditShiftId(null);
  };

  const handleDeleteClick = (targetShift) => {
    const newShifts = [...shifts];
    const index = shifts.findIndex((shift) => shift.shift_ID === targetShift.shift_ID);
    newShifts.splice(index, 1);
    setShifts(newShifts);
  };

  return(
    shifts.map((shift) => (
      <Fragment>
        {editShiftId === shift.shift_ID ? (
          <EditableRow
            editFormData = {editFormData}
            handleEditFormChange = {handleEditFormChange}
            handleCancelClick = {handleCancelClick}
          />
        ) : (
          <ReadOnlyRow
            container = {shift}
            editFormData = {editFormData}
            handleEditClick = {handleEditClick}
            handleDeleteClick = {handleDeleteClick}
          />
        )}
      </Fragment>
    ))
  );
};


const ShiftTableForm = () => {
  const shiftHeadNames = ['Starting date', 'Ending date', 'Status'];

  const [shifts, setShifts] = useState(data);
  const [editFormData] = useState({
    starting_date: "",
    ending_date: "",
    status: "",
  });
  const [editShiftId, setEditShiftId] = useState(null);

  const handleEditFormSubmit = (event) => {
    event.preventDefault();

    const editedShift = {
        shift_ID: editShiftId,
        starting_date: editFormData.starting_date,
        ending_date: editFormData.ending_date,
        status: editFormData.status,
    };

    const newShifts = [...shifts];
    const index = shifts.findIndex((shift) => shift.shift_ID === editShiftId);
    newShifts[index] = editedShift;
    setShifts(newShifts);
    setEditShiftId(null);
  };

  return(
    <form onSubmit={handleEditFormSubmit}>
      <table>
        <thead>
          <Fragment>
            <TableHeadRow
              headRowNames = {shiftHeadNames}
            />
          </Fragment>
        </thead>
        <tbody>
          <Fragment>
            <ShiftTableBody/>
          </Fragment>
        </tbody>
      </table>
    </form>
  );
};


const ShiftAddFormSubmit = () => {
  const shiftNames = ['starting_date', 'ending_date', 'status'];

  const [shifts, setShifts] = useState(data);
  const [addFormData, setAddFormData] = useState({
    starting_date: "",
    ending_date: "",
    status: "",
  });


  const handleAddFormSubmit = (event) => {
    event.preventDefault();

    const newShift = {
        shift_ID: Math.floor(Math.random() * Math.pow(10, 15)),
        starting_date: addFormData.starting_date,
        ending_date: addFormData.ending_date,
        status: addFormData.status,
    };

    const newShifts = [...shifts, newShift];
    setShifts(newShifts);
  };

  const handleAddFormChange = (event) => {
    event.preventDefault();

    const fieldName = event.target.getAttribute("name");
    const fieldValue = event.target.value;
    const newFormData = { ...addFormData };
    newFormData[fieldName] = fieldValue;
    setAddFormData(newFormData);
  };

  return (
    <form onSubmit={handleAddFormSubmit}>
      <Fragment>
        {Object.entries(shiftNames).map(([key, value]) => (
          <input
            type="text"
            name={value}
            required="required"
            placeholder={ "Enter ".concat(value, '...') }
            onChange={handleAddFormChange}
          ></input>
        ))}
      </Fragment>
      <button type="button" className="button-8">Add</button>
    </form>
  );
};



const ShiftMainHandler = () => {
  return(
    <div>
      <Fragment>
        <ShiftTableForm/>
      </Fragment>

      <h3>Add a Shift</h3>
      <Fragment>
        <ShiftAddFormSubmit/>
      </Fragment>
    </div>
  );
};

export default ShiftMainHandler;