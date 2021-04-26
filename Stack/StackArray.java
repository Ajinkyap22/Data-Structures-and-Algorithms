import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<Item>{
  private Item[] s;
  private int n;

  public Stack(){
    s = (Item[]) new Object[1];
    n = 0;
  }

  public boolean isEmpty(){
    return n == 0;

  }

  public int size() {
    return n;
  }

  public void resize(int capacity){
    Item [] copy = (Item[]) new Object[capacity];

    for(int i = 0; i < n; i++){
      copy[i] = s[i];
    }
    s = copy;
  }

  public void push(Item item){
    if(n == s.length) resize(2 * s.length);
    s[n++] = item;
  }

  public Item pop(){
    if (isEmpty()) throw new NoSuchElementException("Stack underflow");

    Item item = s[n-1];
    s[n-1] = null;
    n--;

    if(n > 0 && n == s.length / 4){
      resize(s.length / 2);
    }

    return item;
  }

  public Iterator<Item> iterator() {
      return new StackIterator();
   }

  private class StackIterator implements Iterator<Item> {
    private int i = n;

     public boolean hasNext() {
        return i >= 0;
      }

      public Item next() {
          if (!hasNext()) throw new NoSuchElementException();

          return s[--i];
      }
  }

   public static void main(String[] args){
    
  }
}