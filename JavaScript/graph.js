class Graph {
  constructor() {
    this.adjacencyList = {};
  }

  addVertex(name) {
    // only add if the vertex does not exist
    if (!this.adjacencyList[name]) this.adjacencyList[name] = [];
  }

  addEdge(v1, v2) {
    // add vertex before creating edge
    this.addVertex(v1);
    this.addVertex(v2);

    // check if the edge doesn't already exist
    if (!this.adjacencyList[v1].includes(v2)) this.adjacencyList[v1].push(v2);
    if (!this.adjacencyList[v2].includes(v1)) this.adjacencyList[v2].push(v1);
  }

  removeVertex(v) {
    // check if vertex exists
    if (!this.adjacencyList[v]) return;
    // remove all edges of the vertex first
    this.adjacencyList[v].forEach((vertex) => {
      this.removeEdge(v, vertex);
    });

    delete this.adjacencyList[v];
  }

  removeEdge(v1, v2) {
    // stop if the vertex doesn't exist
    if (!this.adjacencyList[v1] || !this.adjacencyList[v2]) return;

    // remove both vertices from each other's list
    this.adjacencyList[v1] = this.adjacencyList[v1].filter((v) => v != v2);
    this.adjacencyList[v2] = this.adjacencyList[v2].filter((v) => v != v1);
  }

  DFS(vertex) {
    if (!this.adjacencyList[vertex]) return;

    const results = [];
    const visited = {};

    let traverse = (v) => {
      visited[v] = true;
      results.push(v);

      this.adjacencyList[v].forEach((neighbour) => {
        if (!visited[neighbour]) traverse(neighbour);
      });
    };

    traverse(vertex);

    return results;
  }

  DFSIterative(vertex) {
    if (!this.adjacencyList[vertex]) return;

    const stack = [vertex];
    const results = [];
    const visited = {};

    let v;

    visited[vertex] = true;

    while (stack.length) {
      v = stack.pop();

      results.push(v);

      this.adjacencyList[v].forEach((neighbour) => {
        if (!visited[neighbour]) {
          visited[neighbour] = true;
          stack.push(neighbour);
        }
      });
    }

    return results;
  }

  BFS(vertex) {
    if (!this.adjacencyList[vertex]) return;

    const queue = [vertex];
    const results = [];
    const visited = {};

    let v;

    visited[vertex] = true;

    while (queue.length) {
      v = queue.shift();

      results.push(v);

      this.adjacencyList[v].forEach((neighbour) => {
        if (!visited[neighbour]) {
          visited[neighbour] = true;
          queue.push(neighbour);
        }
      });
    }

    return results;
  }
}
