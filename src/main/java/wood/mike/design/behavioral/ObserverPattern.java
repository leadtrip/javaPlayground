package wood.mike.design.behavioral;

import java.util.*;

/**
 * Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.
 */
public class ObserverPattern {
    public static void main(String[] args) {
        InterestingTopic topic = new InterestingTopic();

        Observer obj1 = new InterestingTopicSubscriber("IT1");
        Observer obj2 = new InterestingTopicSubscriber("IT2");
        Observer obj3 = new InterestingTopicSubscriber("IT3");

        topic.register(obj1);
        topic.register(obj2);
        topic.register(obj3);

        obj1.setSubject(topic);
        obj2.setSubject(topic);
        obj3.setSubject(topic);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int executions = 0;
            @Override
            public void run() {
                topic.broadcast( UUID.randomUUID().toString() );
                switch (executions) {
                    case 1 -> topic.unregister(obj1);
                    case 3 -> {
                        topic.unregister(obj2);
                        topic.register(obj1);
                        topic.register(obj1);
                    }
                    case 4 -> topic.unregister(obj3);
                    case 5 -> timer.cancel();
                }
                executions++;
            }
        }, 0, 1000);
    }
}

// The subject
interface Subject{
    void register(Observer obj);
    void unregister(Observer obj);
    void notifyObservers();
    Object getUpdate(Observer obj);
}

// The observer
interface Observer {
    void update();
    void setSubject(Subject sub);
}

// Concrete subject
class InterestingTopic implements Subject {

    private List<Observer> observers;
    private String message;

    public InterestingTopic() {
        this.observers = new ArrayList<>();
    }

    @Override
    public void register(Observer obj) {
        if ( !observers.contains(obj) )
            observers.add(obj);
    }

    @Override
    public void unregister(Observer obj) {
        System.out.printf("Unregistered %s%n", obj);
        observers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    @Override
    public Object getUpdate(Observer obj) {
        return message;
    }

    public void broadcast( String msg ) {
        System.out.printf("Broadcasting %s to %s observers%n", msg, observers.size() );
        message = msg;
        notifyObservers();
    }
}

// Concrete subscriber
class InterestingTopicSubscriber implements Observer {

    private String name;
    private Subject topic;

    public InterestingTopicSubscriber(String nm){
        this.name = nm;
    }

    @Override
    public void update() {
        System.out.printf( "Received message: %s%n", topic.getUpdate(this) );
    }

    @Override
    public void setSubject(Subject sub) {
        this.topic = sub;
    }

}
