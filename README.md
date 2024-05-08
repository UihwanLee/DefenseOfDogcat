# SmartPhoneGameProject
 TUK 스마트폰 게임 프로젝트 

 ## 게임 이름 
 > 디펜스 오브 도그캣 (Defence of Dogcat)

## 개발 컨셉
![22](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/bd273d7d-308c-401d-afcc-811823fb6d85)

 * high_concept: 2011년 안드로이드용 국산 게임으로 출시된 '팔라독' 게임 컨셉을 기반으로 한 디펜스 게임
 * 핵심 매커니즘: 다양한 유닛을 소환하여 적의 기지를 부셔 각기 다른 난이도의 스테이즈를 클리어하자!

  
## 개발 범위

![49d88598a18746f88bc4f381b3044b1f-1](https://github.com/UihwanLee/SmartPhoneGameProject/assets/36596037/ebad0b78-cf9f-4d57-8807-eaf640ece2ae)

## 유닛

> 플레이어

|Type|Player|
|---|:-----:|
|Image|![dogcat_idle](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/ff7a1e39-86a0-47af-afd1-bd47483ca33d)|
|HP|100|
|ATK|20|

* 플레이어는 조이스틱 움직임을 통해 상하좌우 이동할 수 있다.
* 플레이어는 공력력은 20이고, 다른 스킬은 따로 없다.
* 플레이어가 죽을 시 게임이 종료된다.


> 아군 유닛

|Type|rat|rabbit|bear|tutrle|rhinoceros|penguin|dragon|
|---|:----:|:----:|:----:|:----:|:----:|:----:|:----:|
|Image|![ui_ally_01_rat](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/9c81a555-24f4-47d3-a0b2-dc4a21b8b62c)|![ui_ally_02_rabbit](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/0d31a63d-08da-4100-89b0-12823e2399fd)|![ui_ally_03_bear](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/7770bd0d-c5ea-4a9a-89aa-b6713ca742ca)|![ui_ally_04_turtle](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/1d8f5cbf-40ac-480c-8b83-c1496a4efee7)|![ui_ally_05_rhinoceros](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/b988f3d4-c17e-400f-a6bc-937ddfcea6bf)|![ui_ally_06_penguin](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/4c85b1d9-f89f-4e09-ad14-cc234c2ade4e)|![ui_ally_07_dragon](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/311ed828-848d-4400-910a-779fdb30659b)|
|HP|20|15|40|50|70|90|150|
|ATK|5|8|10|20|50|60|100|
|COST|10|20|30|50|100|150|200|
|Feat|기본유닛|기본유닛|기본유닛|기본유닛|기본유닛|추가유닛|추가유닛|

* 기본으로 제공되는 유닛은 총 5종류이며 스테이지 클리어 시 추가 유닛이 추가로 배치된다.
* 방어력은 존재하지 않으며 간단하게 공격력으로 인해 HP가 변경되는 전투 시스템을 갖는다.

> 적군 유닛

|Type|zombie|skel_pirate|skel_ninja|witch|frankenstein|
|---|:----:|:----:|:----:|:----:|:----:|
|Image|![zombie_base](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/88a7b09c-af0c-4471-9484-5f5060d54c0e)|![skeleton_pirate_base](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/69553157-c390-4f82-83b4-fa40a0305be9)|![skeleton_ninja](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/51c15ece-83cd-47a9-b776-37754eeba033)|![witch_base](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/b1c81488-21ad-4fcb-8ed3-58e6c7169120)|![frankenstein_base](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/300a1bf8-8110-4e53-a122-a175a6021eff)|
|HP|10|8|30|70|120|
|ATK|5|8|20|30|50|
|Feat|기본유닛|기본유닛|기본유닛|기본유닛|기본유닛|

* 적군 유닛은 기본적으로 아군 유닛보다 공력력이 한 단계 낮지만 코스트 시스템이 존재하지 않는다.
* 적군 유닛은 아군 유닛과 달리 스테이지 마다 추가 유닛은 없다.

## 레벨 디자인

|SCENE02|stage_select|
|---|:-----------:|
|Image|![level_desing_select_stage](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/af9e8df1-21a6-48ac-bd69-db450764cccc)|

|STAGE|stage_01|stage_02|stage_03|
|---|:-----------:|:-----------:|:-----------:|
|Select|![scene02_stage01](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/d1da6d0d-fc5b-40e8-92ad-4b4c047ce769)|![scene02_stage02](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/58a80984-9363-4a0b-aaea-34c6dc473bf2)|![scene02_stage03](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/e9e0ed58-2cd1-4fa9-9128-2952c2c6afa1)|
|InGame|![level_desing_01](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/4f4b2a7c-5a0d-4db1-8a13-e25f18b2c472)|![level_desing_02](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/50f647c2-a734-47ed-9bd3-85dfa86478f7)|![level_desing_03](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/dc0b7a2f-5acf-47e9-8e7e-d95471627401)|
|Enemy|zombie|skel_pirate&skel_ninja|witch&frankenstein|
|Boss|X|X|O|

* 스테이지는 총 3가지 단계로 각각의 스테이지마다 소환되는 적군 유닛이 다르다.
* 마지막 3단계 스테이지에서는 중간 보스와 최종 보스가 존재하며 최종 보스까지 클리어 시 게임이 종료된다.


## 예상 게임 흐름

![49d88598a18746f88bc4f381b3044b1f-2](https://github.com/UihwanLee/SmartPhoneGameProject/assets/36596037/c3d09c7d-500a-42ef-a429-fe5dad3d2482)


## 클래스 다이어그램

![classdiagram drawio](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/38837ffc-7311-40e5-b0ae-1cd2a409c31a)

> GameObject
- Dogcat:        플레이어 주체, dogcat 캐릭터를 컨트롤 하는 클래스이다.
- Friendly:      아군 유닛, 총 7개의 종류가 있으며 각각 다른 cost, hp, atk 속성을 갖고 있다.
- Enemy:         적군 유닛, 총 5개의 종류가 있으며 각각 다른 hp, hp, atk 속성을 갖고 있다.

> UI
- Background:     배경을 담당하는 클래스, 16:9 사이즈의 화면의 꽉 찬 배경을 그린다.
- Button:         버튼 UI 클래스, button 클릭 시 나타날 수 있는 상호작용을 관리한다.

> System
 - Cost:          아군 유닛 소환 비용을 담당하는 클래스
 - CostGenerator: cost를 시간에 따라 증가시키는 클래스
 - EnemyGenerator: 적군 유닛을 시간에 따라 소환하는 클래스
 - FriendlyGenrator: 아군 유닛을 cost에 따라 소환할 수 있는지 관리하는 클래스

> Controller
 - JoyStick:      Dogcat 캐릭터의 조작을 담당하는 클래스


## 게임 시스템

> Cost 시스템
![cost drawio](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/08261295-9958-4efb-b994-8bfed234dc16)
 - 아군 유닛을 소환할 수 있는 cost의 최대치는 200이며 초당 5씩 찬다.
 - cost가 최대치가 되면 200에서 넘아가지 않는다.

> 전투 시스템
![fight drawio](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/36e5d5ea-86a7-4407-b2ef-8f931c8d80a2)
- 모든 전투는 아군 및 적군의 공격 범위에 도달하면 시작된다.
- 근접 전투 유닛은 근접 충돌 시 원거리 전투 유닛은 특정 범위에 충돌되면 전투를 시작한다.
- 전투 시스템에 사용되는 애니메이이션은 총 3종류로 'walking', 'attack', 'dying' 이다.
- 적군 유닛은 적군 스테이지마다 몬스터 타입별로 나타날 수 있는 확률을 각각 설정하고 2~10초 사이 랜덤으로 자동 생성된다.
- 플레이어 캐릭터 또한 공격을 할 수 있으며 HP가 0이 되면 게임이 종료된다.

> 애니메이션 시스템
![statemachine drawio](https://github.com/UihwanLee/DefenseOfDogcat/assets/36596037/56853746-8ad0-4e55-9744-ca10e0d1607d)
 - 애니메이션은 stateMachine에 따라 동작한다.
 - state 종류는 Friendly, Enemy 모두 'walking', 'attack', 'dying'로 3종류이다.

> 조작방법
- 플레이어 이동: 조이패드
- 아군 유닛 소환: 유닛 슬롯 버튼


## 진행율

|주차|계획 내용|구체 계획|진행율|
|----|:----:|:----------:|:----:|
|1주차|Resource수집 & 게임 프레임워크 구성|-게임 개발에 사용될 Resource 수집, 구성도 다이어그램 검토 및 생성|80%|
|2주차|게임 초기 시스템 구현|-스테이지 단계의 아군 기지 및 적군 기지 구현, 기지 간의 HP 시스템 구현 및 플레이어 생성|80%|
|3주차|디펜스 아군 유닛 구현|-아군을 소환하는 GUI 구현 & 아군 행동 AI 구현|80%|
|4주차|디펜스 적군 유닛 구현|-적군을 소환하는 GUI 구현 & 적군 행동 AI 구현|80%|
|5주차|유닛 전투 시스템 구현 기지 & 유닛 상호작용|-아군과 적군의 충돌처리 및 기본 전투 시스템 구현 & 기지와 유닛의 상호작용 구현|0%|
|6주차|유닛 전투 시스템 구현 기지 & 유닛 상호작용|-아군과 적군의 충돌처리 및 기본 전투 시스템 구현 & 기지와 유닛의 상호작용 구현|0%|
|7주차|스테이지 추가|-3가지 스테이지 구현 및 레벨 디자인 구성 & 스테이지 간 난이도 조절|0%|
|8주차|게임 마무리 구현|-게임 결과 창 구현 & 캐릭터 Status 구현|0%|
|9주차|코드 리펙토링 및 버그 수정|-전체 코드 리펙토링 및 버그 수정 & 플레이어 추가 기능 구현|0%|
||||40%|


## 개발 일정

![49d88598a18746f88bc4f381b3044b1f-3](https://github.com/UihwanLee/SmartPhoneGameProject/assets/36596037/3ddec8e6-598b-42b1-a941-516e5f3c7035)


