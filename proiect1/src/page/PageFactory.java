package page;

public class PageFactory {
    public enum PageType {
        HomeAuth("home auth"),
        HomeNotAuth("home not aut"),
        Login("login"),
        Logout("logout"),
        Movies("movies"), Register("register"),
        SeeDetails("see details"),
        Upgrades("upgrades");

        private String name;

        PageType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static Page createPage(PageType pageType) {
        switch (pageType) {
            case HomeNotAuth:
                return new HomeNotAuth();
            case Login:
                return new Login();
            case Register:
                return new Register();
            case HomeAuth:
                return new HomeAuth();
            case Movies:
                return new Movies();
            case Upgrades:
                return new Upgrades();
            case Logout:
                return new Logout();
            case SeeDetails:
                return new SeeDetails();
        }
        throw new IllegalArgumentException("The page type" + pageType + "is not recognised");
    }
}
