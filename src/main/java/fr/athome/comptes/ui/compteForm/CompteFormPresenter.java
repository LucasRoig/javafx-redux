package fr.athome.comptes.ui.compteForm;

import fr.athome.comptes.models.EditionMode;
import fr.athome.comptes.models.actions.CompteActions;
import fr.athome.comptes.models.actions.RouterActions;
import fr.athome.comptes.models.pojo.Compte;
import fr.athome.comptes.models.store.State;
import fr.athome.comptes.models.store.Store;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CompteFormPresenter {
    private static CompteFormPresenter instance = new CompteFormPresenter();

    private StringProperty username = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private Compte initialCompte;
    private EditionMode editionMode;
    private Store store = Store.getInstance();

    public static CompteFormPresenter getInstance() {
        return instance;
    }

    private CompteFormPresenter() {
        store.subscribe(State.Selector.COMPTE_FORM_CURRENT_COMPTE, o -> {
            Compte compte = (Compte) o;
            if(compte != null){
                username.set(compte.getUsername());
                password.set(compte.getPassword());
                initialCompte = compte;
            }
        });

        store.subscribe(State.Selector.COMPTE_FORM_EDITION_MODE, o -> {
            editionMode = (EditionMode) o;
        });
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void validate(){
        Compte compte = new Compte(username.getValue(),password.getValue());
        if(editionMode == EditionMode.CREATE){
           store.dispatch(CompteActions.addCompteAction(compte));
        } else if(editionMode == EditionMode.EDIT){
            store.dispatch(CompteActions.editCompteAction(initialCompte, compte));
        }
        //
        store.dispatch(store -> {
            store.setCompteFormCurrentCompte(null);
            return store;
        });
        resetForm();
        store.dispatch(RouterActions.openCompteList());
    }

    public void cancel(){
        resetForm();
        store.dispatch(RouterActions.openCompteList());
    }

    private void resetForm(){
        username.set("");
        password.set("");
        store.dispatch(state -> {
            state.setCompteFormCurrentCompte(null);
            return state;
        });
    }
}
