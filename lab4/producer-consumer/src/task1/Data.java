package task1;

public class Data {
    private int id;
    private int iteration;
    public Data(int id, int iteration) { this.id = id; this.iteration = iteration; }
    public Data(int id) { this(id, 0); }

    public void incrementIteration() { iteration++; }

    @Override
    public String toString() { return "Data {id = " + id + ", iteration = " + iteration + "}"; }
}
