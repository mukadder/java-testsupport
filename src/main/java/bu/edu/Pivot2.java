 package bu.edu;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Pivot2 {
	
	static private HashMap<Integer, HashMap<String, Integer>> data = new HashMap<Integer, HashMap<String, Integer>>();

    public static void main(String args[]) {
        readTable();
        printTable();
    }

    public static void readTable() {
        try {
            FileInputStream fstream = new FileInputStream("windfarm.dat");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                String[] dataSplit = strLine.split(" ");
                Integer tower = new Integer(dataSplit[0]);
                String day = dataSplit[1];
                Integer kwh = new Integer(dataSplit[2]);
                if (data.containsKey(tower)) {
                    HashMap<String, Integer> towerData = data.get(tower);
                    if (towerData.containsKey(day))
                        kwh += data.get(tower).get(day);
                    towerData.put(day, kwh);
                    data.put(tower, towerData);
                } else {
                    HashMap<String, Integer> towerData = new HashMap<String, Integer>();
                    towerData.put(day, kwh);
                    data.put(tower, towerData);
                }
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void printTable() {
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        printALine();
        System.out.print(String.format("%7s%3s", "Tower", "|"));
        for (String day : days)
            System.out.print(String.format("%6s%4s", day, "|"));
        System.out.println();
        printALine();
        SortedSet<Integer> sortedKeys = new TreeSet<Integer>(data.keySet());
        for (Integer dataKey : sortedKeys) {
            HashMap current = data.get(dataKey);
            System.out.print(String.format("%7s%3s", dataKey, "|"));
            for (Object day : days) {
                System.out.print(String.format("%8s%2s", current.get(day).toString() + "kWh", "|"));
            }
            System.out.println();
            printALine();
        }
    }

    public static void printALine() {
        for (int i = 0; i < 80; i++)
            System.out.print("-");
        System.out.println();
    }
}