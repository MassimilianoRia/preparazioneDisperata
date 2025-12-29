package a02b.e2;

public interface Logics {

    void addColCounter(Position pos);

    void addRowCounter(Position pos);

    void addCell(Position pos);

    void hit(Position pos);

    int getValue(Position pos);

    void reset();

}