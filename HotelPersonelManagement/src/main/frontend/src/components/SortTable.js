import React, { useState, Fragment } from "react";
import { _withoutProperties } from "../types/utils";



const SortTable = ({items, setItems, strToDel}) => {
    const [tableKey, setTableKey] = useState(null);

    const handleSortFormChange = (event) => {
        event.preventDefault();
        const fieldValue = event.target.value;
        setTableKey(fieldValue);
    };

    const handleSortClick = () => {
        const sortedItems = [];
        function sortByKey(array, key) {
            return array.sort(function(a, b) {
                var x = a[key]; var y = b[key];
                return ((x < y) ? -1 : ((x > y) ? 1 : 0));
            });
        }
        sortedItems = sortByKey(items, tableKey);
        setItems(sortedItems);
    };

    if (!items) return null;
    
    return(
        <div>
            <select
                type="text"
                name="position_ID"
                required="required"
                onChange={handleSortFormChange}
            >
                <Fragment>
                    {Object.entries(_withoutProperties(items[0], [strToDel]))
                        .map(([key, value]) => (
                        <option value={key}>
                            {key}
                        </option>
                    ))}
                </Fragment>
            </select>
            <button type="button" className="button-8" onClick={() => handleSortClick()}>
                Sort
            </button>
        </div>
    );
};

export default SortTable;