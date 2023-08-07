import { styled, useTheme } from "styled-components";
import { Icon } from "../Icon";

type AssigneeElementProps = {
  name: string;
  profile?: string;
};

export function AssigneeElement({ name, profile }: AssigneeElementProps) {
  const theme = useTheme();

  return (
    <Div>
      {profile ? (
        <img src={profile} alt="" />
      ) : (
        <Icon name="userImageSmall" fill={theme.color.neutralSurfaceBold} />
      )}
      <Text>{name}</Text>
    </Div>
  );
}

const Div = styled.div`
  display: flex;
  width: 100%;
  justify-content: flex-start;
  align-items: center;
  gap: 8px;
`;

const Text = styled.span`
  flex-grow: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  font: ${({ theme }) => theme.font.displayMedium12};
  color: ${({ theme }) => theme.color.neutralTextStrong};
`;