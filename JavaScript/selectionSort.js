// smallest value is at the begining at the end of each pass, takes n - 1 passes, O(n) for nearly sorted data

function selectionSort(arr) {
  for (let i = 0; i < arr.length; i++) {
    // store smallest element's index
    let min = i;

    for (let j = i + 1; j < arr.length; j++) {
      // change min if a smaller element is found
      if (arr[min] > arr[j]) min = j;
    }

    // swap only if min has changed
    if (min !== i) swap(arr, i, min);
  }

  return arr;
}

function swap(arr, num1, num2) {
  [arr[num1], arr[num2]] = [arr[num2], arr[num1]];
}
