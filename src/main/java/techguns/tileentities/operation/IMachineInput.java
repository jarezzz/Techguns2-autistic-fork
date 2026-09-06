package techguns.tileentities.operation;

public interface IMachineInput<T> {
    boolean matches(T other);
}
