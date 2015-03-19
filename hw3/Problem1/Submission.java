/**
* Submission.java
*
* A basic dummy Submission object for use with the auto-grader
*/

import java.util.Random;

public class Submission
{
  private static Random rand = new Random();
  private int id;

  public Submission()
  {
    // Give this submission a unique(ish) id
    this.id = rand.nextInt(10000000);
  }  
  
  public int printID(){
	  return this.id;
  }
}

