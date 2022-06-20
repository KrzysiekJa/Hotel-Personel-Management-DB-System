import React, { useState , useEffect, Fragment } from "react";
import axios from "axios";
import { _withoutProperties } from "./utils";
import "../FrontApp.css";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRowWorkPlanEmployee from "../components/ReadOnlyRowWorkPlanEmployee";
import SortTable from "../components/SortTable";



const WorkPlanEmployeeTableBody = ({workPlanEmployees, setWorkPlanEmployees}) => {

    function deleteClickFunction(workPlanEmployee) {
        axios
            .delete(`http://localhost:8080/api/v1/workplanemployee/${workPlanEmployee.work_plan_Employees_ID}`)
            .then(() => {
                axios
                    .get("http://localhost:8080/api/v1/alldata").then(res =>{
                    console.log(res.data);
            });
        });
    };

    return(
        workPlanEmployees.map((workPlanEmployee) => (
        <Fragment>
            <ReadOnlyRowWorkPlanEmployee
            container = {workPlanEmployee}
            keysList = {_withoutProperties(workPlanEmployee, ["work_plan_Employees_ID", "employee_ID", "hotel_ID", "shift_ID"])}
            handleDeleteClick = {deleteClickFunction}
            />
        </Fragment>
        ))
    );
};


const WorkPlanEmployeeTableForm = ({workPlanEmployees, setWorkPlanEmployees}) => {
    const workPlanEmployeeHeadNames = ['Employee name', 'Employee surname', 'Hotel name', 'Starting date', 'Ending date'];

    const [workPlanEmployeesId, setWorkPlanEmployeesId] = useState(null);
    const [editFormData] = useState({
        employee_name: "",
        employee_surname: "",
        employee_ID: "",
        hotel_name: "",
        hotel_ID: "",
        shift_ID: "",
        starting_date: "",
        ending_date: ""
    });

    const handleEditFormSubmit = (event) => {
        event.preventDefault();
        const editedWorkPlanEmployee = {
            work_plan_Employees_ID: workPlanEmployeesId,
            employee_name:  editFormData.employee_name,
            employee_surname:  editFormData.employee_surname,
            employee_ID:  editFormData.employee_ID,
            hotel_name:  editFormData.hotel_name,
            hotel_ID:  editFormData.hotel_ID,
            shift_ID:  editFormData.shift_ID,
            starting_date:  editFormData.starting_date,
            ending_date: editFormData.ending_date
        };
        const newWorkPlanEmployees = [...workPlanEmployees];
        const index = workPlanEmployees.findIndex((workPlanEmployee) => workPlanEmployee.work_plan_Employees_ID === workPlanEmployeesId);
        newWorkPlanEmployees[index] = editedWorkPlanEmployee;
        setWorkPlanEmployees(newWorkPlanEmployees);
        setWorkPlanEmployeesId(null);
    };

    if (!workPlanEmployees) return null;

    return(
        <form onSubmit={handleEditFormSubmit}>
        <table>
            <thead>
            <Fragment>
                <TableHeadRow
                    headRowNames = {workPlanEmployeeHeadNames}
                />
            </Fragment>
            </thead>
            <tbody>
            <Fragment>
                <WorkPlanEmployeeTableBody
                    workPlanEmployees = {workPlanEmployees}
                    setWorkPlanEmployees = {setWorkPlanEmployees}
                />
            </Fragment>
            </tbody>
        </table>
        </form>
    );
};


const WorkPlanEmployeeAddFormSubmit = ({workPlanEmployees, setWorkPlanEmployees, employee_name_surnames,
                                            hotel_names, starting_ending_dates}) => {

    const [addFormData, setAddFormData] = useState({
        employee_name_surname: "",
        employee_ID: "",
        hotel_name: "",
        hotel_ID: "",
        shift_ID: "",
        starting_ending_date: ""
    });

    const handleAddFormSubmit = (event) => {
        event.preventDefault();
        const newWorkPlanEmployee = {
            employee_name_surname:  addFormData.employee_name_surname,
            employee_ID:  addFormData.employee_ID,
            hotel_name:  addFormData.hotel_name,
            hotel_ID:  addFormData.hotel_ID,
            shift_ID:  addFormData.shift_ID,
            starting_ending_date: addFormData.starting_ending_date
        };
        const newWorkPlanEmployees = [...workPlanEmployees, newWorkPlanEmployee];
        //setPositions(newWorkPlanEmployees);
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
        addFormData['employee_ID'] = employee_name_surnames.filter(obj => {
                return obj.employee_name_surname === addFormData['employee_name_surname']
            })[0]['employee_ID'];
        addFormData['hotel_ID'] = hotel_names.filter(obj => {
                return obj.hotel_name === addFormData['hotel_name']
            })[0]['hotel_ID'];
        addFormData['shift_ID'] = starting_ending_dates.filter(obj => {
                return obj.starting_ending_date === addFormData['starting_ending_date']
            })[0]['shift_ID'];
        axios
        .post("http://localhost:8080/api/v1/workplanemployee",
            addFormData,
            {
                headers: {"Content-Type": "application/json"}
            }
        )
        .then(() => {
            axios
                .get("http://localhost:8080/api/v1/alldata").then(res =>{
                setWorkPlanEmployees(res.data);
            });
        });
    };

    return (
        <form onSubmit={handleAddFormSubmit}>
            <select
                type="text"
                name="employee_name_surname"
                required="required"
                onChange={handleAddFormChange}
            >
                <Fragment>
                    {Object.entries(employee_name_surnames).map(([key, value]) => (
                        <option value={value['employee_name_surname']}>
                            {value['employee_name_surname']}
                        </option>
                    ))}
                </Fragment>
            </select>
            <select
                type="text"
                name="hotel_name"
                required="required"
                onChange={handleAddFormChange}
            >
                <Fragment>
                    {Object.entries(hotel_names).map(([key, value]) => (
                        <option value={value['hotel_name']}>
                            {value['hotel_name']}
                        </option>
                    ))}
                </Fragment>
            </select>
            <select
                type="text"
                name="starting_ending_date"
                required="required"
                onChange={handleAddFormChange}
            >
                <Fragment>
                    {Object.entries(starting_ending_dates).map(([key, value]) => (
                        <option value={value['starting_ending_date']}>
                            {value['starting_ending_date']}
                        </option>
                    ))}
                </Fragment>
            </select>
            <button type="button" className="button-8" onClick={() => saveClickFunction()}>
                Add
            </button>
        </form>
    );
};



const WorkPlanEmployeeMainHandler = () => {
    const [workPlanEmployees, setWorkPlanEmployees] = useState(null);

    useEffect(() => {
        axios
            .get("http://localhost:8080/api/v1/alldata").then(res =>{
            setWorkPlanEmployees(res.data);
        });
    }, []);

    if (!workPlanEmployees) return null;

    const employee_name_surnames = workPlanEmployees.map(
        ({employee_name, employee_surname, employee_ID}) => {
            return {
                "employee_name_surname": employee_name.concat(' ', employee_surname),
                "id": employee_ID
            };
        }).filter((value, index, self) =>
            index === self.findIndex((t) => (
            t.id === value.id ))
        )

    const hotel_names = workPlanEmployees.map(
        ({hotel_name, hotel_ID}) => {
            return {
                "hotel_name": hotel_name,
                "id": hotel_ID
            };
        }).filter((value, index, self) =>
            index === self.findIndex((t) => (
            t.id === value.id ))
        )

    const starting_ending_dates = workPlanEmployees.map(
        ({starting_date, ending_date, shift_ID}) => {
            return {
                "starting_ending_date": starting_date.concat(' - ', ending_date),
                "id": shift_ID
            };
        }).filter((value, index, self) =>
            index === self.findIndex((t) => (
            t.id === value.id ))
        )

    return(
        <div>
            <Fragment>
                <WorkPlanEmployeeTableForm
                    workPlanEmployees = {workPlanEmployees}
                    setWorkPlanEmployees = {setWorkPlanEmployees}
                />
            </Fragment>

            <h3>Add a Work Plan element:</h3>
            <Fragment>
                <WorkPlanEmployeeAddFormSubmit
                    workPlanEmployees = {workPlanEmployees}
                    setWorkPlanEmployees = {setWorkPlanEmployees}
                    employee_name_surnames = {employee_name_surnames}
                    hotel_names = {hotel_names}
                    starting_ending_dates = {starting_ending_dates}
                />
            </Fragment>

            <h3>Sort:</h3>
            <Fragment>
                <SortTable
                    items = {workPlanEmployees}
                    setItems = {setWorkPlanEmployees}
                    strsToDel = {["work_plan_Employees_ID", "employee_ID", "hotel_ID", "shift_ID"]}
                />
            </Fragment>
        </div>
    );
};

export default WorkPlanEmployeeMainHandler;