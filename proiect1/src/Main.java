import model.Model;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Model model = new Model(args[0], args[1]);
        model.solveInput();
    }
}
