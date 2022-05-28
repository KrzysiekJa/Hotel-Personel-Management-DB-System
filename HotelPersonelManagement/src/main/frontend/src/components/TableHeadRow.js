import React from "react";


const TableHeadRow = ({ headRowNames }) => {
    return (
      <tr>
        {headRowNames.map((rowName) => (
          <th>{rowName}</th>
        ))}
        <th>Operations</th>
      </tr>
    );
  };

  export default TableHeadRow;