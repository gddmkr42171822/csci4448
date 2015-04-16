import junit.framework.TestCase;

public class FishTests extends TestCase
{
  public void testFishCreation() 
  {
    // Create a new fish and report
    FishReport report = new FishReport();
    Fish fish = new Fish(10, 20, report);

    // Make sure the fish is in the correct location!
    assertEquals("New fish should be at x = 10", 10, report.getLocation()[0], 0.001);
    assertEquals("New fish should be at y = 20", 20, report.getLocation()[1], 0.001);
    assertEquals("New fish should be size = 1", 1, report.getSize(), 0.001);
    assertEquals("New fish should have hunger = 0.9", 0.9, report.getHunger(), 0.001); 
  }

  public void testAge()
  {
    // Create a new fish and report
    FishReport report = new FishReport();
    Fish fish = new Fish(10, 20, report);

    // Have the fish age some
    fish.age(2);

    // Check that the fish aged correctly
    assertEquals("After aging, fish size = ", 2.1218, report.getSize(), 0.001);
    assertEquals("After aging, fish hunger = ", 0.5304, report.getHunger(), 0.001);
  }

  public void testSwimTowards()
  {
    // Create a new fish and report
    FishReport report = new FishReport();
    Fish fish = new Fish(10, 20, report);

    // Move the fish
    fish.swimTowards(5,3);

    // Make sure the fish is in the correct location!
    assertEquals("After swimming towards, fish should be at x = 10.2822", 10.2822, report.getLocation()[0], 0.001);
    assertEquals("After swimming towards, fish should be at y = 20.1693", 20.1693, report.getLocation()[1], 0.001);
  }

  public void testSwimAway()
  {
    // Create a new fish and report
    FishReport report = new FishReport();
    Fish fish = new Fish(10, 20, report);

    // Move the fish
    fish.swimAway(5,3);

    // Make sure the fish is in the correct location!
    assertEquals("After swimming away, fish should be at x = 9.7178", 9.7178, report.getLocation()[0], 0.001);
    assertEquals("After swimming away, fish should be at y = 19.8307", 19.8307, report.getLocation()[1], 0.001);
  }

  public void testSwimRandomly()
  {
    // Create a new fish and report
    FishReport report = new FishReport();
    Fish fish = new Fish(10, 20, report);

    // Move the fish
    fish.swimRandomly();

    // Make sure the fish is in the correct location!
    assertEquals("Random swim moves at most 1 unit", 10, report.getLocation()[0], 1.0);
    assertEquals("Random swim moves at most 1 unit", 20, report.getLocation()[1], 1.0);
  }

  public void testMoveStarving()
  {
    // Create a new fish and report
    FishReport report = new FishReport();
    Fish fish = new Fish(10, 20, report);

    // Make a pond
    Pond pond = new Pond();

    // Grow the fish until starving
    fish.age(20);
    fish.age(20);
    fish.age(20);
    fish.age(20);

    // Move the fish, and check the new location
    fish.move(pond);

    // Check the new location
    assertEquals("Starving fish should move", 10.2981, report.getLocation()[0], 0.001);
    assertEquals("Starving fish should move", 20.5963, report.getLocation()[1], 0.001);
  }


  public void testMoveHungry()
  {
    // Create a new fish and report
    FishReport report = new FishReport();
    Fish fish = new Fish(10, 20, report);

    // Make a pond
    Pond pond = new Pond();

    // Grow the fish until hungry
    fish.age(20);
    fish.age(20);

    // Move the fish, and check the new location
    fish.move(pond);

    // Check the new location
    assertEquals("Hungry fish should move", 9.9030, report.getLocation()[0], 0.001);
    assertEquals("Hungry fish should move", 19.9515, report.getLocation()[1], 0.001);
  }


  public void testMoveFull()
  {
    // Create a new fish and report
    FishReport report = new FishReport();
    Fish fish = new Fish(10, 20, report);

    // Make a pond
    Pond pond = new Pond();

    // Move the fish, and check the new location
    fish.move(pond);

    // Check the new location
    assertEquals("Full fish should move", 10, report.getLocation()[0], 0.001);
    assertEquals("Full fish should move", 20, report.getLocation()[1], 0.001);
  }

}

