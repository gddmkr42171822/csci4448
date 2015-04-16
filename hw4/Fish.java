/**
* Fish
*
* A single fish in the simulation
*/

import java.lang.Math;
import java.util.Random;
import java.util.ArrayList;

public class Fish implements Subject
{

  private static Random random = new Random();
  private static int numberOfFish = 0;

  static final double INITIAL_HUNGER = 0.9;
  static final double INITIAL_SIZE = 1.0;
  static final double STARVING_THRESHOLD = 0.2;
  static final double HUNGER_THRESHOLD = 0.7;
  static final double SAFE_SIZE = 5.0;
  static final double BIG_FISH = 7.0;

  MoveStrategy starvingSmallFishStrategy;
  MoveStrategy starvingBigFishStrategy;
  MoveStrategy hungryBigFishStrategy;
  MoveStrategy hungrySmallFishStrategy;
  MoveStrategy fullFishStrategy;

  private double hunger;            // A value between 0 (hungry) and 1 (full)
  private double size;
  private double x;                 // Where the fish is
  private double y;
  private int id; 
  private MoveStrategy strategy;

  // Don't want to be able to directly query the fish for information, but do 
  // need to get information for logging or displaying on a GUI, etc.

  private ArrayList<Observer> observers;

  public Fish(double x, double y, FishReport report)
  {
    // A fish is born!
    hunger = INITIAL_HUNGER;
    size = INITIAL_SIZE;

    // Put it in the pond
    this.x = x;
    this.y = y;

    // And give it an id
    id = numberOfFish;
    numberOfFish++;

    // Add and notify observers
    observers = new ArrayList<Observer>();
    registerObserver(report);
    notifyObservers();
    
    // Initialize strategies
    starvingSmallFishStrategy = new StarvingSmallFishStrategy();
    starvingBigFishStrategy = new StarvingBigFishStrategy();
    hungryBigFishStrategy = new HungryBigFishStrategy();
    hungrySmallFishStrategy = new HungrySmallFishStrategy();
    fullFishStrategy = new FullFishStrategy();
    // Set initial strategy
    updateMoveStrategy();
  }

  public void registerObserver(Observer o){
    observers.add(o);
  }

  public void notifyObservers(){
    for(Observer o : observers){
      o.update(this);
    }
  }

  public double getSize()           
  {
    return size;
  } 

  private void updateMoveStrategy(){
    if(hunger < STARVING_THRESHOLD){
      if(size < BIG_FISH){
        strategy = starvingSmallFishStrategy;
      }else{
        strategy = starvingBigFishStrategy;
      }
    }else{ 
      if(hunger < HUNGER_THRESHOLD){
        if(size > SAFE_SIZE){
          strategy = hungryBigFishStrategy;
        }else{
          strategy = hungrySmallFishStrategy;
        }
      }else{
        strategy = fullFishStrategy;
      }
    }
  }

  public double getX(){
    return this.x;
  }

  public double getY(){
    return this.y;
  }

  public double getHunger(){
    return this.hunger;
  }
  
  public void age(double timePassed)
  {
    // Growth is based on how much time has passed and how much food
    // has been eaten
    double deltaSize = size * (1 + hunger * Math.exp(-size * timePassed));
    size = size + deltaSize;

    // If the fish grows a lot (relative to the size change), lots of food
    // has been consumed
    hunger = hunger * Math.exp(-deltaSize/size);

    notifyObservers();
    updateMoveStrategy();   
  }

  public void move(Pond pond)
  {
    strategy.move(this, pond);
  }

  // Swim towards a location
  public void swimTowards(double tx, double ty)
  {
    double distance = Math.sqrt((tx - x)*(tx - x) + (ty - y)*(ty - y));
    x = x + (tx/distance);
    y = y + (ty/distance);

    notifyObservers();
  }


  // Swim away from a location
  public void swimAway(double tx, double ty)
  {
    double distance = Math.sqrt((tx - x)*(tx - x) + (ty - y)*(ty - y));
    x = x - (tx/distance);
    y = y - (ty/distance);

    notifyObservers();
  }


  // Just swim around
  public void swimRandomly()
  {
    System.out.println("FISH #" + id + ": I'm swimming around!");

    x = x + random.nextDouble();
    y = y + random.nextDouble();

    notifyObservers();
  }

  // Just let the world know I hid!
  public void hide()
  {
    System.out.println("FISH #" + id + ": I'm hiding!");
  }
}

abstract class MoveStrategy{
  abstract void move(Fish fish, Pond pond);
}

class HungryBigFishStrategy extends MoveStrategy{
  void move(Fish fish, Pond pond){
    fish.swimRandomly();
  }
}

class HungrySmallFishStrategy extends MoveStrategy{
  void move(Fish fish, Pond pond){
    double[] location = pond.findNearestBigFish(fish.getX(), fish.getY());
    fish.swimAway(location[0], location[1]);
  }
}

class StarvingSmallFishStrategy extends MoveStrategy{
  void move(Fish fish, Pond pond){
    double[] location = pond.findNearestPlant(fish.getX(), fish.getY());
    fish.swimTowards(location[0], location[1]);
  }
}

class StarvingBigFishStrategy extends MoveStrategy{
  void move(Fish fish, Pond pond){
    double[] location = pond.findNearestSmallFish(fish.getX(), fish.getY());
    fish.swimTowards(location[0], location[1]);
  }
}

class FullFishStrategy extends MoveStrategy{
  void move(Fish fish, Pond pond){
    fish.hide();
  }
}

interface Subject{
  public void registerObserver(Observer o);
  public void notifyObservers();
}
