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

    public static Hashtable<String, ArrayList<String>> reducedTable = new Hashtable<>();
    
    static ArrayList<String> getInput() {
        ArrayList<String> conditionsAndOutput = new ArrayList<>();
        conditionsAndOutput.add("F,F,E");
        conditionsAndOutput.add("T,F,E");
        conditionsAndOutput.add("F,T,E");
        conditionsAndOutput.add("T,T,H");
        return conditionsAndOutput;
    }

    static ArrayList<String> getArr(String action) {
        ArrayList<String> conditionsAndOutput = getInput();
        ArrayList<String> allConditions = new ArrayList<>();
        for (int i = 0; i < conditionsAndOutput.size(); i++) {
            if (conditionsAndOutput.get(i).endsWith(action)) {
                allConditions.add(conditionsAndOutput.get(i));
            }
        }
        return allConditions;
    }

    static Hashtable<String, ArrayList<String>> makeHashtable() {
        ArrayList<String> conditionsAndOutput = getInput();
        Hashtable<String, ArrayList<String>> sameAction = new Hashtable<>();
        for (int i = 0; i < conditionsAndOutput.size(); i++) {
            sameAction.put(conditionsAndOutput.get(i).substring(4), getArr(conditionsAndOutput.get(i).substring(4)));
        }
        return sameAction;
    }

    static void reduction(int compareSize, String Letter,int size) {
        int[]falseIndices=new int[size];
        
        ////ignore=2
        for(int i =0;i<size;i++)
            falseIndices[i]=2;
        ArrayList<ArrayList<Integer>> indexToMakeDash = new ArrayList<>();
        Hashtable<String, ArrayList<String>> sameAction = makeHashtable();
        ArrayList<String> bigArr = sameAction.get(Letter);
        for (int i = 0; i < bigArr.size(); i++) {
            String[] compare = bigArr.get(i).split(",");
            for (int j = i + 1; j < bigArr.size(); j++) {
                int counter = 0;
                String[] compare2 = bigArr.get(j).split(",");
                for (int k = 0; k < compare.length; k++) {
                    if (compare[k].equals(compare2[k])) {
                        counter++;
                    }
                }
                if (counter == compareSize) {
                    //change = 1
                    //ignore and remove = 0
                    falseIndices[i]=1;
                    falseIndices[j]=0;
                    i = i + 2;
                    for (int k = 0; k < compare.length; k++) {
                        ArrayList<Integer> toBeAddedToList = new ArrayList<>();
                        if (compare[k] != compare2[k]) {

                            toBeAddedToList.add(k);

                        }
                        indexToMakeDash.add(toBeAddedToList);
                    }
                }else{
                    ArrayList<Integer> toBeAddedToList = new ArrayList<>();
                    toBeAddedToList.add(0);
                    indexToMakeDash.add(toBeAddedToList);
                }
            }
        }
        merge(falseIndices,indexToMakeDash,Letter);
    }
    static void merge(int[]falseIndices,ArrayList<ArrayList<Integer>> indexToMakeDash,String Letter){
        Hashtable<String, ArrayList<String>> sameAction = makeHashtable();
        ArrayList<String> bigArr = sameAction.get(Letter);
        ArrayList<String> newSameAction= new ArrayList<>();
        for(int i =0;i<falseIndices.length;i++){
            if(falseIndices[i]==1){
                String newString=bigArr.get(i);
                char dash='-';
               StringBuilder sb=new StringBuilder(newString);
                for(int j=0;j<indexToMakeDash.get(i).size();j++){
                    sb.setCharAt(indexToMakeDash.get(i).get(j), dash);
                    newString=sb.toString();
                }
            newSameAction.add(newString);
            }else if(falseIndices[i]==2){
                String newString=bigArr.get(i);
                newSameAction.add(newString);
            }
        }
        reducedTable.put(Letter, newSameAction);
    }
    public static void main(String[] args) {
        reduction(1,"H",1);
        reduction(1,"E",3);
        ArrayList<String> arr = reducedTable.get("E");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
        System.out.println("-------------------------------------");
        arr = reducedTable.get("H");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }
}
