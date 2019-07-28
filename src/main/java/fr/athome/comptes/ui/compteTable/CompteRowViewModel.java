package fr.athome.comptes.ui.compteTable;

import fr.athome.comptes.models.pojo.Compte;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

public class CompteRowViewModel {
    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private BooleanProperty selected = new SimpleBooleanProperty(false);
    private Compte compte;

    public CompteRowViewModel(Compte compte){
        this.compte = compte;
        this.password.set(compte.getPassword());
        this.username.set(compte.getUsername());
    }

    public ObservableValue<String> usernameProperty() {
        return username;
    }

    public ObservableValue<String> passwordProperty() {
        return password;
    }

    public Compte getCompte() {
        return compte;
    }

    public ObservableValue<Boolean> selectedProperty() {
        return selected;
    }
}
