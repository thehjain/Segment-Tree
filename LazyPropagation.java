

//Lazy Propagation optimization of segment Tree when
//we have to update elements in range. Then it is
//better to use Lazy Propagation Technique.

////Here we are taking Sum of Elements in Given Range Problem.////

import java.util.*;

class LazySegementTree {

	final int MAX = 1000;
	int[] tree = new int[MAX];
	int[] lazy = new int[MAX];

	void updateRangeUtil(int si, int ss, int se, int us, int ue, int diff) {

		// System.out.print(lazy[si] + " ");

		//Check if there are any pending updates
		if (lazy[si] != 0) {

			tree[si] += (se - ss + 1) * lazy[si];

			//Checking for leaf node
			if (ss != se) {

				tree[si * 2 + 1] += lazy[si];
				tree[si * 2 + 2] += lazy[si];

			}
			lazy[si] = 0;
		}

		//Checking for out of range
		if (ss > se || ss > ue || se < us)
			return ;

		//if Current segement is fully in range
		if (ss >= us && se <= ue) {

			//Add the difference to current node
			tree[si] += (se - ss + 1) * diff;

			//Checking for leaf nodes
			if (ss != se) {

				lazy[si * 2 + 1] += diff;
				lazy[si * 2 + 1] += diff;

			}
			return;
		}

		//If it is partially overlaping

		int mid = (ss + se) / 2;

		updateRangeUtil(si * 2 + 1, ss, mid, us, ue, diff);
		updateRangeUtil(si * 2 + 2, mid + 2, se, us, ue, diff);

		tree[si] = tree[si * 2 + 1] + tree[si * 2 + 2];
	}

	void updateRange(int n, int us , int ue, int diff) {

		updateRangeUtil(0, 0, n - 1, us, ue, diff);

	}

	int getSumUtil(int ss, int se, int qs, int qe, int si) {

		//When any updates is pending
		if (lazy[si] != 0) {

			//Make pending updates
			tree[si] += (se - ss + 1) * lazy[si];

			//If it is not leaf node
			if (ss != se) {
				lazy[si * 2 + 1] += lazy[si];
				lazy[si * 2 + 2] += lazy[si];
			}
			lazy[si] = 0;
		}

		//Out of Range
		if (ss > se || ss > qe || se < qs)
			return 0;

		//At this point we can make sure that pending
		//updates for current node are done and simply
		//we can return like normal segment tree

		if (ss >= qs && se <= qe)
			return tree[si];

		int mid = (ss + se) / 2;

		return getSumUtil(ss, mid, qs, qe, 2 * si + 1) + getSumUtil(mid + 1, se, qs, qe, 2 * si + 2);

	}

	int getSum(int n, int qs, int qe) {

		//Check for invalid input
		if (qs < 0 || qe >= n || qs > qe) {
			System.out.println("Invalid Input");
			return -1;
		}

		return getSumUtil(0, n - 1, qs, qe, 0);

	}

	void constructUtil(int[] arr, int ss, int se, int si) {

		if (ss > se)
			return;

		if (ss == se) {
			tree[si] = arr[ss];
			return;
		}

		int mid = (ss + se) / 2;

		constructUtil(arr, ss, mid, si * 2 + 1);
		constructUtil(arr, mid + 1, se, si * 2 + 2);

		tree[si] = tree[si * 2 + 1] + tree[si * 2 + 2];
	}

	void construct(int[] arr, int n) {
		constructUtil(arr, 0, n - 1, 0);
	}
}

class LazyPropagation {
	public static void main(String[] args) {

		int arr[] = {1, 3, 5, 7, 9, 11};
		int n = arr.length;

		LazySegementTree tree = new LazySegementTree();

		tree.construct(arr, n);

		System.out.println("Sum of values in give range: " + tree.getSum(n, 1, 3));

		tree.updateRange(n, 1, 5, 10);

		System.out.println("Sum of value in range after update: " + tree.getSum(n, 1, 3));

	}
}



//Problem is available on geeksforgeeks