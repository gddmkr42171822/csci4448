/*
* Passed_Report.java
*
*
*/

public class PassedReport implements Observer{
	private int numTestsPassed;
	private int numTestsFailed;
	private Subject submission;
	
	public PassedReport(Subject s){
		this.submission = s;
		this.numTestsPassed = 0;
		this.numTestsFailed = 0;
		s.registerObserver(this);
	}
	
	public void update (Boolean passed, Boolean timeout){
		if(passed == true){
			this.numTestsPassed += 1;
		}else{
			this.numTestsFailed += 1;
		}
	}
	
	public void totalPassed(){
		System.out.printf("\nTotal tests passed |%d|", this.numTestsPassed);
	}
	
	public void totalFailed(){
		System.out.printf("\nTotal tests failed |%d|", this.numTestsFailed);
	}
}