import React, { useState , Fragment } from "react";
import { nanoid } from "nanoid";
import "../FrontApp.css";
import data from "../mock-data.json";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";


const ContactHeadRow = () => {
    return (
        <tr>
            <th>Name</th>
            <th>Address</th>
            <th>Phone Number</th>
            <th>Email</th>
            <th>Actions</th>
        </tr>
    );
};


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
                  contact = {contact}
                  handleEditClick = {handleEditClick}
                  handleDeleteClick = {handleDeleteClick}
                />
              )}
            </Fragment>
        ))
    );
};


const ContactMainForm = () => {
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
              <ContactHeadRow/>
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
        <input
          type="text"
          name="fullName"
          required="required"
          placeholder="Enter a name..."
          onChange={handleAddFormChange}
        />
        <input
          type="text"
          name="address"
          required="required"
          placeholder="Enter an addres..."
          onChange={handleAddFormChange}
        />
        <input
          type="text"
          name="phoneNumber"
          required="required"
          placeholder="Enter a phone number..."
          onChange={handleAddFormChange}
        />
        <input
          type="text"
          name="email"
          required="required"
          placeholder="Enter an email..."
          onChange={handleAddFormChange}
        />
        <button type="submit" class="button-8">Add</button>
      </form>
    );
};


export {ContactMainForm, ContactAddFormSubmit};