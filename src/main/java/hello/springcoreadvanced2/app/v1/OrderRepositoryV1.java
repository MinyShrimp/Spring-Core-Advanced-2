package hello.springcoreadvanced2.app.v1;

/**
 * OrderRepository 인터페이스<br>
 * - {@link OrderRepositoryV1Impl}
 */
public interface OrderRepositoryV1 {

    /**
     * 저장 로직
     *
     * @param itemId 아이템 ID
     */
    void save(String itemId);
}
