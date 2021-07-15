public class KruskalMST {
  private Queue<Edge> mst = new Queue<Edge>();

  public KruskalMST(EdgeWeightedGraph G) {
    // build priority queue
    MinPQ<Edge> pq = new MinPQ<Edge>();

    for (Edge e : G.edges())
      pq.insert(e);

    // union find for efficiency
    UF uf = new UF(G.V());

    while (!pq.isEmpty() && mst.size() < G.V() - 1) {
      Edge e = pq.delMin();
      int v = e.either(), w = e.other(v);

      // edge v–w does not create cycle
      if (!uf.connected(v, w)) {
        // merge sets
        uf.union(v, w);
        // add edge to MST
        mst.enqueue(e);
      }
    }
  }

  public Iterable<Edge> edges() {
    return mst;
  }
}