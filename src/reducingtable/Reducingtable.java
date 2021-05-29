/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reducingtable;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Not a gaming laptop
 */
public class Reducingtable {

    static ArrayList<String> getInput() {
        ArrayList<String> conditionsAndOutput = new ArrayList<>();
        conditionsAndOutput.add("F,F,E");
        conditionsAndOutput.add("T,F,E");
        conditionsAndOutput.add("F,T,E");
        conditionsAndOutput.add("T,T,H");
        return conditionsAndOutput;
    }

    static void mergeCol() {

    }
    static ArrayList<String> getArr(String action){
        ArrayList<String> conditionsAndOutput = getInput();
        ArrayList<String> allConditions=new ArrayList<>();
        for(int i =0;i<conditionsAndOutput.size();i++){
            if(conditionsAndOutput.get(i).endsWith(action)){
                allConditions.add(conditionsAndOutput.get(i));
            }
        }
        return allConditions;
    }
    static Hashtable<String, ArrayList<String>> makeHashtable(){
        ArrayList<String> conditionsAndOutput = getInput();
        Hashtable<String, ArrayList<String>> sameAction = new Hashtable<>();
        for (int i = 0; i < conditionsAndOutput.size(); i++) {
            sameAction.put(conditionsAndOutput.get(i).substring(4), getArr(conditionsAndOutput.get(i).substring(4)));
        }
        return sameAction;
    }
    static void reduce() {
        Hashtable<String, ArrayList<String>> sameAction=makeHashtable();
        ArrayList<String> arr = sameAction.get("E");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
        System.out.println("-------------------------------------");
        arr = sameAction.get("H");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }
    
    public static void main(String[] args) {

        reduce();
    }
}
