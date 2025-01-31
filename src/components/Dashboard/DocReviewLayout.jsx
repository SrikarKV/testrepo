import * as React from "react";
import styled from "styled-components";
import {DocHeader} from "./document";
import { UploadOptions } from "./Upload";


export function DocumentReviewLayout() {
  return (
    <ReviewContainer>
      <BackgroundElement />
      <ContentWrapper>
        <DocHeader />
        <UploadOptions />
      </ContentWrapper>
    </ReviewContainer>
  );
}

const ReviewContainer = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  font-family: Poppins, sans-serif;
  justify-content: start;
  padding: 32px 9px 0;
`;

const BackgroundElement = styled.div`
  position: absolute;
  z-index: 0;
  display: flex;
  min-height: 0px;
  width: 1693px;
  justify-content: start;
  right: -49px;
  bottom: -201px;
  @media (max-width: 991px) {
    max-width: 100%;
  }
`;

const ContentWrapper = styled.div`
  border-radius: 16px;
  z-index: 0;
  display: flex;
  min-height: 799px;
  width: 100%;
  flex-direction: column;
  justify-content: start;
  @media (max-width: 991px) {
    max-width: 100%;
  }
`;