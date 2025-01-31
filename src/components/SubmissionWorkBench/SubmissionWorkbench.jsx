import { faAngleLeft, faAngleRight } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import styled from "styled-components";
import { ActionButtons } from "./ActionButtons";
import { InformationCard } from "./InformationCard";
import DocClassification from "./Tabs/DocClassification";
import DocEnreachment from "./Tabs/DocEnreachment";
import { DocReview } from "./Tabs/DocReview";
import { SummarySection } from "./Tabs/SummarySection";
import Triag from "./Tabs/Triag";


const steps = [
  { name: "Document Classification", component: <DocClassification /> },
  { name: "Document Review", component: <DocReview /> },
  { name: "Document Enrichment", component: <DocEnreachment /> },
  { name: "Triage & Prioritization", component: <Triag /> },
  { name: "Summary", component: <SummarySection /> },
];

export default function SubmissionWorkbench() {
  const [activeStep, setActiveStep] = useState(0);
  const navigate = useNavigate();
  const handleNext = () => {
    if (activeStep < steps.length - 1) {
      setActiveStep(activeStep + 1);
    }
  };

  const handlePrevious = () => {
    if (activeStep > 0) {
      setActiveStep(activeStep - 1);
    }
  };

  return (
    <div className="LayoutSection">
    <WorkbenchContainer>
      <MainContent>
        <BackButton onClick={() => {navigate('/dashboard')}}>
          <BackIcon
            loading="lazy"
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/3ae543105da98424522d946ff29ba531bf1298f8034a90ec106bca349f6106e7"
          />
          <BackText>Back to Create New Submission</BackText>
        </BackButton>
        
        <HeaderSection>
          <WorkbenchTitle>Submission Workbench</WorkbenchTitle>
          <div style={{marginTop:"1rem"}}><hr/></div>
          <InformationCard />
        </HeaderSection>

        {/* Progress Tabs */}
        <div style={{display:'flex', flexDirection:'column', gap:'2rem'}}>
        <ProgressContainer>
          <StepsWrapper>
            {steps.map((step, index) => (
              <StepItem key={index} onClick={() => setActiveStep(index)}>
                <StepNumber className={index === activeStep ? "active" : ""}>
                  <NumberText>{index + 1}</NumberText>
                  <NumberCircle isActive={index === activeStep} />
                </StepNumber>
                <StepText isActive={index === activeStep}>{step.name}</StepText>
                {index < steps.length - 1 && <StepConnector />}
              </StepItem>
            ))}
          </StepsWrapper>
        </ProgressContainer>

        <div className="LabelTab">
          {steps[activeStep].name}
        </div>

        {/* Tab Content */}
        <div className="SelectTabs">
          {steps[activeStep].component}
        </div>

        {/* Navigation Buttons */}
        <div className="buttonsGroup">
          <button className="btn-Primary-Neutral" onClick={handlePrevious} disabled={activeStep === 0}>
            <span><FontAwesomeIcon icon={faAngleLeft} /></span> Previous
          </button>
          {activeStep < steps.length - 1 ? (
            <button className="btn-Primary" onClick={handleNext}>
              Next <FontAwesomeIcon icon={faAngleRight} />
            </button>
          ) : (
            <button className="btn-Primary-Submit">
              Submit
            </button>
          )}
        </div>

        <ActionButtons />
        </div>
        
      </MainContent>
    </WorkbenchContainer>
    </div>
  );
}

// Styled Components
export const WorkbenchContainer = styled.div`
  display: flex;
  gap: 28px;
  @media (max-width: 991px) {
    padding-right: 20px;
  }
`;

export const MainContent = styled.main`
  flex: 1;
  @media (max-width: 991px) {
    max-width: 100%;
  }
`;

export const BackButton = styled.button`
  display: flex;
  align-items: center;
  gap: 8px;
  border: none;
  background: none;
  padding: 8px;
  cursor: pointer;
  border-bottom: 1px solid var(--Blue-Grey-900, #101828);
`;

export const BackIcon = styled.img`
  width: 16px;
  height: 16px;
`;

export const BackText = styled.span`
  color: var(--Blue-Grey-900, #101828);
  font: 400 12px Poppins, sans-serif;
`;

export const HeaderSection = styled.div`
  margin-top: 32px;
`;

export const WorkbenchTitle = styled.h1`
  color: var(--Blue-Grey-900, #101828);
  font: 600 24px Poppins, sans-serif;
  margin: 0;
`;

export const HeaderDivider = styled.img`
  width: 100%;
  height: 1px;
  margin-top: 16px;
`;

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
  cursor: pointer;
`;

const StepNumber = styled.div`
  position: relative;
  width: 43px;
  height: 43px;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const NumberText = styled.div`
  position: absolute;
  z-index: 10;
  color: #fff;
  font: 400 16px Poppins, sans-serif;
  text-align: center;
`;

const NumberCircle = styled.div`
  width: 34px;
  height: 34px;
  border-radius: 50%;
  background-color: ${({ isActive }) =>
    isActive ? "#1351ab" : "var(--Primary-Blue, #132f58)"};
  border: 1px solid var(--Primary-Blue-2, #1351ab);
`;

const StepText = styled.div`
  color: ${({ isActive }) => (isActive ? "#1351ab" : "#132f58")};
  font: 600 14px Poppins, sans-serif;
`;

const StepConnector = styled.div`
  flex: 1;
  height: 7px;
  border-radius: 50.897px;
  background: var(--Great-300, #d0d5dd);
`;

const buttonsGroup = styled.div`
  display: flex;
  gap: 16px;
  margin-top: 20px;
`;

const previousButton = styled.button`
  background-color: #f1f1f1;
  border: none;
  padding: 10px 20px;
  font-size: 14px;
  cursor: pointer;
`;

const nextButton = styled.button`
  background-color: #1351ab;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 14px;
  cursor: pointer;
`;

const submitButton = styled.button`
  background-color: #28a745;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 14px;
  cursor: pointer;
`;


