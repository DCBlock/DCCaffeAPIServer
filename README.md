# CaffeAPIServer
클라이언트의 데이터 연동을 위한 Restful API Server

# 코딩 가이드
- Sun 코딩 가이드를 최대한 따릅니다.
- tab은 사용하지 않고 space를 사용합니다.
- space는 4를 기준으로 합니다.
- 한 줄의 최대는 제한 없습니다(개발툴에서 Word Wrap 사용).

# Push Guide
- 개발자들은 master에 직접 개발, push 하지 않습니다.
- 별도의 브랜치를 생성하여 담당 영역을 개발합니다.
- 예를 들면, 저는 'shseo' 브랜치를 생성하고 여기에서 개발하고 push합니다.
- master에 merge는 제가 진행하겠습니다.
- 수홍 과장은 master 새로운 push 올라오면 자신의 브런치에 merge를 하면 됩니다.
- gitignore는 각자 생성하여 사용하고 서버에 push하지 않습니다.
- 개발툴에서 사용하는 각종 설정정보도 push하지 않습니다.

# 개발 환경
- Spring Boot 2.1.0 Release
- Openjdk 1.8(서버 기준, 개발자는 각자 PC에서 oracle jdk로 개발 진행)
