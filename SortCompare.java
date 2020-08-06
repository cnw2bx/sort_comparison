/*
 * Cameron Woodward cnw2bx   
 * extra credit
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class SortCompare {
    private static JPanel selSortBoxPanel, insSortBoxPanel, bubSortBoxPanel;
    private static JButton randomizeAndSortButton;
    private static ArrayList<Thread> listSortComponentThreads = new ArrayList<>();

    //create frame and add components
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Sort Comparison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addContentsToPane(frame.getContentPane());
        frame.getContentPane().setPreferredSize(new Dimension(900, 600));

        frame.pack();
        frame.setVisible(true);
    }

    //adds the rectangles and the button to the bottom
    private static void addContentsToPane(Container pane) {
        JPanel outsidePanel = new JPanel();
        outsidePanel.setLayout(new BorderLayout());

        JPanel northGridPanel = new JPanel();
        northGridPanel.setLayout(new GridLayout(1, 3));

        JPanel centerGridPanel = new JPanel();
        centerGridPanel.setLayout(new GridLayout(1, 3));

        setSelSortBoxPanel(new JPanel());
        getSelSortBoxPanel().setLayout(new BoxLayout(getSelSortBoxPanel(), BoxLayout.PAGE_AXIS));

        setInsSortBoxPanel(new JPanel());
        getInsSortBoxPanel().setLayout(new BoxLayout(getInsSortBoxPanel(), BoxLayout.PAGE_AXIS));

        setBubSortBoxPanel(new JPanel());
        getBubSortBoxPanel().setLayout(new BoxLayout(getBubSortBoxPanel(), BoxLayout.PAGE_AXIS));

        JPanel southBoxPanel = new JPanel();
        southBoxPanel.setLayout(new BoxLayout(southBoxPanel, BoxLayout.PAGE_AXIS));

        //JLabels
        JLabel selectionLabel = new JLabel("Selection Sort");
        selectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        northGridPanel.add(selectionLabel);

        JLabel insertionLabel = new JLabel("Insertion Sort");
        insertionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        northGridPanel.add(insertionLabel);

        JLabel bubbleLabel = new JLabel("Bubble Sort");
        bubbleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        northGridPanel.add(bubbleLabel);

        //adds button to bottom
        randomizeAndSortButton = new JButton();
        randomizeAndSortButton.setText("Start Visualization");
        randomizeAndSortButton.setMaximumSize(new Dimension(150, 30));
        randomizeAndSortButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        randomizeAndSortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomizeAndSort();
            }
        });
        southBoxPanel.add(randomizeAndSortButton);

        JLabel spacerLabel = new JLabel(" ");
        spacerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        southBoxPanel.add(spacerLabel);


        JLabel instructionLabel = new JLabel("Instructions: Click the button to see Selection, Insertion, and Bubble sort algorithms");
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        southBoxPanel.add(instructionLabel);

        // make key label
        JLabel keyLabel = new JLabel("Key: Green = sorted    Black = unsorted   Red = algorithm position");
        keyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        southBoxPanel.add(keyLabel);

        centerGridPanel.add(getSelSortBoxPanel());
        centerGridPanel.add(getInsSortBoxPanel());
        centerGridPanel.add(getBubSortBoxPanel());

        outsidePanel.add(BorderLayout.NORTH, northGridPanel);
        outsidePanel.add(BorderLayout.CENTER, centerGridPanel);
        outsidePanel.add(BorderLayout.SOUTH, southBoxPanel);

        // add panels to frame
        pane.add(outsidePanel);
    }

    /*
     * ends existing threads 
     * creates random list
     * creates three threads 
     * adds components to centerGridPanel
     * Starts the threads
     */
    private static void randomizeAndSort() {
        if (listSortComponentThreads.size() > 0) {
            for (Thread currComp : listSortComponentThreads) {
                currComp.interrupt();
            }
            listSortComponentThreads.clear();
            getSelSortBoxPanel().removeAll();
            getInsSortBoxPanel().removeAll();
            getBubSortBoxPanel().removeAll();
            getSelSortBoxPanel().revalidate();
            getInsSortBoxPanel().revalidate();
            getBubSortBoxPanel().revalidate();
            getSelSortBoxPanel().repaint();
            getInsSortBoxPanel().repaint();
            getBubSortBoxPanel().repaint();
            
        }

        ArrayList<Integer> listRandomNums = new ArrayList<>();
        for (int x = 1; x < 51; x++) {
            listRandomNums.add(x);
        }
        Collections.shuffle(listRandomNums);

        SortCompareRunnable selSortComp = new SortCompareRunnable(listRandomNums, "selection");
        SortCompareRunnable insSortComp = new SortCompareRunnable(listRandomNums, "insertion");
        SortCompareRunnable bubSortComp = new SortCompareRunnable(listRandomNums, "bubble");

        getSelSortBoxPanel().add(selSortComp);
        getInsSortBoxPanel().add(insSortComp);
        getBubSortBoxPanel().add(bubSortComp);
        getSelSortBoxPanel().revalidate();
        getInsSortBoxPanel().revalidate();
        getBubSortBoxPanel().revalidate();
        getSelSortBoxPanel().repaint();
        getInsSortBoxPanel().repaint();
        getBubSortBoxPanel().repaint();

        Thread selSortThread = new Thread(selSortComp);
        Thread insSortThread = new Thread(insSortComp);
        Thread bubSortThread = new Thread(bubSortComp);

        listSortComponentThreads.add(selSortThread);
        listSortComponentThreads.add(insSortThread);
        listSortComponentThreads.add(bubSortThread);

        for (Thread currThread : listSortComponentThreads) {
            currThread.start();
        }
    }

    //creates and displays the GUI
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

	public static JPanel getInsSortBoxPanel() {
		return insSortBoxPanel;
	}

	public static void setInsSortBoxPanel(JPanel insSortBoxPanel) {
		SortCompare.insSortBoxPanel = insSortBoxPanel;
	}

	public static JPanel getBubSortBoxPanel() {
		return bubSortBoxPanel;
	}

	public static void setBubSortBoxPanel(JPanel bubSortBoxPanel) {
		SortCompare.bubSortBoxPanel = bubSortBoxPanel;
	}

	public static JPanel getSelSortBoxPanel() {
		return selSortBoxPanel;
	}

	public static void setSelSortBoxPanel(JPanel selSortBoxPanel) {
		SortCompare.selSortBoxPanel = selSortBoxPanel;
	}
}