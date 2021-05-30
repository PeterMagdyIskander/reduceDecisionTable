/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reducingtable;

import static java.lang.Math.pow;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Not a gaming laptop
 */
public class Reducingtable {

    public static Hashtable<String, ArrayList<String>> reducedTable = new Hashtable<>();
    public static double numberOfConditions = 0;
    public static int numberOfColInEachBigArr = 0;
    public static double numberOfConditionValues = 0;
    public static String[] ActionValues;

    static ArrayList<String> getInput() {
        ArrayList<String> conditionsAndOutput = new ArrayList<>();
        Scanner scannerObject = new Scanner(System.in);
        System.out.print("Conditions separated by commas : ");
        String input = scannerObject.nextLine();
        String[] conditions = input.split(",");
        numberOfConditions = conditions.length;
        System.out.print("Condition Values separated by commas : ");
        input = scannerObject.nextLine();
        String[] conditionValues = input.split(",");
        numberOfConditionValues = conditionValues.length;
        System.out.print("Actions : ");
        input = scannerObject.nextLine();
        System.out.print("Action Values separated by commas : ");
        input = scannerObject.nextLine();
        ActionValues = input.split(",");
        
        double numberOfIterations = pow(numberOfConditionValues, numberOfConditions);
        System.out.println("Action scenario ");
        for (int i = 0; i < numberOfIterations; i++) {
            input = scannerObject.nextLine();
            conditionsAndOutput.add(input);
        }
         
                 /*
        conditionsAndOutput.add("PE,=<75,Y,C");
        conditionsAndOutput.add("PE,=<75,N,C");

        conditionsAndOutput.add("PE,>75,Y,DC");
        conditionsAndOutput.add("PE,>75,N,DC");

        conditionsAndOutput.add("PA,=<75,Y,C");
        conditionsAndOutput.add("PA,=<75,N,DC");
        conditionsAndOutput.add("PA,>75,Y,C");
        conditionsAndOutput.add("PA,>75,N,DC");
*/
        return conditionsAndOutput;
    }

    static ArrayList<String> getArr(int numOfConditions, String action, ArrayList<String> conditionsAndOutput) {
        ArrayList<String> allConditions = new ArrayList<>();
        for (int i = 0; i < conditionsAndOutput.size(); i++) {
            String[] key = conditionsAndOutput.get(i).split(",");
            if (key[numOfConditions].equals(action)) {
                allConditions.add(conditionsAndOutput.get(i));
            }
        }
        return allConditions;
    }

    static Hashtable<String, ArrayList<String>> makeHashtable(int numOfConditions, ArrayList<String> conditionsAndOutput) {

        Hashtable<String, ArrayList<String>> sameAction = new Hashtable<>();
        for (int i = 0; i < conditionsAndOutput.size(); i++) {
            String[] key = conditionsAndOutput.get(i).split(",");
            sameAction.put(key[numOfConditions], getArr(numOfConditions, key[numOfConditions], conditionsAndOutput));
        }
        return sameAction;
    }

    static void reduction(int numOfConditions, String Letter, ArrayList<String> conditionsAndOutput) {
        Hashtable<String, ArrayList<String>> sameAction = makeHashtable(numOfConditions, conditionsAndOutput);
        int size = sameAction.get(Letter).size();
        int[] falseIndices = new int[size];
        ArrayList<ArrayList<Integer>> indexToMakeDash = new ArrayList<>();
        ////initial all the array is populated with 2 meaning everything can change
        for (int i = 0; i < size; i++) {
            falseIndices[i] = 2;
            ArrayList<Integer> toBeAddedToList = new ArrayList<>();
            toBeAddedToList.add(789);
            indexToMakeDash.add(toBeAddedToList);
        }
        ArrayList<String> bigArr = sameAction.get(Letter);
        for (int i = 0; i < bigArr.size(); i++) {
            if (falseIndices[i] != 0 && falseIndices[i] != 1) {

                for (int j = i + 1; j < bigArr.size(); j++) {
                    if (falseIndices[j] != 0 && falseIndices[j] != 1) {
                        int counter = 0;
                        String[] compare = bigArr.get(i).split(",");
                        String[] compare2 = bigArr.get(j).split(",");
                        ArrayList<Integer> toBeAddedToList = new ArrayList<>();
                        for (int k = 0; k <= numOfConditions - 1; k++) {
                            if (compare[k].equals(compare2[k])) {
                                counter++;
                            }
                        }
                        if (counter == numOfConditions - 1) {

                            for (int k = 0; k <= numOfConditions - 1; k++) {
                                if (!compare[k].equals(compare2[k])) {
                                    toBeAddedToList.add(k);
                                }
                            }
                            //change = 1
                            //ignore and remove = 0
                            falseIndices[i] = 1;
                            falseIndices[j] = 0;
                            indexToMakeDash.set(i, toBeAddedToList);
                            break;
                        }
                    }
                }

            }
        }
        merge(numOfConditions, falseIndices, indexToMakeDash, Letter, conditionsAndOutput);
    }

    static void merge(int numOfConditions, int[] falseIndices, ArrayList<ArrayList<Integer>> indexToMakeDash, String Letter, ArrayList<String> conditionsAndOutput) {
        Hashtable<String, ArrayList<String>> sameAction = makeHashtable(numOfConditions, conditionsAndOutput);
        ArrayList<String> bigArr = sameAction.get(Letter);
        ArrayList<String> newSameAction = new ArrayList<>();
        for (int i = 0; i < falseIndices.length; i++) {
            if (falseIndices[i] == 1) {
                String newString;
                String[] newStrings = null;
                for (int j = 0; j < indexToMakeDash.get(i).size(); j++) {
                    newStrings = bigArr.get(i).split(",");
                    newStrings[indexToMakeDash.get(i).get(j)] = "-";
                }
                newString = String.join(",", newStrings);
                newSameAction.add(newString);
            } else if (falseIndices[i] == 2) {
                String newString = bigArr.get(i);
                newSameAction.add(newString);
            }
        }
        reducedTable.put(Letter, newSameAction);
    }

    public static void assembly(ArrayList<String> conditionsAndOutput) {
        for (int i = 0; i < ActionValues.length; i++) {
            reduction((int) numberOfConditions, ActionValues[i], conditionsAndOutput);
        }
        System.out.println("Number Of Rules = " + (int) pow(numberOfConditionValues, numberOfConditions));
        for (int i = 0; i < ActionValues.length; i++) {
            ArrayList<String> arr = reducedTable.get(ActionValues[i]);
            for (int j = 0; j < arr.size(); j++) {
                System.out.println(arr.get(j));
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<String> conditionsAndOutput = getInput();
        assembly(conditionsAndOutput);
    }
}
