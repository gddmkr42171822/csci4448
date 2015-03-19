/*
* TimeOutReport.java
*
*
*/
public class TimeOutReport implements Observer{
	private int numTimeOuts;
	private Subject submission;
	
	public TimeOutReport(Subject s){
		this.submission = s;
		this.numTimeOuts = 0;
		s.registerObserver(this);
	}
	
	public void update(Boolean passed, Boolean lastErrorWasTimeout){
		if(lastErrorWasTimeout == true){
			this.numTimeOuts += 1;
		}
	}
	
	public void totalTimeOuts(){
		System.out.printf("\nTotal timeouts |%d|", this.numTimeOuts);
	}
}