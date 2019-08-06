package core.utils;

import core.data_structures.IDisposable;

public class ServiceRegister {

    static IServiceProvider _provider;
    static ServiceRegister register;

    public IServiceProvider getProvider(){

        if(_provider == null){
            _provider = new AllocatorServiceProvider();
        }

        return _provider;
    }

    protected ServiceRegister(){}

    public static ServiceRegister getInstance(){
        if(register == null)
            register = new ServiceRegister();

        return register;
    }

}
