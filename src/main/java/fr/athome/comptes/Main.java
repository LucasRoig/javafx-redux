package fr.athome.comptes;

import fr.athome.comptes.models.actions.RouterActions;
import fr.athome.comptes.models.store.State;
import fr.athome.comptes.models.store.Store;
import fr.athome.comptes.router.Route;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Store.getInstance().setInitialState(new State(new ArrayList<>(), Route.HOME));
        Store.getInstance().dispatch(RouterActions.openCompteList());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main-window.fxml"));
        try {
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root,600,600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
