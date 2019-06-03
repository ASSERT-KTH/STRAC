package align.implementations;

import align.Aligner;
import scripts.Align;

public interface IImplementationInfo {

    Aligner getAligner(Object...params);
}
