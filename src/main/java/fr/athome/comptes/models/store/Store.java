package fr.athome.comptes.models.store;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Store {
    private static final Store instance = new Store();

    private List<Subscriber> stateObservers = new ArrayList<>();
    private Map<Selector,List<SelectorSubscriber>> subscribersWithSelector = new ConcurrentHashMap<>();

    private State currentState = null;

    public static Store getInstance(){
        return instance;
    }

    private Store(){}

    public void setInitialState(State state){
        this.dispatch(oldState -> state);
    }

    protected State getCurrentState() {
        return currentState;
    }

    public Subscription subscribe(Subscriber onStateChanged){
        this.stateObservers.add(onStateChanged);
        onStateChanged.accept(currentState);
        return () -> stateObservers.remove(onStateChanged);
    }

    public Subscription subscribe(Selector selector, SelectorSubscriber selectorSubscriber){
        List<SelectorSubscriber> subscribers = this.subscribersWithSelector.computeIfAbsent(selector, s -> new ArrayList<>());
        subscribers.add(selectorSubscriber);
        try {
            Field field = State.class.getDeclaredField(selector.getPropertyName());
            field.setAccessible(true);
            Object property = field.get(currentState);
            selectorSubscriber.accept(property);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return () -> subscribers.remove(selectorSubscriber);
    }

    public void dispatch(Action action){
        State oldState = this.currentState;
        State cloneOldState = cloneState(this.currentState);
        State newState = cloneState(action.call(cloneOldState));
        this.currentState = newState;
        notifyObservers(oldState, newState);
    }

    private void notifyObservers(State oldState, State newState) {
        if(oldState == null && newState == null){
            return;
        }
        if(oldState == null || newState == null) {
            this.stateObservers.forEach(c -> c.accept(newState));
            notifySelectorSubscribers(oldState,newState);
        } else if(!newState.equals(oldState)){
            this.stateObservers.forEach(c -> c.accept(newState));
            notifySelectorSubscribers(oldState,newState);
        }
    }

    private void notifySelectorSubscribers(State oldState, State newState) {
        for(Selector selector : this.subscribersWithSelector.keySet()){
            try {
                Field field = State.class.getDeclaredField(selector.getPropertyName());
                field.setAccessible(true);
                Object oldValue = field.get(oldState);
                Object newValue = field.get(newState);
                if(oldValue == null && newValue == null){
                    continue;
                }else if(oldValue == null || newValue == null){
                    this.subscribersWithSelector.get(selector).forEach(c -> c.accept(newValue));
                }else if(!oldValue.equals(newValue)){
                    this.subscribersWithSelector.get(selector).forEach(c -> c.accept(newValue));
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public interface Subscription{
        void unsubscribe();
    }

    public interface Action {
        State call(State oldState);
    }

    public interface Subscriber extends Consumer<State>{}

    public interface SelectorSubscriber extends Consumer<Object> {}

    protected interface Selector {
        String getPropertyName();
    }

    private State cloneState(State state){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(state);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (State) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
