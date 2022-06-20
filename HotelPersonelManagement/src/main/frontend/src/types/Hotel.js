import React, { useState , useEffect, Fragment } from "react";
import axios from "axios";
import { _withoutProperties } from "./utils";
import "../FrontApp.css";
import TableHeadRow from "../components/TableHeadRow";
import ReadOnlyRow from "../components/ReadOnlyRow";
import EditableRow from "../components/EditableRow";
import SortTable from "../components/SortTable";



const HotelTableBody = ({hotels, setHotels}) => {

    const [editHotelId, setEditHotelId] = useState(null);
    const [editFormData, setEditFormData] = useState({
        name: "",
        address: "",
        telephone: "",
        email: "",
        standard: "",
        rooms_number: "",
        creation_date: ""
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
        setEditHotelId(null);
    };

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
            creation_date: hotel.creation_date
        };
        setEditFormData(newFormValues);
    };
    
    function saveClickFunction(id) {
        axios
            .put(`http://localhost:8080/api/v1/hotel/${id}`,
                editFormData,
                {
                    headers: {"Content-Type": "application/json"}
                }
            )
            .then(() => {
                axios
                    .get("http://localhost:8080/api/v1/hotels").then(res =>{
                    setHotels(res.data);
                });
            });
        setEditHotelId(null);
    };

    function deleteClickFunction(hotel) {
        axios
            .delete(`http://localhost:8080/api/v1/hotel/${hotel.hotel_ID}`)
            .then(() => {
                axios
                    .get("http://localhost:8080/api/v1/hotels").then(res =>{
                    setHotels(res.data);
                });
            });
    };
    
    return(
        hotels.map((hotel) => (
            <Fragment>
                {editHotelId === hotel.hotel_ID ? (
                    <EditableRow
                        id = {hotel.hotel_ID}
                        rowObject = {editFormData}
                        handleEditFormChange = {handleEditFormChange}
                        handleSaveClick = {saveClickFunction}
                        handleCancelClick = {handleCancelFormClick}
                    />
                ) : (
                    <ReadOnlyRow
                        container = {hotel}
                        keysList = {_withoutProperties(hotel, ["hotel_ID"])}
                        handleEditClick = {handleEditClick}
                        handleDeleteClick = {deleteClickFunction}
                    />
                )}
            </Fragment>
        ))
    );
};


const HotelTableForm = ({hotels, setHotels}) => {
    const hotelHeadNames = ['Name', 'Address', 'Telephone', 'Email', 'Standard', 'Rooms number', 'Creation date'];

    const [editHotelId, setEditHotelId] = useState(null);
    const [editFormData] = useState({
        name: "",
        address: "",
        telephone: "",
        email: "",
        standard: "",
        rooms_number: "",
        creation_date: ""
    });

    useEffect(() => {
        axios
            .get("http://localhost:8080/api/v1/hotels").then(res =>{
            setHotels(res.data);
        });
    }, []);

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
            creation_date: editFormData.creation_date
        };
        const newHotels = [...hotels];
        const index = hotels.findIndex((hotel) => hotel.hotel_ID === editHotelId);
        newHotels[index] = editedHotel;
        setHotels(newHotels);
        setEditHotelId(null);
    };

    if (!hotels) return null;
    
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
                    <HotelTableBody
                        hotels = {hotels}
                        setHotels = {setHotels}
                    />
                </Fragment>
            </tbody>
        </table>
        </form>
    );
};


const HotelAddFormSubmit = ({hotels, setHotels}) => {
    const hotelNames = ['name', 'address', 'telephone', 'email', 'standard', 'rooms_number', 'creation_date'];
    
    const [addFormData, setAddFormData] = useState({
        name: "",
        address: "",
        telephone: "",
        email: "",
        standard: "",
        rooms_number: "",
        creation_date: ""
    });

    const handleAddFormSubmit = (event) => {
        event.preventDefault();
        const newHotel = {
            name: addFormData.name,
            address: addFormData.address,
            telephone: addFormData.telephone,
            email: addFormData.email,
            standard: addFormData.standard,
            rooms_number: addFormData.rooms_number,
            creation_date: addFormData.creation_date
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

    function saveClickFunction() {
        axios
            .post("http://localhost:8080/api/v1/hotel",
                addFormData,
                {
                    headers: {"Content-Type": "application/json"}
                }
            )
            .then(() => {
                axios
                    .get("http://localhost:8080/api/v1/hotels").then(res =>{
                    setHotels(res.data);
                });
            });
    };

    return (
        <form onSubmit={handleAddFormSubmit}>
            <Fragment>
                {Object.entries(hotelNames.slice(0,-1)).map(([key, value]) => (
                <input key = {key}
                    type="text"
                    name={value}
                    required="required"
                    placeholder={ "Enter ".concat(value, '...') }
                    onChange={handleAddFormChange}
                ></input>
                ))}
            </Fragment>
            <input key = {'creation_date'}
                type="date"
                name="creation_date"
                required="required"
                placeholder="Creation"
                onChange={handleAddFormChange}
            ></input>
            <button type="button" className="button-8" onClick={() => saveClickFunction()}>
                Add
            </button>
        </form>
    );
};



const HotelMainHandler = () => {
    const [hotels, setHotels] = useState(null);
    return(
        <div>
            <Fragment>
                <HotelTableForm
                    hotels = {hotels}
                    setHotels = {setHotels}
                />
            </Fragment>

            <h3>Add a Hotel:</h3>
            <Fragment>
                <HotelAddFormSubmit
                    hotels = {hotels}
                    setHotels = {setHotels}
                />
            </Fragment>

            <h3>Sort:</h3>
            <Fragment>
                <SortTable
                    items = {hotels}
                    setItems = {setHotels}
                    strsToDel = {["hotel_ID"]}
                />
            </Fragment>
        </div>
    );
};

export default HotelMainHandler;