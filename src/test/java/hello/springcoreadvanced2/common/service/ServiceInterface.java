package hello.springcoreadvanced2.common.service;

/**
 * CGLIB 예제를 위한 서비스 인터페이스<br>
 * - 구현체: {@link ServiceImpl}
 */
public interface ServiceInterface {

    /**
     * 저장
     */
    void save();

    /**
     * 조회
     */
    void find();
}
