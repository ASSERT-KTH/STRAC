package align;

import java.util.List;

public interface RepresentationFunction<T, R> {

    R getRepresentativeElement(List<T> target);

}
