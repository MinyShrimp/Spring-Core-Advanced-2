package hello.springcoreadvanced2.trace;

import lombok.Getter;

import java.util.UUID;

/**
 * 현재 Trace 의 정보
 */
@Getter
public class TraceId {
    /**
     * 쓰레드 고유 번호
     */
    private final String id;

    /**
     * 현재 Trace Level
     */
    private final int level;

    /**
     * 외부 호출용 생성자
     */
    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    /**
     * 내부 호출용 생성자
     *
     * @param id    참여중인 Trace ID
     * @param level 현재 Trace Level
     */
    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    /**
     * 새로운 UUID 생성 - {@link TraceId#TraceId()}
     */
    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * @return 자식 Level Trace
     */
    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    /**
     * @return 부모 Level Trace
     */
    public TraceId createPreviousId() {
        return new TraceId(id, level - 1);
    }

    /**
     * @return {@link #level} == 0
     */
    public boolean isFirstLevel() {
        return level == 0;
    }
}
