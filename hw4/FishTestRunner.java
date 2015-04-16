import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class FishTestRunner
{
  public static void main(String[] args)
  {
    Result result = JUnitCore.runClasses(FishTests.class);

    System.out.println("JUnit Test Results:");
    for(Failure failure : result.getFailures())
    {
      System.out.println(failure.toString());
    }

    System.out.println("Total number of tests: " + result.getRunCount());
    System.out.println("Total number of failures: " + result.getFailureCount());
    if(result.wasSuccessful())
    {
      System.out.println("All tests were successful!");
    }
  }
}
