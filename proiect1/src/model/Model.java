package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import input.ActionsInput;
import input.Input;
import page.PageFactory;
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
        if(state.page.getType() == PageFactory.PageType.SeeDetails)
            System.out.println("result (" + state.page.getType() + "): " + result);
        switch (result) {
            case USER_ALREADY_EXISTS,
                    ERROR_PURCHASE_ALREADY_BOUGHT,
                    ERROR_PURCHASE_CURRENCY,
                    ERROR_WATCH_ALREADY_WATCHED,
                    ERROR_WATCH_NOT_PURCHASED,
                    ERROR_LIKE_NOT_WATCHED,
                    ERROR_RATE_NOT_WATCHED,
                    ERROR_BUY_PREMIUM,
                    ERROR_BUY_TOKENS,
                    USER_NOT_FOUND,
                    PAGE_NOT_FOUND,
                    FEATURE_NOT_FOUND,
                    MOVIE_NOT_FOUND,
                    ERROR_RATE_INVALID_RATE-> addDefaultNode(ERROR);
            case SUCCESS_LOGIN,
                    SUCCESS_SEARCH,
                    SUCCESS_PAGE_CHANGE_MOVIES,
                    SUCCESS_SEE_DETAILS,
                    SUCCESS_FILTER,
                    SUCCESS_PURCHASE_MOVIE,
                    SUCCESS_WATCH_MOVIE,
                    SUCCESS_LIKE_MOVIE,
                    SUCCESS_RATE_MOVIE-> addDefaultNode(null);
            case SUCCESS_PAGE_CHANGE, SUCCESS_BUY_TOKENS, SUCCESS_BUY_PREMIUM -> {
            }
        }
    }

    public void addDefaultNode(String error) {
        ObjectNode node = mapper.createObjectNode();
        node.put("error", error);
        //TODO: SAME AS BELLOW
        if (error == null) {
//            System.out.println("\n\nmovies: " + state.movies + "\n\n");
            node.putPOJO("currentMoviesList", viewmodel.getArrayCopy(state.movies));
        }
        else
            node.putPOJO("currentMoviesList", new ArrayList<>());

        if (!Objects.equals(error, ERROR) && state.user != null)
            node.putPOJO("currentUser", new User(state.user));
        else
            node.putPOJO("currentUser", null);

        output.add(node);
    }


}
