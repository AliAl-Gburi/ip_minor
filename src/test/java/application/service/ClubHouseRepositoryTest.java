package application.service;


import application.ClubHouseBuilder;
import application.model.ClubHouse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import javax.swing.text.html.Option;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClubHouseRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClubHouseRepository clubHouseRepository;

    @Test
    public void givenClubHouse_whenFindByID_thenReturnClubhouse() {
        //given
        ClubHouse cb = ClubHouseBuilder.validClubhouse().build();
        entityManager.persistAndFlush(cb);

        //when
        Optional<ClubHouse> found = clubHouseRepository.findById(cb.getId());

        assertNotNull(found);
        assertThat(found.get().getName().equals(cb.getName()));
    }
}
