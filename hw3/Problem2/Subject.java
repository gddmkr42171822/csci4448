/*
* Subject.java
*
*
*/

public interface Subject{
	public void registerObserver(Observer o);
	public void notifyObservers(Boolean passed, Boolean timeout);
}