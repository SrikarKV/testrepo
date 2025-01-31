import * as React from "react";
import styled from "styled-components";

export function ActionButtons() {
  return (
    <ButtonContainer>
      <PreviousButton>
        <ButtonIcon
          loading="lazy"
          src="https://cdn.builder.io/api/v1/image/assets/TEMP/4a6441d569098d59d9d09b2de6280a93a9ca3d9883b7e725f88eed7773ad6d20?placeholderIfAbsent=true&apiKey=d531a4b92b0e48fa95666696bb5bb565"
        />
        Previous
      </PreviousButton>
      <SubmitButton>Submit</SubmitButton>
    </ButtonContainer>
  );
}

const ButtonContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  margin-top: 32px;
`;

const Button = styled.button`
  min-height: 42px;
  padding: 0 16px;
  border-radius: 8px;
  font: 500 14px Poppins, sans-serif;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 140px;
`;

const PreviousButton = styled(Button)`
  border: 0.442px solid var(--Primary-Blue, #132f58);
  background: none;
  color: var(--Primary-Blue, #132f58);
  gap: 8px;
`;

const SubmitButton = styled(Button)`
  border: 0.442px solid rgba(255, 255, 255, 0.2);
  background: var(--Primary-Blue, #132f58);
  color: var(--white, #fff);
`;

const ButtonIcon = styled.img`
  width: 16px;
  height: 16px;
`;