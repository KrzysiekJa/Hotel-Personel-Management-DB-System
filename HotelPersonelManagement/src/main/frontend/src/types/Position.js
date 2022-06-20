import React, { useState , useEffect, Fragment } from "react";
import axios from "axios";
import { _withoutProperties } from "./utils";
import "../FrontApp.css";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";
import SortTable from "../components/SortTable";



const PositionTableBody = ({positions, setPositions}) => {

  const [editPositionId, setEditPositionId] = useState(null);
  const [editFormData, setEditFormData] = useState({
    name: "",
    description: ""
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
    setEditPositionId(null);
  };

  const handleEditClick = (event, position) => {
    event.preventDefault();
    setEditPositionId(position.position_ID);
    const formValues = {
        name: position.name,
        description: position.description
    };
    setEditFormData(formValues);
  };

  function saveClickFunction(id) {
    axios
      .put(`http://localhost:8080/api/v1/position/${id}`,
        editFormData,
        {
          headers: {"Content-Type": "application/json"}
        }
      )
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/positions").then(res =>{
          setPositions(res.data);
        });
      });
    setEditPositionId(null);
  };

  function deleteClickFunction(position) {
    axios
      .delete(`http://localhost:8080/api/v1/position/${position.position_ID}`)
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/positions").then(res =>{
          setPositions(res.data);
        });
      });
  };

  return(
    positions.map((position) => (
      <Fragment>
        {editPositionId === position.position_ID ? (
          <EditableRow
            id = {position.position_ID}
            rowObject = {editFormData}
            handleEditFormChange = {handleEditFormChange}
            handleSaveClick = {saveClickFunction}
            handleCancelClick = {handleCancelFormClick}
          />
        ) : (
          <ReadOnlyRow
            container = {position}
            keysList = {_withoutProperties(position, ["position_ID"])}
            handleEditClick = {handleEditClick}
            handleDeleteClick = {deleteClickFunction}
          />
        )}
      </Fragment>
    ))
  );
};


const PositionTableForm = ({positions, setPositions}) => {
  const positionHeadNames = ['Name', 'Description'];

  const [editPositionId, setEditPositionId] = useState(null);
  const [editFormData] = useState({
    name: "",
    description: ""
  });

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/v1/positions").then(res =>{
      setPositions(res.data);
    });
  }, []);

  const handleEditFormSubmit = (event) => {
    event.preventDefault();
    const editedPosition = {
      position_ID: editPositionId,
      name: editFormData.name,
      description: editFormData.description
    };
    const newPositions = [...positions];
    const index = positions.findIndex((position) => position.position_ID === editPositionId);
    newPositions[index] = editedPosition;
    setPositions(newPositions);
    setEditPositionId(null);
  };

  if (!positions) return null;

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
            <PositionTableBody
              positions = {positions}
              setPositions = {setPositions}
            />
          </Fragment>
        </tbody>
      </table>
    </form>
  );
};


const PositionAddFormSubmit = ({positions, setPositions}) => {
  const positionNames = ['name', 'description'];

  const [addFormData, setAddFormData] = useState({
    name: "",
    description: ""
  });

  const handleAddFormSubmit = (event) => {
    event.preventDefault();
    const newPosition = {
      name: addFormData.name,
      description: addFormData.description
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

  function saveClickFunction() {
    axios
      .post("http://localhost:8080/api/v1/position",
        addFormData,
        {
          headers: {"Content-Type": "application/json"}
        }
      )
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/positions").then(res =>{
          setPositions(res.data);
        });
      });
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
      <button type="button" className="button-8" onClick={() => saveClickFunction()}>
        Add
      </button>
    </form>
  );
};



const PositionMainHandler = () => {
  const [positions, setPositions] = useState(null);
  return(
    <div>
      <Fragment>
        <PositionTableForm
          positions = {positions}
          setPositions = {setPositions}
        />
      </Fragment>

      <h3>Add a Position:</h3>
      <Fragment>
        <PositionAddFormSubmit
          positions = {positions}
          setPositions = {setPositions}
        />
      </Fragment>

      <h3>Sort:</h3>
      <Fragment>
        <SortTable
          items = {positions}
          setItems = {setPositions}
          strsToDel = {["position_ID"]}
        />
      </Fragment>
    </div>
  );
};

export default PositionMainHandler;