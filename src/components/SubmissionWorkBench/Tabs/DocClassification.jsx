import * as React from "react";
import styled from "styled-components";
import { TableComponent } from "./TableComponent";

const tableData = {
  headers: [
    { title: "Document", width: "467px" },
    { title: "Document Type", width: "483px" },
    { title: "Confidence Score", width: "312px" },
    { title: "Action", width: "262px" }
  ],
  rows: [
    {
      document: "doccument3.pdf",
      documentType: "Document_3",
      confidenceScore: "92%",
      actions: ["View", "Edit"]
    }
  ]
};

function DocClassification() {
  return (
    <div>
        <TableComponent />
    </div>
  );
}

const TableContainer = styled.div`
  border-radius: 16px;
  display: flex;
  flex-direction: column;
  justify-content: start;
  font: 500 16px Poppins, sans-serif;
`;

export default DocClassification;