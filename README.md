## Requirements:
The assessment comes with 3 CSV files. The files contain data for countries, airports and runways. Write a program that
retrieves the following information given the provided files:
- Runways for each airport given a country code or country name.
- Top 10 countries with highest number of airports.
- Bonus: Support retrieving the information given a partial/fuzzy country code/name as input parameter, e.g. entering '
  zimb' will result in 'Zimbabwe'.
  
## Solution Design :

To realize the above requirements, 2 Springboot Rest API's have been implemented :
- airport/fetchairportandrunwaysforcountry : Searches the list of airports and its related runways for the given country
  code or name. This API also looks for possible country names for a given fuzzy country name.
- airport/gettoptencountrieswithairports : Delivers the list of top 10 countries with highest number of Airports.

## Technology involved:
- Java 8
- SpringBoot Framework
- Rest API
- Postman
- Junit MockMvc
- Inbuild Tomcat server

## Project Structure :
- ![Optional Text](/image/package.png)

## Assumption:
- Modifications/data refresh on CSVs are only allowed during maintenance window. Example weekly Sunday or daily night
  12PM for 10 min via schedular.
- The data in the CSVs are assumed to be the source of truth.
- The CSV reload functionality is not in the scope.It can be taken as a future enhancement with schedular or 
  GET api which will trigger the reload functionalities.
- The high availability will be taken care on infra structure level. e.g hosting the solution in public cloud.

## Design Specification :

![Optional Text](/image/designfinal.png)

- OpenCSV Api has been used to parse the CSV to objects via a reusable component written in CsvToBeanParser class.
- As per the design, the 3 CSV files will be loaded in static Hashmap via the below loader classes.
- [InitializeCountries,InitializeAirports,InitializeRunways,InitializeupdatedResultEntity].
- During the application bootstrap time the final result map will be processed by parsing 3 static map which will hold the
  country code as Key and Result entity objects as value (contains list of airport and each airports contains list of
  Runways).
- So whenever a request arrives the controller simply fetches the airport list and runway list from the processed map.
- Each time the CSV data refreshed the Result Map is also updated with latest Key-value pair which will reduce the
  response time.

## Swagger Integrration
- Swagger 2 has been integrated. URL for UI: http://localhost:8080/swagger-ui.html#/
  
![Optional Text](/image/swagger.png)

## Steps to run the application

- Clone the project. You can follow either step 1 or step 2 
  ### Step -1: We assume you have either Eclipse or intellij IDE installed.
- import the project in IDE  and run the command 1) mvn clean install 2) spring-boot:Run command
- Wait(It may take 2 to 3 minutes to initialize the result map by parsing the CSV , which is one time activity) till console prints " Tomcat started on port(s): 8080 (http) with context path "
- Use Below URLS to realize the API.
- Use Postman to call the post method : http://localhost:8080/airport/fetchairportandrunwaysforcountry
- Enter the body like below for post method:
   
![Optional Text](/image/postman.png)  

  ### Step -2 :
- Clone the project, we assume Maven and java 8 has laredy been installed in your system.
- Run the command "mvn clean install" on pom level.
- Navigate to target folder
- Run the Command "java -jar details-finder-0.0.1-SNAPSHOT.jar"
- Wait(It may take 2 to 3 minutes to initialize the result map by parsing the CSV , which is one time activity) till console prints " Tomcat started on port(s): 8080 (http) with context path "
- Use Below URLS to realize the API.
 ### URls
- http://localhost:8080/airport/gettoptencountrieswithairports
- http://localhost:8080/airport/fetchairportandrunwaysforcountry
- http://localhost:8080/swagger-ui.html#/

  
