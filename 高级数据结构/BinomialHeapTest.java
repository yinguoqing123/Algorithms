/**
 * Java ����: �����
 *
 * @author skywang
 * @date 2014/04/03
 */

// ����������Ͷ��  ʱ�临�Ӷ���ô����
//����Ķѵ������������Ҫ���ʲô���Ĺ���

class BinomialHeap<T extends Comparable<T>> {

	private BinomialNode<T> mRoot;	// �����

	private class BinomialNode<T extends Comparable<T>> {
		T key;			    // �ؼ���(��ֵ)
    	int degree;			// ����
		BinomialNode<T> child;	// ����
		BinomialNode<T> parent;	// ���ڵ�
		BinomialNode<T> next;	// �ֵܽڵ�

		public BinomialNode(T key) {
			this.key = key;
			this.degree = 0;
			this.child = null;
			this.parent = null;
			this.next = null;
		}

		public String toString() {
			return "key:"+key;
		}
	}

	public BinomialHeap() {
		mRoot = null;
	}

	/*
	 * ��ȡ������е���С�ڵ�ļ�ֵ
	 */
	 
	public T minimum() {
		if (mRoot==null)
			return null;

		BinomialNode<T> x, prev_x;	// x�����������ĵ�ǰ�ڵ�
		BinomialNode<T> y, prev_y;	// y����С�ڵ�

		prev_x  = mRoot;
		x       = mRoot.next;
		prev_y  = null;
		y       = mRoot;
		// �ҵ���С�ڵ�
		while (x != null) {
			if (x.key.compareTo(y.key) < 0) {
				y = x;
				prev_y = prev_x;
			}
			prev_x = x;
			x = x.next;
		}

		return y.key;
	}

	 
	/*
	 * �ϲ���������ѣ���child�ϲ���root��
	 */
	private void link(BinomialNode<T> child, BinomialNode<T> root) {
		child.parent = root;
		child.next   = root.child;
		root.child = child;
		root.degree++;
	}

	/*
	 * ��h1, h2�еĸ���ϲ���һ���������������������غϲ���ĸ��ڵ�
	 */
	private BinomialNode<T> merge(BinomialNode<T> h1, BinomialNode<T> h2) {
		if (h1 == null) return h2;
		if (h2 == null) return h1;

		// root���¶ѵĸ���h3��������h1��h3�ġ�
		BinomialNode<T> pre_h3, h3, root=null;
	 
		pre_h3 = null;
		//����while��h1, h2, pre_h3, h3��������˳��
		while ((h1!=null) && (h2!=null)) {

			if (h1.degree <= h2.degree) {
				h3 = h1;
				h1 = h1.next;
			} else {
				h3 = h2;
				h2 = h2.next;
			}
 
			if (pre_h3 == null) {
				pre_h3 = h3;
				root = h3;
			} else {
				pre_h3.next = h3;
				pre_h3 = h3;
			}

			if (h1 != null) {
				h3.next = h1;
			} else {
				h3.next = h2;
			}
		}
		return root;
	}

	/*
	 * �ϲ�����ѣ���h1, h2�ϲ���һ���ѣ������غϲ���Ķ�
	 */
	private BinomialNode<T> union(BinomialNode<T> h1, BinomialNode<T> h2) {
		BinomialNode<T> root;

		// ��h1, h2�еĸ���ϲ���һ������������������root
		root = merge(h1, h2);
		if (root == null)
			return null;
	 
		BinomialNode<T> prev_x = null;
		BinomialNode<T> x      = root;
		BinomialNode<T> next_x = x.next;
		while (next_x != null) {

			if (   (x.degree != next_x.degree) 
				|| ((next_x.next != null) && (next_x.degree == next_x.next.degree))) {
				// Case 1: x.degree != next_x.degree
				// Case 2: x.degree == next_x.degree == next_x.next.degree
				prev_x = x;
				x = next_x;
			} else if (x.key.compareTo(next_x.key) <= 0) {
				// Case 3: x.degree == next_x.degree != next_x.next.degree
				//      && x.key    <= next_x.key
				x.next = next_x.next;
				link(next_x, x);
			} else {
				// Case 4: x.degree == next_x.degree != next_x.next.degree
				//      && x.key    >  next_x.key
				if (prev_x == null) {
					root = next_x;
				} else {
					prev_x.next = next_x;
				}
				link(x, next_x);
				x = next_x;
			}
			next_x = x.next;
		}

		return root;
	}

	/*
	 * �������other�ϲ�����ǰ����
	 */
	public void union(BinomialHeap<T> other) {
		if (other!=null && other.mRoot!=null)
			mRoot = union(mRoot, other.mRoot);
	}

	/*
	 * �½�key��Ӧ�Ľڵ㣬��������뵽������С�
	 */
	public void insert(T key) {
		BinomialNode<T> node;

		// ��ֹ������ͬ�ļ�ֵ
		if (contains(key)==true) {
			System.out.printf("insert failed: the key(%s) is existed already!\n", key);
			return ;
		}

		node = new BinomialNode<T>(key);
		if (node==null)
			return ;

		mRoot = union(mRoot, node);
	}

	/*
	 * ��ת�����root�������ط�ת��ĸ��ڵ�
	 */
	private BinomialNode<T> reverse(BinomialNode<T> root) {
		BinomialNode<T> next;
		BinomialNode<T> tail = null;

		if (root==null)
			return root;

		root.parent = null;
		while (root.next!=null) {
			next         = root.next;
			root.next    = tail;
			tail         = root;
			root         = next;
			root.parent  = null;
		}
		root.next = tail;

		return root;
	}

	/*
	 * �Ƴ������root�е���С�ڵ㣬������ɾ���ڵ��Ķ�����
	 */
	private BinomialNode<T> extractMinimum(BinomialNode<T> root) {
		if (root==null)
			return root;
	 
		BinomialNode<T> x, prev_x;	// x�����������ĵ�ǰ�ڵ�
		BinomialNode<T> y, prev_y;	// y����С�ڵ�
	 
		prev_x  = root;
		x       = root.next;
		prev_y = null;
		y      = root;
		// �ҵ���С�ڵ�
		while (x != null) {
			if (x.key.compareTo(y.key) < 0) {
				y = x;
				prev_y = prev_x;
			}
			prev_x = x;
			x = x.next;
		}

		if (prev_y == null)	// root�ĸ��ڵ������С���ڵ�
			root = root.next;
		else				// root�ĸ��ڵ㲻����С���ڵ�
			prev_y.next = y.next;
	 
		// ��ת��С�ڵ�����ӣ��õ���С��child��
		// ��������ʹ����С�ڵ����ڶ������ĺ����Ƕ����������Ϊһ�ö����Ķ�����(��������С�ڵ�)
		BinomialNode<T> child = reverse(y.child);
		// ��"ɾ����С�ڵ�Ķ����child"��"root"���кϲ���
		root = union(root, child);

		// help GC
		y = null;

		return root;
	}

	public void extractMinimum() {
		mRoot = extractMinimum(mRoot);
	}

	/* 
	 * ���ٹؼ��ֵ�ֵ����������еĽڵ�node�ļ�ֵ��СΪkey��
	 */
	private void decreaseKey(BinomialNode<T> node, T key) {
		if(key.compareTo(node.key)>=0 || contains(key)==true) {
        	System.out.println("decrease failed: the new key("+key+") is existed already, or is no smaller than current key("+node.key+")");
			return ;
		}
		node.key = key;
	 
		BinomialNode<T> child, parent;
		child = node;
		parent = node.parent;
		while(parent != null && child.key.compareTo(parent.key)<0) {
			// ����parent��child������
			T tmp = parent.key;
			parent.key = child.key;
			child.key = tmp;

			child = parent;
			parent = child.parent;
		}
	}

	/* 
	 * ���ӹؼ��ֵ�ֵ����������еĽڵ�node�ļ�ֵ����Ϊkey��
	 */
	private void increaseKey(BinomialNode<T> node, T key) {
		if(key.compareTo(node.key)<=0 || contains(key)==true) {
        	System.out.println("increase failed: the new key("+key+") is existed already, or is no greater than current key("+node.key+")");
			return ;
		}
		node.key = key;

		BinomialNode<T> cur = node;
		BinomialNode<T> child = cur.child;
		while (child != null) {

			if(cur.key.compareTo(child.key) > 0) {
				// ���"��ǰ�ڵ�" < "��������"��
				// ����"���ĺ�����(���� �� ���ӵ��ֵ�)"�У��ҳ���С�Ľڵ㣻
				// Ȼ��"��С�ڵ��ֵ" �� "��ǰ�ڵ��ֵ"���л���
				BinomialNode<T> least = child;	// least��child�������ֵ��е���С�ڵ�
				while(child.next != null) {
					if (least.key.compareTo(child.next.key) > 0)
						least = child.next;
					child = child.next;
				}
				// ������С�ڵ�͵�ǰ�ڵ��ֵ
				T tmp = least.key;
				least.key = cur.key;
				cur.key = tmp;

				// ��������֮���ٶ�"ԭ��С�ڵ�"���е�����ʹ��������С�ѵ����ʣ����ڵ� <= �ӽڵ�
				cur = least;
				child = cur.child;
			} else {
				child = child.next;
			}
		}
	}

	/* 
	 * ���¶���ѵĽڵ�node�ļ�ֵΪkey
	 */
	private void updateKey(BinomialNode<T> node, T key) {
		if (node == null)
			return ;

		int cmp = key.compareTo(node.key);
		if(cmp < 0)							// key < node.key
			decreaseKey(node, key);
		else if(cmp > 0)					// key > node.key
			increaseKey(node, key);
		else
			System.out.println("No need to update!!!");
	}

	/* 
	 * ��������м�ֵoldkey����Ϊnewkey
	 */
	public void update(T oldkey, T newkey) {
		BinomialNode<T> node;

		node = search(mRoot, oldkey);
		if (node != null)
			updateKey(node, newkey);
	}

	/*
	 * ���ң��ڶ�����в��Ҽ�ֵΪkey�Ľڵ�
	 */
	private BinomialNode<T> search(BinomialNode<T> root, T key) {
		BinomialNode<T> child;
		BinomialNode<T> parent = root;

		parent = root;
		while (parent != null) {
			if (parent.key.compareTo(key) == 0)
				return parent;
			else {
				if((child = search(parent.child, key)) != null)
					return child;
				parent = parent.next;
			}
		}

		return null;
	}
	 
	/*
	 * ��������Ƿ������ֵkey
	 */
	public boolean contains(T key) {
		return search(mRoot, key)!=null ? true : false;
	}

	/* 
	 * ɾ���ڵ㣺ɾ����ֵΪkey�Ľڵ�
	 */
	private BinomialNode<T> remove(BinomialNode<T> root, T key) {
		if (root==null)
			return root;

		BinomialNode<T> node;

		// ���Ҽ�ֵΪkey�Ľڵ�
		if ((node = search(root, key)) == null)
			return root;

		// ����ɾ���Ľڵ�������������Ƶ������ڵĶ������ĸ��ڵ�
		BinomialNode<T> parent = node.parent;
		while (parent != null) {
			// ��������
			T tmp = node.key;
			node.key = parent.key;
			parent.key = tmp;

			// ��һ�����ڵ�
			node   = parent;
			parent = node.parent;
		}

		// �ҵ�node��ǰһ�����ڵ�(prev)
		BinomialNode<T> prev = null;
		BinomialNode<T> pos  = root;
		while (pos != node) {
			prev = pos;
			pos  = pos.next;
		}
		// �Ƴ�node�ڵ�
		if (prev!=null)
			prev.next = node.next;
		else
			root = node.next;

		root = union(root, reverse(node.child)); 

		// help GC
		node = null;

		return root;
	}

	public void remove(T key) {
		mRoot = remove(mRoot, key);
	}

	/*
	 * ��ӡ"�����"
	 *
	 * ����˵����
	 *     node       -- ��ǰ�ڵ�
	 *     prev       -- ��ǰ�ڵ��ǰһ���ڵ�(���ڵ�or�ֵܽڵ�)
	 *     direction  --  1����ʾ��ǰ�ڵ���һ������;
	 *                    2����ʾ��ǰ�ڵ���һ���ֵܽڵ㡣
	 */
	private void print(BinomialNode<T> node, BinomialNode<T> prev, int direction) {
		while(node != null)
		{
			if(direction==1)	// node�Ǹ��ڵ�
				System.out.printf("\t%2d(%d) is %2d's child\n", node.key, node.degree, prev.key);
			else				// node�Ƿ�֧�ڵ�
				System.out.printf("\t%2d(%d) is %2d's next\n", node.key, node.degree, prev.key);

			if (node.child != null)
				print(node.child, node, 1);

			// �ֵܽڵ�
			prev = node;
			node = node.next;
			direction = 2;
		}
	}

	public void print() {
		if (mRoot == null)
			return ;

		BinomialNode<T> p = mRoot;
		System.out.printf("== �����( ");
		while (p != null) {
			System.out.printf("B%d ", p.degree);
			p = p.next;
		}
		System.out.printf(")����ϸ��Ϣ��\n");

		int i=0;
		p = mRoot;
		while (p != null) {
			i++;
			System.out.printf("%d. ������B%d: \n", i, p.degree);
			System.out.printf("\t%2d(%d) is root\n", p.key, p.degree);

			print(p.child, p, 1);
			p = p.next;
		}
		System.out.printf("\n");
	}
}
/**
 * Java ����: �����
 *
 * @author skywang
 * @date 2014/03/31
 */

public class BinomialHeapTest {

	private static final boolean DEBUG = false;

	// ��7�� = 1+2+4
	private static int a[] = {12,  7, 25, 15, 28, 33, 41};
	// ��13�� = 1+4+8
	private static int b[] = {18, 35, 20, 42,  9, 
			   31, 23,  6, 48, 11, 
			   24, 52, 13 };


	// ��֤"����ѵĲ������"
	public static void testInsert() {
		BinomialHeap<Integer> ha=new BinomialHeap<Integer>();

		// �����ha
	    System.out.printf("== �����(ha)���������: ");
		for(int i=0; i<a.length; i++) {
			System.out.printf("%d ", a[i]);
			ha.insert(a[i]);
		}
	    System.out.printf("\n");
		System.out.printf("== �����(ha)����ϸ��Ϣ: \n");
		ha.print();
    }

	// ��֤"����ѵĺϲ�����"
	public static void testUnion() {
		BinomialHeap<Integer> ha=new BinomialHeap<Integer>();
		BinomialHeap<Integer> hb=new BinomialHeap<Integer>();

		// �����ha
	    System.out.printf("== �����(ha)���������: ");
		for(int i=0; i<a.length; i++) {
			System.out.printf("%d ", a[i]);
			ha.insert(a[i]);
		}
	    System.out.printf("\n");
		System.out.printf("== �����(ha)����ϸ��Ϣ: \n");
		ha.print();

		// �����hb
	    System.out.printf("== �����(hb)���������: ");
		for(int i=0; i<b.length; i++) {
			System.out.printf("%d ", b[i]);
			hb.insert(b[i]);
		}
	    System.out.printf("\n");
		// ��ӡ�����hb
		System.out.printf("== �����(hb)����ϸ��Ϣ: \n");
		hb.print();

		// ��"�����hb"�ϲ���"�����ha"�С�
		ha.union(hb);
		// ��ӡ�����ha����ϸ��Ϣ
		System.out.printf("== �ϲ�ha��hb�����ϸ��Ϣ:\n");
		ha.print();
    }

	// ��֤"����ѵ�ɾ������"
	public static void testDelete() {
		BinomialHeap<Integer> hb=new BinomialHeap<Integer>();

		// �����hb
	    System.out.printf("== �����(hb)���������: ");
		for(int i=0; i<b.length; i++) {
			System.out.printf("%d ", b[i]);
			hb.insert(b[i]);
		}
	    System.out.printf("\n");
		// ��ӡ�����hb
		System.out.printf("== �����(hb)����ϸ��Ϣ: \n");
		hb.print();

		// ��"�����hb"�ϲ���"�����ha"�С�
		hb.remove(20);
		System.out.printf("== ɾ���ڵ�20�����ϸ��Ϣ: \n");
		hb.print();
    }

	// ��֤"����ѵĸ���(����)����"
	public static void testDecrease() {
		BinomialHeap<Integer> hb=new BinomialHeap<Integer>();

		// �����hb
	    System.out.printf("== �����(hb)���������: ");
		for(int i=0; i<b.length; i++) {
			System.out.printf("%d ", b[i]);
			hb.insert(b[i]);
		}
	    System.out.printf("\n");
		// ��ӡ�����hb
		System.out.printf("== �����(hb)����ϸ��Ϣ: \n");
		hb.print();

		// ���ڵ�20����Ϊ2
		hb.update(20, 2);
		System.out.printf("== ���½ڵ�20->2�����ϸ��Ϣ: \n");
		hb.print();
    }

	// ��֤"����ѵĸ���(����)����"
	public static void testIncrease() {
		BinomialHeap<Integer> hb=new BinomialHeap<Integer>();

		// �����hb
	    System.out.printf("== �����(hb)���������: ");
		for(int i=0; i<b.length; i++) {
			System.out.printf("%d ", b[i]);
			hb.insert(b[i]);
		}
	    System.out.printf("\n");
		// ��ӡ�����hb
		System.out.printf("== �����(hb)����ϸ��Ϣ: \n");
		hb.print();

		// ���ڵ�6����Ϊ60
		hb.update(6, 60);
		System.out.printf("== ���½ڵ�6->60�����ϸ��Ϣ: \n");
		hb.print();
    }

	public static void main(String[] args) {
		// 1. ��֤"����ѵĲ������"
		testInsert();
		// 2. ��֤"����ѵĺϲ�����"
		testUnion();
		// 3. ��֤"����ѵ�ɾ������"
		testDelete();
		// 4. ��֤"����ѵĸ���(����)����"
		testDecrease();
		// 5. ��֤"����ѵĸ���(����)����"
		testIncrease();
    }
}