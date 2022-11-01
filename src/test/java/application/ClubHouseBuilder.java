package application;

import application.model.Club;
import application.model.ClubHouse;

public class ClubHouseBuilder {
    private long id;
    private String name, gemeente, email;
    private int maxLeden;

    private ClubHouseBuilder() {

    }

    public static ClubHouseBuilder emptyClubhouse() {
        return new ClubHouseBuilder();
    }

    public static ClubHouseBuilder validClubhouse() {
        return emptyClubhouse().withId(1).withName("Forest Gumps").withEmail("forest.gump@usaid.us").withMaxLeden(14).withGemeente("IOWA");
    }

    public static ClubHouseBuilder withNameLessThan5() {
        return emptyClubhouse().withId(2).withName("Fore").withEmail("forest.gump@usaid.us").withMaxLeden(14).withGemeente("IOWA");
    }

    public static ClubHouseBuilder withNegativeMaxLeden() {
        return emptyClubhouse().withId(3).withName("Forest").withEmail("forest.gump@usaid.us").withMaxLeden(-2).withGemeente("IOWA");
    }


    public ClubHouseBuilder withId (long id) {
        this.id = id;
        return this;
    }

    public ClubHouseBuilder withName (String name) {
        this.name = name;
        return this;
    }

    public ClubHouseBuilder withEmail (String email) {
        this.email = email;
        return this;
    }

    public ClubHouseBuilder withMaxLeden (int maxLeden) {
        this.maxLeden = maxLeden;
        return this;
    }

    public ClubHouseBuilder withGemeente (String gemeente) {
        this.gemeente = gemeente;
        return this;
    }
    public ClubHouse build() {
        ClubHouse cb = new ClubHouse();
        cb.setName(name);
        cb.setEmail(email);
        cb.setMaxLeden(maxLeden);
        cb.setGemeente(gemeente);
        return cb;
    }
}
