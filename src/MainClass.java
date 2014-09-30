import algorithms.sort.Shell;
import algorithms.sort.Sort;
import other.InvalidTypeListException;
import structs.RedBlackTree;

import javax.jnlp.IntegrationService;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class MainClass {
    /*
    @param args строка параметров
    */

    public static void main(String[] args) throws Exception{
        final int size=100000;

        Sort<Integer> s=new Shell<Integer>();
        List<Integer>array=new ArrayList<Integer>(size);
        Random r=new Random(System.currentTimeMillis());
        for (int i = 0; i <size ; i++) {
            array.add(r.nextInt());
        }
        s.sort(array);
        method(array);
    }

    static void method(List<Integer>list) throws Exception {
        for (int i = 1; i <list.size() ; i++) {
            if(list.get(i).compareTo(list.get(i-1))<0){
                throw new Exception();
            }
        }
    }
}

