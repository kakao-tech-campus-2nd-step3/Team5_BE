# Team5_BE
5조 백엔드

# week6 코드리뷰 질문

## 도기헌

1. 현재 영상 처리쪽은 파이썬의 fastapi로 개발하고 있습니다. 이때 영상 처리가 끝나면 유저 이메일로 결과를 알려주려고 합니다. 그래서 처음엔 지메일 smtp를 사용했는데
   브라우저에 로그인 해야 smtp를 사용할 수 있다는 것을 알았습니다. 이런 경우에서는 나중에 배포했을 때 제대로 이메일이 가지 않을 것 같은데 혹시 배포했을 때도 따로 설정
   없이 사용가능한 이메일 전송 방법이 있을지 궁금합니다.
2. 영상 처리 쪽으로 요청을 보내면 영상 처리를 시작하는데, 이때 영상 처리를 백그라운드에서 돌려 비동기적으로 요청을 처리하게 만들었습니다. 그런데 또 생각해보니 배포할 서버의
   성능을 고려하여 asyncio를 사용하여 처리할 수 있는 요청을 제한하게 하려 하였는데, 기존에 비동기적으로 작동하는 코드에 asyncio를 적용하면

```python
import asyncio
from fastapi import BackgroundTasks, FastAPI, Response

app = FastAPI()

# 동시 요청 최대 5개로 제한
semaphore = asyncio.Semaphore(5)

@app.post("/extract-subtitle")
async def extract_subtitle(dto: SubtitleAdderDto, background_tasks: BackgroundTasks, response: Response):
    async with semaphore:
        background_tasks.add_task(process_subtitle, dto)
        response.status_code = 200
        return {"message": "Processing started"}
```

인데, 위 코드에서는 백그라운드로 돌려서 일단 요청을 처리하기 때문에 크게 의미가 없어집니다. 그래서 또 백그라운드로 돌리는 방식을 선택하지 않으면 요청에서 timeout이
발생합니다. 그러면 결국 정리해보자면
`요청을 그대로 받는다 -> timeout 발생, 백그라운드 실행으로 해결 -> 동시 요청 제한이 의미가 없어짐`
인데 어떤 식으로 해결할 수 있을지 조언을 받고 싶습니다. 감사합니다!

## 신성민

- 현재 쇼츠 동영상 추천 알고리즘을 자체 설계할 계획입니다. 활용 가능한 input 데이터로는 전체 조회수, 전체 좋아요 수, 유저의 시청여부, 유저의 좋아요 여부, 유저의 선호
  카테고리, 유저의 성별, 유저의 연령대 정도가 있는 것 같습니다.
- 머신러닝 모델을 학습시키기에는 데이터가 턱없이 부족해서 성능을 기대하기 어렵다보니.. 간단한 알고리즘 함수를 만들어서 추천 영상 리스트를 output으로 반환해야 할 것
  같습니다.
- 현재 생각한 간단한 알고리즘 모델이 row는 video, column은 user로 된 2차원 행렬를 구성하여, input들에 가중치를 적절히 조정한 뒤 모두 합산하여 각 (
  row,column) 별로 점수를 매긴 뒤에, 한 user column에서 가장 점수가 높은 상위 n개의 video를 리스트로 리턴하는 방식을 생각하고 있습니다.
- |  | userA | userB | … |
    | --- | --- | --- | --- |
  | videoA | 0.999 | 0.111 |  |
  | videoB | 0.666 | - |  |
  | videoC | - | 0.444 |  |
  | … |  |  |  |
- 다만 input별로 가중치를 어떻게 조절할지, 2차원 행렬 자체를 NoSQL 같은 저장소에 보관해야할지(매번 column의 모든 video에 대해 계산을 새롭게 수행하는 것은
  비효율적이고 각 user 별로 시청 목록이 상이하므로), 그렇다면 동적으로 변하는 input 값들을 어떻게 관리해야할지 등 여러 이슈들이 존재하는 것 같습니다. 멘토님께 추천
  알고리즘 설계 방향성에 대한 조언을 구하고 싶습니다!
