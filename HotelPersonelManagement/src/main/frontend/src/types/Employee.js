import React, { useState , Fragment } from "react";
import "../FrontApp.css";
import data from "../mock-data.json";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";



const EmployeeTableBody = () => {

  const [employees, setEmployees] = useState(data);
  const [editFormData, setEditFormData] = useState({
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
  const [editEmployeeId, setEditEmployeeId] = useState(null);

  const handleEditFormChange = (event) => {
    event.preventDefault();

    const fieldName = event.target.getAttribute("name");
    const fieldValue = event.target.value;
    const newFormData = { ...editFormData };
    newFormData[fieldName] = fieldValue;
    setEditFormData(newFormData);
  };

  const handleEditClick = (event, employee) => {
    event.preventDefault();
    setEditEmployeeId(employee.employee_ID);

    const formValues = {
        name: employee.name,
        surname: employee.surname,
        address: employee.address,
        sex: employee.sex,
        date_of_birth: employee.date_of_birth,
        telephone: employee.telephone,
        email: employee.email,
        number_of_vacation_days: employee.number_of_vacation_days,
        date_of_employment: employee.date_of_employment,
    };
    setEditFormData(formValues);
  };

  const handleCancelClick = () => {
    setEditEmployeeId(null);
  };

  const handleDeleteClick = (targetEmployee) => {
    const newEmployees = [...employees];
    const index = employees.findIndex((employee) => employee.employee_ID === targetEmployee.employee_ID);
    newEmployees.splice(index, 1);
    setEmployees(newEmployees);
  };

  return(
    employees.map((employee) => (
      <Fragment>
        {editEmployeeId === employee.employee_ID ? (
          <EditableRow
            editFormData = {editFormData}
            handleEditFormChange = {handleEditFormChange}
            handleCancelClick = {handleCancelClick}
          />
        ) : (
          <ReadOnlyRow
            container = {employee}
            editFormData = {editFormData}
            handleEditClick = {handleEditClick}
            handleDeleteClick = {handleDeleteClick}
          />
        )}
      </Fragment>
    ))
  );
};


const EmployeeTableForm = () => {
  const employeeHeadNames = ['Name', 'Surname', 'Address', 'Sex', 'Date of birth', 'Telephone', 'Email', 'Number of vacation days', 'Date of employment'];

  const [employees, setEmployees] = useState(data);
  const [editFormData] = useState({
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
  const [editEmployeeId, setEditEmployeeId] = useState(null);

  const handleEditFormSubmit = (event) => {
    event.preventDefault();

    const editedEmployee = {
        employee_ID: editEmployeeId,
        name: editFormData.name,
        surname: editFormData.surname,
        address: editFormData.address,
        sex: editFormData.sex,
        date_of_birth: editFormData.date_of_birth,
        telephone: editFormData.telephone,
        email: editFormData.email,
        number_of_vacation_days: editFormData.number_of_vacation_days,
        date_of_employment: editFormData.date_of_employment,
    };

    const newEmployees = [...employees];
    const index = employees.findIndex((employee) => employee.employee_ID === editEmployeeId);
    newEmployees[index] = editedEmployee;
    setEmployees(newEmployees);
    setEditEmployeeId(null);
  };

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
            <EmployeeTableBody/>
          </Fragment>
        </tbody>
      </table>
    </form>
  );
};


const EmployeeAddFormSubmit = () => {
  const employeeNames = ['name', 'surname', 'address', 'sex', 'date_of_birth', 'telephone', 'email', 'number_of_vacation_days', 'date_of_employment'];

  const [employees, setEmployees] = useState(data);
  const [addFormData, setAddFormData] = useState({
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


  const handleAddFormSubmit = (event) => {
    event.preventDefault();

    const newEmployee = {
        employee_ID: Math.floor(Math.random() * Math.pow(10, 15)),
        name: addFormData.name,
        surname: addFormData.surname,
        address: addFormData.address,
        sex: addFormData.sex,
        date_of_birth: addFormData.date_of_birth,
        telephone: addFormData.telephone,
        email: addFormData.email,
        number_of_vacation_days: addFormData.number_of_vacation_days,
        date_of_employment: addFormData.date_of_employment,
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
      <button type="button" className="button-8">Add</button>
    </form>
  );
};



const EmployeeMainHandler = () => {
  return(
    <div>
      <Fragment>
        <EmployeeTableForm/>
      </Fragment>

      <h3>Add a Employee</h3>
      <Fragment>
        <EmployeeAddFormSubmit/>
      </Fragment>
    </div>
  );
};

export default EmployeeMainHandler;