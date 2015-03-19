/*
* SubmissionQueue.java
*
* Queue class that adds and pops Submission objects
*/

import java.util.ArrayList;

public class SubmissionQueue{
	private static SubmissionQueue submissionQueue;
	private ArrayList<Submission> fifoQueue;
	
	private SubmissionQueue(){
		this.fifoQueue = new ArrayList<Submission>();
	}
	
	public static SubmissionQueue getInstance(){
		if(submissionQueue == null){
			submissionQueue = new SubmissionQueue();
		}
		return submissionQueue;
	}
	
	public void add(Submission s){
		this.fifoQueue.add(s);
		System.out.printf("\nSize of queue after adding submission id |%d| is |%d|", s.printID(), this.fifoQueue.size());
	}
	
	public void process(){
		if(this.fifoQueue.size() > 0){
			System.out.printf("\nRemoving submission |%d| from queue", this.fifoQueue.get(0).printID());
			this.fifoQueue.remove(0);
			System.out.printf("\nSize of queue after removing a submission |%d|", this.fifoQueue.size());
		}
	}
}