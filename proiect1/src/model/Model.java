package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.ActionsInput;
import input.Input;
import user.User;
import viewmodel.State;
import viewmodel.Viewmodel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static constants.Constants.*;

public class Model {
    Input input;
    Viewmodel viewmodel;
    State state;
    ObjectMapper mapper;
    ObjectWriter writer;
    ArrayNode output;
    String outFileName;

    public Model(String inName, String outName) throws IOException {
        System.out.println("OUT NAME:" + outName);
        this.mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        this.input = mapper.readValue(new File(inName), Input.class);
        this.output = mapper.createArrayNode();
        this.writer = mapper.writerWithDefaultPrettyPrinter();
        this.outFileName = outName;
        this.viewmodel = Viewmodel.getInstance();
        this.viewmodel.initializeViewmodel(input, output);
        this.state = viewmodel.getState();
    }

    public void solveInput() {
        try {
            for (ActionsInput action : input.getActions()) {
                solveAction(action);
            }
            writer.writeValue(new File(outFileName), output);
        } catch (Exception e) {
            System.out.println("Exception:" + e);
            e.printStackTrace();
        }
    }

    public void solveAction(ActionsInput action) {
        String result = viewmodel.doAction(action);
        System.out.println("result: " + result);
        switch (result) {
            case USER_ALREADY_EXISTS, USER_NOT_FOUND, PAGE_NOT_FOUND, FEATURE_NOT_FOUND -> addDefaultNode(ERROR);
            case SUCCESS_LOGIN , SUCCESS_SEARCH, SUCCESS_PAGE_CHANGE_MOVIES -> addDefaultNode(null);
            case SUCCESS_PAGE_CHANGE -> {}
        }
    }

    public void addDefaultNode(String error) {
        ObjectNode node = mapper.createObjectNode();
        node.put("error", error);
        //TODO: SAME AS BELLOW
        node.putPOJO("currentMoviesList", viewmodel.getArrayCopy(state.movies));
        if (!Objects.equals(error, ERROR) && state.user != null)
            node.putPOJO("currentUser",  new User(state.user) );
        else
            node.putPOJO("currentUser", null);

        output.add(node);
    }


}
