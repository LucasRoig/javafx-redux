package fr.athome.comptes.ui.compteTable;

import fr.athome.comptes.models.actions.CompteActions;
import fr.athome.comptes.models.actions.RouterActions;
import fr.athome.comptes.models.pojo.Compte;
import fr.athome.comptes.models.store.State;
import fr.athome.comptes.models.store.Store;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class CompteTablePresenter {
    private static CompteTablePresenter instance = new CompteTablePresenter();
    private ObservableList<CompteRowViewModel> compteRows = FXCollections.observableArrayList();

    public static CompteTablePresenter getInstance(){
        return instance;
    }

    private CompteTablePresenter(){
        Store.getInstance().subscribe(State.Selector.COMPTES_LIST_SELECTOR, o -> {
            List<Compte> comptes = (List<Compte>) o;
            compteRows.clear();
            compteRows.addAll(comptes.stream().map(CompteRowViewModel::new).collect(Collectors.toList()));
        });
    };

    public ObservableList<CompteRowViewModel> getCompteRows() {
        return compteRows;
    }

    public void openNewCompteView() {
        Store.getInstance().dispatch(RouterActions.openNewCompteForm());
    }

    public void delete() {
        Store.getInstance().dispatch(CompteActions.deleteCompteAction(compteRows.stream().filter(row -> row.selectedProperty().getValue()).map(row -> row.getCompte()).collect(Collectors.toList())));
    }

    public void edit(Compte compte) {
        Store.getInstance().dispatch(RouterActions.openEditCompteForm(compte));
    }
}
