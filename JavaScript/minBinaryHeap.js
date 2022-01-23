class MinBinaryHeap {
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

      // stop if parent index is invalid or parent is lesser than child
      if (parent <= -1 || this.values[parent] <= this.values[index]) return;

      // else swap parent and child
      this.swap(index, parent);

      index = parent;
    }
  }

  extractMin() {
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
      leftChild = this.values[left];
      rightChild = this.values[right];
      parent = this.values[parentIndex];
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

  swap(index1, index2) {
    [this.values[index1], this.values[index2]] = [
      this.values[index2],
      this.values[index1],
    ];
  }
}
