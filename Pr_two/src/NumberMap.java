import javafx.util.Pair;
import java.util.Stack;

public class NumberMap
{
    Libr Li;
    int [][] numberMap;
    int [][] numberMapSec;

    Stack<Pair<Integer, Integer>> stack = new Stack<Pair<Integer, Integer>>();

    public void sendMessage (Pair <Integer, Integer> pair)
    {
        if (stack.empty()==true)
        {
            if (numberMap[pair.getKey()][pair.getValue()] == 5)
            {
                makeBomb(pair.getKey(), pair.getValue());
                while(checkMap())
                {
                    sortMap();
                    //printMap();
                }
            }
            else
            {
                stack.push(pair);
            }
        }
        else
        {
            Pair <Integer, Integer> pair2 = stack.pop();
            int x1 = pair2.getKey();
            int y1 = pair2.getValue();
            int x2 = pair.getKey();
            int y2 = pair.getValue();

            if ((x1==x2 && Math.abs(y1-y2)==1) || (y1==y2 && Math.abs(x1-x2)==1))
            {
                swap (x1, y1, x2, y2);
                if (checkMap()==false)
                {
                    swap (x1, y1, x2, y2);
                }
                else
                {
                    do {
                        sortMap();
                        System.out.println();
                       //printMap();
                    } while(checkMap());
                }
            }


        }

    }

    public NumberMap()
    {
        Li = Libr.getInstance();
        numberMap = new int[Li.N][Li.M];
        for (int i=0; i<Li.N;i++)
        {
            for (int j = 0; j < Li.M; j++)
            {
                numberMap[i][j] = Li.getNumber();
            }
        }
        while (checkMap())
        {
            System.out.println();
            sortMap();
            clearMap(5);
            //printMap();
        }

    }

    public void sortMap()
    {
        numberMapSec = new int[Li.N][Li.M];
        fromFirstToSec();

        for (int i=0; i<Li.N; i++)
        {
            for (int j=0; j<Li.M-2; )
            {
                int k=horChange(i, j);
                if (k<2)
                {
                    j++;
                }
                else
                {
                    j+=k;
                }
            }
        }

        for (int j=0; j<Li.M; j++)
        {
            for (int i=0; i<Li.N-2; )
            {
                int l=verChange(i, j);
                if (l<2)
                {
                    i++;
                }
                else
                {
                    i+=l;
                }
            }
        }
        fromSecToFirst();
        fullMap();
    }

    public boolean checkMap ()
    {
        for (int i=0; i<Li.N; i++)
        {
            for (int j=0; j<Li.M-2; )
            {
                int k=horCheck(numberMap[i][j], i, j);
                if (k<3)
                {
                    j+=k;
                }
                else
                {
                    return true;
                }
            }
        }

        for (int j=0; j<Li.M; j++)
        {
            for (int i=0; i<Li.N-2; )
            {
                int l=verCheck(numberMap[i][j],i, j);
                if (l<3)
                {
                    i+=l;
                }
                else
                {
                    return true;
                }
            }
        }
        return false;
    }

    private int horCheck(int point, int x_, int y_)
    {
        if (point==5)
        {
            return 1;
        }
        else
        {
            if (y_>Li.M-1)
            {
                return 0;
            }
            else
            {
                if (numberMap[x_][y_]==point)
                {
                    return 1+horCheck(point, x_, y_+1);
                }

            }
            return 0;
        }
    }

    public int horChange (int x_, int y_)
    {
        int k = horCheck(numberMap[x_][y_], x_, y_);
        if (k>=3)
        {
            for (int i=0; i<k; i++)
            {
                Li.changeScore(numberMap[x_][y_+i], Li.DefScore*(i+1));
                numberMapSec[x_][y_+i]=-1;
            }
            if (k>3)
            {
                numberMapSec[x_][y_]=5;
            }

        }
        return k;
    }

    private void fromFirstToSec()
    {
        for (int i=0; i<Li.N; i++)
        {
            for (int j=0; j<Li.M; j++)
            {
                numberMapSec[i][j]=numberMap[i][j];
            }
        }
    }

    private void fromSecToFirst()
    {
        for (int i=0; i<Li.N; i++)
        {
            for (int j=0; j<Li.M; j++)
            {
                numberMap[i][j]=numberMapSec[i][j];
            }
        }
    }

    private int verCheck(int point, int x_, int y_)
    {
        if (point==5)
        {
            return 1;
        }
        else
        {
            if (x_>Li.N-1)
            {
                return 0;
            }
            else
            {
                if (numberMap[x_][y_]==point)
                {
                    return 1+verCheck(point, x_+1, y_);
                }

            }
            return 0;
        }
    }

    public int verChange (int x_, int y_)
    {
        boolean fl = false;
        int k = verCheck(numberMap[x_][y_], x_, y_);
        if (k>=3)
        {
            for (int i=0; i<k; i++)
            {
                if (numberMapSec[x_+i][y_]==-1 || numberMapSec[x_+i][y_]==5)
                {
                    numberMapSec[x_+i][y_]=5;
                    fl=true;
                }
                else
                {
                    Li.changeScore(numberMap[x_+i][y_], Li.DefScore*(i+1));
                    numberMapSec[x_+i][y_]=-1;
                }

            }
            if (k>3&&fl==false)
            {
                numberMapSec[x_][y_]=5;
            }

        }
        return k;
    }

    private void swap (int i1, int j1, int i2, int j2)
    {
        int c = numberMap[i1][j1];
        numberMap[i1][j1]=numberMap[i2][j2];
        numberMap[i2][j2]=c;
    }

    private void fullPoint (int x_, int y_)
    {
        if (numberMap[x_][y_]==-1)
        {
            if (x_==Li.N-1)
            {
                numberMap[x_][y_]=Li.getNumber();
            }
            else
            {
                swap(x_+1, y_, x_, y_);
                fullPoint(x_+1, y_);
            }
        }

    }

    private void fullMap()
    {
        for (int i=Li.N-1; i>=0; i--)
        {
            for (int j=0; j<Li.M; j++)
            {
                fullPoint(i, j);
            }
        }
    }

    private void printMap()
    {
        System.out.println();
        for (int i=0; i<Li.N; i++)
        {
            for (int j=0; j<Li.M; j++)
            {
                System.out.print(numberMap[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private void makeBomb(int x_, int y_)
    {
        for (int i=0; i<Li.K; i++)
        {
            Li.changeScore(i, Li.DefScore*5);
        }

        numberMap[x_][y_]=-1;
        if (numberMap[(x_==0)?1:x_-1][y_]==5)
        {
            makeBomb((x_==0)?1:x_-1, y_);
        }
        else
        {
            numberMap[(x_==0)?1:x_-1][y_]=-1;
        }

        if (numberMap[(x_==Li.N-1)?Li.N-2:x_+1][y_]==5)
        {
            makeBomb((x_==Li.N-1)?Li.N-2:x_+1,y_);
        }
        else
        {
            numberMap[(x_==Li.N-1)?Li.N-2:x_+1][y_]=-1;
        }

        if (numberMap[x_][(y_==0)?1:y_-1]==5)
        {
            makeBomb(x_,(y_==0)?1:y_-1);
        }
        else
        {
            numberMap[x_][(y_==0)?1:y_-1]=-1;
        }

        if (numberMap[x_][(y_==Li.M-1)?Li.M-2:y_+1]==5)
        {
            makeBomb(x_, (y_==Li.M-1)?Li.M-2:y_+1);
        }
        else
        {
            numberMap[x_][(y_==Li.M-1)?Li.M-2:y_+1]=-1;
        }
        fullMap();
    }

    public void clearMap (int point)
    {
        int counter = 1;
        for (int i=0; i<Li.N; i++)
        {
            for (int j=0; j<Li.M; j++)
            {
                if (numberMap[i][j]==point)
                {
                    numberMap[i][j]=-1;
                    Li.changeScore(Li.getNumber(), Li.DefScore*(counter++));
                }
            }
        }
        fullMap();
    }
}
