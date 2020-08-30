package main.java;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class InventoryAllocator {

    /*
    reads the file and puts the Json object in a HashMap
     */
    public HashMap<String, Object> getInputs(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(fileName);
        HashMap<String, Object> order = objectMapper.readValue(file, HashMap.class);
        return order;


    }

    /**
     * checks to see if anything wants to be ordered in the first place
     * @param order
     * @return true if items are ordered, false if no orders were shipped in the first place
     */
    public boolean areThereOrders(HashMap<String, Object> order){
        for(String item : order.keySet()){
            if((int) order.get(item) == 0){
                return false;
            }
        }
        return true;
    }
    /*
    algorithm that keeps track of how it can distribute within the inventory to ship the number of items that
    are going to be ordered.

     */
    public ArrayList<Map<String, Map<String, Integer>>> cheapestShipment(String fileName) throws IOException {
        ArrayList<Map<String, Map<String, Integer>>> shipments = new ArrayList<>();
        Map<String,Integer> a = new HashMap<>();
        Object name = "";
        HashMap <String, Object> order = getInputs(fileName);
        HashMap<String, Object> ord = (HashMap<String, Object>) order.get("order");
        if(!areThereOrders(ord)){
            return shipments;
        }
        List<Map<String,Map<String,Integer>>> inventory = new ArrayList<>();
        for(String item : order.keySet()){
            if(item.equals("inventory_list")){
                 ArrayList<HashMap<String, Object>> distributions = (ArrayList<HashMap<String, Object>>) order.get("inventory_list");
                for(HashMap<String, Object> element : distributions){
                     name = element.get("name");
                    LinkedHashMap<String, Integer> objects = (LinkedHashMap<String, Integer>) element.get("inventory");
                    List<Map<String, List<Map<String, Integer>>>> dist = new ArrayList<>();
                    a = new HashMap<>();
                    for(String x: objects.keySet()){
                        if(ord.containsKey(x) && (int) ord.get(x) > 0){
                            int leftOver = (int) ord.get(x) - objects.get(x);
                            if(leftOver == 0 || leftOver < 0 || leftOver < (int) ord.get(x)){
                                a.put(x, objects.get(x));
                                ord.put(x, leftOver);
                            }

                        }
                    }
                    Map<String,Map<String,Integer>> ship = new HashMap<>();
                    ship.put((String)name, a);
                    inventory.add(ship);


                }

            }
        }

        boolean all_is_empty = true;
        for(String it : ord.keySet()){
            if((int) ord.get(it) > 0){
                all_is_empty = false;
            }
        }
        if(all_is_empty){
            for(int i = 0; i < inventory.size(); i++){
                shipments.add(inventory.get(i));
            }
        }


    return shipments;
    }


}
