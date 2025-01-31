import * as React from "react";
import styled from "styled-components";

export function NavigationSidebar() {
  return (
    <Sidebar>
      <NavContainer>
        <TopIcon>
          <IconWrapper>
            <Icon
              loading="lazy"
              src="https://cdn.builder.io/api/v1/image/assets/TEMP/3c05ca8bad6faa2670210e7cd34744366efe348fb410bf91c9e8ce1b20769585?placeholderIfAbsent=true&apiKey=d531a4b92b0e48fa95666696bb5bb565"
            />
          </IconWrapper>
        </TopIcon>
        <Spacer />
        <NavIcons>
          <Icon
            loading="lazy"
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/ab26d7fca71b6392fad9c9f111ee5ff00936fdbb269c2487c3d1599bbacbc1ec?placeholderIfAbsent=true&apiKey=d531a4b92b0e48fa95666696bb5bb565"
          />
          <Icon
            loading="lazy"
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/202f1fa94a2ee01e8c34578ea25ab29477c6cfc8e6838fd3a83cd2a43eddebc4?placeholderIfAbsent=true&apiKey=d531a4b92b0e48fa95666696bb5bb565"
          />
          <Icon
            loading="lazy"
            src="https://cdn.builder.io/api/v1/image/assets/TEMP/7d96a848308891b3ef5f9c48f3191ca67f452b82542f053d854a9e60961deafe?placeholderIfAbsent=true&apiKey=d531a4b92b0e48fa95666696bb5bb565"
          />
        </NavIcons>
      </NavContainer>
    </Sidebar>
  );
}

const Sidebar = styled.nav`
  background: var(--white, #fff);
  width: 62px;
  padding: 8px;
  @media (max-width: 991px) {
    display: none;
  }
`;

const NavContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 8px 0;
  width: 46px;
`;

const TopIcon = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
`;

const IconWrapper = styled.div`
  display: flex;
  width: 46px;
`;

const Icon = styled.img`
  width: 24px;
  height: 24px;
  object-fit: contain;
`;

const Spacer = styled.div`
  height: 16px;
  margin-top: 16px;
`;

const NavIcons = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 28px;
  margin-top: 16px;
`;