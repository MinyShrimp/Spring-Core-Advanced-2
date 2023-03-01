package hello.springcoreadvanced2.trace.logtrace;

import hello.springcoreadvanced2.trace.TraceStatus;

/**
 * 로그 Trace Interface<br>
 * - {@link FieldLogTrace}, {@link ThreadLocalLogTrace}
 */
public interface LogTrace {
    String START_PREFIX = "-->";
    String COMPLETE_PREFIX = "<--";
    String EX_PREFIX = "<X-";

    /**
     * Trace 생성
     *
     * @param message 메시지
     * @return 생성된 Trace
     */
    TraceStatus begin(String message);

    /**
     * 현재 Trace 종료 - 정상 종료
     *
     * @param status {@link TraceStatus}
     */
    void end(TraceStatus status);

    /**
     * 현재 Trace 종료 - 예외 발생
     *
     * @param status {@link TraceStatus}
     * @param e      발생한 예외
     */
    void exception(TraceStatus status, Exception e);

    /**
     * Trace Level에 따라 공백 추가<br>
     * <code>
     * - Level 0: <br>
     * - Level 1: |--><br>
     * - Level 2: |   |-->
     * </code>
     *
     * @param prefix {@link LogTrace#START_PREFIX}, {@link LogTrace#COMPLETE_PREFIX}, {@link LogTrace#EX_PREFIX}
     * @param level  Trace Level
     */
    default String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
