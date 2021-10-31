function linearSearch(arr, val) {
  for (let i = 0; i < arr.length; i++) {
    if (arr[i] === val) return `Element found at index ${i}`;
  }

  return "Element not found";
}
