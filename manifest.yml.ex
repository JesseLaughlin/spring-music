---
applications:
- name: spring-music
  memory: 512M
  instances: 1
  host: spring-music-webinar
  domain: cfapps.io
  path: build/libs/spring-music.war
  env:
    NEW_RELIC_LICENSE_KEY: {license key here}
    NEW_RELIC_APP_NAME: spring-music

