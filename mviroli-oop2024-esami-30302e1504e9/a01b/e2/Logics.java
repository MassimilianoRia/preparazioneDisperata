package a01b.e2;

public interface Logics {

    void add(Position pos);

    void hit(Position pos);

    Position getFirstHitPosition();

    int getGridValue(Position pos);

}