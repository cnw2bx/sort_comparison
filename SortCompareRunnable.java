/*
 * Cameron Woodward cnw2bx   
 * extra credit
 */
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class SortCompareRunnable extends JComponent implements Runnable {

    private ArrayList<Integer> randNums;
    private int changedIndex;
    private int sortedIndex = 0;
    private static final int PAUSE_TIME = 40;
    private String typeSort;
    public static long totTimeBub;
    public static long insTimeTot;
    public static long selTimeTot;

    //runnable constructor; creates deep copy of list 
    public SortCompareRunnable(ArrayList<Integer> randomNums, String algorithm) {
        this.randNums = new ArrayList<>();
        for (Integer currentIndex : randomNums) {
            this.randNums.add(currentIndex);
        }
        this.typeSort = algorithm;
    }

    //Selection sort: find smallest element and move to the top
    private void selectionSort() {
    	long startTime = System.currentTimeMillis();
        int smallestIndex;
        int smallestValue;

        for (int currentIndex = 0; currentIndex < randNums.size(); currentIndex++) {
            smallestIndex = currentIndex;
            smallestValue = randNums.get(smallestIndex);

            sortedIndex = currentIndex;

            // find smallest element
            for (int iterIdx = currentIndex + 1; iterIdx < randNums.size(); iterIdx++) {
            	//used in GUI
                changedIndex = iterIdx;
                revalidate();
                repaint();
              //slows code, able to see what's happening
                try {
                    Thread.sleep(PAUSE_TIME);
                } catch (InterruptedException e) {
                    System.out.println("Interruption");
                    return;
                }

                //finding smallest value
                if (randNums.get(iterIdx) < smallestValue) {
                    smallestValue = randNums.get(iterIdx);
                    smallestIndex = iterIdx;
                }
            }

            // if smallest element has been changed from currIdx, swap with currIdx
            if (smallestIndex != currentIndex) {
                int temp = randNums.get(currentIndex);
                randNums.set(currentIndex, randNums.get(smallestIndex));
                randNums.set(smallestIndex, temp);
            }
        }

        long endTime = System.currentTimeMillis();
        selTimeTot = (endTime - startTime)/1000;
        
        JLabel selTime = new JLabel("	Time: " + SortCompareRunnable.selTimeTot + " seconds");
        selTime.setHorizontalAlignment(SwingConstants.CENTER);
        SortCompare.getSelSortBoxPanel().add(selTime);
        //used for GUI
        sortedIndex = randNums.size();
        changedIndex = -1;
        
        revalidate();
        repaint();
    }

    //Insertion sort algorithm
    private void insertionSort() {
    	long startTime = System.currentTimeMillis();
        for (int currentIndex = 1; currentIndex < randNums.size(); currentIndex++) {
            int iterIdx = currentIndex;
            
            sortedIndex = currentIndex + 1;

            while (iterIdx > 0 && randNums.get(iterIdx) < randNums.get(iterIdx - 1)) {

                changedIndex = iterIdx;
                revalidate();
                repaint();
                //slows code, able to see what's happening
                try {
                    Thread.sleep(PAUSE_TIME);
                } catch (InterruptedException e) {
                    System.out.println("Interuption");
                    return;
                }

                int temp = randNums.get(iterIdx - 1);
                randNums.set(iterIdx - 1, randNums.get(iterIdx));
                randNums.set(iterIdx, temp);
                iterIdx--;
            }
        }

        long endTime = System.currentTimeMillis();
        insTimeTot = (endTime - startTime)/1000;
        
        JLabel insTime = new JLabel("	Time: " + SortCompareRunnable.insTimeTot);
        insTime.setHorizontalAlignment(SwingConstants.CENTER);
        SortCompare.getInsSortBoxPanel().add(insTime);
        
        //used in GUI
        sortedIndex = randNums.size();
        changedIndex = -1;
        revalidate();
        repaint();

    }

    //bubble sort algorithm
    private void bubbleSort() {
    	//allows while loop to run
        boolean swapped = true;
        long startTime = System.currentTimeMillis();

        while (swapped) {
            swapped = false;

            int lastSwappedIndex = 0;

            // go from 49 to 0 | compare the next index with the current
            for (int currentIndex = randNums.size() - 2; currentIndex >= sortedIndex; currentIndex--) {
                changedIndex = currentIndex + 1;
                revalidate();
                repaint();
                
                //slows code, able to see what's happening
                try {
                    Thread.sleep(PAUSE_TIME);
                } catch (InterruptedException e) {
                    System.out.println("Interuption");
                    return;
                }

                // if current element is less than element before, swap them
                if (randNums.get(currentIndex) > randNums.get(currentIndex + 1)) {
                    int temp = randNums.get(currentIndex + 1);
                    randNums.set(currentIndex + 1, randNums.get(currentIndex));
                    randNums.set(currentIndex, temp);

                    lastSwappedIndex = currentIndex + 1;

                    swapped = true;
                }
            }
            sortedIndex = lastSwappedIndex;
        }

        sortedIndex = randNums.size();
        changedIndex = -1;
		long endTime = System.currentTimeMillis();
        totTimeBub = (endTime - startTime)/1000;
        
        JLabel bubTime = new JLabel("	Time: " + SortCompareRunnable.totTimeBub + " seconds");
        bubTime.setHorizontalAlignment(SwingConstants.CENTER);
        SortCompare.getBubSortBoxPanel().add(bubTime);
        
        revalidate();
        repaint();

    }
    /*
     * paints the panel using graphics
     * if currIdx is less than sortedIndex: green
     * if currIdx is modified index: red
     * else: black
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int x = 20;
        int y = 0;

        for (int currIdx = 0; currIdx < randNums.size(); currIdx++) {
            if (currIdx == changedIndex) {
                g2.setColor(Color.red);
                g2.fillRect(x, y, 4 * randNums.get(currIdx), 5);
            } else if (currIdx < sortedIndex) {
                g2.setColor(Color.green);
                g2.fillRect(x, y, 4 * randNums.get(currIdx), 5);
            } else {
                g2.setColor(Color.black);
                g.fillRect(x, y, 4 * randNums.get(currIdx), 5);
            }
            y += 10;
        }
    }
    
    //calls the appropriate sort method for the given string
    @Override
    public void run() {
        if (typeSort.equals("selection")) {
            selectionSort();
        } else if (typeSort.equals("insertion")) {
            insertionSort();
        } else if (typeSort.equals("bubble")) {
            bubbleSort();
        }
    }
}