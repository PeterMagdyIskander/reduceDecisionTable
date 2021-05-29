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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numberOfconditions=3;
        int numberOfActions=1;
        ArrayList<String>conditionsAndOutput=new ArrayList<>();
        conditionsAndOutput.add("F,F,E");
        conditionsAndOutput.add("T,F,E");
        conditionsAndOutput.add("F,T,E");
        conditionsAndOutput.add("T,T,H");
        Hashtable<String,String[]> sameAction = new Hashtable<>();
        for(int i =0;i<conditionsAndOutput.size();i++){
            String[] arrOfStr =conditionsAndOutput.get(i).split(",",3);
            
            sameAction.put(conditionsAndOutput.get(i).substring(4), arrOfStr);
        }
        System.out.println(sameAction);
        
    }
}
