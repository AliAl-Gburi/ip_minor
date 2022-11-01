package application.controller;

import application.model.ClubHouse;
import application.service.ClubHouseService;
import application.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.rmi.server.ServerCloneException;

@Controller
@RequestMapping("/clubhouses")
public class ClubHouseController {
    @Autowired
    private ClubHouseService service;

    private static final String OVERVIEW = "clubhouse-overview";
    private static final String ADD = "add-clubhouse";
    private static final String UPDATE = "update-clubhouse";
    private static final String DELETE = "delete-clubhouse";
    private static final String FILTER = "filter-clubhouse";
    private static final String FILTERRES = "filter-res";

    @GetMapping("/overview")
    public String overview(Model model) {
        Iterable<ClubHouse> clubhouses = service.findAll();
        model.addAttribute("clubhouses", clubhouses);
        return OVERVIEW;
    }

    @GetMapping("/add")
    public String add(ClubHouse clubhouse, Model model) {
        String nu = null;
        model.addAttribute("nu", nu);
        return ADD;
    }
    @PostMapping("/add")
    public String add(@Valid ClubHouse clubHouse, BindingResult result, Model model) {
        String nu = null;
        if(result.hasErrors()) {
            return ADD;
        }
        try {
            service.add(clubHouse);
        } catch (DataIntegrityViolationException e) {
            nu = "name.gemeente.not.unique";
            model.addAttribute("nu", nu);
            return ADD;
        }
        return overview(model);
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") long id, Model model) {
        String nu = null;
        try {
            ClubHouse clubHouse = service.findById(id);
            model.addAttribute(clubHouse);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return overview(model);
        }
        model.addAttribute("nu", nu);
        return UPDATE;

    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") long id, @Valid ClubHouse ch, Model model, BindingResult br) {
        String nu = null;
        if(br.hasErrors()) {
            return UPDATE;
        }
        try {
            service.update(ch);
        } catch (DataIntegrityViolationException xd) {
            nu = "name.gemeente.not.unique";
            model.addAttribute("nu", nu);
            return UPDATE;
        }
        catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return UPDATE;
        }
        return overview(model);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model, ClubHouse c) {
        try {
            ClubHouse clubHouse = service.findById(id);
            model.addAttribute(clubHouse);
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return overview(model);
        }
        return DELETE;
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try{
            service.deleteById(id);
        } catch (ServiceException e) {
            model.addAttribute("error", e.getMessage());
            return overview(model);
        }

        return overview(model);


    }

    @GetMapping("/filterpage")
    public String filterPage() {
        return FILTER;
    }

    @GetMapping("/filter")
    public String filter(@RequestParam("from") String from, @RequestParam("to") String to, Model model) {
        Iterable<ClubHouse> res;
        if(from.trim().isEmpty() && to.trim().isEmpty()) {
            model.addAttribute("error", "no.value.entered");
            return FILTER;
        } else {
            if(from.trim().isEmpty()) {
                Integer intfrom = 0;
                Integer intto = 0;
                try {
                    intto = Integer.parseInt(to);
                } catch (Exception e) {
                    model.addAttribute("error", "not.a.number");
                    return FILTER;
                }
                res = service.findAllByMaxLedenBetween(intfrom, intto);
                model.addAttribute("clubhouses", res);
                return FILTERRES;
            }else if(to.trim().isEmpty()) {
                Integer intfrom = 0;
                Integer intto = Integer.MAX_VALUE;
                try {
                    intfrom = Integer.parseInt(from);
                } catch (Exception e) {
                    model.addAttribute("error", "not.a.number");
                    return FILTER;
                }
                res = service.findAllByMaxLedenBetween(intfrom, intto);
                model.addAttribute("clubhouses", res);
                return FILTERRES;
            } else {
                Integer intfrom = 0;
                Integer intto = 0;
                try {
                    intfrom = Integer.parseInt(from);
                    intto = Integer.parseInt(to);
                } catch (Exception e) {
                    model.addAttribute("error", "not.a.number");
                    return FILTER;
                }
                res = service.findAllByMaxLedenBetween(intfrom, intto);
                model.addAttribute("clubhouses", res);
                return FILTERRES;
            }
        }


    }
}
