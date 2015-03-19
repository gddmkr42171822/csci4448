/**
* Submission.java
*
* A representation of a Submission
* Sources: Head First Desgin Patters pp. 57-60
*/

import java.util.Random;
import java.util.ArrayList;

public class Submission implements Subject
{	
    private Random myRandom;
	private boolean lastErrorWasTimeout;
	
	// You may add attributes to this class if necessary
	private ArrayList<Observer> observers;
	
	public Submission()
	{
	    this.myRandom = new Random();
		this.lastErrorWasTimeout = false;
		this.observers = new ArrayList<Observer>();
		
	}
	
	public void registerObserver(Observer o){
		this.observers.add(o);
	}
	
	public void notifyObservers(Boolean passed, Boolean timeout){
		for (Observer observer : this.observers){
			observer.update(passed, timeout);
		}
	}

    public void runTestCase()
	{
	    // For now, randomly pass or fail, possibly due to a timeout
		
		boolean passed = myRandom.nextBoolean();
		if(!passed)
		{
		    this.lastErrorWasTimeout = myRandom.nextBoolean();
		}
		
		// You can add to the end of this method for reporting purposes		
		this.notifyObservers(passed, this.wasTimeoutError());
		
	}
	
    public boolean wasTimeoutError()
	{
	    return this.lastErrorWasTimeout;
	}
	
}
