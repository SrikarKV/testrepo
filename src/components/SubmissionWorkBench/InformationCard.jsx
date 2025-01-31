import * as React from "react";
import styled from "styled-components";

const cardData = [
  { label: "Name of Insured", value: "ABC Group" },
  { label: "Case ID", value: "SUB-1151" },
  { label: "Request Type", value: "New Submission" },
  { label: "Days Until Quote is Due", value: "60 days" }
];

export function InformationCard() {
  return (
    <CardContainer>
      {cardData.map((item, index) => (
        <InfoItem key={index}>
          <Label>{item.label}</Label>
          <Value>{item.value}</Value>
        </InfoItem>
      ))}
    </CardContainer>
  );
}

const CardContainer = styled.div`
  display: flex;
  margin-top: 16px;
  gap: 56px;
  padding: 16px 0;
  flex-wrap: wrap;
`;

const InfoItem = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
`;

const Label = styled.span`
  color: var(--Greay-700, #344054);
  font: 500 12px Poppins, sans-serif;
`;

const Value = styled.span`
  color: var(--Blue-Grey-900, #101828);
  font: 600 18px Poppins, sans-serif;
`;