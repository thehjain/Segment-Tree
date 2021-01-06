

//First of all Segment Tree is used in special cases
//where we have to work upon given ranges in an array.

//It can operate the query and update the query both in O(logN)
//time compelexity. Firstly we have to built it according
//to the logic given in question.

//Here an array is given to us and we have to perform multiple
//queries on this array including finding sum in given range
//and updating en element

import java.util.*;
import java.math.*;

class SumOfGivenRange {
	public static void main(String[] args) {

		int arr[] = {1, 3, 5, 7, 9, 11};
		int n = arr.length;

		SegmentTree tree = new SegmentTree(arr, n);

		//Print the sum of element in given range
		System.out.println("Sum of elements= " + tree.getSum(n, 1, 3));

		//Update the value in an array
		tree.updateValue(arr, n, 1, 10);

		//Print the sum of updated elements
		System.out.println("Sum of elements after update= " + tree.getSum(n, 1, 3));

	}
}

class SegmentTree {

	int[] st;

	SegmentTree(int[] arr, int n) {

		//Heigh of Segment Tree
		int x = (int)(Math.ceil(Math.log(n) / Math.log(2)));

		//Maximum size of an array
		int max_size = 2 * (int)Math.pow(2, x) - 1;

		st = new int[max_size];

		construct(arr, 0, n - 1, 0);
	}

	void updateValueUtil(int ss, int se, int i, int diff, int si) {

		//Base case if i is outside the arr
		if (i < ss || i > se)
			return;

		st[si] = st[si] + diff;

		if (se != ss) {
			int mid = (ss + se) / 2;
			updateValueUtil(ss, mid, i, diff, 2 * si + 1);
			updateValueUtil(mid + 1, se, i, diff, 2 * si + 2);
		}

	}

	void updateValue(int[] arr, int n, int i, int new_val) {

		//If input is invalid
		if (i < 0 || i >= n) {
			System.out.println("Invalid Input");
			return;
		}

		//difference between new value and old value
		int diff = new_val - arr[i];

		//update the value
		arr[i] = new_val;

		//update the value of node in segment tree
		updateValueUtil(0, n - 1, i, diff, 0);
	}

	int getSumUtil(int ss, int se, int qs, int qe, int si) {

		//If Segment of part of given range
		if (qs <= ss && qe >= se)
			return st[si];

		//If segment is outside the given query
		if (se < qs || ss > qe)
			return 0;

		int mid = (ss + se) / 2;

		return getSumUtil(ss, mid, qs, qe, si * 2 + 1) + getSumUtil(mid + 1, se, qs, qe, si * 2 + 2);
	}

	int getSum(int n, int qs, int qe) {

		if (qs < 0 || qe >= n || qs > qe) {
			System.out.println("Invalid Input");
			return -1;
		}
		return getSumUtil(0, n - 1, qs, qe, 0);
	}

	int construct(int[] arr, int ss, int se, int si) {

		if (ss == se) {
			st[si] = arr[ss];
			return arr[ss];
		}

		int mid = (ss + se) / 2;
		st[si] = construct(arr, ss, mid, si * 2 + 1) + construct(arr, mid + 1, se, si * 2 + 2);
		return st[si];
	}

}