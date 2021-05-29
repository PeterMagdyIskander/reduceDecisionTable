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
        conditionsAndOutput.add("PE,=<75,-,C");
        conditionsAndOutput.add("PE,=<75,-,C");
        conditionsAndOutput.add("PE,>75,-,DC");
        conditionsAndOutput.add("PE,>75,-,DC");

        conditionsAndOutput.add("PA,-,Y,C");
        conditionsAndOutput.add("PA,-,N,DC");
        conditionsAndOutput.add("PA,-,Y,C");
        conditionsAndOutput.add("PA,-,N,DC");

        return conditionsAndOutput;
    }

    static ArrayList<String> getArr(int numOfConditions, String action) {
        ArrayList<String> conditionsAndOutput = getInput();
        ArrayList<String> allConditions = new ArrayList<>();
        for (int i = 0; i < conditionsAndOutput.size(); i++) {
            String[] key = conditionsAndOutput.get(i).split(",");
            if (key[numOfConditions].equals(action)) {
                allConditions.add(conditionsAndOutput.get(i));
            }
        }
        return allConditions;
    }

    static Hashtable<String, ArrayList<String>> makeHashtable(int numOfConditions) {
        ArrayList<String> conditionsAndOutput = getInput();
        Hashtable<String, ArrayList<String>> sameAction = new Hashtable<>();
        for (int i = 0; i < conditionsAndOutput.size(); i++) {
            String[] key = conditionsAndOutput.get(i).split(",");
            sameAction.put(key[numOfConditions], getArr(numOfConditions, key[numOfConditions]));
        }
        return sameAction;
    }

    static void reduction(int numOfConditions, String Letter, int size) {
        int[] falseIndices = new int[size];
        ////initial 
        ArrayList<ArrayList<Integer>> indexToMakeDash = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            falseIndices[i] = 2;
        }
        Hashtable<String, ArrayList<String>> sameAction = makeHashtable(numOfConditions);
        ArrayList<String> bigArr = sameAction.get(Letter);
        for (int i = 0; i < bigArr.size(); i++) {
            if (falseIndices[i] == 2) {
                String[] compare = bigArr.get(i).split(",");
                for (int j = i + 1; j < bigArr.size(); j++) {
                    if (falseIndices[j] == 2) {
                        int counter = 0;
                        String[] compare2 = bigArr.get(j).split(",");
                        ArrayList<Integer> toBeAddedToList = new ArrayList<>();
                        for (int k = 0; k <= numOfConditions - 1; k++) {
                            if (compare[k].equals(compare2[k]) && !(compare[k].equals("-") && compare2[k].equals("-"))) {
                                counter++;

                            } else {
                                toBeAddedToList.add(k);
                            }
                        }
                        if (counter == numOfConditions - 1) {
                            //change = 1
                            //ignore and remove = 0
                            if(falseIndices[i]==1){
                                continue;
                            }
                            falseIndices[i] = 1;
                            falseIndices[j] = 0;
                            indexToMakeDash.add(toBeAddedToList);
                        } else {
                            falseIndices[i] = 2;
                            toBeAddedToList.clear();
                            indexToMakeDash.add(toBeAddedToList);
                        }
                    }
                }
            }

        }
        merge(numOfConditions, falseIndices, indexToMakeDash, Letter);
    }

    static void merge(int numOfConditions, int[] falseIndices, ArrayList<ArrayList<Integer>> indexToMakeDash, String Letter) {
        Hashtable<String, ArrayList<String>> sameAction = makeHashtable(numOfConditions);
        ArrayList<String> bigArr = sameAction.get(Letter);
        ArrayList<String> newSameAction = new ArrayList<>();
        for (int i = 0; i < falseIndices.length; i++) {
            if (falseIndices[i] == 1) {
                String newString = bigArr.get(i);
                char dash = '-';
                StringBuilder sb = new StringBuilder(newString);
                for (int j = 0; j < indexToMakeDash.get(i).size(); j++) {
                    sb.setCharAt(indexToMakeDash.get(i).get(j), dash);
                    newString = sb.toString();
                }

                newSameAction.add(newString);
            } else if (falseIndices[i] == 2) {
                String newString = bigArr.get(i);
                newSameAction.add(newString);
            }
        }
        reducedTable.put(Letter, newSameAction);
    }

    public static void main(String[] args) {
        reduction(3, "C", 4);
        reduction(3, "DC", 4);

        ArrayList<String> arr = reducedTable.get("C");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }

        System.out.println("-------------------------------------");

        arr = reducedTable.get("DC");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }

    }
}
