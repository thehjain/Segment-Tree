
//Problem is available on HackerEartch named HelpAshu

//Here we have given an array and we can perform 3 type
//of queries
//1. to make an update in an array
//2. count the number of even elements in given range
//3. count the number of odd elements in given range

//So to reduce the time complexity we are going to use
//Segment Tree here.

import java.util.*;
import java.io.*;
import java.math.*;

class HelpAshu {
	public static void main(String[] args) throws IOException {

		FastScanner sc = new FastScanner();

		int n = sc.nextInt();

		int[] arr = new int[n];

		for (int i = 0; i < n; i++)
			arr[i] = sc.nextInt();

		int no_of_queries = sc.nextInt();

		Tree tree = new Tree(arr, n);

		for (int i = 0; i < no_of_queries; i++) {

			int q = sc.nextInt();

			if (q == 0) {
				int index = sc.nextInt() - 1;
				int newVal = sc.nextInt();
				if (arr[index] % 2 == 0 && newVal % 2 == 0)
					continue;
				if (arr[index] % 2 == 1 && newVal % 2 == 1)
					continue;
				tree.update(arr, index, newVal);
			} else if (q == 1) {
				int l = sc.nextInt() - 1;
				int r = sc.nextInt() - 1;
				System.out.println(tree.getEven(l, r));
			} else if (q == 2) {
				int l = sc.nextInt() - 1;
				int r = sc.nextInt() - 1;
				System.out.println(tree.getOdd(l, r));
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
	int n;

	Tree(int[] arr, int n) {

		st = new Pair[4 * n + 1];
		this.n = n;

		for (int i = 0; i < 4 * n + 1; i++)
			st[i] = new Pair();

		construct(0, n - 1, 0, arr);

	}

	int getOddUtil(int ss, int se, int qs, int qe, int si) {

		if (ss > qe || se < qs) return 0;

		if (ss >= qs && qe >= se) return st[si].odd;

		int mid = (ss + se) / 2;

		int l = getOddUtil(ss, mid, qs, qe, si * 2 + 1);
		int r = getOddUtil(mid + 1, se, qs, qe, si * 2 + 2);

		return l + r;

	}

	int getOdd(int qs, int qe) {
		return getOddUtil(0, n - 1, qs, qe, 0);
	}

	int getEvenUtil(int ss, int se, int qs, int qe, int si) {

		if (ss > qe || se < qs) return 0;

		if (ss >= qs && qe >= se)
			return st[si].even;

		int mid = (ss + se) / 2;

		int l = getEvenUtil(ss, mid, qs, qe, si * 2 + 1);
		int r = getEvenUtil(mid + 1, se, qs, qe, si * 2 + 2);

		return l + r;
	}

	int getEven(int qs, int qe) {

		return getEvenUtil(0, n - 1, qs, qe, 0);

	}

	void updateUtil(int ss, int se, int[] arr, int index, int newVal, int si) {

		if (ss == se) {
			if (arr[ss] % 2 == 0) {
				st[si].even = 0;
				st[si].odd = 1;
			} else {
				st[si].even = 1;
				st[si].odd = 0;
			}
			arr[ss] = newVal;
			return;
		}

		int mid = (ss + se) / 2;

		if (index <= mid)
			updateUtil(ss, mid, arr, index, newVal, si * 2 + 1);
		else
			updateUtil(mid + 1, se, arr, index, newVal, si * 2 + 2);

		st[si].even = st[si * 2 + 1].even + st[si * 2 + 2].even;
		st[si].odd = st[si * 2 + 1].odd + st[si * 2 + 2].odd;

	}

	void update(int[] arr, int index, int newVal) {

		updateUtil(0, n - 1, arr, index, newVal, 0);

	}

	void construct(int ss, int se, int si, int[] arr) {

		if (ss == se) {
			if (arr[ss] % 2 == 0)
				st[si].even = 1;
			else
				st[si].odd = 1;
			return;
		}

		int mid = (ss + se) / 2;

		construct(ss, mid, si * 2 + 1, arr);
		construct(mid + 1, se, si * 2 + 2, arr);

		st[si].even = st[si * 2 + 1].even + st[si * 2 + 2].even;
		st[si].odd = st[si * 2 + 1].odd + st[si * 2 + 2].odd;
	}

}

class Pair {
	int even = 0;
	int odd = 0;
}