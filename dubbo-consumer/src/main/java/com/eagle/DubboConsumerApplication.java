package com.eagle;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.fastjson.JSON;
import com.eagle.api.StudentService;
import com.eagle.common.dubbo.DubboGenericInvokeHelper;
import com.eagle.entity.Grade;
import com.eagle.entity.Student;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.eagle")
@RestController
public class DubboConsumerApplication {
    @Reference(timeout = 40000)
    StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);
    }
    @RequestMapping("/api/public")
    public String helloWorld(){
        String interfaceClass="com.eagle.api.ClothService";
        String methodName="cloth";
        ArrayList<Map<String, Object>> parameters = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        //指定暴露服务接口中的方法入参的类型
        map.put("ParamType","com.eagle.entity.Color");
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("red", "square");
        HashMap<String, String> worldMap = new HashMap<>();
        worldMap.put("world", "wolrd");
        map1.put("world", worldMap);
        //给指定暴露服务接口中的方法入参传递值

        map.put("Object",map1);

        parameters.add(map);
        Object result = DubboGenericInvokeHelper.dubboGenericInvoke(interfaceClass, methodName, parameters);
        System.out.println(JSON.toJSONString(result));
        return JSON.toJSONString(result);
    }

    @RequestMapping("/dubbo")
    public String dubbo(){
        Student student = new Student();
        Grade grade = studentService.student(student);
        return JSON.toJSONString(grade);
    }

}
