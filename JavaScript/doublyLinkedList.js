class Node {
  constructor(val) {
    this.val = val;
    this.next = null;
    this.prev = null;
  }
}

class DoublyLinkedList {
  constructor() {
    this.head = null;
    this.tail = null;
    this.length = 0;
  }

  // add node to the end - O(1)
  push(val) {
    const node = new Node(val);

    if (!this.head) {
      this.head = node;
      this.tail = node;
    } else {
      this.tail.next = node;
      node.prev = this.tail;
      this.tail = node;
    }

    this.length++;
    return this;
  }

  // remove node from the end - O(1)
  pop() {
    if (!this.head) return undefined;
    let oldTail = this.tail;

    if (this.length === 1) {
      this.head = null;
      this.tail = null;
    } else {
      let newTail = oldTail.prev;
      this.tail = newTail;
      newTail.next = null;
      oldTail.prev = null;
    }

    this.length--;
    return oldTail;
  }

  // remove node from the start - O(1)
  shift() {
    if (this.length === 1) return this.pop();

    if (!this.head) return undefined;

    const oldHead = this.head;

    this.head = oldHead.next;
    this.head.prev = null;
    oldHead.next = null;

    this.length--;

    return oldHead;
  }

  // add node to the start - O(1)
  unshift(val) {
    const node = new Node(val);

    if (!this.head) return this.push(val);

    this.head.prev = node;
    node.next = this.head;
    this.head = node;

    this.length++;
    return this;
  }

  // get a node with the given index - O(logn)
  get(index) {
    if (index < 0 || index >= this.length) return null;

    let current, count;

    // check if index is closer to start or end

    if (index < this.length / 2) {
      current = this.head;
      count = 0;

      while (count !== index) {
        current = current.next;
        count++;
      }
    } else {
      current = this.tail;
      count = this.length - 1;

      while (count !== index) {
        current = current.prev;
        count--;
      }
    }

    return current;
  }

  // set the value of the node with the given index - O(logn)
  set(index, val) {
    let node = this.get(index);

    if (!node) return false;

    node.val = val;

    return true;
  }

  // insert node at the given index
  insert(index, val) {
    if (index < 0 || index > this.length) return false;

    if (index === 0) return !!this.unshift(val);

    if (index === this.length) return !!this.push(val);

    let node = new Node(val);
    let prev = this.get(index - 1);
    let next = prev.next;

    node.prev = prev;
    node.next = next;
    next.prev = node;
    prev.next = node;
    this.length++;

    return true;
  }

  // remove node at the given index
  remove(index) {
    if (index < 0 || index >= this.length) return false;

    if (index === 0) return !!this.shift(val);

    if (index === this.length - 1) return !!this.pop(val);

    let prev = this.get(index - 1);
    let node = prev.next;
    let next = node.next;

    prev.next = next;
    next.prev = prev;
    node.next = null;
    node.prev = null;
    this.length--;

    return true;
  }

  // reverse linked list
  reverse() {
    // set current
    let current = this.head;

    // swap head and tail
    this.head = this.tail;
    this.tail = current;

    // declare prev and next
    let prev = null;
    let next = null;

    // loop
    while (current) {
      // current's next becomes the next one to loop
      next = current.next;
      // current's next will be prev (initially null)
      current.next = prev;
      current.prev = next;
      // current will become the prev in next iteration
      prev = current;
      current = next;
    }

    return this;
  }
}

const list = new DoublyLinkedList();
