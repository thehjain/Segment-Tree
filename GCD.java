import java.util.*;
import java.io.*;
import java.math.*;

class GCD {

	private static int MAX = Integer.MAX_VALUE;
	private static int MIN = Integer.MIN_VALUE;
	private static int MOD = 1000000007;
	static FastScanner sc = new FastScanner();

	public static void main(String[] args) throws IOException {
		// int T = sc.nextInt();
		// while (T-- > 0) {
		solve();
		// }
	}

	static void solve() throws IOException {

		int n = sc.nextInt();
		int q = sc.nextInt();

		int[] arr = new int[n];

		for (int i = 0; i < n; i++)
			arr[i] = sc.nextInt();

		int[] res = new int[q];
		int index = 0;

		SegmentTree tree = new SegmentTree(arr, n);

		for (int i = 0; i < q; i++) {

			int l = sc.nextInt() - 1;
			int r = sc.nextInt() - 1;

			res[index++] = tree.findRangeGCD(l, r, n);
		}
		for (int ele : res)
			System.out.println(ele);
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

class SegmentTree {

	int[] st;

	SegmentTree(int[] arr, int n) {

		int height = (int)(Math.ceil(Math.log(n) / Math.log(2)));
		int size = 2 * (int)Math.pow(2, height) - 1;

		st = new int[size];

		construct(arr, 0, n - 1, 0);
	}

	int gcd(int a, int b) {
		if (b == 0) return a;
		return gcd(b, a % b);
	}

	int findRangeGCD(int qs, int qe, int n) {

		if (qs < 0 || qe >= n || qs > qe) {
			// System.out.println("Invalid Input");
			return -1;
		}
		return findGCD(0, n - 1, qs, qe, 0);
	}

	int findGCD(int ss, int se, int qs, int qe, int si) {

		if (ss > qe || se < qs)
			return 0;

		//Segment is part of query
		if (qs <= ss && qe >= se)
			return st[si];

		int mid = (ss + se) / 2;

		return gcd(findGCD(ss, mid, qs, qe, si * 2 + 1), findGCD(mid + 1, se, qs, qe, si * 2 + 2));

	}

	int construct(int[] arr, int ss, int se, int si) {

		if (ss == se) {
			st[si] = arr[ss];
			return st[si];
		}

		int mid = (ss + se) / 2;
		st[si] = gcd(construct(arr, ss, mid, si * 2 + 1), construct(arr, mid + 1, se, si * 2 + 2));
		return st[si];
	}

}