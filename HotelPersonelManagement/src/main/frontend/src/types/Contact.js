import React, { useState , Fragment } from "react";
import { nanoid } from "nanoid";
import "../FrontApp.css";
import data from "../mock-data.json";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";



const ContactTableBody = () => {

  const [contacts, setContacts] = useState(data);
  const [editFormData, setEditFormData] = useState({
    fullName: "",
    address: "",
    phoneNumber: "",
    email: "",
  });
  const [editContactId, setEditContactId] = useState(null);

  const handleEditFormChange = (event) => {
    event.preventDefault();

    const fieldName = event.target.getAttribute("name");
    const fieldValue = event.target.value;
    const newFormData = { ...editFormData };
    newFormData[fieldName] = fieldValue;
    setEditFormData(newFormData);
  };

  const handleEditClick = (event, contact) => {
    event.preventDefault();
    setEditContactId(contact.id);

    const formValues = {
    fullName: contact.fullName,
    address: contact.address,
    phoneNumber: contact.phoneNumber,
    email: contact.email,
    };
    setEditFormData(formValues);
  };

  const handleCancelClick = () => {
    setEditContactId(null);
  };

  const handleDeleteClick = (contactId) => {
    const newContacts = [...contacts];
    const index = contacts.findIndex((contact) => contact.id === contactId);
    newContacts.splice(index, 1);
    setContacts(newContacts);
  };

  return(
    contacts.map((contact) => (
      <Fragment>
        {editContactId === contact.id ? (
          <EditableRow
            editFormData = {editFormData}
            handleEditFormChange = {handleEditFormChange}
            handleCancelClick = {handleCancelClick}
          />
        ) : (
          <ReadOnlyRow
            container = {contact}
            editFormData = {editFormData}
            handleEditClick = {handleEditClick}
            handleDeleteClick = {handleDeleteClick}
          />
        )}
      </Fragment>
    ))
  );
};


const ContactTableForm = () => {
  const contactHeadNames = ['Name', 'Address', 'Phone Number', 'Email'];

  const [contacts, setContacts] = useState(data);
  const [editFormData] = useState({
    fullName: "",
    address: "",
    phoneNumber: "",
    email: "",
  });
  const [editContactId, setEditContactId] = useState(null);

  const handleEditFormSubmit = (event) => {
    event.preventDefault();

    const editedContact = {
      id: editContactId,
      fullName: editFormData.fullName,
      address: editFormData.address,
      phoneNumber: editFormData.phoneNumber,
      email: editFormData.email,
    };

    const newContacts = [...contacts];
    const index = contacts.findIndex((contact) => contact.id === editContactId);
    newContacts[index] = editedContact;
    setContacts(newContacts);
    setEditContactId(null);
  };

  return(
    <form onSubmit={handleEditFormSubmit}>
      <table>
        <thead>
          <Fragment>
            <TableHeadRow
              headRowNames = {contactHeadNames}
            />
          </Fragment>
        </thead>
        <tbody>
          <Fragment>
            <ContactTableBody/>
          </Fragment>
        </tbody>
      </table>
    </form>
  );
};


const ContactAddFormSubmit = () => {
  const contactNames = ['fullName', 'address', 'phoneNumber', 'email'];

  const [contacts, setContacts] = useState(data);
  const [addFormData, setAddFormData] = useState({
    fullName: "",
    address: "",
    phoneNumber: "",
    email: "",
  });


  const handleAddFormSubmit = (event) => {
    event.preventDefault();

    const newContact = {
      id: nanoid(),
      fullName: addFormData.fullName,
      address: addFormData.address,
      phoneNumber: addFormData.phoneNumber,
      email: addFormData.email,
    };

    const newContacts = [...contacts, newContact];
    setContacts(newContacts);
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
        {Object.entries(contactNames).map(([key, value]) => (
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



const ContactMainHandler = () => {
  return(
    <div>
      <Fragment>
        <ContactTableForm/>
      </Fragment>

      <h3>Add a Contact</h3>
      <Fragment>
        <ContactAddFormSubmit/>
      </Fragment>
    </div>
  );
};

export default ContactMainHandler;