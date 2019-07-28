package fr.athome.comptes.ui.compteForm;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CompteView {

    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField usernameTextField;

    private CompteFormPresenter presenter;

    public void setPresenter(CompteFormPresenter presenter){
        this.presenter = presenter;
        passwordTextField.textProperty().bindBidirectional(presenter.passwordProperty());
        usernameTextField.textProperty().bindBidirectional(presenter.usernameProperty());
    }

    public void onValidate(){
        presenter.validate();
    }

    public void onCancel(){
        presenter.cancel();
    }
}
