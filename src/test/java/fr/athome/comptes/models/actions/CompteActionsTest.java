package fr.athome.comptes.models.actions;

import fr.athome.comptes.models.CompteUtils;
import fr.athome.comptes.models.pojo.Compte;
import fr.athome.comptes.models.store.State;
import fr.athome.comptes.models.store.Store;
import fr.athome.comptes.router.Route;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CompteActionsTest {
    private Store store = Store.getInstance();
    private final State emptyState = new State(new ArrayList<>(), Route.HOME);

    @Before
    public void setUp() throws Exception {
        store.setInitialState(emptyState);
    }

    @Test
    public void addCompteActionAddsACompteInTheStore(){
        Compte compte = CompteUtils.validCompte();
        Store.Action action = CompteActions.addCompteAction(compte);

        Store.Subscriber subscriber = mock(Store.Subscriber.class);
        doNothing().doAnswer(invocationOnMock -> {
            State state = invocationOnMock.getArgument(0, State.class);
            Assert.assertTrue(state.getComptesList().contains(compte));
            return null;
        }).when(subscriber).accept(any(State.class));
        Store.Subscription subscription = store.subscribe(subscriber);
        store.dispatch(action);

        verify(subscriber,times(2)).accept(any(State.class));
        subscription.unsubscribe();
    }

    @Test
    public void deleteCompteActionDeletesACompteInTheStore(){
        Compte compte = CompteUtils.validCompte();
        store.dispatch(state -> {
            state.getComptesList().add(compte);
            return state;
        });

        Store.Action action = CompteActions.deleteCompteAction(compte);

        Store.Subscriber subscriber = mock(Store.Subscriber.class);
        doNothing().doAnswer(invocationOnMock -> {
            State state = invocationOnMock.getArgument(0, State.class);
            assertEquals(0, state.getComptesList().size());
            return null;
        }).when(subscriber).accept(any(State.class));

        Store.Subscription subscription = store.subscribe(subscriber);
        store.dispatch(action);

        verify(subscriber,times(2)).accept(any(State.class));
        subscription.unsubscribe();
    }

    @Test
    public void editCompteActionEditsACompteInTheStore(){
        Compte compte = CompteUtils.validCompte();
        store.dispatch(state -> {
            state.getComptesList().add(compte);
            return state;
        });

        Compte newCompte = CompteUtils.validCompte();
        newCompte.setUsername("newUser");

        Store.Action action = CompteActions.editCompteAction(compte, newCompte);

        Store.Subscriber subscriber = mock(Store.Subscriber.class);
        doNothing().doAnswer(invocationOnMock -> {
            State state = invocationOnMock.getArgument(0, State.class);
            assertTrue(state.getComptesList().contains(newCompte));
            assertEquals(1, state.getComptesList().size());
            return null;
        }).when(subscriber).accept(any(State.class));

        Store.Subscription subscription = store.subscribe(subscriber);
        store.dispatch(action);

        verify(subscriber,times(2)).accept(any(State.class));
        subscription.unsubscribe();
    }
}
