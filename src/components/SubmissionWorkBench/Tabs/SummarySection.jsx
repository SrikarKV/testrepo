import * as React from "react";
import styled from "styled-components";

const summaryData = {
  program: {
    title: "Program Information",
    fields: [
      { label: "Line of Business", value: "Property" },
      { label: "Request Type", value: "New Submission" },
      { label: "Quote Due Date", value: "03/24/25" },
      { label: "Program Effective Date", value: "10/31/2024" },
      { label: "Entity", value: "ABC Group" }
    ]
  },
  entity: {
    title: "Entity Information",
    fields: [
      { label: "DUNS Number", value: "060704780" },
      { label: "D&B Standard Industry Description", value: "Electronic Computer Manufacturing" },
      { label: "D&B Standard Industry Code", value: "334111" },
      { label: "NAICS Description", value: "Radio Wireless Equipment Manufacture" },
      { label: "NAICS", value: "334220" },
      { label: "SIC Code Description", value: "Computer Terminal Equipment Manufacture" },
      { label: "SIC Code", value: "33" }
    ]
  },
  triage: {
    title: "Triage Information",
    fields: [
      { label: "Days until Quote is Due", value: "60 Days" },
      { label: "Commercial Quality Score", value: "51.00" },
      { label: "Risk Quality Score", value: "42.50" },
      { label: "Complexity", value: "None" },
      { label: "Total Triage Score", value: "46.75" }
    ]
  },
  underwriter: {
    title: "Underwriter Information",
    fields: [
      { label: "Underwriter Name", value: "John Doe" },
      { label: "Underwriter Detail", value: "None" }
    ]
  },
  broker: {
    title: "Broker Information",
    fields: [
      { label: "Broker Name", value: "Alpha Brokers" },
      { label: "Broker Email", value: "john@gmail.com" },
      { label: "Broker Phone", value: "None" },
      { label: "Broker Physical Address", value: "None" }
    ]
  }
};

export function SummarySection() {
  return (
    <><div className='PrimaryTile'>
      <div className='XsmallLabel'>
        <span className='warningText'>Instruction: </span> The Summary Report page provides a comprehensive overview of
        submission details,including program,entity,triage,underwriter and broker information,summarizing essential data
        needed for decision xmaking and report generation
      </div>
    </div><SummaryContainer>
        <SummaryHeader>
          <SummaryTitle>Summary Report</SummaryTitle>
          <ActionGroup>
            <ActionButton>Export to PDF</ActionButton>
            <ActionButton>Send to Underwriting</ActionButton>
            <ActionButton>Run To CLearance</ActionButton>
          </ActionGroup>
        </SummaryHeader>
        <ContentGrid>
          {Object.values(summaryData).map((section, index) => (
            <InfoCard key={index}>
              <CardTitle>{section.title}</CardTitle>
              <CardContent>
                <FieldList>
                  {section.fields.map((field, fieldIndex) => (
                    <FieldItem key={fieldIndex}>
                      <FieldLabel>{field.label}</FieldLabel>
                      <FieldValue>{field.value}</FieldValue>
                    </FieldItem>
                  ))}
                </FieldList>
              </CardContent>
            </InfoCard>
          ))}
        </ContentGrid>
      </SummaryContainer></>
  );
}

const SummaryContainer = styled.section`
  margin-top: 32px;
`;

const SummaryHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 28px;
  background: var(--Greay-700, #344054);
  border-radius: 16px;
  @media (max-width: 991px) {
    flex-direction: column;
    gap: 16px;
    padding: 16px;
  }
`;

const SummaryTitle = styled.h2`
  color: var(--Light-Blue-1, #d9f1ff);
  font: 600 18px Poppins, sans-serif;
  margin: 0;
`;

const ActionGroup = styled.div`
  display: flex;
  gap: 24px;
  @media (max-width: 991px) {
    flex-wrap: wrap;
  }
`;

const ActionButton = styled.button`
  color: var(--Light-Blue-1, #d9f1ff);
  font: 500 14px Poppins, sans-serif;
  background: none;
  border: none;
  cursor: pointer;
  padding: 8px 16px;
  border-radius: 8px;
  transition: background-color 0.2s;

  &:hover {
    background-color: rgba(255, 255, 255, 0.1);
  }
`;

const ContentGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 32px;
  margin-top: 32px;
`;

const InfoCard = styled.div`
  border: 1px solid var(--Great-300, #d0d5dd);
  border-radius: 16px;
  padding: 24px;
`;

const CardTitle = styled.h3`
  color: var(--Primary-Blue-2, #1351ab);
  font: 600 18px Poppins, sans-serif;
  margin: 0 0 16px 0;
`;

const CardContent = styled.div`
  padding: 16px 0;
`;

const FieldList = styled.dl`
  margin: 0;
  display: grid;
  gap: 8px;
`;

const FieldItem = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
`;

const FieldLabel = styled.dt`
  color: var(--Blue-Grey-900, #101828);
  font: 500 14px Poppins, sans-serif;
`;

const FieldValue = styled.dd`
  color: var(--Blue-Grey-900, #101828);
  font: 400 14px Poppins, sans-serif;
  margin: 0;
`;