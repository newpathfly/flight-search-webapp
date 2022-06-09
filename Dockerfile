ARG BASE_IMAGE=maven:3-jdk-8-slim

FROM ${BASE_IMAGE} AS buildtime

WORKDIR /src

# download all dependencies
# (as long as ./pom.xml stays the same, this step will be cached.)
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

# compile and package
COPY . .
RUN mvn clean install -Dmaven.test.skip=true -P production

FROM openjdk:8-slim as runtime

WORKDIR /app
COPY --from=buildtime /src/target/flight-search-webapp-*.jar ./

CMD java -jar $(ls flight-search-webapp-*.jar) 