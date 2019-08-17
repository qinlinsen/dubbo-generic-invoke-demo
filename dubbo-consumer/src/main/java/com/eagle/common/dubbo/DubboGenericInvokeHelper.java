package com.eagle.common.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author qinlinsen
 */
public class DubboGenericInvokeHelper {
    /**
     * 这里着重解释一下parameters暴露服务中的入参
     * 这是一个List<Map<String,Object>形式的。
     * 以dubbo-provider工程中的ClothService中的暴露服务的方法Shape cloth(Color color)为例;
     * 这个服务提供中如何传递color这个入参呢。
     * 操作步骤如下：
     * ====================================================
     * 首先指定这个color的类型是com.eagle.entity.Color
     * 即： HashMap<String, Object> map = new HashMap<>();
     *         map.put("ParamType","com.eagle.entity.Color");
     * 其次 给这个color赋值
     *                map.put("Object",map1);
     *     这个map1就是color这个变量的值,dubbo内部会自动的把一个map1对象转成对应的实体类
     *     其中map1中的key就是相应实体类中的属性,value就是给属性赋的值。
     *     对一个实体类中的引用类型,也可以看成一个map,所以对于传递参数而言只要是引用类型都可以传递map。
     *     具体的map转成实体类由dubbo框架帮我们做。
     *
     * ====================================================
     *
     *
     * @param interfaceClass  暴露服务的接口的全限名
     * @param methodName      暴露服务中的方法名
     * @param parameters      暴露服务中的入参
     * @return
     */
    public static Object dubboGenericInvoke(String interfaceClass, String methodName, List<Map<String, Object>> parameters){
        Object result = DubboGenericInvokeHelper.getInstance().genericInvoke(interfaceClass, methodName, parameters);
        return result;
    }


    private ApplicationConfig application;
    private RegistryConfig registry;

    private static class SingletonHolder {
        private static DubboGenericInvokeHelper INSTANCE = new DubboGenericInvokeHelper();
    }

    private DubboGenericInvokeHelper(){
        Properties prop = new Properties();
        ClassLoader loader = DubboGenericInvokeHelper.class.getClassLoader();

        try {
            prop.load(loader.getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(prop.getProperty("dubbo.application.name"));
        //这里配置了dubbo的application信息*(demo只配置了name)*，因此demo没有额外的dubbo.xml配置文件
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(prop.getProperty("dubbo.registry.address"));
        registryConfig.setProtocol(prop.getProperty("dubbo.registry.protocol"));
        registryConfig.setId(prop.getProperty("dubbo.registry.id"));

        //这里配置dubbo的注册中心信息，因此demo没有额外的dubbo.xml配置文件

        this.application = applicationConfig;
        this.registry = registryConfig;

    }

    private static DubboGenericInvokeHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Object genericInvoke(String interfaceClass, String methodName, List<Map<String, Object>> parameters){

        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(application);
        reference.setRegistry(registry);
        reference.setInterface(interfaceClass); // 接口名
        reference.setGeneric(true); // 声明为泛化接口

        //ReferenceConfig实例很重，封装了与注册中心的连接以及与提供者的连接，
        //需要缓存，否则重复生成ReferenceConfig可能造成性能问题并且会有内存和连接泄漏。
        //API方式编程时，容易忽略此问题。
        //这里使用dubbo内置的简单缓存工具类进行缓存

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);
        // 用com.alibaba.dubbo.rpc.service.GenericService可以替代所有接口引用

        int len = parameters.size();
        String[] invokeParamTyeps = new String[len];
        Object[] invokeParams = new Object[len];
        for(int i = 0; i < len; i++){
            invokeParamTyeps[i] = parameters.get(i).get("ParamType") + "";
            invokeParams[i] = parameters.get(i).get("Object");
        }
        return genericService.$invoke(methodName, invokeParamTyeps, invokeParams);
    }
}
