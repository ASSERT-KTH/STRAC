package strac.align.align.implementations;

import strac.align.align.Aligner;

public interface IImplementationInfo {

    Aligner getAligner(Object...params);
}
