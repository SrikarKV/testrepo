import * as React from "react";
import styled from "styled-components";

export function UploadOptions() {
  const uploadOptions = [
    { id: "device", label: "Upload from device" },
    { id: "existing", label: "Select From Existing File" }
  ];

  return (
    <UploadContainer>
      <UploadTitle>Select how you Want to Upload file ?</UploadTitle>
      <OptionsWrapper>
        {uploadOptions.map((option) => (
          <RadioOption key={option.id}>
            <input
              type="radio"
              id={option.id}
              name="uploadOption"
              className="visually-hidden"
            />
            <RadioButton aria-hidden="true" />
            <label htmlFor={option.id}>{option.label}</label>
          </RadioOption>
        ))}
      </OptionsWrapper>
    </UploadContainer>
  );
}

const UploadContainer = styled.div`
  justify-content: center;
  align-items: center;
  border-radius: 16px;
  border: 1px solid var(--Great-300, #d0d5dd);
  background: var(--Light-Blue-2, #eef9ff);
  display: flex;
  margin-top: 32px;
  min-height: 119px;
  width: 100%;
  flex-direction: column;
  padding: 22px 28px;
  @media (max-width: 991px) {
    max-width: 100%;
    padding: 0 20px;
  }
`;

const UploadTitle = styled.h2`
  color: var(--Blue-Great-950, #0c111d);
  font-size: 24px;
  font-weight: 600;
  @media (max-width: 991px) {
    max-width: 100%;
  }
`;

const OptionsWrapper = styled.div`
  display: flex;
  margin-top: 16px;
  width: 640px;
  max-width: 100%;
  align-items: start;
  gap: 32px;
  font-size: 16px;
  color: var(--Base-Black, #000);
  font-weight: 400;
  justify-content: center;
  flex-wrap: wrap;
`;

const RadioOption = styled.div`
  display: flex;
  align-items: center;
  gap: 5px;
  justify-content: start;
`;

const RadioButton = styled.span`
  border-radius: 50px;
  border: 1px solid var(--base-gray-400-default, #98a2b3);
  background: var(--Base-White, #fff);
  display: flex;
  width: 16px;
  height: 16px;
  margin: auto 0;
`;