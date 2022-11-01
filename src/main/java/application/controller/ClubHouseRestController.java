package application.controller;

import application.model.Club;
import application.model.ClubHouse;
import application.model.DomainException;
import application.service.ClubHouseService;
import application.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/clubhuis")
public class ClubHouseRestController {
    @Autowired
    private ClubHouseService service;

    @GetMapping("/all")
    public Iterable<ClubHouse> all() {
        return service.findAll();
    }

    @PostMapping("/add")
    public Iterable<ClubHouse> add(@Valid @RequestBody ClubHouse clubHouse) {
        service.add(clubHouse);
        return service.findAll();
    }

    @PutMapping("/update/{id}")
    public Iterable<ClubHouse> update(@Valid @RequestBody ClubHouse clubHouse, @PathVariable("id") String id) {
        long lid;
        try {
            lid = Long.parseLong(id);
        } catch (Exception e) {
            throw new ServiceException("error", "please enter numbers only");
        }
        ClubHouse ch = service.findById(lid);
        ch.setName(clubHouse.getName());
        ch.setEmail(clubHouse.getEmail());
        ch.setMaxLeden(clubHouse.getMaxLeden());
        ch.setGemeente(clubHouse.getGemeente());
        try {
            service.update(ch);

        } catch (Exception e) {
            throw new ServiceException("error", "Name and region not unique");
        }
        return service.findAll();
    }

    @DeleteMapping("/delete")
    public Iterable<ClubHouse> delete(@RequestParam("id") String id) {
        long lid;
        try {
            lid = Long.parseLong(id);
        } catch (Exception e) {
            throw new ServiceException("error", "please enter numbers only");
        }
        service.deleteById(lid);
        return service.findAll();
    }

    @GetMapping("/searchd/{param}")
    public Iterable<ClubHouse> search(@PathVariable("param") String param) {
        return service.findAllByGemeenteContains(param);
    }

    @GetMapping("/search/{param}")
    public Iterable<ClubHouse> searchbytype(@PathVariable("param") String param) {

        return service.findByType(param);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ServiceException.class, ResponseStatusException.class})
    public Map<String, String> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();
        if (ex instanceof MethodArgumentNotValidException) {
            ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }
        else if (ex instanceof ServiceException) {
            errors.put(((application.service.ServiceException) ex).getAction(), ex.getMessage());
        }
        else {
            errors.put(((ResponseStatusException)ex).getReason(), ex.getCause().getMessage());
        }
        return errors;
    }
}
