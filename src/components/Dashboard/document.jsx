import * as React from "react";
import styled from "styled-components";

export function DocHeader() {
  return (
    <HeaderContainer>
      <HeaderTitle>
        <HeaderText>Document Review</HeaderText>
      </HeaderTitle>
    </HeaderContainer>
  );
}

const HeaderContainer = styled.header`
  border-radius: 16px;
  background: var(--Greay-700, #344054);
  display: flex;
  min-height: 74px;
  width: 100%;
  gap: 40px 146px;
  font-size: 18px;
  color: var(--Light-Blue-1, #d9f1ff);
  font-weight: 600;
  justify-content: start;
  padding: 16px 28px;
  @media (max-width: 991px) {
    max-width: 100%;
    padding: 0 20px;
  }
`;

const HeaderTitle = styled.div`
  display: flex;
  width: 172px;
  align-items: center;
  justify-content: start;
  height: 100%;
`;

const HeaderText = styled.h1`
  width: 172px;
  align-self: stretch;
  margin: auto 0;
  padding: 8px 2px 8px 3px;
  font-size: inherit;
  font-weight: inherit;
`;