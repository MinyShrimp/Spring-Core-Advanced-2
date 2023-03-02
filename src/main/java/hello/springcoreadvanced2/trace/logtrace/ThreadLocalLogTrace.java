package hello.springcoreadvanced2.trace.logtrace;

import hello.springcoreadvanced2.trace.TraceId;
import hello.springcoreadvanced2.trace.TraceStatus;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;

/**
 * ThreadLocal 기반 LogTrace
 */
@Slf4j
public class ThreadLocalLogTrace implements LogTrace {

    private final ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);

        return new TraceStatus(traceId, startTimeMs, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    @Override
    public boolean isFirstLevel() {
        return traceIdHolder.get() == null;
    }

    /**
     * 현재 Trace 종료<br>
     * - {@link #end}, {@link #exception}에서 호출
     *
     * @param status 현재 상태
     * @param e      발생한 예외
     */
    private void complete(
            @Nonnull TraceStatus status,
            @Nullable Exception e
    ) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTime = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info(
                    "[{}] {}{} time = [{}ms]",
                    traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()),
                    status.getMessage(), resultTime
            );
        } else {
            log.info(
                    "[{}] {}{} time = [{}ms] e = {}",
                    traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()),
                    status.getMessage(), resultTime, e.toString()
            );
        }

        releaseTraceId();
    }

    /**
     * 다음 TraceID 로 전환<br>
     * - {@link #begin}에서 호출
     */
    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        } else {
            traceIdHolder.set(traceId.createNextId());
        }
    }

    /**
     * 이전 TraceID 로 전환<br>
     * - {@link #complete}에서 호출
     */
    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirstLevel()) {
            traceIdHolder.remove();
        } else {
            traceIdHolder.set(traceId.createPreviousId());
        }
    }
}