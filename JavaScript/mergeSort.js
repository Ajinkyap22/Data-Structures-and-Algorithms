function mergeSort(arr) {
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

  while (i < arr1.length || j < arr2.length) {
    if (arr1[i] <= arr2[j] || j >= arr2.length) {
      res.push(arr1[i]);
      i++;
    } else if (arr1[i] > arr2[j] || i >= arr1.length) {
      res.push(arr2[j]);
      j++;
    }
  }
  return res;
}
