public class FlowEdge {
  private final int v, w; // from and to
  private final double capacity; // capacity
  private double flow; // flow

  public FlowEdge(int v, int w, double capacity) {
    this.v = v;
    this.w = w;
    this.capacity = capacity;
  }

  public int from() {
    return v; 
  }

  public int to() {
    return w;
  }

  public double capacity() {
    return capacity;
  }

  public double flow() {
    return flow; 
  }

  public int other(int vertex) {
    if (vertex == v) return w;
    else if (vertex == w) return v;
    else throw new RuntimeException("Illegal endpoint");
  }

  public double residualCapacityTo(int vertex) {
    if (vertex == v) return flow; // Backward edge
    else if (vertex == w) return capacity - flow;  // forward edge
    else throw new IllegalArgumentException();
  }

  public void addResidualFlowTo(int vertex, double delta) {
    if (vertex == v) flow -= delta; // Backward edge
    else if (vertex == w) flow += delta; // forward edge
    else throw new IllegalArgumentException();
  }
}