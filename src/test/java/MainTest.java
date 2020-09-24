import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void testTreeMap() {
        TreeMap<String,Integer> map = new TreeMap<>();
        map.put("Yuval",1);
        map.put("A",1);
        map.put("Z",1);
        map.put("d",1);
        map.put("D",1);
        map.put("z",1);
        for (Map.Entry entry: map.entrySet()
             ) {
            System.out.println(entry.getKey());
        }

    }

}