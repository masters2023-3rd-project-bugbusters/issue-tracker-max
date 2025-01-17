import { styled } from "styled-components";
import { Icon } from "../../components/icon/Icon";
import { LabelData } from "./Label";
import { LabelTableElement } from "./LabelTableElement";

export function LabelTable({
  labels,
  fetchData,
}: {
  labels: LabelData[];
  fetchData: () => void;
}) {
  return (
    <Div>
      <TableHeader>{labels.length}개의 레이블</TableHeader>
      <TableBody>
        {labels.map((label, index) => (
          <LabelTableElement fetchData={fetchData} key={index} label={label} />
        ))}
        {!labels.length && (
          <NoneElement>
            <Icon name="AlertCircle" />
            <span>레이블이 비어 있습니다.</span>
          </NoneElement>
        )}
      </TableBody>
    </Div>
  );
}

const Div = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  border: 1px solid ${({ theme }) => theme.color.neutralBorderDefault};
  border-radius: ${({ theme }) => theme.radius.large};
  background: ${({ theme }) => theme.color.neutralSurfaceStrong};
`;

const TableHeader = styled.div`
  display: flex;
  height: 64px;
  padding: 20px 32px;
  box-sizing: border-box;
  align-items: center;
  align-self: stretch;
  color: ${({ theme }) => theme.color.neutralTextDefault};
  font: ${({ theme }) => theme.font.displayBold16};
  background: ${({ theme }) => theme.color.neutralSurfaceDefault};
  border-top-right-radius: ${({ theme }) => theme.radius.large};
  border-top-left-radius: ${({ theme }) => theme.radius.large};
`;

const TableBody = styled.div`
  width: 100%;
`;

const NoneElement = styled.div`
  width: 100%;
  height: 96px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;
  font: ${({ theme }) => theme.font.displayBold16};
  color: ${({ theme }) => theme.color.neutralTextStrong};
  border-top: solid 1px ${({ theme }) => theme.color.neutralBorderDefault};
`;
