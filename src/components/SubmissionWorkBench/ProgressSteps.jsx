import * as React from "react";
import styled from "styled-components";

const steps = [
  "Document Classification",
  "Document Review",
  "Document Enrichment",
  "Triage & Priortization",
  "Summary"
];

export function ProgressSteps() {
  return (
    <ProgressContainer>
      <StepsWrapper>
        {steps.map((step, index) => (
          <StepItem key={index}>
            <StepNumber>
              <NumberText>{index + 1}</NumberText>
              <NumberCircle />
            </StepNumber>
            <StepText>{step}</StepText>
            {index < steps.length - 1 && (
              <StepConnector>
                <ConnectorLine />
              </StepConnector>
            )}
          </StepItem>
        ))}
      </StepsWrapper>
    </ProgressContainer>
  );
}

const ProgressContainer = styled.div`
  border-radius: 16px;
  border: 1px solid var(--Great-300, #d0d5dd);
  background: var(--white, #fff);
  padding: 20px 40px;
  margin-top: 32px;
  @media (max-width: 991px) {
    padding: 20px;
  }
`;

const StepsWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 24px;
  flex-wrap: wrap;
`;

const StepItem = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
`;

const StepNumber = styled.div`
  position: relative;
  width: 43px;
  height: 43px;
`;

const NumberText = styled.div`
  position: absolute;
  z-index: 10;
  color: #fff;
  font: 400 16px Poppins, sans-serif;
  text-align: center;
  padding: 10px;
`;

const NumberCircle = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 34px;
  height: 34px;
  border-radius: 50%;
  background-color: var(--Primary-Blue, #132f58);
  border: 1px solid var(--Primary-Blue-2, #1351ab);
`;

const StepText = styled.div`
  color: var(--Primary-Blue, #132f58);
  font: 600 14px Poppins, sans-serif;
`;

const StepConnector = styled.div`
  flex: 1;
  height: 7px;
  border-radius: 50.897px;
  background: var(--Great-300, #d0d5dd);
`;

const ConnectorLine = styled.div`
  width: 100%;
  height: 100%;
  border-radius: 50.897px;
  background: var(--Primary-Blue, #132f58);
`;