class Node {
  constructor(val) {
    this.val = val;
    this.next = null;
  }
}

class SinglyLinkedList {
  constructor() {
    this.head = null;
    this.tail = null;
    this.length = 0;
  }

  // push, pop, get, set, shift, unshift, insert, remove, reverse
  // add node to the end - O(1)
  push(val) {
    const node = new Node(val);

    if (!this.head) {
      this.head = node;
      this.tail = node;
    } else {
      this.tail.next = node;
      this.tail = node;
    }

    this.length++;
    return this;
  }

  // remove node from the end - O(n)
  pop() {
    if (!this.head) return undefined;

    let current = this.head;
    let newTail = this.tail;

    while (current.next) {
      newTail = current;
      current = current.next;
    }

    this.tail = newTail;
    this.tail.next = null;
    this.length--;

    if (this.length === 0) {
      this.head = null;
      this.tail = null;
    }
    return current;
  }

  // remove node from the start - O(1)
  shift() {
    if (!this.head) return undefined;

    const oldHead = this.head;
    this.head = oldHead.next;
    this.length--;

    if (this.length === 0) this.tail = null;

    return oldHead;
  }

  // add node to the start - O(1)
  unshift(val) {
    const node = new Node(val);

    if (!this.head) {
      this.head = node;
      this.tail = node;
    } else {
      node.next = this.head;
      this.head = node;
    }

    this.length++;
    return this;
  }

  // get a node with the given index - O(n)
  get(index) {
    if (index < 0 || index >= this.length) return null;

    let current = this.head;
    let count = 0;

    while (count !== index) {
      current = current.next;
      count++;
    }

    return current;
  }

  // set the value of the node with the given index - O(n)
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

    node.next = prev.next;
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

    prev.next = node.next;
    node.next = null;
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
      // current will become the prev in next iteration
      prev = current;
      current = next;
    }

    return this;
  }
}

const list = new SinglyLinkedList();
