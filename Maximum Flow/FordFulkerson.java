public class FordFulkerson {
  private boolean[] marked; // true if s->v path in residual network
  private FlowEdge[] edgeTo; // last edge on s->v path
  private double value; // value of flow

  public FordFulkerson(FlowNetwork G, int s, int t) {
    value = 0.0;

    while (hasAugmentingPath(G, s, t)) {
      double bottle = Double.POSITIVE_INFINITY;

      for (int v = t; v != s; v = edgeTo[v].other(v))
        bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));

      for (int v = t; v != s; v = edgeTo[v].other(v))
        edgeTo[v].addResidualFlowTo(v, bottle);
      value += bottle;
    }
  }
  
  private boolean hasAugmentingPath(FlowNetwork G, int s, int t){
    edgeTo = new FlowEdge[G.V()];
    marked = new boolean[G.V()];
    Queue<Integer> queue = new Queue<Integer>();

    queue.enqueue(s);
    marked[s] = true;

    while (!queue.isEmpty()) {
      int v = queue.dequeue();

      for (FlowEdge e : G.adj(v)) {
        int w = e.other(v);

        // found path from s to win the residual network?
        if (e.residualCapacityTo(w) > 0 && !marked[w]) {
          // save last edge on path to w;
          edgeTo[w] = e;
          // Mark w
          marked[w] = true;
          // Add w to queue
          queue.enqueue(w);
        }
      }
    }

    // is t reachable from s in residual network
    return marked[t];    
  }

  public double value(){
    return value;
  }

  // is v reachable from s in residual network
  public boolean inCut(int v) {
    return marked[v];
  }
}