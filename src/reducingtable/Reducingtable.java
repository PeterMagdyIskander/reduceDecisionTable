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

    public static Hashtable<ArrayList<String>, ArrayList<String>> reducedTable = new Hashtable<>();
    public static int numberOfConditions = 0;
    public static int numberOfColInEachBigArr = 0;
    public static int numberOfConditionValues = 0;
    public static String[] ActionValues;
    public static ArrayList<ArrayList<String>> keyValues = new ArrayList<>();

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
        ///a harder test case
        /*
       a,y,m,W,X,EMP,Z
       a,y,f,EMP,EMP,EMP,Z
       a,n,m,EMP,X,EMP,Z
       a,n,f,EMP,EMP,EMP,Z
       b,y,m,W,EMP,EMP,Z
       b,y,f,EMP,EMP,EMP,Z
       b,n,m,EMP,EMP,EMP,Z
       b,n,f,EMP,EMP,Y,Z
         */
        return conditionsAndOutput;
    }

    static ArrayList<String> getArr(int numOfConditions, ArrayList<String> actionCol, ArrayList<String> conditionsAndOutput) {

        ArrayList<String> allConditions = new ArrayList<>();
        for (int i = 0; i < conditionsAndOutput.size(); i++) {
            String[] key = conditionsAndOutput.get(i).split(",");

            boolean typical = true;
            for (int j = numOfConditions; j < key.length; j++) {

                if (!key[j].equals(actionCol.get(j-numOfConditions).toString())) {
                    typical = false;
                    break;
                }
            }
            if (typical) {
                allConditions.add(conditionsAndOutput.get(i));
            }

        }
        return allConditions;
    }

    static Hashtable<ArrayList<String>, ArrayList<String>> makeHashtable(int numOfConditions, ArrayList<String> conditionsAndOutput) {

        Hashtable<ArrayList<String>, ArrayList<String>> sameAction = new Hashtable<>();

        for (int i = 0; i < conditionsAndOutput.size(); i++) {
            String[] colOfConditionsAndOutput = conditionsAndOutput.get(i).split(",");
            ArrayList<String> actionCol = new ArrayList<>();
            for (int j = numOfConditions; j < colOfConditionsAndOutput.length; j++) {

                actionCol.add(colOfConditionsAndOutput[j]);
            }
            sameAction.put(actionCol, getArr(numOfConditions, actionCol, conditionsAndOutput));
            if (!keyValues.contains(actionCol)) {
                keyValues.add(actionCol);
            }

        }
        return sameAction;
    }

    static void reduction(int numOfConditions, ArrayList<String> key, ArrayList<String> conditionsAndOutput) {
        Hashtable<ArrayList<String>, ArrayList<String>> sameAction = makeHashtable(numOfConditions, conditionsAndOutput);
        int size = sameAction.get(key).size();
        int[] falseIndices = new int[size];
        ArrayList<ArrayList<Integer>> indexToMakeDash = new ArrayList<>();
        ////initial all the array is populated with 2 meaning everything can change
        for (int i = 0; i < size; i++) {
            falseIndices[i] = 2;
            ArrayList<Integer> toBeAddedToList = new ArrayList<>();
            toBeAddedToList.add(789);
            indexToMakeDash.add(toBeAddedToList);
        }
        ArrayList<String> bigArr = sameAction.get(key);
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
        merge(numOfConditions, falseIndices, indexToMakeDash, key, conditionsAndOutput);
    }

    static void merge(int numOfConditions, int[] falseIndices, ArrayList<ArrayList<Integer>> indexToMakeDash, ArrayList<String> key, ArrayList<String> conditionsAndOutput) {
        Hashtable<ArrayList<String>, ArrayList<String>> sameAction = makeHashtable(numOfConditions, conditionsAndOutput);
        ArrayList<String> bigArr = sameAction.get(key);
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
        reducedTable.put(key, newSameAction);
    }

    public static void assembly(ArrayList<String> conditionsAndOutput) {

        for (int i = 0; i < keyValues.size(); i++) {
            reduction((int) numberOfConditions, keyValues.get(i), conditionsAndOutput);
        }

        System.out.println("Number Of Rules = " + (int) pow(numberOfConditionValues, numberOfConditions));
        afterReduction();
    }

    public static void beforeReduction(Hashtable<ArrayList<String>, ArrayList<String>> sameAction) {
        System.out.println("------- BEFORE REDUCING -------");
        for (int i = 0; i < keyValues.size(); i++) {
            ArrayList<String> arr = sameAction.get(keyValues.get(i));
            for (int j = 0; j < arr.size(); j++) {
                System.out.println(arr.get(j));
            }
        }
    }

    public static void afterReduction() {
        System.out.println("------- AFTER REDUCING -------");
        for (int i = 0; i < keyValues.size(); i++) {
            ArrayList<String> arr = reducedTable.get(keyValues.get(i));
            for (int j = 0; j < arr.size(); j++) {
                System.out.println(arr.get(j));
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<String> conditionsAndOutput = getInput();
        Hashtable<ArrayList<String>, ArrayList<String>> sad;
        sad = makeHashtable(numberOfConditions, conditionsAndOutput);
        beforeReduction(sad);
        assembly(conditionsAndOutput);
    }
}
