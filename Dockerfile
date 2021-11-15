ARG BASE_IMAGE=maven:3-amazoncorretto-8

FROM ${BASE_IMAGE} AS buildtime

WORKDIR /src

# download all dependencies
# (as long as ./pom.xml stays the same, this step will be cached.)
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

# compile and package
COPY . .
RUN mvn clean install -Dmaven.test.skip=true -P production

FROM amazoncorretto:8-alpine-jdk as runtime

RUN apk add bash unzip curl aria2

WORKDIR /app
COPY --from=buildtime /src/target/flight-search-webapp-*.jar ./

CMD java -jar $(ls flight-search-webapp-*.jar) 