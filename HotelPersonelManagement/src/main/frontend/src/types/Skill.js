import React, { useState , useEffect, Fragment } from "react";
import axios from "axios";
import { _withoutProperties } from "./utils";import "../FrontApp.css";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";



const SkillTableBody = ({skills, setSkills}) => {

  const [editSkillId, setEditSkillId] = useState(null);
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
    setEditSkillId(null);
  };

  const handleEditClick = (event, skill) => {
    event.preventDefault();
    setEditSkillId(skill.skill_ID);
    const formValues = {
      name: skill.name,
      description: skill.description
    };
    setEditFormData(formValues);
  };

  function saveClickFunction(id) {
    axios
      .put(`http://localhost:8080/api/v1/skill/${id}`,
        editFormData,
        {
          headers: {"Content-Type": "application/json"}
        }
      )
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/skills").then(res =>{
          setSkills(res.data);
        });
      });
    setEditSkillId(null);
  };

  function deleteClickFunction(skill) {
    axios
      .delete(`http://localhost:8080/api/v1/skill/${skill.skill_ID}`)
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/skills").then(res =>{
          setSkills(res.data);
        });
      });
  };

  return(
    skills.map((skill) => (
      <Fragment>
        {editSkillId === skill.skill_ID ? (
          <EditableRow
            id = {skill.skill_ID}
            rowObject = {editFormData}
            handleEditFormChange = {handleEditFormChange}
            handleSaveClick = {saveClickFunction}
            handleCancelClick = {handleCancelFormClick}
          />
        ) : (
          <ReadOnlyRow
            container = {skill}
            keysList = {_withoutProperties(skill, ["skill_ID"])}
            handleEditClick = {handleEditClick}
            handleDeleteClick = {deleteClickFunction}
          />
        )}
      </Fragment>
    ))
  );
};


const SkillTableForm = ({skills, setSkills}) => {
  const skillHeadNames = ['Name', 'Description'];

  const [editSkillId, setEditSkillId] = useState(null);
  const [editFormData] = useState({
    name: "",
    description: ""
  });

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/v1/skills").then(res =>{
      setSkills(res.data);
    });
  }, []);

  const handleEditFormSubmit = (event) => {
    event.preventDefault();
    const editedSkill = {
      skill_ID: editSkillId,
      name: editFormData.name,
      description: editFormData.description
    };
    const newSkills = [...skills];
    const index = skills.findIndex((skill) => skill.skill_ID === editSkillId);
    newSkills[index] = editedSkill;
    setSkills(newSkills);
    setEditSkillId(null);
  };

  if (!skills) return null;

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
            <SkillTableBody
              skills = {skills}
              setSkills = {setSkills}
            />
          </Fragment>
        </tbody>
      </table>
    </form>
  );
};


const SkillAddFormSubmit = ({skills, setSkills}) => {
  const skillNames = ['name', 'description'];

  const [addFormData, setAddFormData] = useState({
    name: "",
    description: ""
  });

  const handleAddFormSubmit = (event) => {
    event.preventDefault();
    const newSkill = {
      name: addFormData.name,
      description: addFormData.description
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

  function saveClickFunction() {
    axios
      .post("http://localhost:8080/api/v1/skill",
        addFormData,
        {
          headers: {"Content-Type": "application/json"}
        }
      )
      .then(() => {
        axios
          .get("http://localhost:8080/api/v1/skills").then(res =>{
          setSkills(res.data);
        });
      });
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
      <button type="button" className="button-8" onClick={() => saveClickFunction()}>
        Add
      </button>
    </form>
  );
};



const SkillMainHandler = () => {
  const [skills, setSkills] = useState(null);
  return(
    <div>
      <Fragment>
        <SkillTableForm
          skills = {skills}
          setSkills = {setSkills}
        />
      </Fragment>

      <h3>Add a Skill</h3>
      <Fragment>
        <SkillAddFormSubmit
          skills = {skills}
          setSkills = {setSkills}
        />
      </Fragment>
    </div>
  );
};

export default SkillMainHandler;