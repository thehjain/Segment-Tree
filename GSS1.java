

//Here we have given an array with numbers and
//number of queries and we have to find the maximum sum subarray
//in each query

//So to reduce the time complexity we are going to implement
//it using Segement Tree


import java.util.*;
import java.io.*;
import java.math.*;

class GSS1 {

	private static int MAX = Integer.MAX_VALUE;
	private static int MIN = Integer.MIN_VALUE;
	private static int MOD = 1000000007;
	static FastScanner sc = new FastScanner();
	static int[] arr;

	public static void main(String[] args) throws IOException {
		// int T = sc.nextInt();
		// while (T-- > 0) {
		solve();
		// }
	}

	static void solve() throws IOException {

		int n = sc.nextInt();
		arr = new int[n];

		for (int i = 0; i < n; i++)
			arr[i] = sc.nextInt();

		SegmentTree tree = new SegmentTree(n);

		int q = sc.nextInt();

		for (int i = 0; i < q; i++) {
			int qs = sc.nextInt() - 1;
			int qe = sc.nextInt() - 1 ;
			System.out.println(tree.query(qs, qe, n));
		}

	}

	static class SegmentTree {

		int maxPrefixSum;
		int maxSuffixSum;
		int totalSum;
		int maxSubarraySum;

		SegmentTree[] tree;
		SegmentTree() {}
		SegmentTree(int n) {
			maxSubarraySum = totalSum = maxPrefixSum = maxSuffixSum = MIN;
			construct(n);
		}

		void construct(int n) {

			tree = new SegmentTree[4 * n + 1];

			for (int i = 0; i < 4 * n + 1; i++)
				tree[i] = new SegmentTree();

			constructUtil(0, n - 1, 0);

			// return tree;
		}

		void constructUtil(int ss, int se, int si) {

			if (ss == se) {
				tree[si].totalSum = arr[ss];
				tree[si].maxSuffixSum = arr[ss];
				tree[si].maxPrefixSum = arr[ss];
				tree[si].maxSubarraySum = arr[ss];
				return;
			}

			int mid = (ss + se) / 2;

			constructUtil(ss, mid, si * 2 + 1);
			constructUtil(mid + 1, se, si * 2 + 2);

			tree[si] = merge(tree[2 * si + 1], tree[2 * si + 2]);

		}

		SegmentTree merge(SegmentTree leftChild, SegmentTree rightChild) {

			SegmentTree parentNode = new SegmentTree();

			parentNode.maxPrefixSum = Math.max(leftChild.maxPrefixSum,
			                                   leftChild.totalSum + rightChild.maxPrefixSum);
			parentNode.maxSuffixSum = Math.max(rightChild.maxSuffixSum,
			                                   rightChild.totalSum + leftChild.maxSuffixSum);
			parentNode.totalSum = leftChild.totalSum + rightChild.totalSum;
			parentNode.maxSubarraySum = Math.max(Math.max(leftChild.maxSubarraySum, rightChild.maxSubarraySum)
			                                     , leftChild.maxSuffixSum + rightChild.maxPrefixSum);

			return parentNode;

		}

		SegmentTree queryUtil(int ss, int se, int qs, int qe, int si) {

			if (ss > qe || se < qs) {
				SegmentTree nullNode = new SegmentTree();
				return nullNode;
			}

			if (ss >= qs && se <= qe)
				return tree[si];

			int mid = (ss + se) / 2;

			SegmentTree left = queryUtil(ss, mid, qs, qe, si * 2 + 1);
			SegmentTree right = queryUtil(mid + 1, se, qs, qe, si * 2 + 2);

			SegmentTree res = merge(left, right);

			return res;

		}

		int query(int start, int end, int n) {
			SegmentTree res = queryUtil(0, n - 1, start, end, 0);
			return res.maxSubarraySum;
		}

	}

	static class FastScanner {
		public BufferedReader reader;
		public StringTokenizer tokenizer;
		public FastScanner() {
			reader = new BufferedReader(new InputStreamReader(System.in), 32768);
			tokenizer = null;
		}
		public String next() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(reader.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}
		public int nextInt() {
			return Integer.parseInt(next());
		}
		public long nextLong() {
			return Long.parseLong(next());
		}
		public double nextDouble() {
			return Double.parseDouble(next());
		}
		public String nextLine() {
			try {
				return reader.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}