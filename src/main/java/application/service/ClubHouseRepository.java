package application.service;

import application.model.ClubHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubHouseRepository extends JpaRepository<ClubHouse, Long> {
    Iterable<ClubHouse> findAllByMaxLedenBetween(Integer from, Integer to);
    Iterable<ClubHouse> findAllByGemeenteIgnoreCaseContains(String text);
    ClubHouse findClubHouseByName(String name);
    Iterable<ClubHouse> findClubHouseByType(String type);
}
