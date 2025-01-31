import * as React from "react";
import styled from "styled-components";

export function TableComponent() {
  // Sample data for headers and rows
  const headers = ["Document", "Document Type", "Confidence Score", "Actions"];

  const data = [
    {
      document: "Document 1",
      documentType: "Invoice",
      confidenceScore: "95%",
      actions: ["View", "Edit"],
    },
    {
      document: "Document 2",
      documentType: "Receipt",
      confidenceScore: "88%",
      actions: ["View", "Edit"],
    },
    {
      document: "Document 3",
      documentType: "Contract",
      confidenceScore: "75%",
      actions: ["View", "Edit"],
    },
  ];

  return (
    <Table>
      <thead>
        <HeaderRow>
          {headers.map((header, index) => (
            <HeaderCell key={index}>{header}</HeaderCell>
          ))}
        </HeaderRow>
      </thead>
      <tbody>
        {data.map((row, rowIndex) => (
          <Row key={rowIndex}>
            <DocumentCell>{row.document}</DocumentCell>
            <DataCell>{row.documentType}</DataCell>
            <DataCell>{row.confidenceScore}</DataCell>
            <ActionCell>
              {row.actions.map((action, actionIndex) => (
                <ActionLink key={actionIndex} tabIndex={0} role="button">
                  {action}
                </ActionLink>
              ))}
            </ActionCell>
          </Row>
        ))}
      </tbody>
    </Table>
  );
}

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  border-radius: 16px;
`;

const HeaderRow = styled.tr`
  border-top-right-radius: 1rem;
  border-top-left-radius: 1rem;
  background: #e4e7ec;
  font-weight: bold;
  text-align: left;
`;

const HeaderCell = styled.th`
  padding: 15px;
  border-bottom: 1px solid #344054;
`;

const Row = styled.tr`
  &:nth-child(even) {
    background: #f8f9fa;
  }
`;

const BaseCell = styled.td`
  padding: 15px;
  border-bottom: 1px solid #ddd;
  text-align: left;
`;

const DocumentCell = styled(BaseCell)`
  color: #1351ab;
  text-decoration: underline;
`;

const DataCell = styled(BaseCell)`
  white-space: nowrap;
`;

const ActionCell = styled(BaseCell)`
  display: flex;
  gap: 16px;
  color: #116de4;
  align-items: center;
`;

const ActionLink = styled.div`
  text-decoration: underline;
  cursor: pointer;
  &:focus {
    outline: 2px solid #116de4;
    outline-offset: 2px;
  }
`;
