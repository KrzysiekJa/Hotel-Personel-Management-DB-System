import React, { useState , Fragment } from "react";
import "../FrontApp.css";
import data from "../mock-data.json";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";



const PositionTableBody = () => {

  const [positions, setPositions] = useState(data);
  const [editFormData, setEditFormData] = useState({
    name: "",
    description: "",
  });
  const [editPositionId, setEditPositionId] = useState(null);

  const handleEditFormChange = (event) => {
    event.preventDefault();

    const fieldName = event.target.getAttribute("name");
    const fieldValue = event.target.value;
    const newFormData = { ...editFormData };
    newFormData[fieldName] = fieldValue;
    setEditFormData(newFormData);
  };

  const handleEditClick = (event, position) => {
    event.preventDefault();
    setEditPositionId(position.position_ID);

    const formValues = {
        name: position.name,
        description: position.description,
    };
    setEditFormData(formValues);
  };

  const handleCancelClick = () => {
    setEditPositionId(null);
  };

  const handleDeleteClick = (targetPosition) => {
    const newPositions = [...positions];
    const index = positions.findIndex((position) => position.position_ID === targetPosition.position_ID);
    newPositions.splice(index, 1);
    setPositions(newPositions);
  };

  return(
    positions.map((position) => (
      <Fragment>
        {editPositionId === position.position_ID ? (
          <EditableRow
            editFormData = {editFormData}
            handleEditFormChange = {handleEditFormChange}
            handleCancelClick = {handleCancelClick}
          />
        ) : (
          <ReadOnlyRow
            container = {position}
            editFormData = {editFormData}
            handleEditClick = {handleEditClick}
            handleDeleteClick = {handleDeleteClick}
          />
        )}
      </Fragment>
    ))
  );
};


const PositionTableForm = () => {
  const positionHeadNames = ['Name', 'Description'];

  const [positions, setPositions] = useState(data);
  const [editFormData] = useState({
    name: "",
    description: "",
  });
  const [editPositionId, setEditPositionId] = useState(null);

  const handleEditFormSubmit = (event) => {
    event.preventDefault();

    const editedPosition = {
        position_ID: editPositionId,
        name: editFormData.name,
        description: editFormData.description,
    };

    const newPositions = [...positions];
    const index = positions.findIndex((position) => position.position_ID === editPositionId);
    newPositions[index] = editedPosition;
    setPositions(newPositions);
    setEditPositionId(null);
  };

  return(
    <form onSubmit={handleEditFormSubmit}>
      <table>
        <thead>
          <Fragment>
            <TableHeadRow
              headRowNames = {positionHeadNames}
            />
          </Fragment>
        </thead>
        <tbody>
          <Fragment>
            <PositionTableBody/>
          </Fragment>
        </tbody>
      </table>
    </form>
  );
};


const PositionAddFormSubmit = () => {
  const positionNames = ['name', 'description'];

  const [positions, setPositions] = useState(data);
  const [addFormData, setAddFormData] = useState({
    name: "",
    description: "",
  });


  const handleAddFormSubmit = (event) => {
    event.preventDefault();

    const newPosition = {
        position_ID: Math.floor(Math.random() * Math.pow(10, 15)),
        name: addFormData.name,
        description: addFormData.description,
    };

    const newPositions = [...positions, newPosition];
    setPositions(newPositions);
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
        {Object.entries(positionNames).map(([key, value]) => (
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



const PositionMainHandler = () => {
  return(
    <div>
      <Fragment>
        <PositionTableForm/>
      </Fragment>

      <h3>Add a Position</h3>
      <Fragment>
        <PositionAddFormSubmit/>
      </Fragment>
    </div>
  );
};

export default PositionMainHandler;