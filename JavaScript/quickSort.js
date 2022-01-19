// moves a pivot element to it's sorted position such that all elements on left are smaller & all elements on right are larger
// does the same for the left side & right side
function quickSort(arr, left = 0, right = arr.length - 1) {
  if (left < right) {
    // call pivot on whole arr
    let pivotIndex = pivot(arr, left, right);
    // call quicksort on 0 to index & index + 1 to length - 1
    quickSort(arr, left, pivotIndex - 1);
    quickSort(arr, pivotIndex + 1, right);
  }
  return arr;
}

function pivot(arr, start = 0, end = arr.length) {
  let pivot = arr[start];
  let pivotIndex = start;

  for (let i = start + 1; i <= end; i++) {
    // if pivot element >  current element, inc the pivot index & swap the current element with element at pivot index
    if (pivot > arr[i]) {
      pivotIndex++;
      swap(arr, i, pivotIndex);
    }
  }

  // swap the pivot elment with the element at pivotIndex so that all elements at left are smaller & right are larger
  swap(arr, start, pivotIndex);
  return pivotIndex;
}

function swap(arr, index1, index2) {
  [arr[index1], arr[index2]] = [arr[index2], arr[index1]];
}
