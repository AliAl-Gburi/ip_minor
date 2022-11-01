package application.service;

import application.ClubHouseBuilder;
import application.model.ClubHouse;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClubHouseServiceTest {
    @Mock
    ClubHouseRepository repository;

    @InjectMocks
    ClubHouseService clubHouseService;

    @Test
    public void givenClubhouseExists_whenClubHouseDeleted_thenClubhouseIsNoLongerInRepo() {
        //given
        ClubHouse vch = ClubHouseBuilder.validClubhouse().build();
        Iterable<ClubHouse> el = Arrays.asList();

        //mocking
        when(clubHouseService.delete(vch)).thenReturn(el);


        //when
        Iterable<ClubHouse> afterDeletion = clubHouseService.delete(vch);

        //then
        assertThat(afterDeletion).isSameAs(el);

    }

    @Test
    public void givenClubhouseDoesntExist_whenClubHouseDeleted_thenErrorIsShown() {
        //given
        ClubHouse vch = ClubHouseBuilder.validClubhouse().build();


        //mocking



        //when
        final Throwable raisedException = catchThrowable(() -> clubHouseService.deleteById(vch.getId()));

        //then
        assertThat(raisedException).isInstanceOf(ServiceException.class)
                .hasMessageContaining("clubhouse.not.found");

    }


}
