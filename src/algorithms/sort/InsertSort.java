package algorithms.sort;

import java.util.List;

/**
 * сортировка вставкой
 * время работы О(n*n).
 */
public class InsertSort<T extends Comparable<? super T>> extends Sort<T>{
    @Override
    public void customSort(List<T> list) {
        for(int i=1;i<list.size();i++){
            T currentElement=list.get(i);
            int j=i-1;
            while (j>=0 && list.get(j).compareTo(currentElement)>0){
                list.set(j+1,list.get(j));
                j--;
            }
            list.set(j+1,currentElement);
        }
    }
}
