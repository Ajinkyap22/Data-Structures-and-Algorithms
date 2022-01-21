class Node {
  constructor(value) {
    this.value = value;
    this.left = null;
    this.right = null;
  }
}

class BST {
  constructor() {
    this.root = null;
  }

  // insert
  insert(value) {
    let node = new Node(value);
    // male root node
    if (!this.root) {
      this.root = node;
      return this;
    }

    let current = this.root;

    while (true) {
      if (value === current.value) return undefined;

      if (value < current.value) {
        if (!current.left) {
          current.left = node;
          return this;
        }
        current = current.left;
      } else {
        if (!current.right) {
          current.right = node;
          return this;
        }
        current = current.right;
      }
    }
  }

  // remove
  remove(value) {
    let _remove = (val, node = this.root) => {
      if (node) {
        // if less than root, call remove with node.left
        if (val < node.value) {
          node.left = _remove(val, node.left);
          // if greater than root, call remove with node.right
        } else if (val > node.value) {
          node.right = _remove(val, node.right);
          // if has left and right, find right minimum child's value assign it to node, call remove on node's right & set it to node's right
        } else if (node.left && node.right) {
          node.value = this.findRightMinValue(node);
          node.right = _remove(node.value, node.right);
        } else {
          node = node.left || node.right;
        }
      }

      return node;
    };

    this.root = _remove(value);
    return this.root;
  }

  findRightMinValue(node) {
    if (!node || !node.right) return null;
    return node.right.value;
  }

  // find
  find(value) {
    if (!this.root) return false;

    let current = this.root;

    while (true) {
      if (value === current.value) return current;

      if (value < current.value) {
        if (!current.left) return null;

        current = current.left;
      } else {
        if (!current.right) return null;

        current = current.right;
      }
    }
  }

  // contains
  contains(value) {
    let contains = this.find(value);

    if (!contains) {
      return false;
    } else {
      return true;
    }
  }

  BFS() {
    // queue to deque nodes in bfs order
    const queue = [];
    // array to store values in bfs order
    const visited = [];

    if (!this.root) return visited;

    queue.push(this.root);

    while (queue.length) {
      // dequeue a node from the queue
      const node = queue.shift();

      // push the value of the node in the result array
      visited.push(node.value);

      // enqueue left child of node to the queue
      if (node.left) {
        queue.push(node.left);
      }

      // enqueue right child of node to the queue
      if (node.right) {
        queue.push(node.right);
      }
    }

    return visited;
  }

  preOrderDFS() {
    // array to store dfs values
    const visited = [];

    let predOrder = function (node) {
      // push the root node to the result first
      visited.push(node.value);

      // if node has left or right children, call the function recursively on the children
      if (node.left) {
        predOrder(node.left);
      }

      if (node.right) {
        predOrder(node.right);
      }
    };

    predOrder(this.root);

    return visited;
  }

  inOrderDFS() {
    // array to store dfs values
    const visited = [];

    let indOrder = function (node) {
      // if node has left child, call the function recursively on it
      if (node.left) {
        indOrder(node.left);
      }

      // push the root node to the result after the left child
      visited.push(node.value);

      // if node has right child, call the function recursively on it
      if (node.right) {
        indOrder(node.right);
      }
    };

    indOrder(this.root);

    return visited;
  }

  postOrderDFS() {
    // array to store dfs values
    const visited = [];

    let postdOrder = function (node) {
      // if node has left child, call the function recursively on it
      if (node.left) {
        postdOrder(node.left);
      }

      // if node has right child, call the function recursively on it
      if (node.right) {
        postdOrder(node.right);
      }

      // push the root node to the result after the left & right children
      visited.push(node.value);
    };

    postdOrder(this.root);

    return visited;
  }
}
