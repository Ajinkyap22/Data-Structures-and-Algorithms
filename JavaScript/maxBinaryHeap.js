class MaxBinaryHeap {
  constructor() {
    this.values = [];
  }

  insert(value) {
    this.values.push(value);

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
      if (parent <= -1 || this.values[parent] >= this.values[index]) return;

      // else swap parent and child
      this.swap(index, parent);

      index = parent;
    }
  }

  extractMax() {
    // swap root with the last element
    this.swap(0, this.values.length - 1);
    // remove the root element
    let max = this.values.pop();

    if (this.values.length) {
      this.sinkDown();
    }

    return max;
  }

  sinkDown() {
    let parentIndex = 0;
    let parent, leftChild, rightChild, left, right, swap;

    while (true) {
      left = 2 * parentIndex + 1;
      right = 2 * parentIndex + 2;
      leftChild = this.values[left];
      rightChild = this.values[right];
      parent = this.values[parentIndex];
      swap = null;

      // compare it to its left and right children
      if (parent < leftChild || parent < rightChild) {
        // swap it with the largest child
        if (leftChild >= rightChild && left < this.values.length) swap = left;
        if (leftChild < rightChild && right < this.values.length) swap = right;
      } else {
        // stop if there no swap
        break;
      }

      this.swap(parentIndex, swap);
      parentIndex = swap;
    }
  }

  max() {
    return this.values[0];
  }

  swap(index1, index2) {
    [this.values[index1], this.values[index2]] = [
      this.values[index2],
      this.values[index1],
    ];
  }
}
