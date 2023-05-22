# MIDUBANG-BACK
믿어방 백엔드 레포지토리

# 기술 아키텍쳐
![](https://velog.velcdn.com/images/goinggoing/post/fd64b73d-7946-4bf5-9d4a-5c782ce754b6/image.png)

# application-secret.yml
```YAML
spring:
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    username: [USERNAME]
    url: jdbc:mariadb://[AWS_RDS_ENDPOINT]:[PORT]/[DB_NAME]?useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true
    password: [PASSWORD]

  redis:
    port: [PORT]
    host: [HOST]
    password: [PASSWORD]

  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: [KAKAO_CLIENT_ID]
            redirect-uri:[REDIREC_URI]
            client-authentication-method: POST
            client-secret: [KAKAO_CLIENT_SECRET]
            authorization-grant-type: authorization_code
            scope:
              - account_email
            client_name: kakao
        provider:
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id


jwt:
  access_token_secret: [JWT_ACCESS_TOKEN_SECRET_KEY]
  refresh_token_secret: [JWT_REFRESH_TOKEN_SECRET_KEY]


naver-app:
  client-id: [NAVER_OPEN_API_CLIENT_ID]
  client-secret: [NAVER_OPEN_API_CLIENT_SECRET]


openai:
  model: gpt-3.5-turbo
  api:
    url: https://api.openai.com/v1/chat/completions
    key: [OPEN_AI_KEY]
```
