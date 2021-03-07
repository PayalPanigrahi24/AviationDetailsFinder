#Requirements
The assessment comes with 3 CSV files. The files contain data for countries, airports and runways.
Write a program that retrieves the following information given the provided files:
- Runways for each airport given a country code or country name.
- Top 10 countries with highest number of airports.
- Bonus: Support retrieving the information given a partial/fuzzy country code/name as input parameter, e.g. entering 'zimb' will result in 'Zimbabwe'.

#Solution Design :
To realize the above requirements 2 Springboot Rest API's have been implemented.
 - airport/fetchairportandrunwaysforcountry : Searches the list of airports and its related runways for the given countries.
 - airport/gettoptencountrieswithairports : Delivers the list of top 10 countries with highest number of Airports.

#Technology involved:
- Java 8
- SpringBoot Framework.
- Rest API
- Postman
- Junit MockMvc
- Inbuild Tomcat server

#Assumption 
- Modifications/data refresh on CSV's are only allowed during maintenance window. Example weekly Sunday or daily night 12PM for 10 min via schedular.
- The data in the CSV's are already been validated.
- The CSV reload functionality is not in the scope. Can be taken as future enhancement with schedular/get api which will trigger the reload functionalities.
- The high availability will be taken care on infra structure level. e.g hosting the solution in public cloud.

#Design Specification :
![Optional Text](/image/designfinal.png)

- OpenCSV api has been used to parse the CSV to objects via a reusable component written in CsvToBeanParser class.
- As per the design, the 3 CSV files will be loaded in static Hashmap via the below loader classes.
- [InitializeCountries,InitializeAirports,InitializeRunways,InitializeupdatedResultEntity].
- During application bootstraap time the final result map will be processed by parsing 3 static map which will holds the country code as KEy and Resultentity objects as value(contains list of airport and each airports contains list of Runways).
- So whenever a request arrives the controller simply fetches the airport list and runway list from the processed map.
- Eachtime CSV data refressed the Result Map also refressed with latest Key value pair. Which will reduce the response time.

#Project Structure :


