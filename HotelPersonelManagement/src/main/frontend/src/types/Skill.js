import React, { useState , Fragment } from "react";
import "../FrontApp.css";
import data from "../mock-data.json";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";



const SkillTableBody = () => {

  const [skills, setSkills] = useState(data);
  const [editFormData, setEditFormData] = useState({
    name: "",
    description: "",
  });
  const [editSkillId, setEditSkillId] = useState(null);

  const handleEditFormChange = (event) => {
    event.preventDefault();

    const fieldName = event.target.getAttribute("name");
    const fieldValue = event.target.value;
    const newFormData = { ...editFormData };
    newFormData[fieldName] = fieldValue;
    setEditFormData(newFormData);
  };

  const handleEditClick = (event, skill) => {
    event.preventDefault();
    setEditSkillId(skill.skill_ID);

    const formValues = {
        name: skill.name,
        description: skill.description,
    };
    setEditFormData(formValues);
  };

  const handleCancelClick = () => {
    setEditSkillId(null);
  };

  const handleDeleteClick = (targetSkill) => {
    const newSkills = [...skills];
    const index = skills.findIndex((skill) => skill.skill_ID === targetSkill.skill_ID);
    newSkills.splice(index, 1);
    setSkills(newSkills);
  };

  return(
    skills.map((skill) => (
      <Fragment>
        {editSkillId === skill.skill_ID ? (
          <EditableRow
            editFormData = {editFormData}
            handleEditFormChange = {handleEditFormChange}
            handleCancelClick = {handleCancelClick}
          />
        ) : (
          <ReadOnlyRow
            container = {skill}
            editFormData = {editFormData}
            handleEditClick = {handleEditClick}
            handleDeleteClick = {handleDeleteClick}
          />
        )}
      </Fragment>
    ))
  );
};


const SkillTableForm = () => {
  const skillHeadNames = ['Name', 'Description'];

  const [skills, setSkills] = useState(data);
  const [editFormData] = useState({
    name: "",
    description: "",
  });
  const [editSkillId, setEditSkillId] = useState(null);

  const handleEditFormSubmit = (event) => {
    event.preventDefault();

    const editedSkill = {
        skill_ID: editSkillId,
        name: editFormData.name,
        description: editFormData.description,
    };

    const newSkills = [...skills];
    const index = skills.findIndex((skill) => skill.skill_ID === editSkillId);
    newSkills[index] = editedSkill;
    setSkills(newSkills);
    setEditSkillId(null);
  };

  return(
    <form onSubmit={handleEditFormSubmit}>
      <table>
        <thead>
          <Fragment>
            <TableHeadRow
              headRowNames = {skillHeadNames}
            />
          </Fragment>
        </thead>
        <tbody>
          <Fragment>
            <SkillTableBody/>
          </Fragment>
        </tbody>
      </table>
    </form>
  );
};


const SkillAddFormSubmit = () => {
  const skillNames = ['name', 'description'];

  const [skills, setSkills] = useState(data);
  const [addFormData, setAddFormData] = useState({
    name: "",
    description: "",
  });


  const handleAddFormSubmit = (event) => {
    event.preventDefault();

    const newSkill = {
        skill_ID: Math.floor(Math.random() * Math.pow(10, 15)),
        name: addFormData.name,
        description: addFormData.description,
    };

    const newSkills = [...skills, newSkill];
    setSkills(newSkills);
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
        {Object.entries(skillNames).map(([key, value]) => (
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



const SkillMainHandler = () => {
  return(
    <div>
      <Fragment>
        <SkillTableForm/>
      </Fragment>

      <h3>Add a Skill</h3>
      <Fragment>
        <SkillAddFormSubmit/>
      </Fragment>
    </div>
  );
};

export default SkillMainHandler;