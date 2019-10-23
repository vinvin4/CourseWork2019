import javax.swing.*;
import java.util.Random;

public class Libr
{
    final int N = 5; //Number of line in game-map
    final int M = 7; //Number of collums in game-map
    final int K = 5; //Number of score variations
    final int Ncolours = 6; //Number of items variations
    int[] scoreMass;
    int limitScore = 1500;
    ImageIcon[] icon;
    final int DefScore = 10;
    private Random rand;

    private static Libr ourInstance = null;

    public static Libr getInstance() {
        if (ourInstance == null)
        {
            ourInstance = new Libr();
        }
        return ourInstance;
    }

    private Libr() {
        icon = new ImageIcon[Ncolours];
        icon[0]= new ImageIcon("src/images/red_icon.png");
        icon[1]= new ImageIcon("src/images/blue_icon.png");
        icon[2]= new ImageIcon("src/images/green_icon.png");
        icon[3]= new ImageIcon("src/images/yellow_icon.png");
        icon[4]= new ImageIcon("src/images/violet_icon.png");
        icon[5]=new ImageIcon("src/images/dragon.png");

        rand = new Random();

        scoreMass = new int[K];
        for (int i=0; i<K; i++)
        {
            scoreMass[i] = 10;
        }
    }

    public void changeScore (int point, int value)
    {
        scoreMass[point] = scoreMass[point] + value;
    }

    public int getNumber()
    {
        return rand.nextInt(5);
    }
}
