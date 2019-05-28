package core.persistence;

import core.data_structures.IArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.stream.Stream;

public abstract class PersistentDataStructure {

    protected File _storage;
    protected RandomAccessFile _access;

    protected PersistentDataStructure(File storeIn) throws FileNotFoundException {
        this._storage = storeIn;

        _access = new RandomAccessFile(_storage, "rw");
    }

}
