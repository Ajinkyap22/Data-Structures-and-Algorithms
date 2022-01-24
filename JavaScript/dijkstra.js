class Node {
  constructor(value, priority) {
    this.value = value;
    this.priority = priority;
  }
}

class PriorityQueue {
  constructor() {
    this.values = [];
  }

  enqueue(value, priority) {
    let node = new Node(value, priority);

    this.values.push(node);

    this.bubbleUp();

    return this.values;
  }

  bubbleUp() {
    // start from the bottom
    let index = this.values.length - 1;
    let parent;

    while (true) {
      // parent = i - 1 / 2
      parent = Math.floor((index - 1) / 2);

      // stop if parent index is invalid or parent is greater than child
      if (
        parent <= -1 ||
        this.values[parent].priority <= this.values[index].priority
      )
        return;

      // else swap parent and child
      this.swap(index, parent);

      index = parent;
    }
  }

  dequeue() {
    // swap root with the last element
    this.swap(0, this.values.length - 1);
    // remove the root element
    let min = this.values.pop();

    if (this.values.length) {
      this.sinkDown();
    }

    return min;
  }

  sinkDown() {
    let parentIndex = 0;
    let parent, leftChild, rightChild, left, right, swap;

    while (true) {
      left = 2 * parentIndex + 1;
      right = 2 * parentIndex + 2;
      leftChild = this.values[left]?.priority;
      rightChild = this.values[right]?.priority;
      parent = this.values[parentIndex].priority;
      swap = null;

      // swap with the smallest child
      if (leftChild && parent > leftChild) {
        swap = left;
      }

      if (rightChild && parent > rightChild) {
        if (!swap || rightChild < leftChild) {
          swap = right;
        }
      }

      // stop if there was no swap
      if (!swap) break;

      this.swap(parentIndex, swap);
      parentIndex = swap;
    }
  }

  min() {
    return this.values[0];
  }

  isEmpty() {
    return this.values.length <= 0;
  }

  swap(index1, index2) {
    [this.values[index1], this.values[index2]] = [
      this.values[index2],
      this.values[index1],
    ];
  }
}

class WeightedGraph {
  constructor() {
    this.adjacencyList = {};
  }

  addVertex(name) {
    // only add if the vertex does not exist
    if (!this.adjacencyList[name]) this.adjacencyList[name] = [];
  }

  addEdge(v1, v2, weight) {
    // add vertex before creating edge
    this.addVertex(v1);
    this.addVertex(v2);

    // check if the edge doesn't already exist
    if (!this.adjacencyList[v1].includes(v2))
      this.adjacencyList[v1].push({ node: v2, weight });
    if (!this.adjacencyList[v2].includes(v1))
      this.adjacencyList[v2].push({ node: v1, weight });
  }

  removeVertex(v) {
    // check if vertex exists
    if (!this.adjacencyList[v]) return;
    // remove all edges of the vertex first
    this.adjacencyList[v].forEach((vertex) => {
      this.removeEdge(v, vertex.node);
    });

    delete this.adjacencyList[v];
  }

  removeEdge(v1, v2) {
    // stop if the vertex doesn't exist
    if (!this.adjacencyList[v1] || !this.adjacencyList[v2]) return;

    // remove both vertices from each other's list
    this.adjacencyList[v1] = this.adjacencyList[v1].filter((v) => v.node != v2);
    this.adjacencyList[v2] = this.adjacencyList[v2].filter((v) => v.node != v1);
  }

  Dijkstra(start, end) {
    // to store the previous node of each vertex
    const previous = {};
    const distances = {};

    // priority queue to extract the minimum vertex each time
    const pq = new PriorityQueue();

    // to store the final shortest path
    let path = [];
    let smallest;

    // set distance & priority of each vertex except start to Number.POSITIVE_INFINITY
    for (const vertex in this.adjacencyList) {
      if (vertex === start) {
        distances[vertex] = 0;
        pq.enqueue(vertex, 0);
      } else {
        distances[vertex] = Number.POSITIVE_INFINITY;
        pq.enqueue(vertex, Number.POSITIVE_INFINITY);
      }

      // initialize the previous of each vertex to null
      previous[vertex] = null;
    }

    // loop as long as the pq is not empty
    while (pq.values.length) {
      // pick the smallest, by extracting min from pq
      smallest = pq.dequeue().value;

      // stop if smallest is the end vertext
      if (smallest === end) {
        while (previous[smallest]) {
          path.push(smallest);
          smallest = previous[smallest];
        }

        break;
      }

      if (smallest && distances[smallest] !== Number.POSITIVE_INFINITY) {
        // look at all it's neighbours, pick one
        this.adjacencyList[smallest].forEach((neighbour) => {
          // Check if the current distance for that neigbour is greater than the distance from the first node
          let dist = neighbour.weight + distances[smallest];

          // if yes update it & change the neighbour's previous value
          if (dist < distances[neighbour.node]) {
            distances[neighbour.node] = dist;
            previous[neighbour.node] = smallest;

            // enqueue that vertext with a new distance
            pq.enqueue(neighbour.node, dist);
          }
        });
      }
    }
    path = path.concat(smallest).reverse();
    let distance = distances[end];

    return { path, distance };
  }
}
