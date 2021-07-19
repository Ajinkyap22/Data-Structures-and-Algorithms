public class EdgeWeightedDigraph
{
  private final int V;
  private final Bag<DirectedEdge>[] adj;

  // create an empty graph with V vertices
  public EdgeWeightedDigraph(int V) {
    this.V = V;

    adj = (Bag<DirectedEdge>[]) new Bag[V];
    
    for (int v = 0; v < V; v++)
      adj[v] = new Bag<DirectedEdge>();
  }

  // add weighted edge e to this graph
  public void addEdge(DirectedEdge e) {
    int v = e.from();
    adj[v].add(e);
  }

  // edges incident to v
  public Iterable<DirectedEdge> adj(int v) { 
    return adj[v]; 
  }
}