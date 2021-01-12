

//Problem is taken from CodeChef named MultipleOf3

//Here we have given size of an array initialized with
//zero and we can perform two types of queries.
//1.To count the numbers in range of ele%3.
//2.To update the numbers in given range.


//To reduce time complexity we can use segment tree here and
//one thing to notice is that here we have to update the queries
//in range so segment tree will slow the updation.

//for that we are going to use lazy propagation.

import java.util.*;
import java.io.*;
import java.math.*;

class MultipleOf3 {
	public static void main(String[] args) {

		FastScanner sc = new FastScanner();

		int n = sc.nextInt();
		int[] arr = new int[n];
		int no_of_queries = sc.nextInt();

		Tree tree = new Tree(arr, n);

		for (int i = 0; i < no_of_queries; i++) {

			int type = sc.nextInt();

			if (type == 0) {
				int l = sc.nextInt();
				int r = sc.nextInt();
				tree.update(arr, l, r);
			} else if (type == 1) {
				int l = sc.nextInt();
				int r = sc.nextInt();
				System.out.println(tree.getValue(n, l, r));
			}

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

class Tree {

	Pair[] st;
	int[] lazy;

	Tree(int[] arr, int n) {

		st = new Pair[4 * n];
		lazy = new int[4 * n];

		for (int i = 0; i < 4 * n; i++)
			st[i] = new Pair();

		construct(arr, 0, n - 1, 0);
	}

	int getValueUtil(int ss, int se, int qs, int qe, int si) {

		//Checking for pending updates
		if (lazy[si] != 0) {
			int add = lazy[si];
			lazy[si] = 0;
			if (ss != se) {
				lazy[si * 2 + 1] += add;
				lazy[si * 2 + 2] += add;
			}
			add %= 3;
			for (int i = 0; i < add; i++) {
				shift(si);
			}
		}

		//If it is out of range
		if (ss > qe || se < qs) return 0;
		if (ss >= qs && se <= qe)
			return st[si].val[0];

		int mid = (ss + se) / 2;

		int l = getValueUtil(ss, mid, qs, qe, si * 2 + 1);
		int r = getValueUtil(mid + 1, se, qs, qe, si * 2 + 2);

		return l + r;
	}

	int getValue(int n, int qs, int qe) {
		return getValueUtil(0, n - 1, qs, qe, 0);
	}

	void shift(int si) {
		int temp = st[si].val[2];
		st[si].val[2] = st[si].val[1];
		st[si].val[1] = st[si].val[0];
		st[si].val[0] = temp;
	}

	void updateUtil(int ss, int se, int qs, int qe, int si) {

		//Check if there are any updates pending
		if (lazy[si] != 0) {
			int add = lazy[si];
			lazy[si] = 0;
			if (ss != se) {
				lazy[si * 2 + 1] += add;
				lazy[si * 2 + 2] += add;
			}
			add %= 3;
			for (int i = 0; i < add; i++) {
				shift(si);
			}
		}

		//If it is out of range
		if (ss > qe || se < qs)
			return;

		if (ss >= qs && se <= qe) {
			shift(si);
			if (ss != se) {
				lazy[2 * si + 1]++;
				lazy[2 * si + 2]++;
			}
			return;
		}

		int mid = (ss + se) / 2;

		updateUtil(ss, mid, qs, qe, si * 2 + 1);
		updateUtil(mid + 1, se, qs, qe, si * 2 + 2);

		st[si].val[0] = st[si * 2 + 1].val[0] + st[si * 2 + 2].val[0];
		st[si].val[1] = st[si * 2 + 1].val[1] + st[si * 2 + 2].val[1];
		st[si].val[2] = st[si * 2 + 1].val[2] + st[si * 2 + 2].val[2];

	}

	void update(int[] arr, int qs, int qe) {
		int n = arr.length;
		updateUtil(0, n - 1, qs, qe, 0);
	}

	void construct(int[] arr, int ss, int se, int si) {

		if (ss == se) {
			st[si].val[0] = 1;
			st[si].val[1] = 0;
			st[si].val[2] = 0;
			return;
		}

		int mid = (ss + se) / 2;

		construct(arr, ss, mid, si * 2 + 1);
		construct(arr, ss, mid, si * 2 + 2);

		st[si].val[0] = st[si * 2 + 1].val[0] + st[si * 2 + 2].val[0];
		st[si].val[1] = st[si * 2 + 1].val[1] + st[si * 2 + 2].val[1];
		st[si].val[2] = st[si * 2 + 1].val[2] + st[si * 2 + 2].val[2];
	}

}

class Pair {
	int[] val = new int[3];
}