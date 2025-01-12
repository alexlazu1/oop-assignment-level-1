package input;

public final class Credentials {
    private String name;
    private String password;
    private String accountType;
    private String country;
    private String balance;

    public Credentials() {}

    public Credentials(Credentials credentials) {
        this.name = credentials.getName();
        this.password = credentials.getPassword();
        this.accountType = credentials.getAccountType();
        this.country = credentials.getCountry();
        this.balance = credentials.getBalance();
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(final String balance) {
        this.balance = balance;
    }
    public void changeBalance(int balance) {
        int currentBalance = Integer.parseInt(this.balance);
        currentBalance -= balance;
        this.balance = String.valueOf(currentBalance);
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", accountType=" + accountType +
                ", country='" + country + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}