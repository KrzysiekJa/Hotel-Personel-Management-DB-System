import React, { useState , useEffect, Fragment } from "react";
import axios from "axios";
import { _withoutProperties } from "./utils";
import "../FrontApp.css";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRowShift from "../components/EditableRowShift";
import SortTable from "../components/SortTable";



const ShiftTableBody = ({shifts, setShifts}) => {

  const [editShiftId, setEditShiftId] = useState(null);
  const [editFormData, setEditFormData] = useState({
    starting_date: "",
    ending_date: "",
    status: ""
  });

  const handleEditFormChange = (event) => {
    event.preventDefault();
    const fieldName = event.target.getAttribute("name");
    const fieldValue = event.target.value;
    const newFormData = { ...editFormData };
    newFormData[fieldName] = fieldValue;
    setEditFormData(newFormData);
  };
  const handleCancelFormClick = () => {
    setEditShiftId(null);
  };

  const handleEditClick = (event, shift) => {
    event.preventDefault();
    setEditShiftId(shift.shift_ID);
    const formValues = {
        starting_date: shift.starting_date,
        ending_date: shift.ending_date,
        status: shift.status
    };
    setEditFormData(formValues);
  };

  function saveClickFunction(id) {
    axios
      .put(`http://localhost:8080/api/v1/shift/${id}`,
        editFormData,
        {
          headers: {"Content-Type": "application/json"}
        }
      )
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/shifts").then(res =>{
          setShifts(res.data);
        });
      });
    setEditShiftId(null);
  };

  function deleteClickFunction(shift) {
    axios
      .delete(`http://localhost:8080/api/v1/shift/${shift.shift_ID}`)
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/shifts").then(res =>{
          setShifts(res.data);
        });
      });
  };

  return(
    shifts.map((shift) => (
      <Fragment>
        {editShiftId === shift.shift_ID ? (
          <EditableRowShift
            id = {shift.shift_ID}
            rowObject = {editFormData}
            handleEditFormChange = {handleEditFormChange}
            handleSaveClick = {saveClickFunction}
            handleCancelClick = {handleCancelFormClick}
          />
        ) : (
          <ReadOnlyRow
            container = {shift}
            keysList = {_withoutProperties(shift, ["shift_ID"])}
            handleEditClick = {handleEditClick}
            handleDeleteClick = {deleteClickFunction}
          />
        )}
      </Fragment>
    ))
  );
};


const ShiftTableForm = ({shifts, setShifts}) => {
  const shiftHeadNames = ['Starting date', 'Ending date', 'Status'];

  const [editShiftId, setEditShiftId] = useState(null);
  const [editFormData] = useState({
    starting_date: "",
    ending_date: "",
    status: ""
  });

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/v1/shifts").then(res =>{
      setShifts(res.data);
    });
  }, []);

  const handleEditFormSubmit = (event) => {
    event.preventDefault();
    const editedShift = {
      shift_ID: editShiftId,
      starting_date: editFormData.starting_date,
      ending_date: editFormData.ending_date,
      status: editFormData.status
    };
    const newShifts = [...shifts];
    const index = shifts.findIndex((shift) => shift.shift_ID === editShiftId);
    newShifts[index] = editedShift;
    setShifts(newShifts);
    setEditShiftId(null);
  };

  if (!shifts) return null;

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
            <ShiftTableBody
              shifts = {shifts}
              setShifts = {setShifts}
            />
          </Fragment>
        </tbody>
      </table>
    </form>
  );
};


const ShiftAddFormSubmit = ({shifts, setShifts}) => {

  const [addFormData, setAddFormData] = useState({
    starting_date: "",
    ending_date: "",
    status: ""
  });

  const handleAddFormSubmit = (event) => {
    event.preventDefault();
    const newShift = {
      starting_date: addFormData.starting_date,
      ending_date: addFormData.ending_date,
      status: addFormData.status
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

  function saveClickFunction() {
    axios
      .post("http://localhost:8080/api/v1/shift",
        addFormData,
        {
          headers: {"Content-Type": "application/json"}
        }
      )
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/shifts").then(res =>{
          setShifts(res.data);
        });
      });
  };

  return (
    <form onSubmit={handleAddFormSubmit}>
      <input
        type="datetime-local"
        name="starting_date"
        required="required"
        onChange={handleAddFormChange}
      ></input>
      <input
        type="datetime-local"
        name="ending_date"
        required="required"
        onChange={handleAddFormChange}
      ></input>
      <select
        type="text"
        name="status"
        required="required"
        onChange={handleAddFormChange}
      >
        <option value="Planned">Planned</option>
        <option value="In progress">In progress</option>
        <option value="Completed">Completed</option>
      </select>
      <button type="button" className="button-8" onClick={() => saveClickFunction()}>
        Add
      </button>
    </form>
  );
};



const ShiftMainHandler = () => {
  const [shifts, setShifts] = useState(null);
  return(
    <div>
      <Fragment>
        <ShiftTableForm
          shifts = {shifts}
          setShifts = {setShifts}
        />
      </Fragment>

      <h3>Add a Shift</h3>
      <Fragment>
        <ShiftAddFormSubmit
          shifts = {shifts}
          setShifts = {setShifts}
        />
      </Fragment>

      <h3>Sort:</h3>
      <Fragment>
        <SortTable
          items = {shifts}
          setItems = {setShifts}
          strToDel = {"shift_ID"}
        />
      </Fragment>
    </div>
  );
};

export default ShiftMainHandler;