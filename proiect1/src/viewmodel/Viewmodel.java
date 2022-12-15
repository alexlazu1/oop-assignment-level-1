package viewmodel;

import input.Input;

public class Viewmodel {
    Input input;
    public Viewmodel(Input input){
        this.input = input;
        System.out.println(input);
    }
}
