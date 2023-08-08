import { useEffect, useState } from "react";
import styled from "styled-components";
import { Icon, IconType } from "./icon/Icon";

type TextInputProps = {
  width?: number;
  size: "L" | "S";
  value?: string;
  placeholder?: string;
  label?: string;
  caption?: string;
  icon?: keyof IconType;
  fixLabel?: boolean;
  borderNone?: boolean;
  disabled?: boolean;
  isError?: boolean;
  maxLength?: number;
  type?: React.HTMLInputTypeAttribute;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  onFocus?: () => void;
  onBlur?: () => void;
};

type TextInputState = "Enabled" | "Active" | "Disabled" | "Error";

type InputProps = {
  $state: TextInputState;
};

type InputContainerProps = {
  $width: number;
  $size: "L" | "S";
  $borderNone?: boolean;
  $state: TextInputState;
};

export function TextInput({
  width = 254,
  size,
  value: initialValue = "",
  label,
  placeholder,
  caption,
  icon,
  fixLabel = false,
  borderNone = false,
  disabled = false,
  isError = false,
  maxLength,
  type,
  onChange,
  onFocus,
  onBlur,
}: TextInputProps) {
  const [state, setState] = useState<TextInputState>(
    disabled ? "Disabled" : "Enabled",
  );
  const [isFocused, setIsFocused] = useState(false);

  useEffect(() => {
    setState(isError ? "Error" : isFocused ? "Active" : "Enabled");
  }, [isError, isFocused]);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    onChange && onChange(event);
  };

  const handleInputFocus = () => {
    if (state !== "Error") {
      setState("Active");
    }

    setIsFocused(true);
    onFocus && onFocus();
  };

  const handleInputBlur = () => {
    if (state !== "Error") {
      setState(disabled ? "Disabled" : "Enabled");
    }

    setIsFocused(false);
    onBlur && onBlur();
  };

  return (
    <Div $state={state} $width={width}>
      <InputContainer
        $width={width}
        $size={size}
        $state={state}
        $borderNone={borderNone}
      >
        {(fixLabel || initialValue) && label && (
          <StyledSpan>{label}</StyledSpan>
        )}
        {icon && (
          <IconWrapper>
            <Icon name={icon} color="neutralTextDefault" />
          </IconWrapper>
        )}
        <Input
          placeholder={placeholder}
          value={initialValue}
          maxLength={maxLength}
          onChange={handleInputChange}
          onFocus={handleInputFocus}
          onBlur={handleInputBlur}
          disabled={state === "Disabled"}
          $state={state}
          type={type}
        />
      </InputContainer>
      {caption && <Caption $state={state}>{caption}</Caption>}
    </Div>
  );
}

const Div = styled.div<{ $state: TextInputState; $width: number }>`
  display: flex;
  width: ${({ $width }) => $width}px;
  flex-direction: column;
  align-items: flex-start;
  gap: 4px;
  flex-shrink: 0;
  opacity: ${(props) => (props.$state === "Disabled" ? 0.2 : 1)};
  background: ${({ theme: { color } }) => color.grayScale50};
`;

const InputContainer = styled.div<InputContainerProps>`
  display: flex;
  width: ${({ $width }) => $width}px;
  height: ${({ $size }) => ($size === "L" ? "56px" : "40px")};
  padding: 5px 16px;
  align-self: stretch;
  flex-direction: ${({ $size }) => ($size === "L" ? "column" : "")};
  align-items: ${({ $size }) => ($size === "L" ? "flex-start" : "center")};
  justify-content: center;
  border-radius: ${({ theme }) => theme.radius.large};
  box-sizing: border-box;
  background: ${({ $state, theme }) => {
    switch ($state) {
      case "Enabled":
        return theme.color.neutralSurfaceBold;
      case "Active":
      case "Error":
        return theme.color.neutralSurfaceStrong;
      default:
        return theme.color.neutralSurfaceBold;
    }
  }};
  border: ${({ theme, $state, $borderNone }) => {
    if ($borderNone) {
      return "none";
    }
    switch ($state) {
      case "Active":
        return `1px solid ${theme.color.neutralBorderDefaultActive}`;
      case "Error":
        return `1px solid ${theme.color.dangerBorderDefault}`;
      default:
        return "none";
    }
  }};
`;

const StyledSpan = styled.span`
  min-width: 64px;
  color: ${({ theme }) => theme.color.neutralTextWeak};
  font: ${({ theme }) => theme.font.displayMedium12};
`;

const IconWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 32px;
`;

const Input = styled.input<InputProps>`
  width: 100%;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  border: none;
  &:focus {
    outline: none;
  }
  font: ${({ theme }) => theme.font.displayMedium16};
  color: ${({ theme, $state }) => {
    switch ($state) {
      case "Enabled":
      case "Disabled":
      case "Error":
        return theme.color.neutralTextDefault;
      case "Active":
        return theme.color.neutralTextStrong;
      default:
        return theme.color.neutralTextDefault;
    }
  }};
  background: none;
  &::placeholder {
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
  &:-ms-input-placeholder {
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
  &::-ms-input-placeholder {
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
  &::-webkit-input-placeholder {
    color: ${({ theme }) => theme.color.neutralTextWeak};
  }
`;

const Caption = styled.span<{ $state: TextInputState }>`
  display: flex;
  padding-left: 0px;
  align-items: flex-start;
  align-self: stretch;
  padding-left: 16px;
  color: ${({ theme, $state }) =>
    $state === "Error"
      ? theme.color.dangerTextDefault
      : theme.color.neutralTextWeak};
  font: ${({ theme }) => theme.font.displayMedium12};
`;
