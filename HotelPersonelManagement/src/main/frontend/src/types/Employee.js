import React, { useState , useEffect, Fragment } from "react";
import axios from "axios";
import { _withoutProperties } from "./utils";import "../FrontApp.css";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";



const EmployeeTableBody = ({employees, setEmployees}) => {

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
          <EditableRow
            id = {employee.employee_ID}
            editFormData = {editFormData}
            handleEditFormChange = {handleEditFormChange}
            handleSaveClick = {saveClickFunction}
            handleCancelClick = {handleCancelFormClick}
          />
        ) : (
          <ReadOnlyRow
            container = {employee}
            keysList = {_withoutProperties(employee, ["employee_ID"])}
            handleEditClick = {handleEditClick}
            handleDeleteClick = {deleteClickFunction}
          />
        )}
      </Fragment>
    ))
  );
};


const EmployeeTableForm = ({employees, setEmployees}) => {
  const employeeHeadNames = ['position_ID', 'Name', 'Surname', 'Address', 'Sex', 'Date of birth', 'Telephone', 'Email', 'Number of vacation days', 'Date of employment'];

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
            />
          </Fragment>
        </tbody>
      </table>
    </form>
  );
};


const EmployeeAddFormSubmit = (employees, setEmployees) => {
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
      <Fragment>
        {Object.entries(employeeNames).map(([key, value]) => (
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



const EmployeeMainHandler = () => {
const [employees, setEmployees] = useState(null);
  return(
    <div>
      <Fragment>
        <EmployeeTableForm
          employees = {employees}
          setEmployees = {setEmployees}
        />
      </Fragment>

      <h3>Add a Employee</h3>
      <Fragment>
        <EmployeeAddFormSubmit
          employees = {employees}
          setEmployees = {setEmployees}
        />
      </Fragment>
    </div>
  )
};

export default EmployeeMainHandler;