package net.loomchild.maligna.comparator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.loomchild.maligna.coretypes.Alignment;

/**
 * Represents alignment list comparator.
 * 
 * Responsible for comparing two alignments and returning the differences.
 * TODO: do not remember how it works, analyze and describe it at some point.
 * 
 * @author loomchild
 */
public class Comparator {

	/**
	 * Compares left and right alignment list.
	 * 
	 * @param leftAlignmentList
	 * @param rightAlignmentList
	 * @return list differences
	 */
	public static Diff compare(List<Alignment> leftAlignmentList,
			List<Alignment> rightAlignmentList) {

		Diff diff;
		
		if (leftAlignmentList.size() > 0 && rightAlignmentList.size() > 0) {

			int[][] occurrenceArray = 
				createOccurenceArray(leftAlignmentList, rightAlignmentList);
			
			int[] indexArray = new int[leftAlignmentList.size()];
			for (int i = 0; i < indexArray.length; ++i) {
				indexArray[i] = occurrenceArray[i].length;
			}
			
			int bestLength = -1;
			int[] bestIndexArray = null;
			
			do {

				int length = length(indexArray, occurrenceArray);
				if (length >= bestLength) {
					bestLength = length;
					bestIndexArray = 
						Arrays.copyOf(indexArray, indexArray.length);
				}

			} while (next(indexArray, occurrenceArray));
			
			diff = createDiff(leftAlignmentList, rightAlignmentList, 
					bestIndexArray, occurrenceArray);

		} else {
		
			List<Alignment> commonList = Collections.emptyList(); 
			diff = new Diff(commonList, 
					Collections.singletonList(leftAlignmentList), 
					Collections.singletonList(rightAlignmentList));

		}
		
		return diff;
		
	}
		
	private static int[][] createOccurenceArray(List<Alignment> leftAlignmentList,
			List<Alignment> rightAlignmentList) {

		Map<Alignment, List<Integer>> rightOccurrenceMap = 
			new HashMap<Alignment, List<Integer>>();
		
		int position = 0;
		for (Alignment alignment : rightAlignmentList) {
			List<Integer> occurrenceList = 
				rightOccurrenceMap.get(alignment);
			if (occurrenceList == null) {
				occurrenceList = new ArrayList<Integer>();
			}
			occurrenceList.add(position);
			rightOccurrenceMap.put(alignment, occurrenceList);
			++position;
		}

		int[][] occurrenceArray = new int[leftAlignmentList.size()][];
	
		int i = 0;
		for (Alignment alignment : leftAlignmentList) {
		
			List<Integer> list = rightOccurrenceMap.get(alignment);
			int[] array; 
			
			if (list != null) {
				array = new int[list.size()];
				int k = 0;
				for (int occurence : list) {
					array[k] = occurence;
					++k;
				}
			} else {
				array = new int[0];
			}
			
			occurrenceArray[i] = array;
			
			++i;
			
		}
		
		return occurrenceArray;
		
	}
	
	private static boolean next(int[] indexArray, int[][] occurrenceArray) {
		
		for (int i = indexArray.length - 1; i >= 0; --i) {

			if (indexArray[i] > 0) {
				
				int k = i - 1;
				while (k >= 0 && indexArray[k] == occurrenceArray[k].length) {
					--k;
				}
				
				if (k >= 0 && occurrenceArray[k][indexArray[k]] >= 
						occurrenceArray[i][indexArray[i] - 1]) {
					continue;
				}
				
				--indexArray[i];
				
				int position = occurrenceArray[i][indexArray[i]];
				
				for (int m = i + 1; m < indexArray.length; ++m) {
					
					int n = 0; 
					while (n < occurrenceArray[m].length
							&& position > occurrenceArray[m][n]) {
						++n;
					}
					
					if (n < occurrenceArray[m].length) {
						position = occurrenceArray[m][n];
					}

					indexArray[m] = n;

				}
				
				return true;
				
			}
			
		}

		return false;
		
	}

	
	private static int length(int[] indexArray, int[][] occurrenceArray) {

		int length = 0;
		
		for (int i = 0; i < indexArray.length; ++i) {
			if (indexArray[i] < occurrenceArray[i].length) {
				++length;
			}
		}
		
		return length;
	
	}
	
	private static Diff createDiff(List<Alignment> leftAlignmentList,
			List<Alignment> rightAlignmentList, 
			int[] indexArray, int[][] occurrenceArray) {

		List<Alignment> commonList = new ArrayList<Alignment>();
		List<List<Alignment>> leftList = new ArrayList<List<Alignment>>();
		List<List<Alignment>> rightList = new ArrayList<List<Alignment>>();
		
		int previousLeftPosition = 0;
		int previousRightPosition = 0;
		
		for (int i = 0; i < indexArray.length; ++i) {

			if (indexArray[i] < occurrenceArray[i].length) {

				Alignment commonAlignment = leftAlignmentList.get(i);
				commonList.add(commonAlignment);	
		
				int leftPosition = i;
				int rightPosition = occurrenceArray[i][indexArray[i]];

				if (leftPosition > previousLeftPosition ||
						rightPosition > previousRightPosition) {

					List<Alignment> leftGroup = new ArrayList<Alignment>(
							leftAlignmentList.subList(previousLeftPosition, leftPosition));
					leftList.add(leftGroup);
					
					List<Alignment> rightGroup = new ArrayList<Alignment>(
							rightAlignmentList.subList(previousRightPosition, rightPosition));
					rightList.add(rightGroup);

				}
				
				previousLeftPosition = leftPosition + 1;
				previousRightPosition = rightPosition + 1;

			}
			
		}

		return new Diff(commonList, leftList, rightList);
		
	}
	
}
 /*
	public static Diff compare(List<Alignment> leftAlignmentList, 
			List<Alignment> rightAlignmentList) {

		Diff diff;
		
		if (leftAlignmentList.size() > 0 && rightAlignmentList.size() > 0) {

			int[][] occurrenceArray = 
				createOccurenceArray(leftAlignmentList, rightAlignmentList);
			
			int[] indexArray = new int[leftAlignmentList.size()];
			indexArray[indexArray.length - 1] = -1;
			
			int bestLength = 0;
			int[] bestIndexArray = new int[0];
			
			while (hasNext(indexArray, occurrenceArray)) {
				
				next(indexArray, occurrenceArray);
				
				if (isValid(indexArray, occurrenceArray)) {
					int length = length(indexArray, occurrenceArray);
					if (length > bestLength) {
						bestLength = length;
						bestIndexArray = 
							Arrays.copyOf(indexArray, indexArray.length);
					}
				}
				
			}
			
			diff = createDiff(leftAlignmentList, rightAlignmentList, 
					bestIndexArray, occurrenceArray);

		} else {
		
			List<Alignment> commonList = Collections.emptyList(); 
			diff = new Diff(commonList, 
					Collections.singletonList(leftAlignmentList), 
					Collections.singletonList(rightAlignmentList));

		}
		
		return diff;
		
	}
	
	private static void next(int[] indexArray, int[][] occurrenceArray) {
		
		for (int i = indexArray.length - 1; i >= 0; --i) {
			if (indexArray[i] < occurrenceArray[i].length) {
				++indexArray[i];
				for (int k = i + 1; k < indexArray.length; ++k) {
					indexArray[k] = 0;
				}
				return;
			}
		}

		throw new NoSuchElementException("There is no successor.");
		
	}

	private static boolean hasNext(int[] indexArray, int[][] occurrenceArray) {
		
		if (indexArray.length == 0) {
			return false;
		}
		
		for (int i = 0; i < indexArray.length; ++i) {
			if (indexArray[i] < occurrenceArray[i].length) {
				return true;
			}
		}
		
		return false;
		
	}

	private static boolean isValid(int[] indexArray, int[][] occurrenceArray) {

		int previousPosition = -1;
		
		for (int i = 0; i < indexArray.length; ++i) {
			if (indexArray[i] < occurrenceArray[i].length) {
				int currentPosition = occurrenceArray[i][indexArray[i]];
				if (currentPosition <= previousPosition) {
					return false;
				}
				previousPosition = currentPosition;
			}
		}
		
		return true;
	}

*/
