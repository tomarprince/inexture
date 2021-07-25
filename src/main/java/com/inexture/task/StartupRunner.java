/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inexture.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

/**
 * Start Runner Class
 *
 * @author Prince
 */
@Component
public class StartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        try {
            File file = ResourceUtils.getFile("classpath:call-logs.txt");
            InputStream inputStream = new FileInputStream(file);
            HashMap<String, Integer> dateMap = new HashMap<>(); // Map contains key as startdate & value as count of call land at that date & time
            try ( BufferedReader br
                    = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] dateArr = line.split(":");
                    String startDate = dateArr[0];
                    if (dateMap.containsKey(startDate)) {
                        dateMap.put(startDate, dateMap.get(startDate) + 1);
                    } else {
                        dateMap.put(startDate, 1);
                    }
                }
                Map<String, Integer> sortedValueMap = sortByValue(dateMap); //Sort MaP BY value in Desc order
                //Fetch Highest call logs count as per date, print 
                System.out.println(sortedValueMap.values().toArray()[0]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list
                = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                    Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
