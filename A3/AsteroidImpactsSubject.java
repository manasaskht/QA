import java.util.ArrayList;
import java.util.List;

public class AsteroidImpactsSubject implements IAsteroidSubject {

    List<BoardComponent> observers = null;

    public AsteroidImpactsSubject() {
        observers = new ArrayList<BoardComponent>();
    }

    @Override
    public void attachObserver(BoardComponent observer) {

        observers.add(observer);

    }

    @Override
    public void detachObserver(BoardComponent observer) {
        observers.remove(observer);

    }
    @Override
    public void notifyObservers(BoardComponent observer) {

        for(int i=0; i < observers.size(); i++) {

            observers.get(i).Update(observer);
        }
    }

}