package com.aviation.airport.detailsfinder.controller;

import com.aviation.airport.detailsfinder.dataloader.InitializeAirports;
import com.aviation.airport.detailsfinder.dataloader.InitializeCountries;
import com.aviation.airport.detailsfinder.dataloader.InitializeUpdateResultEntity;
import com.aviation.airport.detailsfinder.dataloader.InitializeRunways;
import com.aviation.airport.detailsfinder.service.CountriesWithAirportService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AirportControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @InjectMocks
    @Autowired
    private CountriesWithAirportService countriesWithAirportService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        InitializeCountries.initializeCountryList();
        InitializeRunways.initializeRunwayMap();
        InitializeAirports.initializeAirportMap();
        InitializeUpdateResultEntity.initializeResultEntity();
    }

    /**
     * This method retrives all details of runways of the airports for a given
     * fuzzy or partial country name.
     *
     * @throws Exception that occurs during the test case execution
     */
    @Test
    void fetchRunwaysForFuzzyOrPartialCountry_Test() throws Exception {
        mockMvc.perform(post("/airport/fetchairportandrunwaysforcountry")
                .content("{\n" +
                        "\"countrycode\":\" \",\n" +
                        "\"countryname\":\"Zimb\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value("ZW"))
                .andExpect(jsonPath("$.name").value("Zimbabwe"))
                .andExpect(jsonPath("$.airportsList").exists())
                .andExpect(jsonPath("$.airportsList", hasSize(139)))
                .andDo(MockMvcResultHandlers.print());

    }

    /**
     * This method returns a error message if the entered fuzzy country name is invalid
     *
     * @throws Exception that occurs during the test case execution
     */
    @Test
    void fetchRunwaysFor_UnkownFuzzyOrPartialCountry_Test() throws Exception {
        mockMvc.perform(post("/airport/fetchairportandrunwaysforcountry")
                .content("{\n" +
                        "\"countrycode\":\" \",\n" +
                        "\"countryname\":\"Limb\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.finalmessage").value("Please enter a valid Country code or name"))
                .andDo(MockMvcResultHandlers.print());

    }

    /**
     * It tests the following things :
     * This methods fetches all the runways in the Airports for a given country code
     * If both Country Code and Country Name are present then Country code takes the precedence
     *
     * @throws Exception exception for any country/name not found
     */
    @Test
    void fetchAllRunwaysForAGivenCountryCode_Test() throws Exception {
        mockMvc.perform(post("/airport/fetchairportandrunwaysforcountry")
                .content("{\n" +
                        "\"countrycode\":\"AG\",\n" +
                        "\"countryname\":\"United States\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value("AG"))
                .andExpect(jsonPath("$.name").value("Antigua and Barbuda"))
                .andExpect(jsonPath("$.airportsList").exists())
                .andExpect(jsonPath("$.airportsList", hasSize(4)))
                .andDo(MockMvcResultHandlers.print());

    }

    /**
     * It checks that the api returns a valid response for a given country Name
     * Here country is taken as empty
     * @throws Exception that occurs during the test case execution
     */
    @Test
    void fetchAllRunwaysForAGivenCountryName_Test() throws Exception {
        mockMvc.perform(post("/airport/fetchairportandrunwaysforcountry")
                .content("{\n" +
                        "\"countrycode\":\" \",\n" +
                        "\"countryname\":\"Andorra\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value("AD"))
                .andExpect(jsonPath("$.name").value("Andorra"))
                .andExpect(jsonPath("$.airportsList").exists())
                .andExpect(jsonPath("$.airportsList", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());

    }

    /**
     * This tests that the api returns response for a given valid country Name irrespective
     * of it given in Upper or Lower Case
     *
     * @throws Exception that occurs during the test case execution
     */
    @Test
    void getRunwaysOfACountryName_InAllLetterCases_Test() throws Exception {
        mockMvc.perform(post("/airport/fetchairportandrunwaysforcountry")
                .content("{\n" +
                        "\"countrycode\":\" \",\n" +
                        "\"countryname\":\"aNdoRRa\"\n" +
                        "}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value("AD"))
                .andExpect(jsonPath("$.name").value("Andorra"))
                .andExpect(jsonPath("$.airportsList").exists())
                .andExpect(jsonPath("$.airportsList", hasSize(2)))
                .andDo(MockMvcResultHandlers.print());

    }

    /**
     * This tests that the api returns response for a given valid country code irrespective
     * of it given in Upper or Lower Case
     *
     * @throws Exception that occurs during the test case execution
     */
    @Test
    void getRunwaysOfACountryCode_InLowerCase_Test() throws Exception {
        mockMvc.perform(post("/airport/fetchairportandrunwaysforcountry")
                .content("{\n" +
                        "\"countrycode\":\"zW \",\n" +
                        "\"countryname\":\" \"\n" +

                        "}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.code").value("ZW"))
                .andExpect(jsonPath("$.name").value("Zimbabwe"))
                .andExpect(jsonPath("$.airportsList").exists())
                .andDo(MockMvcResultHandlers.print());

    }

    /**
     * This is the get method to get the top 10 countries list with highest number of airports
     * For instance, as US holds the largest value , the expected json is checked for
     * the descending order response
     *
     * @throws Exception that occurs during the test case execution
     */
    @Test
    void getTopTenCountrieswithAirportsTest() throws Exception {
        mockMvc.perform(get
                ("/airport/gettoptencountrieswithairports"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.US.code").value("US"))
                .andDo(MockMvcResultHandlers.print());
    }

}