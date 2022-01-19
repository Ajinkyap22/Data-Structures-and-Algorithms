class Node {
  constructor(val) {
    this.val = val;
    this.next = null;
  }
}

class Queue {
  constructor() {
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  enqueue(val) {
    let node = new Node(val);

    if (!this.first) {
      this.first = node;
      this.last = node;
    } else {
      this.last.next = node;
      this.last = node;
    }

    return ++this.size;
  }

  dequeue() {
    if (!this.first) return null;

    let node = this.first;

    if (this.size === 1) {
      this.last = null;
    }

    this.first = node.next;
    node.next = null;

    this.size--;

    return node.val;
  }

  isEmpty() {
    return this.size === 0;
  }
}
