import java.util.*;
public class ListUDG1
{
	private static int INF=Integer.MAX_VALUE;
	
	//��
	private class VNode
	{
		char data;
		ENode firstEdge;//��Ӧ������
		
		boolean isHeap; //Prim�㷨�ж��Ƿ���ڶ���
		
	}
	
	//�ߺ����ӱ��ͳһ
	private class ENode
	{
		int ivex;//�յ�
		int weight;
		ENode nextEdge; //��������
	}
	
	private int mEdgNum;   //�ߵ�����
	private VNode[] mVexs; //��������
	private EData[] edges;
	
	//��
	private static class EData
	{
		char start;
		char end;
		int weight;
		public EData(char start,char end,int weight){
			this.start=start;
			this.end=end;
			this.weight=weight;
		}
	}
	
	//ֱ�Ӹ��ݴ�������鴴��ͼ
	public ListUDG1(char[] vexs,EData[] edges)
	{
		this.edges=edges;
		int vlen=vexs.length;
		int elen=edges.length;
		mEdgNum=elen;
		mVexs=new VNode[vlen];
		for(int i=0;i<vlen;i++)
		{
			mVexs[i] = new VNode();
            mVexs[i].data = vexs[i];
            mVexs[i].firstEdge = null;
			mVexs[i].isHeap=false;
        
		}
		for(int i=0;i<elen;i++)
		{
			char c1 = edges[i].start;
            char c2 = edges[i].end;
            int weight = edges[i].weight;
			
			int p1=getPosition(c1);
			int p2=getPosition(c2);
			
			ENode node1=new ENode();
			node1.ivex = p2;
            node1.weight = weight;
			
			if(mVexs[p1].firstEdge == null)
              mVexs[p1].firstEdge = node1;
            else
                linkLast(mVexs[p1].firstEdge, node1);
			
			ENode node2 = new ENode();
            node2.ivex = p1;
            node2.weight = weight;
            // ��node2���ӵ�"p2���������ĩβ"
            if(mVexs[p2].firstEdge == null)
              mVexs[p2].firstEdge = node2;
            else
                linkLast(mVexs[p2].firstEdge, node2);
		}
	}
	
	private int getPosition(char ch)
	{
		int vlen=mVexs.length;
		int i=0;
		while(ch!=mVexs[i].data)
		{
			i++;
		}
		return i;
	}
	
	private void linkLast(ENode list, ENode node) {
        ENode p = list;

        while(p.nextEdge!=null)
            p = p.nextEdge;
        p.nextEdge = node;
    }

	
	//�����㷨���ǻ���̰�Ĳ���
	
	//쳲�������ʵ��
	//�ڵ��Ĺ�ϵ���ڻ���
	public void mstPrim(char begin)
	{
		int max=Integer.MAX_VALUE;
		int vlen=mVexs.length;
		int elen=edges.length;
		int index=getPosition(begin);
		int totalWeight=0;
		
		//����
		FibHeap  fh=new FibHeap();
		
		//��������Ͷѽڵ�һһ��Ӧ�Ĺ�ϵ  ��֪���˶����֪�����ڶ��еĶѽڵ�
		FibHeap.FibNode[] fn=new FibHeap.FibNode[vlen];
		for(int i=0;i<vlen;i++)
		{
			 if(i==index)
			 {
				fn[i]=new FibHeap.FibNode(0,mVexs[i]);
				fh.insert(fn[i]);
			 }
			 else
			{
				fn[i]=new FibHeap.FibNode(max,mVexs[i]);
				fh.insert(fn[i]);
			 }
		}
		while(fh.min!=null)
		{
			FibHeap.FibNode m=fh.removeMin();
			totalWeight+=m.key;
			ENode tmp=m.vex.firstEdge;
			
			if(m.p!=null)
			{
				System.out.print("("+m.p.data+","+m.vex.data+")"+"  ");
			}
			
			//�ѽڵ� �߽ڵ� ����ڵ��Ĺ�ϵ������
			//tmp.ivex �յ��ڶ��������е��±�
			while(tmp!=null)
			{
				if(mVexs[tmp.ivex].isHeap&&tmp.weight<fn[tmp.ivex].key)
				
				{
					fn[tmp.ivex].p=m.vex;
				
					//�ѽڵ��keyֵֻ��ͨ��decrease�ı�
					//fn[tmp.ivex].key=tmp.weight;
					fh.decrease(fn[tmp.ivex],tmp.weight);
				}
				tmp=tmp.nextEdge;
			}
			System.out.println();
		}
		
		System.out.println("Prim�㷨�ó�����Ȩ���ǣ�"+totalWeight);
	}
	
	//�ò��鼯ʵ��
	public void  mstKruskal()
	{
		LinkedList<EData> list=new LinkedList<>();
		FindUnion<VNode> fn=new FindUnion();
		int vlen=mVexs.length;
		FindUnion.Node[] vexs=new FindUnion.Node[vlen];
		for(int i=0;i<vlen;i++)
		{
			vexs[i]=new FindUnion.Node(mVexs[i]);
			fn.set(vexs[i]);
		}
		sort(edges,0,mEdgNum-1);
		for(int i=0;i<mEdgNum;i++)
		{
			int p1=getPosition(edges[i].start);
			int p2=getPosition(edges[i].end);
			if(fn.find(vexs[p1])!=fn.find(vexs[p2]))
			{
				list.add(edges[i]);
				fn.union(vexs[p1],vexs[p2]);
			}
		}
		print(list);
	}
	
	public void print(LinkedList<EData> list)
	{
		int sum=0;
		while(list.size()!=0)
		{
			sum+=list.getLast().weight;
			System.out.print("("+list.getLast().start+","+list.pollLast().end+")"+" ");
		}
		System.out.println("Kruskal����СȨ�أ�"+sum);
	}
	
	//�鲢����
	public void sort(EData[] edges,int low,int high)
	{
		if(low<high)
		{
			int mid=(low+high)/2;
			sort(edges,low,mid);
			sort(edges,mid+1,high);
			merge(edges,low,mid,high);
		}
	}
	
	public void merge(EData[] edges,int low,int mid,int high)
	{
		int p1=low;
		int p2=mid+1;
		EData[] arr=new EData[high-low+1];
		for(int i=0;i<high-low+1;i++)
		{
			
		   //˳���ܴ�
		    //if((edges[p1].weight<=edges[p2].weight)&&p1<=mid&&p2<=high||p2==high+1)
			if((p1<=mid&&p2<=high&&edges[p1].weight<=edges[p2].weight)||p2==high+1)
				arr[i]=edges[p1++];
			else
				arr[i]=edges[p2++];
		}
		System.arraycopy(arr,0,edges,low,high-low+1);
	}
	
	public static void main(String[] args)
	{
		 char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        EData[] edges = {
                   // ��� �յ� Ȩ
            new EData('A', 'B', 12), 
            new EData('A', 'F', 16), 
            new EData('A', 'G', 14), 
            new EData('B', 'C', 10), 
            new EData('B', 'F',  7), 
            new EData('C', 'D',  3), 
            new EData('C', 'E',  5), 
            new EData('C', 'F',  6), 
            new EData('D', 'E',  4), 
            new EData('E', 'F',  2), 
            new EData('E', 'G',  8), 
            new EData('F', 'G',  9), 
        };
		ListUDG1 pG;
        pG = new ListUDG1(vexs,edges);
		pG.mstKruskal();
		pG.mstPrim('A');
	}
	
	
/**
  * void union(FibHeap other) 
  * FibNode removeMin()
  * int minimum() ������Сkeyֵ  
  * void update(int oldkey, int newkey)
  * boolean contains(int key)
  * void remove(int key)
  * void destroy()
  * void print() 
  * void decrease(FibNode node, int key)
  */
	
static class FibHeap {

	private int keyNum;         // ���нڵ������
	private FibNode min;	    // ��С�ڵ�(ĳ����С�ѵĸ��ڵ�)

	static class FibNode {
		int key;			// �ؼ���(��ֵ)
    	int degree;			// ����
		FibNode left;		// ���ֵ�
		FibNode right;		// ���ֵ�
		FibNode child;		// ��һ�����ӽڵ�
		FibNode parent;		// ���ڵ�
        boolean marked;     // �Ƿ�ɾ����һ������
		VNode vex;
		VNode p;

		public FibNode(int key,VNode vex) {
			this.key    = key;
			this.degree = 0;
			this.marked = false;
			this.left   = this;
			this.right  = this;
			this.parent = null;
			this.child  = null;
			this.vex=vex;
			this.p=null;
		}
	}

	public FibHeap() {
		this.keyNum = 0;
		this.min = null;
	}

	/* 
	 * ��node��˫�����Ƴ�
	 */
	private void removeNode(FibNode node) {
		node.left.right = node.right;
		node.right.left = node.left;
	}
	 
	/*
	 * ��node�ѽ�����root���֮ǰ(ѭ��������)
	 *   a ���� root
	 *   a ���� node ���� root
	*/
	private void addNode(FibNode node, FibNode root) {
		node.left        = root.left;
		root.left.right  = node;
		node.right       = root;
		root.left        = node;
	}
	 
	/*
	 * ���ڵ�node���뵽쳲���������
	 */
	public void insert(FibNode node) {
		if (keyNum == 0)
			min = node;
		else {
			addNode(node, min);
			if (node.key < min.key)
				min = node;
		}

		keyNum++;
		node.vex.isHeap=true;
	}
	
	
	
	
	 
    /*
     * ��˫������b���ӵ�˫������a�ĺ���
     */
    private void catList(FibNode a, FibNode b) {
        FibNode tmp;

        tmp           = a.right;
        a.right       = b.right;
        b.right.left  = a;
        b.right       = tmp;
        tmp.left      = b;
    }

	/*
	 * ��other�ϲ�����ǰ����
	 */
	public void union(FibHeap other) {
		if (other==null)
			return ;

		if((this.min) == null) {                // this��"��С�ڵ�"
			this.min = other.min;
			this.keyNum = other.keyNum;
			other = null;
		} else if((other.min) == null) {        // this��"��С�ڵ�" && other��"��С�ڵ�"
			other = null;
		} else {                                // this��"��С�ڵ�" && other��"��С�ڵ�"
			// ��"other�и�����"��ӵ�"this"��
            catList(this.min, other.min) ;

			if (this.min.key > other.min.key)
				this.min = other.min;
			this.keyNum += other.keyNum;
			other = null;;
		}
	}

	/*
	 * ��"�ѵ���С���"�Ӹ��������Ƴ���
	 * ����ζ��"����С�ڵ���������"�Ӷ����Ƴ�!   �Ƴ�������
	 */
	private FibNode extractMin() {
		FibNode p = min;

		if (p == p.right)
			min = null;
		else {
			removeNode(p);
			min = p.right;
		}
		p.left = p.right = p;
		return p;
	}
	 
	/*
	 * ��node���ӵ�root����� nodeΪһ����
	 */
	private void link(FibNode node, FibNode root) {
		// ��node��˫�������Ƴ�
		removeNode(node);
		// ��node��Ϊroot�ĺ���
		if (root.child == null)
			root.child = node;
		else
			addNode(node, root.child);

		node.parent = root;
		root.degree++;
		node.marked = false;
	}
	 
	/* 
	 * �ϲ�쳲������ѵĸ�������������ͬ��������
	 */
	private void consolidate() {
        // ����log2(keyNum)��floor��ζ������ȡ����
        // ex. log2(13) = 3������ȡ��Ϊ4��
		int maxDegree = (int) Math.floor(Math.log(keyNum) / Math.log(2.0));
		int D = maxDegree + 1;
		FibNode[] cons = new FibNode[D+1];

		for (int i = 0; i < D; i++)
			cons[i] = null;
	 
		// �ϲ���ͬ�ȵĸ��ڵ㣬ʹÿ����������Ψһ
		while (min != null) {
			FibNode x = extractMin();			// ȡ�����е���С��(��С�ڵ����ڵ���)
			int d = x.degree;						// ��ȡ��С���Ķ���
			// cons[d] != null����ζ����������(x��y)��"����"��ͬ��
			while (cons[d] != null) {
				FibNode y = cons[d];				// y��"��x�Ķ�����ͬ����" 
				if (x.key > y.key) {	// ��֤x�ļ�ֵ��yС
					FibNode tmp = x;
					x = y;
					y = tmp;
				}

				link(y, x);	// ��y���ӵ�x��
				cons[d] = null;
				d++;
			}
			cons[d] = x;
		}
		min = null;
	 
		// ��cons�еĽ�����¼ӵ�������
		for (int i=0; i<D; i++) {

			if (cons[i] != null) {
				if (min == null)
					min = cons[i];
				else {
					addNode(cons[i], min);
					if ((cons[i]).key < min.key)
						min = cons[i];
				}
			}
		}
	}
	 
	/*
	 * �Ƴ���������С�ڵ�
	 */
	public FibNode removeMin() {
		if (min==null)
			return null;

		FibNode m = min;
	    // ��minÿһ������(���ӺͶ��ӵ��ֵ�)����ӵ�"쳲������ѵĸ�����"��
		while (m.child != null) {
			FibNode child = m.child;

			removeNode(child);
			if (child.right == child)
				m.child = null;
			else
				m.child = child.right;

			addNode(child, min);
			child.parent = null;
		}

		// ��m�Ӹ��������Ƴ�
		removeNode(m);
		// ��m�Ƕ���Ψһ�ڵ㣬�����öѵ���С�ڵ�Ϊnull��
		// �������öѵ���С�ڵ�Ϊһ���ǿսڵ�(m.right)��Ȼ���ٽ��е��ڡ�
		if (m.right == m)
			min = null;
		else {
			min = m.right;
			consolidate();
		}
		keyNum--;
		m.vex.isHeap=false;
		return m;
	}

	/*
	 * ��ȡ쳲�����������С��ֵ��ʧ�ܷ���-1
	 */
	public int minimum() {
		if (min==null)
			return -1;

		return min.key;
	}
	  
	
	 
	 private void renewDegree(FibNode parent) {
		parent.degree--;
	}
	
	/* 
	 * ��node�Ӹ��ڵ�parent���������а��������
	 * ��ʹnode��Ϊ"�ѵĸ�����"�е�һԱ��
	 */
	private void cut(FibNode node, FibNode parent) {
		removeNode(node);
		renewDegree(parent);
		// nodeû���ֵ�
		if (node == node.right) 
			parent.child = null;
		else 
			parent.child = node.right;

		node.parent = null;
		node.left = node.right = node;
		node.marked = false;
		// ��"node������"��ӵ�"������"��
		addNode(node, min);
	}

	/* 
	 * �Խڵ�node����"��������"
	 *
	 * �������У������С��Ľ���ƻ�����С�����ʣ�
	 *     �����������(��������˫��������ɾ��������
	 *     ����뵽����С�����ڵ��γɵ�˫��������)��
	 *     Ȼ���ٴ�"���нڵ�ĸ��ڵ�"�����������ڵ�ݹ�ִ�м�����֦
	 */
	private void cascadingCut(FibNode node) {
		FibNode parent = node.parent;

		if (parent != null) {
			if (node.marked == false) 
				node.marked = true;
			else {
				cut(node, parent);
				cascadingCut(parent);
			}
		}
	}

	/* 
	 * ��쳲��������нڵ�node��ֵ����Ϊkey
	 */
	public void decrease(FibNode node, int key) {
		if (min==null ||node==null) 
			return ;

		if (key > node.key) {
        	System.out.printf("decrease failed: the new key(%d) is no smaller than current key(%d)\n", key, node.key);
			return ;
		}

		FibNode parent = node.parent;
		node.key = key;
		if (parent!=null && (node.key < parent.key)) {
			// ��node�Ӹ��ڵ�parent�а������������node��ӵ���������
			cut(node, parent);
			cascadingCut(parent);
		}

		// ������С�ڵ�
		if (node.key < min.key)
			min = node;
	}

	/*
	 * ����С��root�в��Ҽ�ֵΪkey�Ľڵ�
	 */
	private FibNode search(FibNode root, int key) {
		FibNode t = root;	// ��ʱ�ڵ�
		FibNode p = null;	// Ҫ���ҵĽڵ�

		if (root==null)
			return root;

		do {
			if (t.key == key) {
				p = t;
				break;
			} else {
				if ((p = search(t.child, key)) != null) 
					break;
			}
			t = t.right;
		} while (t != root);

		return p;
	}
	 
	

	/*
	 * ɾ�����node
	 */
	private void remove(FibNode node) {
		int m = min.key;
		decrease(node, m-1);
		removeMin();
	}

	
	/* 
	 * ����쳲�������  ��node��������������ȫ����ֵΪnull
	 */
	private void destroyNode(FibNode node) {
		if(node == null)
			return;

		FibNode start = node;
		do {
			destroyNode(node.child);
			// ����node������nodeָ����һ��
			node = node.right;
			node.left = null;
		} while(node != start);
	}
	 
	public void destroy() {
		destroyNode(min);
	}

	/*
	 * ��ӡ"쳲�������"
	 *
	 * ����˵����
	 *     node       -- ��ǰ�ڵ�
	 *     prev       -- ��ǰ�ڵ��ǰһ���ڵ�(���ڵ�or�ֵܽڵ�)
	 *     direction  --  1����ʾ��ǰ�ڵ���һ������;
	 *                    2����ʾ��ǰ�ڵ���һ���ֵܽڵ㡣
	 */
	private void print(FibNode node, FibNode prev, int direction) {
		FibNode start=node;

		if (node==null)
			return ;
		do {
			if (direction == 1)
				System.out.printf("%8d(%d) is %2d's child\n", node.key, node.degree, prev.key);
			else
				System.out.printf("%8d(%d) is %2d's next\n", node.key, node.degree, prev.key);

			if (node.child != null)
				print(node.child, node, 1);

			// �ֵܽڵ�
			prev = node;
			node = node.right;
			direction = 2;
		} while(node != start);
	}

	public void print() {
		if (min==null)
			return ;
		
		int i=0;
		FibNode p = min;
		System.out.printf("== 쳲������ѵ���ϸ��Ϣ: ==\n");
		do {
			i++;
			System.out.printf("%2d. %4d(%d) is root\n", i, p.key, p.degree);

			print(p.child, p, 1);
			p = p.right;
		} while (p != min);
		System.out.printf("\n");
	}
}
}



class FindUnion<T>
{
	static class Node<T>
	{
		T t;
		int rank;
		Node<T> parent;
		public Node(){}
		public Node(T t)
		{
			this.t=t;
		}
	}	
	
	public FindUnion(){}
	
	public void set(Node<T> node)
	{
		node.parent=node;
		node.rank=0;
	}
	
	public Node<T> find(Node<T> node)
	{
		 if(node.parent!=node)
			 node.parent=find(node.parent);
		 return node.parent;
	}
	
	public void union(Node<T> node1,Node<T> node2)
	{
		link(find(node1),find(node2));
	}
	
	private void link(Node<T> node1,Node<T> node2)
	{
		if(node1.rank>node2.rank)
		{
			node2.parent=node1;
		}
		else 
		{
			node1.parent=node2;
			if(node1.rank==node2.rank)
				node2.rank+=1;
		}
	}
}

