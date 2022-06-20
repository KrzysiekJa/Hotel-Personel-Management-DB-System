import React, { useState , useEffect, Fragment } from "react";
import axios from "axios";
import { _withoutProperties } from "./utils";import "../FrontApp.css";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRowEmployee from "../components/ReadOnlyRowEmployee";
import EditableRowEmployee from "../components/EditableRowEmployee";
import SortTable from "../components/SortTable";



const EmployeeTableBody = ({employees, setEmployees, positions}) => {

  const [editEmployeeId, setEditEmployeeId] = useState(null);
  const [editFormData, setEditFormData] = useState({
    position_ID: "",
    name: "",
    surname: "",
    address: "",
    sex: "",
    date_of_birth: "",
    telephone: "",
    email: "",
    number_of_vacation_days: "",
    date_of_employment: ""
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
    setEditEmployeeId(null);
  };

  const handleEditClick = (event, employee) => {
    event.preventDefault();
    setEditEmployeeId(employee.employee_ID);
    const formValues = {
      position_ID: employee.position_ID,
      name: employee.name,
      surname: employee.surname,
      address: employee.address,
      sex: employee.sex,
      date_of_birth: employee.date_of_birth,
      telephone: employee.telephone,
      email: employee.email,
      number_of_vacation_days: employee.number_of_vacation_days,
      date_of_employment: employee.date_of_employment
    };
    setEditFormData(formValues);
  };

  function saveClickFunction(id) {
    editFormData['position_ID'] = positions.filter(obj => {
        return obj.name === editFormData['position_ID']; }
      )[0]['position_ID']
    axios
      .put(`http://localhost:8080/api/v1/employee/${id}`,
        editFormData,
        {
          headers: {"Content-Type": "application/json"}
        }
      )
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/employees").then(res =>{
          setEmployees(res.data);
        });
      });
    setEditEmployeeId(null);
  };

  function deleteClickFunction(employee) {
    axios
      .delete(`http://localhost:8080/api/v1/employee/${employee.employee_ID}`)
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/employees").then(res =>{
          setEmployees(res.data);
        });
      });
  };

  return(
    employees.map((employee) => (
      <Fragment>
        {editEmployeeId === employee.employee_ID ? (
          <EditableRowEmployee
            id = {employee.employee_ID}
            rowObject = {_withoutProperties(editFormData, ["position_ID"])}
            handleEditFormChange = {handleEditFormChange}
            handleSaveClick = {saveClickFunction}
            handleCancelClick = {handleCancelFormClick}
            positions = {positions}
          />
        ) : (
          <ReadOnlyRowEmployee
            container = {employee}
            keysList = {_withoutProperties(employee, ["employee_ID"])}
            handleEditClick = {handleEditClick}
            handleDeleteClick = {deleteClickFunction}
            positions = {positions}
          />
        )}
      </Fragment>
    ))
  );
};


const EmployeeTableForm = ({employees, setEmployees, positions}) => {
  const employeeHeadNames = ['Position', 'Name', 'Surname', 'Address', 'Sex', 'Date of birth', 'Telephone', 'Email', 'Number of vacation days', 'Date of employment'];

  const [editEmployeeId, setEditEmployeeId] = useState(null);
  const [editFormData] = useState({
    position_ID: "",
    name: "",
    surname: "",
    address: "",
    sex: "",
    date_of_birth: "",
    telephone: "",
    email: "",
    number_of_vacation_days: "",
    date_of_employment: "",
  });

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/v1/employees").then(res =>{
      setEmployees(res.data);
    });
  }, []);

  const handleEditFormSubmit = (event) => {
    event.preventDefault();
    const editedEmployee = {
      employee_ID: editEmployeeId,
      position_ID: editFormData.position_ID,
      name: editFormData.name,
      surname: editFormData.surname,
      address: editFormData.address,
      sex: editFormData.sex,
      date_of_birth: editFormData.date_of_birth,
      telephone: editFormData.telephone,
      email: editFormData.email,
      number_of_vacation_days: editFormData.number_of_vacation_days,
      date_of_employment: editFormData.date_of_employment
    };
    const newEmployees = [...employees];
    const index = employees.findIndex((employee) => employee.employee_ID === editEmployeeId);
    newEmployees[index] = editedEmployee;
    setEmployees(newEmployees);
    setEditEmployeeId(null);
  };
  
  if (!employees) return null;

  return(
    <form onSubmit={handleEditFormSubmit}>
      <table>
        <thead>
          <Fragment>
            <TableHeadRow
              headRowNames = {employeeHeadNames}
            />
          </Fragment>
        </thead>
        <tbody>
          <Fragment>
            <EmployeeTableBody
              employees = {employees}
              setEmployees = {setEmployees}
              positions = {positions}
            />
          </Fragment>
        </tbody>
      </table>
    </form>
  );
};


const EmployeeAddFormSubmit = ({employees, setEmployees, positions}) => {
  const employeeNames = ['position_ID', 'name', 'surname', 'address', 'sex', 'date_of_birth', 'telephone', 'email', 'number_of_vacation_days', 'date_of_employment'];

  const [addFormData, setAddFormData] = useState({
    position_ID: "",
    name: "",
    surname: "",
    address: "",
    sex: "",
    date_of_birth: "",
    telephone: "",
    email: "",
    number_of_vacation_days: "",
    date_of_employment: ""
  });

  const handleAddFormSubmit = (event) => {
    event.preventDefault();
    const newEmployee = {
      position_ID: addFormData.position_ID,
      name: addFormData.name,
      surname: addFormData.surname,
      address: addFormData.address,
      sex: addFormData.sex,
      date_of_birth: addFormData.date_of_birth,
      telephone: addFormData.telephone,
      email: addFormData.email,
      number_of_vacation_days: addFormData.number_of_vacation_days,
      date_of_employment: addFormData.date_of_employment
    };
    const newEmployees = [...employees, newEmployee];
    setEmployees(newEmployees);
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
    addFormData['position_ID'] = positions.filter(obj => {
        return obj.name === addFormData['position_ID']; }
      )[0]['position_ID']
    axios
      .post("http://localhost:8080/api/v1/employee",
        addFormData,
        {
          headers: {"Content-Type": "application/json"}
        }
      )
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/employees").then(res =>{
          setEmployees(res.data);
        });
      });
  };
  
  return (
    <form onSubmit={handleAddFormSubmit}>
      <select
        type="text"
        name="position_ID"
        required="required"
        onChange={handleAddFormChange}
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
        {Object.entries(employeeNames.slice(1,4)).map(([key, value]) => (
          <input
            type="text"
            name={value}
            required="required"
            placeholder={ "Enter ".concat(value, '...') }
            onChange={handleAddFormChange}
          ></input>
        ))}
      </Fragment>
      <Fragment>
        <select
          type="text"
          name="sex"
          required="required"
          onChange={handleAddFormChange}
        >
          <option value="Male">Male</option>
          <option value="Female">Female</option>
        </select>
      </Fragment>
      <input
        type="date"
        name="date_of_birth"
        required="required"
        placeholder="Birth"
        onChange={handleAddFormChange}
      ></input>
      <Fragment>
        {Object.entries(employeeNames.slice(6,-1)).map(([key, value]) => (
          <input
            type="text"
            name={value}
            required="required"
            placeholder={ "Enter ".concat(value, '...') }
            onChange={handleAddFormChange}
          ></input>
        ))}
      </Fragment>
      <input
        type="date"
        name="date_of_employment"
        required="required"
        placeholder="Employment"
        onChange={handleAddFormChange}
      ></input>
      <button type="button" className="button-8" onClick={() => saveClickFunction()}>
        Add
      </button>
    </form>
  );
};



const EmployeeMainHandler = () => {
  const [employees, setEmployees] = useState(null);
  const [positions, setPositions] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/v1/positions").then(res =>{
      setPositions(res.data);
    });
  }, []);
  
  if (!positions) return null;

  return(
    <div>
      <Fragment>
        <EmployeeTableForm
          employees = {employees}
          setEmployees = {setEmployees}
          positions = {positions}
        />
      </Fragment>

      <h3>Add a Employee:</h3>
      <Fragment>
        <EmployeeAddFormSubmit
          employees = {employees}
          setEmployees = {setEmployees}
          positions = {positions}
        />
      </Fragment>

      <h3>Sort:</h3>
      <Fragment>
        <SortTable
          items = {employees}
          setItems = {setEmployees}
          strsToDel = {["employee_ID"]}
        />
      </Fragment>
    </div>
  )
};

export default EmployeeMainHandler;