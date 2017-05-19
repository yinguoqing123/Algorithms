import java.util.*;
public class ListUDG1
{
	private static int INF=Integer.MAX_VALUE;
	
	//点
	private class VNode
	{
		char data;
		ENode firstEdge;//对应的链表
		
		boolean isHeap; //Prim算法判断是否点在堆中
		
	}
	
	//边和连接表的统一
	private class ENode
	{
		int ivex;//终点
		int weight;
		ENode nextEdge; //构成链表
	}
	
	private int mEdgNum;   //边的数量
	private VNode[] mVexs; //顶点数组
	private EData[] edges;
	
	//边
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
	
	//直接根据传入的数组创建图
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
            // 将node2链接到"p2所在链表的末尾"
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

	
	//两个算法都是基于贪心策略
	
	//斐波那契堆实现
	//节点间的关系过于混乱
	public void mstPrim(char begin)
	{
		int max=Integer.MAX_VALUE;
		int vlen=mVexs.length;
		int elen=edges.length;
		int index=getPosition(begin);
		int totalWeight=0;
		
		//建堆
		FibHeap  fh=new FibHeap();
		
		//建立顶点和堆节点一一对应的关系  即知道了顶点就知道了在堆中的堆节点
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
			
			//堆节点 边节点 顶点节点间的关系？？？
			//tmp.ivex 终点在顶点数组中的下标
			while(tmp!=null)
			{
				if(mVexs[tmp.ivex].isHeap&&tmp.weight<fn[tmp.ivex].key)
				
				{
					fn[tmp.ivex].p=m.vex;
				
					//堆节点的key值只能通过decrease改变
					//fn[tmp.ivex].key=tmp.weight;
					fh.decrease(fn[tmp.ivex],tmp.weight);
				}
				tmp=tmp.nextEdge;
			}
			System.out.println();
		}
		
		System.out.println("Prim算法得出的总权重是："+totalWeight);
	}
	
	//用并查集实现
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
		System.out.println("Kruskal总最小权重："+sum);
	}
	
	//归并排序
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
			
		   //顺序不能错
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
                   // 起点 终点 权
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
  * int minimum() 返回最小key值  
  * void update(int oldkey, int newkey)
  * boolean contains(int key)
  * void remove(int key)
  * void destroy()
  * void print() 
  * void decrease(FibNode node, int key)
  */
	
static class FibHeap {

	private int keyNum;         // 堆中节点的总数
	private FibNode min;	    // 最小节点(某个最小堆的根节点)

	static class FibNode {
		int key;			// 关键字(键值)
    	int degree;			// 度数
		FibNode left;		// 左兄弟
		FibNode right;		// 右兄弟
		FibNode child;		// 第一个孩子节点
		FibNode parent;		// 父节点
        boolean marked;     // 是否被删除第一个孩子
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
	 * 将node从双链表移除
	 */
	private void removeNode(FibNode node) {
		node.left.right = node.right;
		node.right.left = node.left;
	}
	 
	/*
	 * 将node堆结点加入root结点之前(循环链表中)
	 *   a …… root
	 *   a …… node …… root
	*/
	private void addNode(FibNode node, FibNode root) {
		node.left        = root.left;
		root.left.right  = node;
		node.right       = root;
		root.left        = node;
	}
	 
	/*
	 * 将节点node插入到斐波那契堆中
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
     * 将双向链表b链接到双向链表a的后面
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
	 * 将other合并到当前堆中
	 */
	public void union(FibHeap other) {
		if (other==null)
			return ;

		if((this.min) == null) {                // this无"最小节点"
			this.min = other.min;
			this.keyNum = other.keyNum;
			other = null;
		} else if((other.min) == null) {        // this有"最小节点" && other无"最小节点"
			other = null;
		} else {                                // this有"最小节点" && other有"最小节点"
			// 将"other中根链表"添加到"this"中
            catList(this.min, other.min) ;

			if (this.min.key > other.min.key)
				this.min = other.min;
			this.keyNum += other.keyNum;
			other = null;;
		}
	}

	/*
	 * 将"堆的最小结点"从根链表中移除，
	 * 这意味着"将最小节点所属的树"从堆中移除!   移除的是树
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
	 * 将node链接到root根结点 node为一颗树
	 */
	private void link(FibNode node, FibNode root) {
		// 将node从双链表中移除
		removeNode(node);
		// 将node设为root的孩子
		if (root.child == null)
			root.child = node;
		else
			addNode(node, root.child);

		node.parent = root;
		root.degree++;
		node.marked = false;
	}
	 
	/* 
	 * 合并斐波那契堆的根链表中左右相同度数的树
	 */
	private void consolidate() {
        // 计算log2(keyNum)，floor意味着向上取整！
        // ex. log2(13) = 3，向上取整为4。
		int maxDegree = (int) Math.floor(Math.log(keyNum) / Math.log(2.0));
		int D = maxDegree + 1;
		FibNode[] cons = new FibNode[D+1];

		for (int i = 0; i < D; i++)
			cons[i] = null;
	 
		// 合并相同度的根节点，使每个度数的树唯一
		while (min != null) {
			FibNode x = extractMin();			// 取出堆中的最小树(最小节点所在的树)
			int d = x.degree;						// 获取最小树的度数
			// cons[d] != null，意味着有两棵树(x和y)的"度数"相同。
			while (cons[d] != null) {
				FibNode y = cons[d];				// y是"与x的度数相同的树" 
				if (x.key > y.key) {	// 保证x的键值比y小
					FibNode tmp = x;
					x = y;
					y = tmp;
				}

				link(y, x);	// 将y链接到x中
				cons[d] = null;
				d++;
			}
			cons[d] = x;
		}
		min = null;
	 
		// 将cons中的结点重新加到根表中
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
	 * 移除并返回最小节点
	 */
	public FibNode removeMin() {
		if (min==null)
			return null;

		FibNode m = min;
	    // 将min每一个儿子(儿子和儿子的兄弟)都添加到"斐波那契堆的根链表"中
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

		// 将m从根链表中移除
		removeNode(m);
		// 若m是堆中唯一节点，则设置堆的最小节点为null；
		// 否则，设置堆的最小节点为一个非空节点(m.right)，然后再进行调节。
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
	 * 获取斐波那契堆中最小键值；失败返回-1
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
	 * 将node从父节点parent的子链接中剥离出来，
	 * 并使node成为"堆的根链表"中的一员。
	 */
	private void cut(FibNode node, FibNode parent) {
		removeNode(node);
		renewDegree(parent);
		// node没有兄弟
		if (node == node.right) 
			parent.child = null;
		else 
			parent.child = node.right;

		node.parent = null;
		node.left = node.right = node;
		node.marked = false;
		// 将"node所在树"添加到"根链表"中
		addNode(node, min);
	}

	/* 
	 * 对节点node进行"级联剪切"
	 *
	 * 级联剪切：如果减小后的结点破坏了最小堆性质，
	 *     则把它切下来(即从所在双向链表中删除，并将
	 *     其插入到由最小树根节点形成的双向链表中)，
	 *     然后再从"被切节点的父节点"到所在树根节点递归执行级联剪枝
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
	 * 将斐波那契堆中节点node的值减少为key
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
			// 将node从父节点parent中剥离出来，并将node添加到根链表中
			cut(node, parent);
			cascadingCut(parent);
		}

		// 更新最小节点
		if (node.key < min.key)
			min = node;
	}

	/*
	 * 在最小堆root中查找键值为key的节点
	 */
	private FibNode search(FibNode root, int key) {
		FibNode t = root;	// 临时节点
		FibNode p = null;	// 要查找的节点

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
	 * 删除结点node
	 */
	private void remove(FibNode node) {
		int m = min.key;
		decrease(node, m-1);
		removeMin();
	}

	
	/* 
	 * 销毁斐波那契堆  将node所在链表级及以下全部赋值为null
	 */
	private void destroyNode(FibNode node) {
		if(node == null)
			return;

		FibNode start = node;
		do {
			destroyNode(node.child);
			// 销毁node，并将node指向下一个
			node = node.right;
			node.left = null;
		} while(node != start);
	}
	 
	public void destroy() {
		destroyNode(min);
	}

	/*
	 * 打印"斐波那契堆"
	 *
	 * 参数说明：
	 *     node       -- 当前节点
	 *     prev       -- 当前节点的前一个节点(父节点or兄弟节点)
	 *     direction  --  1，表示当前节点是一个左孩子;
	 *                    2，表示当前节点是一个兄弟节点。
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

			// 兄弟节点
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
		System.out.printf("== 斐波那契堆的详细信息: ==\n");
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

