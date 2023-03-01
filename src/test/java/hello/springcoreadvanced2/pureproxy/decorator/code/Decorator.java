package hello.springcoreadvanced2.pureproxy.decorator.code;

/**
 * GOF 에서 정의한 Decorator 패턴의 구현 방식
 */
public abstract class Decorator implements Component {
    private final Component target;

    public Decorator(Component target) {
        this.target = target;
    }

    @Override
    public String operation() {
        return target.operation();
    }
}
