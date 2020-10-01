public interface StateSpaceModel {
    Object action(Object o);

    void allActions(boolean turn);

    Object result();

    Object state();

    int cost();

    boolean goal();
}