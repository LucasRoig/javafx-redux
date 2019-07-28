package fr.athome.comptes.models.actions;

import fr.athome.comptes.models.EditionMode;
import fr.athome.comptes.models.pojo.Compte;
import fr.athome.comptes.models.store.Store;
import fr.athome.comptes.router.Route;

public class RouterActions {
    private RouterActions(){};

    public static Store.Action openNewCompteForm(){
        return state -> {
            state.setCompteFormCurrentCompte(new Compte("", ""));
            state.setCompteFormEditionMode(EditionMode.CREATE);
            state.setRoute(Route.COMPTE_FORM);
            return state;
        };
    }

    public static Store.Action openCompteList(){
        return state -> {
            state.setRoute(Route.COMPTE_LIST);
            return state;
        };
    }

    public static Store.Action openEditCompteForm(Compte compte){
        return state -> {
            state.setCompteFormCurrentCompte(compte);
            state.setRoute(Route.COMPTE_FORM);
            state.setCompteFormEditionMode(EditionMode.EDIT);
            return state;
        };
    }
}
