package com.zephyr;

/**
 * Created by akahn on 8/17/15.
 */
public class HCP {

    private String lastName;
    private String firstInitials = "";

    public HCP(String name)  {
        String[] names = name.split(" ");
        if(names.length < 3 || !names[0].equals("Dr.")) {
            System.out.println("Name input has too few arguments or does not begin with Dr.");
        }
        else {
            lastName = names[names.length-1]; // Assume the final name is lastName
            for(int i = 1; i < names.length - 1; i++) { // for every word except 'Dr.' and the lastName
                String[] currNames = names[i].split("-"); // for each non-last name, split by '-', and append initial(s)
                for (String s : currNames) {
                    firstInitials += s.charAt(0);
                }
            }
        }
    }

    public String getName() {
        return lastName + " " + firstInitials;
    }
}
