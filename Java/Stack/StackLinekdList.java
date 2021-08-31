import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Item>{
  private int n;
  private node first = null;

  private class Node{
    Item item;
    Node next;
  }

  public Stack() {
    first = null;
    n = 0;
  }

  public boolean isEmpty(){
    return first == null;
  }

  public int size() {
    return n;
  }

  public void push(Item item){
    Node oldFisrt = first;
    first = new Node();
    first.item = item;
    first.next = oldFisrt;
    n++;
  }

  public Item pop(){
    if (isEmpty()) throw new NoSuchElementException("Stack underflow");
    Item item = first.item;
    first = first.next;
    n--;
    return item;
  }

  public Iterator<Item> iterator() {
      return new StackIterator();
   }

  private class StackIterator implements Iterator<Item> {
    private node current = first;

     public boolean hasNext() {
        return current != null;
      }

      public Item next() {
          if (!hasNext()) throw new NoSuchElementException();

          Item item = current.item;
          current = current.next;
          return item;
      }
  }

  public static void main(String[] args){

  }
}