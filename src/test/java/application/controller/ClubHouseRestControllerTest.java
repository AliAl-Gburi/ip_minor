package application.controller;

import application.ClubHouseBuilder;
import application.model.Club;
import application.model.ClubHouse;
import application.service.ClubHouseService;
import application.service.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ClubHouseRestController.class)
public class ClubHouseRestControllerTest {
    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    ClubHouseService service;

    @Autowired
    MockMvc controller;
    ClubHouse validCB, nameblankCB, negativeMaxLedenCB;

    @Before
    public void setUp() {
        validCB = ClubHouseBuilder.validClubhouse().build();
        nameblankCB = ClubHouseBuilder.withNameLessThan5().build();
        negativeMaxLedenCB = ClubHouseBuilder.withNegativeMaxLeden().build();
    }

    @Test
    public void givenNoClubHouses_whenAddingValidClubHouse_ThenListOfClubHousesReturned() throws Exception {
        //given
        List<ClubHouse> ch = Arrays.asList(validCB);

        //mocking
        when(service.add(validCB)).thenReturn(validCB);
        when(service.findAll()).thenReturn(ch);

        //when
        controller.perform(post("/api/clubhuis/add")
                .content(mapper.writeValueAsString(validCB))
                .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", Is.is(validCB.getName())));

    }

    @Test
    public void givenNoClubHouses_whenAddingLessThan5CharactersNameClubHouse_ThenErrorIsShown() throws Exception {
        //given



        //when
        controller.perform(post("/api/clubhuis/add")
                        .content(mapper.writeValueAsString(nameblankCB))
                        .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", Is.is("name.too.short")));

    }

    @Test
    public void givenNoClubHouses_whenAddingNegativeMaxLeden_ThenErrorIsShown() throws Exception {
        //given



        //when
        controller.perform(post("/api/clubhuis/add")
                        .content(mapper.writeValueAsString(negativeMaxLedenCB))
                        .contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.maxLeden", Is.is("maxLeden.negative")));

    }

}
