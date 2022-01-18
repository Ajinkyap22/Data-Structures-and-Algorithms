// Gradually sorts by creating a larger part of array which is always sorted

function insertionSort(arr) {
  let j;

  // start at 1 to save a step
  for (let i = 1; i < arr.length; i++) {
    let currentVal = arr[i];

    //
    for (j = i - 1; j >= 0 && arr[j] > currentVal; j--) {
      arr[j + 1] = arr[j];
    }
    arr[j + 1] = currentVal;
  }

  return arr;
}
