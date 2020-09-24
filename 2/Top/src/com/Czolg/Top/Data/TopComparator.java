package com.Czolg.Top.Data;

import java.util.Comparator;
import java.util.List;

public class TopComparator <T extends Comparable<T>> implements Comparator<List<T>> {

    @Override
    public int compare(List<T> o1, List<T> o2) {
        return o1.get(1).compareTo(o2.get(1));
    }

}
