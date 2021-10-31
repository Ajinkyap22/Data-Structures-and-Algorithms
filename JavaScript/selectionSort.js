function selectionSort(arr) {
  for (let i = 0; i < arr.length; i++) {
    let min = i;

    for (let j = i + 1; j < arr.length; j++) {
      if (arr[min] > arr[j]) min = j;
    }

    if (min !== i) swap(arr, i, min);
  }

  return arr;
}

function swap(arr, num1, num2) {
  [arr[num1], arr[num2]] = [arr[num2], arr[num1]];
}
