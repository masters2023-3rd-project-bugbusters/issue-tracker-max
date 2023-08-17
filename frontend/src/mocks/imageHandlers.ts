import { rest } from "msw";
import { users } from "./handlers";

export const imageHandlers = [
  rest.post("/api/images", (req, res, ctx) => {
    const authorizationToken = req.headers.get("authorization")!.split(" ")[1];

    const user = users.find(
      ({ jwt }) => jwt.accessToken === authorizationToken,
    );

    if (!user) {
      const unauthorizedError = {
        code: 401,
        status: "UNAUTHORIZED",
        message: "인증에 실패하였습니다.",
        data: null,
      };

      return res(ctx.status(401), ctx.json(unauthorizedError));
    }

    const response = {
      code: 201,
      status: "OK",
      message: "이미지 저장에 성공하였습니다.",
      data: {
        url: images[Math.floor(Math.random() * images.length)],
      }
    }

    return res(ctx.status(201), ctx.json(response));
  })
]

const images = [
  "https://storage.enuri.info/pic_upload/knowbox/mobile_img/202101/2021011317170539419.gif",
  "https://mblogthumb-phinf.pstatic.net/MjAyMDAzMTVfMjY0/MDAxNTg0MjU5ODgyNTQ5.Uwz1guxMV9SVXLW7iDEDmtlbVstumgn84AeSu42rjyMg.WcCNq2ZO5hFqcV8y9RbyiBNCW0TNSXAZk5co7rpnv7Qg.JPEG.se___ovo/%EC%BB%B4%ED%93%A8%ED%84%B0%EB%B0%B0%EA%B2%BD%ED%99%94%EB%A9%B4%EC%9E%90%EC%97%B002.jpg?type=w800",
  "data:image/jpeg;base64,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",
  "https://t1.daumcdn.net/cfile/tistory/99E3B33E5B2852742A",
  "https://t1.daumcdn.net/cfile/tistory/99308E3359D49CCC1D"
]