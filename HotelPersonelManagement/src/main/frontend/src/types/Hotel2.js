import React, { useState , useEffect, Fragment } from "react";
import apiClient from "../http-common";
import formatResponse from "./utils";
import "../FrontApp.css";
import data from "../hotel-data.json";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";



const HotelTableBody = () => {

    const [hotels, setHotels] = useState(null);
    const [editHotelId, setEditHotelId] = useState(null);
    const [editFormData, setEditFormData] = useState({
        name: "",
        address: "",
        telephone: "",
        email: "",
        standard: "",
        rooms_number: "",
        creation_date: "",
    });

    const handleEditFormChange = (event) => {
        event.preventDefault();
        const fieldName = event.target.getAttribute("name");
        const fieldValue = event.target.value;
        const newFormData = { ...editFormData };
        newFormData[fieldName] = fieldValue;
        setEditFormData(newFormData);
    };

    // TODO: update through axios
    const handleEditClick = (event, hotel) => {
        event.preventDefault();
        setEditHotelId(hotel.hotel_ID);
        const newFormValues = {
            name: hotel.name,
            address: hotel.address,
            telephone: hotel.telephone,
            email: hotel.email,
            standard: hotel.standard,
            rooms_number: hotel.rooms_number,
            creation_date: hotel.creation_date,
        };
        setEditFormData(newFormValues);
    };

    const handleCancelClick = () => {
        setEditHotelId(null);
    };
    
    // TODO: delete through axios
    const handleDeleteClick = (targetHotel) => {
        const newHotels = [...hotels];
        const index = hotels.findIndex((hotel) => hotel.hotel_ID === targetHotel.hotel_ID);
        newHotels.splice(index, 1);
        setHotels(newHotels);
    };

    return(
        hotels.map((hotel) => (
            <Fragment>
                {editHotelId === hotel.hotel_ID ? (
                    <EditableRow
                        editFormData = {editFormData}
                        handleEditFormChange = {handleEditFormChange}
                        handleCancelClick = {handleCancelClick}
                    />
                ) : (
                    <ReadOnlyRow
                        container = {hotel}
                        editFormData = {editFormData}
                        handleEditClick = {handleEditClick}
                        handleDeleteClick = {handleDeleteClick}
                    />
                )}
            </Fragment>
        ))
    );
};


const HotelTableForm = () => {
    const hotelHeadNames = ['Name', 'Address', 'Telephone', 'Email', 'Standard', 'Rooms number', 'Creation date'];

    const [hotels, setHotels] = useState(data);
    const [editHotelId, setEditHotelId] = useState(null);
    const [editFormData] = useState({
            name: "",
            address: "",
            telephone: "",
            email: "",
            standard: "",
            rooms_number: "",
            creation_date: "",
    });

    useEffect(() => {
        async function getHotels(){
            try {
                const result = await apiClient.get("/hotels");
                setHotels(result.data);
            } catch (error){
                setHotels(formatResponse( error.response?.data || error ));
            }
        }
        getHotels();
    }, [hotels]);

    const handleEditFormSubmit = (event) => {
        event.preventDefault();
        const editedHotel = {
            hotel_ID: editHotelId,
            name: editFormData.name,
            address: editFormData.address,
            telephone: editFormData.telephone,
            email: editFormData.email,
            standard: editFormData.standard,
            rooms_number: editFormData.rooms_number,
            creation_date: editFormData.creation_date,
        };
        const newHotels = [...hotels];
        const index = hotels.findIndex((hotel) => hotel.hotel_ID === editHotelId);
        newHotels[index] = editedHotel;
        setHotels(newHotels);
        setEditHotelId(null);
    };

    return(
        <form onSubmit={handleEditFormSubmit}>
        <table>
            <thead>
                <Fragment>
                    <TableHeadRow
                    headRowNames = {hotelHeadNames}
                    />
                </Fragment>
            </thead>
            <tbody>
                <Fragment>
                    <HotelTableBody/>
                </Fragment>
            </tbody>
        </table>
        </form>
    );
};


const HotelAddFormSubmit = () => {
    const hotelNames = ['name', 'address', 'telephone', 'email', 'standard', 'rooms_number', 'creation_date'];

    const [hotels, setHotels] = useState(data);
    const [addFormData, setAddFormData] = useState({
            name: "",
            address: "",
            telephone: "",
            email: "",
            standard: "",
            rooms_number: "",
            creation_date: "",
    });

    // TODO: add through axios
    const handleAddFormSubmit = (event) => {
        event.preventDefault();
        const newHotel = {
            hotel_ID: Math.floor(Math.random() * Math.pow(10, 15)),
            name: addFormData.name,
            address: addFormData.address,
            telephone: addFormData.telephone,
            email: addFormData.email,
            standard: addFormData.standard,
            rooms_number: addFormData.rooms_number,
            creation_date: addFormData.creation_date,
        };
        const newHotels = [...hotels, newHotel];
        setHotels(newHotels);
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
                {Object.entries(hotelNames).map(([key, value]) => (
                <input key = {key}
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



const Hotel2MainHandler = () => {
    return(
        <div>
            <Fragment>
                <HotelTableForm/>
            </Fragment>

            <h3>Add a Hotel:</h3>
            <Fragment>
                <HotelAddFormSubmit/>
            </Fragment>
        </div>
    );
};

export default Hotel2MainHandler;