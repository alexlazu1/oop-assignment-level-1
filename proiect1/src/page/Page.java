package page;

import page.PageFactory.PageType;

import java.util.ArrayList;
import java.util.List;

public abstract class Page {
    protected String name;
    protected ArrayList<PageType> nextPages; // TODO: CAN I PUT HERE = NEW ARRAYLIST?
    protected ArrayList<String> features;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PageType> getNextPages() {
        return nextPages;
    }

    public void setNextPages(ArrayList<PageType> nextPages) {
        this.nextPages = nextPages;
    }

    public ArrayList<String> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<String> features) {
        this.features = features;
    }
}

class HomeNotAuth extends Page {
    public HomeNotAuth() {
        name = "home not auth";
        nextPages = new ArrayList<>(List.of(PageType.Register,PageType.Login));
        features = new ArrayList<>();
    }
}
class Login extends Page {
    public Login() {
        name = "login";
        nextPages = new ArrayList<>();
        features = new ArrayList<>(List.of("login"));
    }
}

class Register extends Page {
    public Register() {
        name = "register";
        nextPages = new ArrayList<>();
        features = new ArrayList<>(List.of("register"));
    }
}

class HomeAuth extends Page {
    public HomeAuth() {
        name = "home auth";
        nextPages = new ArrayList<>(List.of(PageType.Logout,PageType.Movies, PageType.Upgrades));
        features = new ArrayList<>();
    }
}

class Movies extends Page {
    public Movies() {
        name = "movies";
        nextPages = new ArrayList<>(List.of(PageType.HomeNotAuth,PageType.SeeDetails, PageType.Logout));
        features = new ArrayList<>(List.of("search", "filter"));
    }
}

class Upgrades extends Page {
    public Upgrades() {
        name = "upgrades";
        nextPages = new ArrayList<>(List.of(PageType.HomeAuth,PageType.Movies, PageType.Logout));
        features = new ArrayList<>(List.of("buy premium account", "buy tokens"));
    }
}

class SeeDetails extends Page {
    public SeeDetails() {
        name = "see details";
        nextPages = new ArrayList<>(List.of(PageType.HomeAuth,PageType.Movies, PageType.Upgrades, PageType.Logout));
        features = new ArrayList<>(List.of("purchase")); // TODO: ADD WATCH, LIKE, RATE MOVIE
    }

}

class Logout extends Page {
    public Logout() {
        name = "logout";
        nextPages = new ArrayList<>();
        features = new ArrayList<>();
    }
}


