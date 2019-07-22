import align.ICellComparer;
import align.implementations.DTW;
import align.implementations.FastDTW;
import align.implementations.IImplementationInfo;
import core.LogProvider;
import core.ServiceRegister;
import core.TestLogProvider;
import core.TraceHelper;
import core.models.TraceMap;
import interpreter.AlignInterpreter;
import interpreter.dto.Alignment;
import interpreter.dto.Payload;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClassRegisterTest {


    @Test
    public void loadClasses(){


        ServiceRegister.setup();
    }

}
