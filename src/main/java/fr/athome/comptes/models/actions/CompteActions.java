package fr.athome.comptes.models.actions;

import fr.athome.comptes.models.pojo.Compte;
import fr.athome.comptes.models.store.State;
import fr.athome.comptes.models.store.Store;

import java.util.Collection;

public class CompteActions {

    private CompteActions(){};

    public static Store.Action addCompteAction(Compte compte) {
        return state -> {
            state.getComptesList().add(compte);
            return state;
        };
    }

    public static Store.Action deleteCompteAction(Compte compte) {
        return state -> {
            state.getComptesList().remove(compte);
            return state;
        };
    }

    public static Store.Action deleteCompteAction(Collection<Compte> comptes){
        return state -> {
            state.getComptesList().removeAll(comptes);
            return state;
        };
    }

    public static Store.Action editCompteAction(Compte oldCompte, Compte newCompte) {
        return state ->{
            int index = state.getComptesList().indexOf(oldCompte);
            if(index == -1){
                return state;
            } else {
                state.getComptesList().remove(index);
                state.getComptesList().add(index,newCompte);
                return state;
            }
        };
    }
}
