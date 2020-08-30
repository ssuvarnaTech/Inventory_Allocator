package test.java;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovyjarjarpicocli.CommandLine;
import main.java.InventoryAllocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class InventoryAllocatorTest {
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * running Junit tests using the junit parameterized test
     *
     * @return
     */
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"./src/test/resources/order.json", "./src/test/resources/expected.json"}, {"./src/test/resources/order2.json", "./src/test/resources/expected2.json"},
                {"./src/test/resources/order3.json", "./src/test/resources/expected3.json"}, {"./src/test/resources/order4.json", "./src/test/resources/expected4.json"},
                {"./src/test/resources/order5.json", "./src/test/resources/expected5.json"}, {"./src/test/resources/order6.json", "./src/test/resources/expected6.json"}
        });
    }

    private String file;
    private String expectedFile;

    public InventoryAllocatorTest(String file1, String file2) {
        this.file = file1;
        this.expectedFile = file2;
    }

    @Before
    public void setUp() {

    }

    @After
    public void closeTest() {

    }

    /**
     * helper method that checks to see if two JsonNodes are equal
     *
     * @param o1 first JsonNode that is passed
     * @param o2 second JsonNode that is passed
     * @return 1 if o1 != o2 and 0 if o1 == o2
     */
    public int compare(JsonNode o1, JsonNode o2) {
        if (o1.equals(o2)) {
            return 0;
        }
        if ((o1.isArray()) && (o2.isArray()) && (o1.size() == o2.size())) {
            boolean isThere = true;
            for (JsonNode item1 : o1) {
                boolean found = true;
                for (JsonNode item2 : o2) {
                    if (item1.equals(item2)) {
                        found = true;
                        break;
                    } else {
                        found = false;
                        continue;
                    }

                }
                if (found == false) {
                    return 1;
                }
            }

        }
        return 0;
    }

    /**
     * Test to check to see everything in the first file matches the expected input
     *
     * @throws IOException
     */
    @Test
    public void readJsonTest() throws IOException {
        InventoryAllocator invent = new InventoryAllocator();


        HashMap<String, Object> order = invent.getInputs("./src/test/resources/order.json");
        assertEquals(order.size(), 2);
        assert (order.keySet().contains("order"));
        assert (order.keySet().contains("inventory_list"));
        HashMap<String, Object> shipment = (HashMap<String, Object>) order.get("order");
        assertEquals(shipment.size(), 1);
        assert (shipment.keySet().contains("apple"));
        assertEquals(shipment.get("apple"), 10);

        ArrayList<HashMap<String, Object>> inventory = (ArrayList<HashMap<String, Object>>) order.get("inventory_list");
        assert (inventory.get(0).containsKey("name"));
        assertEquals(inventory.get(0).get("name"), "owd");
        assert (inventory.get(0).containsKey("inventory"));
        LinkedHashMap<String, Object> distribution = (LinkedHashMap<String, Object>) inventory.get(0).get("inventory");
        assertEquals(distribution.size(), 1);
        assert (distribution.containsKey("apple"));
        assertEquals(distribution.get("apple"), 5);
        assert (inventory.get(1).containsKey("name"));
        assertEquals(inventory.get(1).get("name"), "dm");
        assert (inventory.get(1).containsKey("inventory"));

        LinkedHashMap<String, Object> dist = (LinkedHashMap<String, Object>) inventory.get(1).get("inventory");
        assertEquals(dist.size(), 1);
        assert (dist.containsKey("apple"));
        assertEquals(dist.get("apple"), 5);


    }

    /**
     * Test to check to see everything in the second file matches the expected input
     *
     * @throws IOException
     */
    @Test
    public void readJsonTest2() throws IOException {
        InventoryAllocator invent2 = new InventoryAllocator();
        //check to see everything in the second file matches the expected input

        HashMap<String, Object> order2 = invent2.getInputs("./src/test/resources/order2.json");
        assertEquals(order2.size(), 2);
        assert (order2.keySet().contains("order"));
        assert (order2.keySet().contains("inventory_list"));
        HashMap<String, Object> shipment2 = (HashMap<String, Object>) order2.get("order");
        assertEquals(shipment2.size(), 1);
        assert (shipment2.keySet().contains("apple"));
        assertEquals(shipment2.get("apple"), 2);
        ArrayList<HashMap<String, Object>> inventory2 = (ArrayList<HashMap<String, Object>>) order2.get("inventory_list");
        assert (inventory2.get(0).containsKey("name"));
        assertEquals(inventory2.get(0).get("name"), "owd");
        assert (inventory2.get(0).containsKey("inventory"));
        LinkedHashMap<String, Object> distribution2 = (LinkedHashMap<String, Object>) inventory2.get(0).get("inventory");
        assertEquals(distribution2.size(), 1);
        assert (distribution2.containsKey("apple"));
        assertEquals(distribution2.get("apple"), 1);
    }

    /**
     * Test to check to see everything in the third file matches the expected input
     *
     * @throws IOException
     */
    @Test
    public void readJsonTest3() throws IOException {
        InventoryAllocator invent3 = new InventoryAllocator();
        //check to see everything in the third file matches the expected input

        HashMap<String, Object> order3 = invent3.getInputs("./src/test/resources/order3.json");
        assertEquals(order3.size(), 2);
        assert (order3.keySet().contains("order"));
        assert (order3.keySet().contains("inventory_list"));
        HashMap<String, Object> shipment3 = (HashMap<String, Object>) order3.get("order");
        assertEquals(shipment3.size(), 1);
        assert (shipment3.keySet().contains("apple"));
        assertEquals(shipment3.get("apple"), 1);
        ArrayList<HashMap<String, Object>> inventory3 = (ArrayList<HashMap<String, Object>>) order3.get("inventory_list");
        assert (inventory3.get(0).containsKey("name"));
        assertEquals(inventory3.get(0).get("name"), "owd");
        assert (inventory3.get(0).containsKey("inventory"));
        LinkedHashMap<String, Object> distribution3 = (LinkedHashMap<String, Object>) inventory3.get(0).get("inventory");
        assertEquals(distribution3.size(), 1);
        assert (distribution3.containsKey("apple"));
        assertEquals(distribution3.get("apple"), 0);
    }

    /**
     * Test to  check to see everything in the fourth file matches the expected input
     *
     * @throws IOException
     */
    @Test
    public void readJsonTest4() throws IOException {
        InventoryAllocator invent4 = new InventoryAllocator();

        // check to see everything in the fourth file matches the expected input

        HashMap<String, Object> order4 = invent4.getInputs("./src/test/resources/order4.json");
        assertEquals(order4.size(), 2);
        assert (order4.keySet().contains("order"));
        assert (order4.keySet().contains("inventory_list"));
        HashMap<String, Object> shipment4 = (HashMap<String, Object>) order4.get("order");
        assertEquals(shipment4.size(), 1);
        assert (shipment4.keySet().contains("apple"));
        assertEquals(shipment4.get("apple"), 1);
        ArrayList<HashMap<String, Object>> inventory4 = (ArrayList<HashMap<String, Object>>) order4.get("inventory_list");
        assert (inventory4.get(0).containsKey("name"));
        assertEquals(inventory4.get(0).get("name"), "owd");
        assert (inventory4.get(0).containsKey("inventory"));
        LinkedHashMap<String, Object> distribution4 = (LinkedHashMap<String, Object>) inventory4.get(0).get("inventory");
        assertEquals(distribution4.size(), 1);
        assert (distribution4.containsKey("apple"));
        assertEquals(distribution4.get("apple"), 1);
    }

    /**
     * Test to check to see everything in the fifth file matches the expected input
     *
     * @throws IOException
     */
    @Test
    public void readJsonTest5() throws IOException {
        InventoryAllocator invent5 = new InventoryAllocator();

        // check to see everything in the fifth file matches the expected input

        HashMap<String, Object> order5 = invent5.getInputs("./src/test/resources/order5.json");
        assertEquals(order5.size(), 2);
        assert (order5.keySet().contains("order"));
        assert (order5.keySet().contains("inventory_list"));
        HashMap<String, Object> shipment5 = (HashMap<String, Object>) order5.get("order");
        assertEquals(shipment5.size(), 1);
        assert (shipment5.keySet().contains("apple"));
        assertEquals(shipment5.get("apple"), 0);
        ArrayList<HashMap<String, Object>> inventory5 = (ArrayList<HashMap<String, Object>>) order5.get("inventory_list");
        assert (inventory5.get(0).containsKey("name"));
        assertEquals(inventory5.get(0).get("name"), "owd");
        assert (inventory5.get(0).containsKey("inventory"));
        LinkedHashMap<String, Object> distribution5 = (LinkedHashMap<String, Object>) inventory5.get(0).get("inventory");
        assertEquals(distribution5.size(), 1);
        assert (distribution5.containsKey("apple"));
        assertEquals(distribution5.get("apple"), 1);

    }

    /**
     * Test to check to see everything in the sixth file matches the expected input
     *
     * @throws IOException
     */
    @Test
    public void readJsonTest6() throws IOException {
        InventoryAllocator invent6 = new InventoryAllocator();

        HashMap<String, Object> order6 = invent6.getInputs("./src/test/resources/order6.json");
        assertEquals(order6.size(), 2);
        assert (order6.keySet().contains("order"));
        assert (order6.keySet().contains("inventory_list"));
        HashMap<String, Object> shipment6 = (HashMap<String, Object>) order6.get("order");
        assertEquals(shipment6.size(), 3);
        assert (shipment6.keySet().contains("apple"));
        assertEquals(shipment6.get("apple"), 5);
        assert (shipment6.keySet().contains("banana"));
        assertEquals(shipment6.get("banana"), 5);
        assert (shipment6.keySet().contains("orange"));
        assertEquals(shipment6.get("orange"), 5);
        ArrayList<HashMap<String, Object>> inventory6 = (ArrayList<HashMap<String, Object>>) order6.get("inventory_list");
        assert (inventory6.get(0).containsKey("name"));
        assertEquals(inventory6.get(0).get("name"), "owd");
        assert (inventory6.get(0).containsKey("inventory"));
        LinkedHashMap<String, Object> distribution6 = (LinkedHashMap<String, Object>) inventory6.get(0).get("inventory");
        assertEquals(distribution6.size(), 2);
        assert (distribution6.containsKey("apple"));
        assertEquals(distribution6.get("apple"), 5);
        assertEquals(distribution6.size(), 2);
        LinkedHashMap<String, Object> dist6 = (LinkedHashMap<String, Object>) inventory6.get(1).get("inventory");
        assertEquals(dist6.size(), 2);
        assert (dist6.containsKey("banana"));
        assertEquals(dist6.get("banana"), 5);
        assert (dist6.containsKey("orange"));
        assertEquals(dist6.get("orange"), 10);
    }

    /**
     * Test to see if algorithm is working correctly and if outputs match expected outputs
     *
     * @throws IOException
     */

    @Test

    public void CheapestShipmentTest() throws IOException {
        InventoryAllocator i = new InventoryAllocator();
        ArrayList<Map<String, Map<String, Integer>>> ship = i.cheapestShipment(this.file);
        JsonNode arrayListNode = objectMapper.convertValue(ship, JsonNode.class);
        File file = new File(this.expectedFile);
        JsonNode arrayListNode2 = objectMapper.readValue(file, JsonNode.class);

        assertEquals(compare(arrayListNode, arrayListNode2), 0);


    }


}
