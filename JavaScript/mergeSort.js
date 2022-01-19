// divides the array into single element array, then sorts them by merging each one together
function mergeSort(arr) {
  // already sorted if length <= 1
  if (arr.length <= 1) return arr;

  let mid = Math.floor(arr.length / 2);

  let arr1 = mergeSort(arr.slice(0, mid));
  let arr2 = mergeSort(arr.slice(mid));

  return merge(arr1, arr2);
}

function merge(arr1, arr2) {
  const res = [];
  let i = 0;
  let j = 0;

  // while there are elements left to push
  while (i < arr1.length || j < arr2.length) {
    // if element in the 1st arr is smaller or there are no more elements in the 2nd arr, push it
    if (arr1[i] <= arr2[j] || j >= arr2.length) {
      res.push(arr1[i]);
      i++;
      // if element in the 2nd arr is smaller or there are no more elements in the 1st arr, push it
    } else if (arr1[i] > arr2[j] || i >= arr1.length) {
      res.push(arr2[j]);
      j++;
    }
  }
  return res;
}
