package fr.athome.comptes.models.store;

import fr.athome.comptes.models.EditionMode;
import fr.athome.comptes.models.pojo.Compte;
import fr.athome.comptes.router.Route;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State implements Serializable {

    private List<Compte> comptesList = new ArrayList<>();
    private Route route = Route.COMPTE_FORM;
    private Compte compteFormCurrentCompte = new Compte("", "");
    private EditionMode compteFormEditionMode = EditionMode.CREATE;

    public enum Selector implements Store.Selector {
        COMPTES_LIST_SELECTOR("comptesList"), ROUTE_SELECTOR("route"),
        COMPTE_FORM_CURRENT_COMPTE("compteFormCurrentCompte"),
        COMPTE_FORM_EDITION_MODE("compteFormEditionMode");

        private String propertyName;

        Selector(String propertyName) {
            this.propertyName = propertyName;
        }

        @Override
        public String getPropertyName() {
            return propertyName;
        }
    }

    public State(List<Compte> comptesList, Route route) {
        this.comptesList = comptesList;
        this.route = route;
    }

    public Compte getCompteFormCurrentCompte() {
        return compteFormCurrentCompte;
    }

    public void setCompteFormCurrentCompte(Compte compteFormCurrentCompte) {
        this.compteFormCurrentCompte = compteFormCurrentCompte;
    }

    public List<Compte> getComptesList() {
        return comptesList;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setComptesList(List<Compte> comptesList) {
        this.comptesList = comptesList;
    }

    public EditionMode getCompteFormEditionMode() {
        return compteFormEditionMode;
    }

    public void setCompteFormEditionMode(EditionMode compteFormEditionMode) {
        this.compteFormEditionMode = compteFormEditionMode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(comptesList, state.comptesList) &&
                route == state.route &&
                Objects.equals(compteFormCurrentCompte, state.compteFormCurrentCompte) &&
                compteFormEditionMode == state.compteFormEditionMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(comptesList, route, compteFormCurrentCompte);
    }
}
