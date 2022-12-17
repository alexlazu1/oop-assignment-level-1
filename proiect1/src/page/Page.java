package page;

import page.PageFactory.PageType;
import viewmodel.Viewmodel;

import java.util.ArrayList;
import java.util.List;

public abstract class Page {
    protected PageType type;
    protected ArrayList<PageType> nextPages; // TODO: CAN I PUT HERE = NEW ARRAYLIST?
    protected ArrayList<String> features;

    public PageType getType() {
        return type;
    }

    public void setType(PageType type) {
        this.type = type;
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
        type = PageType.HomeNotAuth;
        nextPages = new ArrayList<>(List.of(PageType.Register,PageType.Login));
        features = new ArrayList<>();
    }
}
class Login extends Page {
    public Login() {
        type = PageType.Login;
        nextPages = new ArrayList<>();
        features = new ArrayList<>(List.of("login"));
    }
}

class Register extends Page {
    public Register() {
        type = PageType.Register;
        nextPages = new ArrayList<>();
        features = new ArrayList<>(List.of("register"));
    }
}

class HomeAuth extends Page {
    public HomeAuth() {
        type = PageType.HomeAuth;
        nextPages = new ArrayList<>(List.of(PageType.Logout,PageType.Movies, PageType.Upgrades));
        features = new ArrayList<>();
    }
}

class Movies extends Page {
    public Movies() {
        type = PageType.Movies;

        nextPages = new ArrayList<>(List.of(PageType.HomeNotAuth,PageType.SeeDetails, PageType.Logout));
        features = new ArrayList<>(List.of("search", "filter"));

        Viewmodel.getInstance().loadMovies();
    }
}

class Upgrades extends Page {
    public Upgrades() {
        type = PageType.Upgrades;

        nextPages = new ArrayList<>(List.of(PageType.HomeAuth,PageType.Movies, PageType.Logout));
        features = new ArrayList<>(List.of("buy premium account", "buy tokens"));
    }
}

class SeeDetails extends Page {
    public SeeDetails() {
        type = PageType.SeeDetails;
        nextPages = new ArrayList<>(List.of(PageType.HomeAuth,PageType.Movies, PageType.Upgrades, PageType.Logout));
        features = new ArrayList<>(List.of("purchase")); // TODO: ADD WATCH, LIKE, RATE MOVIE
    }

}

class Logout extends Page {
    public Logout() {
        type = PageType.Logout;
        nextPages = new ArrayList<>();
        features = new ArrayList<>();
    }
}


