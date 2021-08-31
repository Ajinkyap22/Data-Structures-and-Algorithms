import edu.princeton.cs.algs4.StdRandom;

public class KnuthShuffle<Item>{
  public void shuffle(object [] a){
    int n = a.length;

    for(int i = 0; i < n; i++){
      int j = StdRandom.uniform(i + 1);
      Item temp = a[i];
      a[i] = a[j];
      a[j] = temp;
    }
  }

  public static void main(String args[]){
    
  }
}