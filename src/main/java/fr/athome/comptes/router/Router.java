package fr.athome.comptes.router;

import fr.athome.comptes.models.store.State;
import fr.athome.comptes.models.store.Store;
import fr.athome.comptes.ui.compteForm.CompteFormPresenter;
import fr.athome.comptes.ui.compteForm.CompteView;
import fr.athome.comptes.ui.compteTable.CompteTablePresenter;
import fr.athome.comptes.ui.compteTable.CompteTableView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class Router {
    @FXML
    private BorderPane mainPanel;

    @FXML
    private void initialize(){
        Store.getInstance().subscribe(State.Selector.ROUTE_SELECTOR, o -> {
            navigateTo((Route) o);
        });
    }

    private void navigateTo(Route route) {
        FXMLLoader loader = new FXMLLoader();
        switch (route){
            case COMPTE_FORM:
                loader.setLocation(getClass().getResource("/fxml/compte-form.fxml"));
                try {
                    Parent parent = loader.load();
                    mainPanel.setCenter(parent);
                    CompteView compteView = loader.getController();
                    compteView.setPresenter(CompteFormPresenter.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case COMPTE_LIST:
                loader.setLocation(getClass().getResource("/fxml/compte-table.fxml"));
                try {
                    Parent parent = loader.load();
                    mainPanel.setCenter(parent);
                    CompteTableView view = loader.getController();
                    view.setPresenter(CompteTablePresenter.getInstance());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
