import React, { useState, Fragment } from "react";
import "./FrontApp.css";
import data from "./mock-data.json";
import { ContactHeadRow, ContactTableBody, ContactAddFormSubmit } from "./types/Contact";


const FrontApp = () => {
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

  return (
    <div className="app-container">
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

      <h3>Add a Contact</h3>
      <Fragment>
        <ContactAddFormSubmit/>
      </Fragment>
    </div>
  );
};


export default FrontApp;
