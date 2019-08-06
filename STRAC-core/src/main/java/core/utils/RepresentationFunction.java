package core.utils;

import core.data_structures.IArray;

import java.util.List;

public interface RepresentationFunction<T, R> {

    R getRepresentativeElement(List<T> target);

}
