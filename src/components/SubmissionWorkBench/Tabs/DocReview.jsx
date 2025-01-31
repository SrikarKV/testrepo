import * as React from "react";
import styled from "styled-components";
import './Tabs.css';

export function DocReview() {
  const headers = ["Field Name", "Value", "Action"];
  const data = [
    { fieldName: "Insured Name", value: "Acme Corp" },
    { fieldName: "Date", value: "2024-01-30" },
    { fieldName: "Amount", value: "$150.00" },
  ];

  const [selectedRow, setSelectedRow] = React.useState(null);

  return (
    <Container>
      <div className='file-wrapper' style={{backgroundColor:'#EEF9FF', padding:'1rem'}}>
        <span>Select Document</span>
        <div className="file-input-container" >
          <input type="text"  placeholder="No file chosen" readOnly  />
          <input type="file" id="fileUpload" hidden />
          <button className="btn-Primary">Show Document</button>
        </div>
      </div>
      <div style={{display:'flex', gap:'1rem'}}>
        <TableContainer>
          <Table>
            <HeaderRow>
              {headers.map((header, index) => (
                <HeaderCell key={index}>{header}</HeaderCell>
              ))}
            </HeaderRow>
            {data.map((row, rowIndex) => (
              <Row key={rowIndex}>
                <DataCell>{row.fieldName}</DataCell>
                <DataCell>{row.value}</DataCell>
                <ActionCell>
                  <ActionLink
                    tabIndex={0}
                    role="button"
                    onClick={() => setSelectedRow(row)}
                  >
                    View
                  </ActionLink>
                </ActionCell>
              </Row>
            ))}
          </Table>
        </TableContainer>
        {selectedRow && (
          <DetailsPanel>
            <CloseIcon onClick={() => setSelectedRow(null)}>&times;</CloseIcon>
            <h3>Details</h3>
            <p><strong>Field Name:</strong> {selectedRow.fieldName}</p>
            <p><strong>Value:</strong> {selectedRow.value}</p>
          </DetailsPanel>
        )}
      </div>
    </Container>
  );
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const TableContainer = styled.div`
  flex: 1;
`;

const Table = styled.div`
  width: 100%;
  border-radius: 16px;
`;

const HeaderRow = styled.div`
  display: flex;
  min-height: 58px;
  width: 100%;
  border-top-right-radius: 1rem;
  border-top-left-radius: 1rem;
  background: #e4e7ec;
  color: #1351ab;
`;

const HeaderCell = styled.div`
  flex: 1;
  padding: 10px 20px;
  border-bottom: 1px solid #132f58;
  font-weight: bold;
  text-align: left;
`;

const Row = styled.div`
  display: flex;
  min-height: 58px;
  width: 100%;
  background: white;
  color: #0c111d;
  border-bottom: 0.5px solid #344054;
`;

const DataCell = styled.div`
  flex: 1;
  padding: 10px 20px;
`;

const ActionCell = styled(DataCell)`
  color: #116de4;
`;

const ActionLink = styled.div`
  text-decoration: none;
  cursor: pointer;
  &:focus {
    color: black;
  }
`;

const DetailsPanel = styled.div`
  width: 50%;
  background: #f4f4f4;
  padding: 20px;
  border-radius: 10px;
  position: relative;
  transition: all 0.3s ease-in-out;
`;

const CloseIcon = styled.span`
  position: absolute;
  top: 10px;
  right: 15px;
  font-size: 24px;
  cursor: pointer;
  &:hover {
    color: red;
  }
`;
