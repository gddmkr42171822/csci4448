/*
* Auto_Grader_Driver.java
*
* Acts like the auto-grader in the write-up
*/

public class AutoGraderDriver{
	public static void main(String [] args){
		Submission s1 = new Submission();
		Submission s2 = new Submission();
		SubmissionQueue queue1 = SubmissionQueue.getInstance();
		SubmissionQueue queue2 = SubmissionQueue.getInstance();
		queue1.add(s1);
		queue2.add(s2);
		queue1.process();
		queue1.process();
		queue1.process();
	}
}