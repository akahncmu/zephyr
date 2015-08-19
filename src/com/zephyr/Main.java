package com.zephyr;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Map<String,Integer> numPubs = new HashMap<>();
        String csvFile = "/Users/akahn/dev/zephyr/src/com/zephyr/pubmed_result.csv";
        BufferedReader br = null;
        String line;

        //If this list was bigger, I would have put it in it's own file.  I settled on just hardcoding it here.
        String[] HCPs = ("Dr. Vincent Rajkumar\n" +
                "Dr. María-Victoria Mateos\n" +
                "Dr. David Vesole\n" +
                "Dr. William Bensinger\n" +
                "Dr. Edward Libby\n" +
                "Dr. Heinz Ludwig\n" +
                "Dr. Sagar Lonial\n" +
                "Dr. Hermann Einsele\n" +
                "Dr. Jatin Shah\n" +
                "Dr. Philip McCarthy\n" +
                "Dr. Amrita Krishnan\n" +
                "Dr. Ravi Vij").split("\\n");
        for (String hcp: HCPs) {
            numPubs.put(new HCP(hcp).getName().toUpperCase(), new Integer(0));
        }

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] columns = line.replaceAll("^\"", "").split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?");
                //regex is for ignoring commas inside quotes, while respecting commas outside them

                for(int i = 0; i < columns.length; i++) {
                    if(i==2 && !columns[2].equals("[No authors listed]")) {
                        String[] authors = columns[i].split(",");
                        for (String a : authors) {
                            if(a.length() > 0) {
                                if (a.charAt(0) == ' ')
                                    a = a.substring(1);
                            }
                            //KNOWN BUG: this strategy doesn't take into account multi-word last names

                            String[] name = a.split(" ");
                            String key = "";
                            if(name.length > 0) { // ignore empty columns
                                key = name[0].toUpperCase(); //name[0] is last name, uppercase for key matching
                                if(name[name.length - 1].length() > 0) {
                                    key = key + " " + name[name.length - 1].replaceAll("\\.", "");
                                    //remove any periods from the first initial, and add it to the last name
                                }
                            }
                            if(numPubs.containsKey(key)) {
                                numPubs.put(key, numPubs.get(key)+1);
                            }
                        }
                    }
                }
            }
            System.out.print(numPubs);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
