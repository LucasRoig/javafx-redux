package fr.athome.comptes.models.store;

import fr.athome.comptes.models.CompteUtils;
import fr.athome.comptes.router.Route;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class StoreTest {
    private Store store = Store.getInstance();

    private final State emptyState = new State(new ArrayList<>(), Route.HOME);

    @Before
    public void setUp(){
        store.setInitialState(emptyState);
    }

    @Test
    public void itShouldNotifySubscriberWhenStateChange(){
        Store.Subscriber subscriber = createSubscriber();
        Store.Subscription subscription = store.subscribe(subscriber);

        store.dispatch(state -> {
            state.getComptesList().add(CompteUtils.validCompte());
            return state;
        });

        Mockito.verify(subscriber, times(2)).accept(Mockito.any(State.class));
        subscription.unsubscribe();
    }

    @Test
    public void itShouldNotifySubscriberWhenInitialStateIsSet(){
        Store.Subscriber subscriber = createSubscriber();
        Store.Subscription subscription = store.subscribe(subscriber);

        store.setInitialState(new State(Collections.singletonList(CompteUtils.validCompte()),Route.HOME));

        Mockito.verify(subscriber,times(2)).accept(Mockito.any(State.class));
        subscription.unsubscribe();
    }

    @Test
    public void itShouldNotCallSubscriberWhenStateDoesNotChange(){
        Store.Subscriber subscriber = createSubscriber();
        Store.Subscription subscription = store.subscribe(subscriber);

        store.dispatch(state -> state);

        Mockito.verify(subscriber,times(1)).accept(Mockito.any(State.class));
        subscription.unsubscribe();
    }

    @Test
    public void itShouldCallSelectorSubscriber(){
        Store.SelectorSubscriber selectorSubscriber = Mockito.mock(Store.SelectorSubscriber.class);
        Mockito.doNothing().when(selectorSubscriber).accept(Mockito.any(Object.class));
        Store.Subscription subscription = store.subscribe(State.Selector.COMPTES_LIST_SELECTOR, selectorSubscriber);

        store.dispatch(state -> {
            state.getComptesList().add(CompteUtils.validCompte());
            return state;
        });

        Mockito.verify(selectorSubscriber,times(2)).accept(Mockito.any(Object.class));
        subscription.unsubscribe();
    }

    @Test
    public void itShouldNotCallSelectorSubscriberIfAnotherPropertyChanged(){
        Store.SelectorSubscriber selectorSubscriber = Mockito.mock(Store.SelectorSubscriber.class);
        Store.Subscription subscription = store.subscribe(State.Selector.COMPTES_LIST_SELECTOR, selectorSubscriber);

        store.dispatch(state -> {
            state.setRoute(Route.COMPTE_FORM);
            return state;
        });

        Mockito.verify(selectorSubscriber,times(1)).accept(Mockito.any(Object.class));
        subscription.unsubscribe();
    }

    private Store.Subscriber createSubscriber(){
        Store.Subscriber subscriber = Mockito.mock(Store.Subscriber.class);
        Mockito.doNothing().when(subscriber).accept(Mockito.any(State.class));
        return subscriber;
    }

}
