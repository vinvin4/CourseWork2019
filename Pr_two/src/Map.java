import javafx.util.Pair;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Map extends JFrame
{
    Libr Li;
    JButton[][] buttMap;
    JPanel panel;
    NumberMap numbMap;
    JButton newWindow;
    JPanel panelScore;
    JButton[] buttScore;

    public Map() {
        //Name of window
        super("Three In Line Game");
        //super.setPreferredSize(new Dimension(530, 500));
        super.setMaximumSize(new Dimension(740, 640));

        Li = Libr.getInstance();
        numbMap = new NumberMap();
        createPanel();
        createPanelScore();

        newWindow = new JButton ("Сброс поля");
        newWindow.addActionListener(new NewWindow());

        Container Contain = getContentPane();
        Contain.setLayout(new BorderLayout());

        Contain.add(BorderLayout.CENTER, panel);
        Contain.add(BorderLayout.SOUTH, newWindow);
        Contain.add(BorderLayout.EAST, panelScore );

        updateUI();
    }

    private void updateUI() {
            while (numbMap.checkMap())
            {
                numbMap.sortMap();
            }

            for (int i=0; i<Li.N;i++)
            {
                for (int j=0; j<Li.M; j++)
                {
                    buttMap[i][j].setIcon(Li.icon[numbMap.numberMap[i][j]]);
                }
            }

            buttScore[0].setText("Red score: "+ Li.scoreMass[0]+"/"+Li.limitScore);
            buttScore[1].setText("Blue score: "+Li.scoreMass[1]+"/"+Li.limitScore);
            buttScore[2].setText("Green score: "+Li.scoreMass[2]+"/"+Li.limitScore);
            buttScore[3].setText("Yellow score: "+Li.scoreMass[3]+"/"+Li.limitScore);
            buttScore[4].setText("Violet score: "+Li.scoreMass[4]+"/"+Li.limitScore);
            System.out.println();
            /*for (int i=0; i<5; i++)
            {
                System.out.println(Li.scoreMass[i]);
            }*/
        }

    public static void main(String args[]) {
                Map outMap = new Map();
                outMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                outMap.pack();
                outMap.setVisible(true);
        }

    public class MouseClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String actorName = e.getActionCommand();
            int i_ = Integer.parseInt(actorName,0,1,10);
            int j_ = Integer.parseInt(actorName,1,2,10);
            //text.setText("Button["+Integer.toString(i_)+"]["+Integer.toString(j_)+"]");
            numbMap.sendMessage(new Pair<Integer, Integer>(i_, j_));
            updateUI();
        }
    }

    public class NewWindow implements ActionListener {
        @Override
        public void actionPerformed (ActionEvent e)
        {
            numbMap = new NumberMap();
            updateUI();
        }
    }

    public class RedClear implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Li.scoreMass[0]>=Li.limitScore)
            {
                Li.scoreMass[0]-=Li.limitScore;
                Li.limitScore+=500;
                numbMap.clearMap(0);
                while (numbMap.checkMap())
                {
                numbMap.sortMap();
                }
                updateUI();
            }
        }
    }

    public class BlueClear implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Li.scoreMass[1]>=Li.limitScore)
            {
                Li.scoreMass[1]-=Li.limitScore;
                Li.limitScore+=500;
                numbMap.clearMap(1);
                while (numbMap.checkMap())
                {
                    numbMap.sortMap();
                }
                updateUI();
            }

        }
    }

    public class GreenClear implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Li.scoreMass[2]>=Li.limitScore)
            {
                Li.scoreMass[2]-=Li.limitScore;
                Li.limitScore+=500;
                numbMap.clearMap(2);
                while (numbMap.checkMap())
                {
                    numbMap.sortMap();
                }
                updateUI();
            }

        }
    }

    public class YellowClear implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Li.scoreMass[3]>=Li.limitScore)
            {
                Li.scoreMass[3]-=Li.limitScore;
                Li.limitScore+=500;
                numbMap.clearMap(3);
                while (numbMap.checkMap())
                {
                    numbMap.sortMap();
                }
                updateUI();
            }

        }
    }

    public class VioletClear implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Li.scoreMass[4]>=Li.limitScore)
            {
                Li.scoreMass[4]-=Li.limitScore;
                Li.limitScore+=500;
                numbMap.clearMap(4);
                while (numbMap.checkMap())
                {
                    numbMap.sortMap();
                }
                updateUI();
            }
        }
    }

    private void createPanel() {
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(570, 410));
        panel.setLayout(new GridLayout(5, 7, 5, 5));
        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        createButtonMapMassive();
    }

    private void createPanelScore()
    {
        panelScore = new JPanel();
        panelScore.setLayout(new GridLayout(5,1,5,5));
        panelScore.setBorder(BorderFactory.createLineBorder(Color.black));
        createButtonScoreMassive();
    }

    private void createButtonScoreMassive()
    {
        buttScore = new JButton[Li.K];
        for (int i=0; i<Li.N; i++)
        {
            buttScore[i] = new JButton();
            buttScore[i].setPreferredSize(new Dimension(175, 75));
            panelScore.add(buttScore[i]);
        }
        buttScore[0].addActionListener(new RedClear());
        buttScore[1].addActionListener(new BlueClear());
        buttScore[2].addActionListener(new GreenClear());
        buttScore[3].addActionListener(new YellowClear());
        buttScore[4].addActionListener(new VioletClear());
    }

    private void createButtonMapMassive()
    {
        buttMap = new JButton[Li.N][Li.M];
        for (int i = 0; i < Li.N; i++) {
            for (int j = 0; j < Li.M; j++) {
                buttMap[i][j] = new JButton();
                buttMap[i][j].setText(i+""+j);
                buttMap[i][j].setFont(new Font ("TimesRoman", Font.PLAIN,0));
                buttMap[i][j].setVerticalTextPosition(SwingConstants.BOTTOM);
                buttMap[i][j].addActionListener(new MouseClicked());
                buttMap[i][j].setMinimumSize(new Dimension(70, 70));
                buttMap[i][j].setPreferredSize(new Dimension(100, 100));
                buttMap[i][j].setMaximumSize(new Dimension(130, 130));
                panel.add(buttMap[i][j]);
            }
        }
    }
    }