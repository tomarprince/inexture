package com.inexture.task;

import static com.inexture.task.StartupRunner.sortByValue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskApplicationTests {

    private String input = "1385718407:1385718491\n"
            + "1385718407:1385718409\n"
            + "1385718408:1385718452\n"
            + "1385718408:1385718464\n"
            + "1385718407:1385718452";
    private Integer expectedOutput = 3;

    @org.junit.Test
    public void givenFileNameAsAbsolutePath_whenUsingClasspath_thenFileData() throws FileNotFoundException, IOException {
        HashMap<String, Integer> dateMap = new HashMap<>();
        if (input != null) {
            String[] dates = input.split("\\n");
            for (String tmp : dates) {
                String[] dateArr = tmp.split(":");
                String startDate = dateArr[0];
                if (dateMap.containsKey(startDate)) {
                    dateMap.put(startDate, dateMap.get(startDate) + 1);
                } else {
                    dateMap.put(startDate, 1);
                }
            }
        }
        Map<String, Integer> sortedValueMap = sortByValue(dateMap);
        Assert.assertEquals(sortedValueMap.values().toArray()[0], expectedOutput);
    }

}
