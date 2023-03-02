package hello.springcoreadvanced2.common.service;

import lombok.extern.slf4j.Slf4j;

/**
 * CGLIB 예제를 위한 서비스 구현체<br>
 * - {@link ServiceInterface} 상속
 */
@Slf4j
public class ServiceImpl implements ServiceInterface {

    @Override
    public void save() {
        log.info("save 호출");
    }

    @Override
    public void find() {
        log.info("find 호출");
    }
}
