package application.service;

import application.model.ClubHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClubHouseService {

    @Autowired
    private ClubHouseRepository repo;

    public ClubHouse add(ClubHouse clubHouse) throws ServiceException {
       return repo.save(clubHouse);
    }

    public Iterable<ClubHouse> findAllByMaxLedenBetween(Integer from, Integer to) {
        Iterable<ClubHouse> res;
        try {
            res = repo.findAllByMaxLedenBetween(from, to);
        } catch (Exception e) {
            throw new ServiceException("get", e.getMessage());
        }
        return res;
    }

    public ClubHouse findByName(String name) {
        return repo.findClubHouseByName(name);
    }

    public ClubHouse findById(long id) {
        return repo.findById(id).orElseThrow(()-> new ServiceException("get", "clubhouse.not.found"));
    }

    public Iterable<ClubHouse> findAll() {
        return repo.findAll();
    }

    public ClubHouse update(ClubHouse clubHouse) throws ServiceException {
        ClubHouse cb = findById(clubHouse.getId());
        return repo.save(clubHouse);
    }

    public void deleteById(long id) {
        ClubHouse cb = findById(id);
        repo.deleteById(cb.getId());
    }

    public Iterable<ClubHouse> delete(ClubHouse cb) {
        repo.delete(cb);
        return findAll();
    }

    public Iterable<ClubHouse> findByType(String type) {
         return repo.findClubHouseByType(type);
    }

    public Iterable<ClubHouse> findAllByGemeenteContains(String text) {
        return repo.findAllByGemeenteIgnoreCaseContains(text);
    }
}
