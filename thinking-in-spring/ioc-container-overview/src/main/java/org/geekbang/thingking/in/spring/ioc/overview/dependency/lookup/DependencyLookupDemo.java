package org.geekbang.thingking.in.spring.ioc.overview.dependency.lookup;

import org.geekbang.thingking.in.spring.ioc.overview.annotation.Super;
import org.geekbang.thingking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找实例
 *
 * 1.配置 XML 配置文件
 * 2.启动 Spring 应用上下文
 *
 * Created by DINGJUN on 2020.06.07.
 */
public class DependencyLookupDemo {
    public static void main(String[] args) {
        /**
         * 通常在maven工程中，会把resources中的文件打包到classes目录下，所以这里可以使用META-INFO/dependency-lookup-context.xml，或者加一个classpath前缀使用绝对路径classpath:/..
         */
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INFO/dependency-lookup-context.xml");

        // 1.通过名称查找
        lookupInRealTime(beanFactory);
        lookupInLazy(beanFactory);

        // 2.通过类型查找. a.单个对象 b.集合类型
        lookupByType(beanFactory);
        lookupCollectionByType(beanFactory);

        // 3.通过注解查找
        lookupByAnnotationType(beanFactory);

    }

    private static void lookupByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, Object> beansWithAnnotation = listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("查找所有 @Super 注解的 User ：" + beansWithAnnotation);
        }
    }

    private static void lookupCollectionByType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找的所有 User 集合：" + users); // key：实例id value：实例对象
        }
    }

    private static void lookupByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("实时获取-类型：" + user);
    }

    private static void lookupInLazy(BeanFactory beanFactory) {
        /**
         * ObjectFactory 对象并不是直接返回了实际的 Bean， 而是一个 Bean的查找代理。
         * 当得到 ObjectFactory 对象时，相当于 Bean 没有被创建，只有当 getObject() 方法时，才会触发实例化等生命周期
         * 这也是 ObjectFactory 和 FactoryBean 的一个重大区别
         */
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延迟获取：" + user);
    }

    private static void lookupInRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println("实时获取：" + user);
    }
}
