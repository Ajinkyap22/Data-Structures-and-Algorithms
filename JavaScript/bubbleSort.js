// Largest element bubbles up to the top after each pass, takes n - 1 passes.

function bubbleSort(arr) {
  let noSwaps;
  // Start from the end so that inner loop does not have to run for all element every time
  for (let i = arr.length - 1; i >= 0; i--) {
    // OPTIMIZATION - break out of loop if there we no swaps made in the entie pass i.e. array is already sorted
    noSwaps = true;
    for (let j = 0; j < i; j++) {
      if (arr[j] > arr[j + 1]) {
        swap(arr, j, j + 1);
        noSwaps = false;
      }
    }

    if (noSwaps) break;
  }

  return arr;
}

function swap(arr, num1, num2) {
  [arr[num1], arr[num2]] = [arr[num2], arr[num1]];
}
