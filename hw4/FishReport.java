/**
* FishReport
*
* An object individual fish update whenever their status changes
*/

public class FishReport implements Observer
{
  private double hunger;
  private double size;
  private double x;
  private double y;

  public FishReport()
  {
    hunger = 0;
    size = 0;
    x = 0;
    y = 0;
  }

 
  public void updateHunger(double hunger)
  {
    this.hunger = hunger;
  }

  public void updateSize(double size)
  {
    this.size = size;
  }

  public void updateLocation(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public double getHunger()
  {
    return hunger;
  } 

  public double getSize()
  {
    return size;
  }

  public double[] getLocation()
  {
    double location[] = {x, y};
    return location;
  }

  public void update(Fish fish){
    updateHunger(fish.getHunger());
    updateSize(fish.getSize());
    updateLocation(fish.getX(), fish.getY());
  }
}

interface Observer{
  public void update(Fish fish);
}
