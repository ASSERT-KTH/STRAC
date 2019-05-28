package core;

public class ServiceRegister {

    static IServiceProvider _provider;

    public static void registerProvider(IServiceProvider provider){
        _provider = provider;
    }

    public static IServiceProvider getProvider(){
        return _provider;
    }

}
