public interface IGameObserver {
	// Method which is invoked when an asteroid hits ground(ie.., when subject is notified)
	public abstract void Update(BoardComponent asteroidHit);

}