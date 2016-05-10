package Sort;
 
public class Sorter {
  public static int[] Sort(int[] unSortedList) {
    if (unSortedList.length > 1)
      SortList(unSortedList);
    return unSortedList;
 }
 
 private static void SortList(int[] unSortedList) {
   int unsortedLength = unSortedList.length;
   while (unsortedLength > 0) {
     PlacedLargestElementAtEndOfList(unSortedList);
     unsortedLength--;
   }
 }
 
 private static void PlacedLargestElementAtEndOfList(int[] unSortedList) {
   for (int j = 0; j < unSortedList.length - 1; j++)
     SortElements(unSortedList, j);
 }
 
 private static void SortElements(int[] unSortedList, int j) {
   if (unSortedList[j] > unSortedList[j + 1])
     SwapElements(unSortedList, j);
 }
 
 private static void SwapElements(int[] unSortedList, int j) {
   int backupelement = unSortedList[j];
   unSortedList[j] = unSortedList[j + 1];
   unSortedList[j + 1] = backupelement;
 }
}
