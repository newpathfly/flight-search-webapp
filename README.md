# flight-search-webapp

## Environment Variables

Name | Description | Default
---- | ----------- | -------
NEWPATHFLY_API_BASE_URL| base URL override for the API. | the URL provided in the [newpathfly OpenAPI spec](https://newpathfly.ticketcombine.com/). E.g. `http://uat-api.newpathfly.com/v2`

## Usage

1. For local testing purposes, run the application with the following command:

    ```shell
    java -jar /home/tigerinus/dev/newpathfly/flight-search-webapp/target/flight-search-webapp-<version>.jar
    ```

2. Open the browser to <http://localhost:8080/>

## Heroku

```shell
mvn clean heroku:deploy -P production
```
