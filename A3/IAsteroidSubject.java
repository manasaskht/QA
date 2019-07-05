public interface IAsteroidSubject {

    // Registring an observer to the subject.So,that observer will get notification when the subject is notified
    public void attachObserver(BoardComponent observer);

    //  Unregistring an observer. So,that observer will no longer get notification when subject is notified.
    public void detachObserver(BoardComponent observer);

    // To notify all observers in the observer collection when subject is notified.
    public void notifyObservers(BoardComponent observer);
}