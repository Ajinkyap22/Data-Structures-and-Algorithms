public class BinarySearch{
  public int binarySearch(int [] a, int key){
    int low = 0, high = a.length - 1;

    while(low <= high){
      int mid = low + (high - low) / 2;

      if(key < a[mid]) high = mid - 1;
      else if(key > a[mid]) low = mid + 1;
      else return mid;
    }
    return -1;
  }

  public static void main(String args[]){
    BinarySearch bs = new BinarySearch();
    int arr[] = {1, 5, 7, 10 ,11};
    int key = 1;

    int result = bs.binarySearch(arr, key);

    if (result == -1)
      System.out.println("Element not present");
    else
      System.out.println("Element found at index " + result);
  }
}