/*
* Auto_Grader_Driver.java
*
*
*/
public class AutoGraderDriver{
	public static void main(String [] args){
	
		Submission s = new Submission();
		PassedReport pr = new PassedReport(s);
		TimeOutReport tor = new TimeOutReport(s);
		
		for(int i = 0; i < 10; i++){
			s.runTestCase();
		}
		
		pr.totalPassed();
		pr.totalFailed();
		tor.totalTimeOuts();
		
	}
}