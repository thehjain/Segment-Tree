

//Here we are going to built Segment Tree of Range Minimum Query

import java.util.*;
import java.math.*;

class RangeMinimumQuery {
	public static void main(String[] args) {

		int arr[] = {1, 3, 2, 7, 9, 11};
		int n = arr.length;

		SegmentTree tree = new SegmentTree(arr, n);

		System.out.println("Minimum Value= " + tree.getMin(n, 1, 3));

	}
}

class SegmentTree {

	int[] st;

	SegmentTree(int[] arr, int n) {

		st = new int[4 * n + 1];

		construct(arr, 0, n - 1, 0);
	}

	int getMinUtil(int ss, int se, int qs, int qe, int si) {

		//If segment of node is part of query
		if (qs <= ss && qe >= se)
			return st[si];

		//If segment is outside the query
		if (ss > qe || se < qs)
			return Integer.MAX_VALUE;

		int mid = (ss + se) / 2;

		return Math.min(getMinUtil(ss, mid, qs, qe, si * 2 + 1), getMinUtil(mid + 1, se, qs, qe, si * 2 + 2));

	}

	int getMin(int n, int qs, int qe) {

		if (qs < 0 || qe >= n || qs > qe) {
			System.out.println("Invalid Input");
			return -1;
		}

		return getMinUtil(0, n - 1, qs, qe, 0);

	}

	int construct(int[] arr, int ss, int se, int si) {

		if (ss == se) {
			st[si] = arr[ss];
			return arr[ss];
		}

		int  mid = (ss + se) / 2;

		st[si] = Math.min(construct(arr, ss, mid, si * 2 + 1), construct(arr, mid + 1, se, si * 2 + 2));

		return st[si];
	}

}


//Explanation is available on geeksforgeeks