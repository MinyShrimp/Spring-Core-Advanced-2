package hello.springcoreadvanced2.trace;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Trace 상태 정보
 */
@Getter
@RequiredArgsConstructor
public class TraceStatus {
    /**
     * Trace ID
     */
    private final TraceId traceId;

    /**
     * 시작 시간
     */
    private final Long startTimeMs;

    /**
     * 메서드 이름
     */
    private final String message;
}
