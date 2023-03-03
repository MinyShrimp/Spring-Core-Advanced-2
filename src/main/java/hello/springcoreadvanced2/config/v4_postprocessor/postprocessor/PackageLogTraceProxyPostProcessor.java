package hello.springcoreadvanced2.config.v4_postprocessor.postprocessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 빈 후처리기를 사용해서 프록시를 등록
 *
 * @see BeanPostProcessor
 * @see BeanPostProcessor#postProcessAfterInitialization(Object, String)
 */
@Slf4j
@RequiredArgsConstructor
public class PackageLogTraceProxyPostProcessor implements BeanPostProcessor {

    private final String basePackage;
    private final Advisor advisor;

    /**
     * 빈 생성 -> @PostConstruct -> postProcessAfterInitialization
     *
     * @return 프록시 생성
     */
    @Override
    public Object postProcessAfterInitialization(
            Object bean,
            String beanName
    ) throws BeansException {
        log.info("[       param] beanName = {} bean = {}", beanName, bean);

        String packageName = bean.getClass().getPackageName();
        if (!packageName.startsWith(basePackage)) {
            return bean;
        }

        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);

        Object proxy = proxyFactory.getProxy();
        log.info("[create proxy] target = {} proxy = {}", bean.getClass(), proxy.getClass());

        return proxy;
    }
}
