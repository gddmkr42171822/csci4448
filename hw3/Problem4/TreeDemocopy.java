import java.util.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.*;

interface Terrain
{
	void draw(Graphics graphics, int x, int y);
}
class Tree implements Terrain
{
	private int x;
	private int y;
	private Image image;
	public Tree(String type)
	{
		System.out.println("Creating a new instance of a tree of type " + type);
		String filename = "tree" + type + ".png";
		try
		{
			image = ImageIO.read(new File(filename));
		} catch(Exception exc) { }
	}
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public int getX() { return x; }
	public int getY() { return y; }
	@Override
	public void draw(Graphics graphics, int x, int y)
	{
		graphics.drawImage(image, x, y, null);
	}
}
class TreeFactory
{
	private static final ArrayList<Tree> mylist = new ArrayList<Tree>();
	public static Terrain getTree(String type)
	{
		Tree tree = new Tree(type);
		mylist.add(tree);
		return tree;
   }
}
/**
 * Don’t change anything in TreeDemo
 */
class TreeDemo extends JPanel
{
	private static final int width = 800;
	private static final int height = 700;
	private static final int numTrees = 50;
	private static final String type[] = { "Apple", "Lemon", "Blob", "Elm", "Maple" };

	public void paint(Graphics graphics)
	{
		for(int i=0; i < numTrees; i++) 
		{
			Tree tree = (Tree)TreeFactory.getTree(getRandomType());
			tree.draw(graphics, getRandomX(width), getRandomY(height));
		}
	}
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame();
		frame.add(new TreeDemo());
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private static String getRandomType() 
	{
		return type[(int)(Math.random()*type.length)];
	}
	private static int getRandomX(int max) 
	{
		return (int)(Math.random()*max );
	}
	private static int getRandomY(int max) 
	{
		return (int)(Math.random()*max);
	}
}

