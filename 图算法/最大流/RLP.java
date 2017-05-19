/* bug总结：链表删除时，考虑删除最后一个元素 防止出现空指针异常
			INF这样做 程序错误会导致  溢出


*/

public class RLP
{
	static int INF = Integer.MAX_VALUE;
	static LinkedList1<Vertex> list = new LinkedList1<Vertex>();
	
	public static void buildGList(Graph graph)
	{
		int len = graph.vexs.length;
		for(int i=1;i<len-1;i++)
			list.add(graph.vexs[i]);
		for(int i=1;i<len-1;i++)
		{
			for(int j=0;j<len;j++)
			{
				if(graph.edges[i][j])
					graph.vexs[i].list.add(graph.vexs[j]);
			}
			for(int j=0;j<len;j++)
			{
				if(graph.edges[j][i])
					graph.vexs[i].list.add(graph.vexs[j]);
			}
		}
		for(int i=1;i<len-1;i++)
		{
			graph.vexs[i].current = graph.vexs[i].list.getFirst();
		}
	}
	
	
	public static void push(Graph graph, Vertex from, Vertex to)
	{
		 //from是一个溢出节点  残存流大于0  from.height = to.height + 1
		int res;
		if(graph.edges[from.id][to.id])
		{
			res = graph.capacity[from.id][to.id] - graph.flow[from.id][to.id];
		}
		else if(graph.edges[to.id][from.id])
		{
			res = graph.flow[to.id][from.id];
		}
		
		else
		{
			System.out.println("不存在这样的残存流");
			return ;
		}
			
		int delta = (res>from.extra ? from.extra : res);
		
		if(graph.edges[from.id][to.id])
			graph.flow[from.id][to.id] += delta;
		else 
			graph.flow[to.id][from.id] -= delta;
		
		from.extra -= delta;
		to.extra += delta;
	}
	
	//判断边 from->to 是不是残存网络中的边 
	public static boolean isReu(Graph graph, Vertex from, Vertex to)
	{
		if(graph.edges[from.id][to.id])
		{
			if(graph.capacity[from.id][to.id]-graph.flow[from.id][to.id]>0)
				return true;
			
		}
		
		else if(graph.edges[to.id][from.id]&&graph.flow[to.id][from.id]>0)
			return true;	
		
		
		return false;
	}
	
	
	public static void relabel(Graph graph, Vertex vex)
	{
		// vex是溢出节点  vex的高度<=在残存网络中所有的出边指向的点的高度
		int m = INF;
		for(int i=0;i<graph.edges.length;i++)
		{
			if(graph.edges[vex.id][i])
			{
				if(isReu(graph,vex,graph.vexs[i]))
					m = (m>graph.vexs[i].height ? graph.vexs[i].height : m);
			}
			if(graph.edges[i][vex.id])
			{
				if(isReu(graph,vex,graph.vexs[i]))
					m = (m>graph.vexs[i].height ? graph.vexs[i].height : m);
			}
			
		}
		vex.height = 1 + m;
	}
	
	public static void init_preflow(Graph graph, Vertex begin)
	{
		int len =graph.vexs.length;
		for(int i=0;i<len;i++)
		{
			graph.vexs[i].height = 0;
			graph.vexs[i].extra = 0;
		}
		for(int i=0;i<len;i++)
		{
			for(int j=0;j<len;j++)
			{
				if(graph.edges[i][j])
					graph.flow[i][j] = 0;
			}
		}
		begin.height = len;
		for(int i=0;i<len;i++)
		{
			if(graph.edges[begin.id][i])
			{
				graph.flow[begin.id][i] = graph.capacity[begin.id][i];
				graph.vexs[i].extra = graph.capacity[begin.id][i];
				begin.extra -= graph.capacity[begin.id][i];
			}
		}
		
	}
	
	public static void discharge(Graph graph, Vertex u)
	{
		while(u.extra>0)
		{
			Vertex v=u.current;
			if(v==null)
			{
				relabel(graph, u);
				u.current = u.list.getFirst();
			}
			else if(isReu(graph,u,v)&&u.height==v.height+1)
				push(graph,u,v);
			else
				u.current = u.list.next(v);
		}
		
	}
	
	public static void relabel_front(Graph graph, Vertex begin, Vertex end)
	{
		buildGList(graph);
		init_preflow(graph, begin);
		Vertex u = list.getFirst();
		while(u!=null)
		{
			int height=u.height;
			discharge(graph, u);
			if(u.height>height)
			{
				list.delete(u);
				list.add(u);
			}
			u = list.next(u);
			
		}
	}
	
	public static void main(String[] args)
	{
		Vertex[] vexs=new Vertex[6];
		for(int i=0;i<6;i++)
		{
			vexs[i] = new Vertex(i);
		}
		boolean[][] edges=new boolean[6][6];
		edges[0][1]=true;
		edges[1][2]=true;
		edges[2][5]=true;
		edges[0][3]=true;
		edges[3][4]=true;
		edges[4][2]=true;
		edges[4][5]=true;
		edges[3][1]=true;
		edges[2][3]=true;
		
		int[][] capacity=new int[6][6];
		capacity[0][1]=16;
		capacity[1][2]=12;
		capacity[2][5]=20;
		capacity[0][3]=13;
		capacity[3][4]=14;
		capacity[4][2]=7;
		capacity[4][5]=4;
		capacity[3][1]=4;
		capacity[2][3]=9;
		
		int[][] flow=new int[6][6];
		
		Graph graph = new Graph(vexs, edges, flow, capacity);
		relabel_front(graph, graph.vexs[0], graph.vexs[5]);
	    
		
		//buildGList(graph);
		
		//System.out.println(list.getFirst());
		
		
		for(int i=0;i<6;i++)
		{
			for(int j=0;j<6;j++)
			{
				System.out.print(graph.flow[i][j]+"  ");
			}
			System.out.println();
		}
		
	}
}
class Graph
{
	Vertex[] vexs;
	boolean[][] edges;
	int[][] capacity;
	int[][] flow;
	Graph(Vertex[] vexs, boolean[][] edges,  int[][] flow, int[][] capacity)
	{
		this.vexs=vexs;
		this.flow=flow;
		this.edges=edges;
		this.capacity=capacity;
	}
}
class Vertex
{
	int id;
	int height;
	int extra;  //溢出流量
	LinkedList1<Vertex> list; 
	Vertex current;
	Vertex(int id)
	{
		this.id=id;
		list = new LinkedList1<>();
	}
	public String toString()
	{
		return id+" ";	}
}



//重新写个链表
class LinkedList1<T>{
	private Node<T> first;
	private int size;
	
	public LinkedList1(){}
	public  void add(T t)
	{
		Node<T> f=first;
		Node<T> newnode=new Node<>(null,t,f);
		first=newnode;
		if(f!=null)
		{
			f.prev=newnode;
		}
		size++;
	}
	
	public T getFirst()
	{
		return first.key;
	}
	
	public T next(T t)
	{
		Node<T> nownode=first;
		for(int i=0;i<size;i++)
		{
			if(!t.equals(nownode.key))
			{
				nownode=nownode.next;
				
			}
		}
		nownode = nownode.next;
		if(nownode != null)
			return nownode.key;
		else 
			return null;
	}
	
	
	public void delete(Object t)
	{
		Node<T> nownode=first;
		for(int i=0;i<size;i++)
		{
			if(!t.equals(nownode.key))
			{
				nownode=nownode.next;
				
			}
			else
			{
				if(nownode.next==null)
				{
					nownode.prev.next=null;
					
				}
				
				
				else
				{
					nownode.prev.next=nownode.next;
					nownode.next.prev=nownode.prev;
					size--;
					nownode = null;
					
				}
				
				break;
			}
		}
	}
	public int sercher(Object t)
	{
		Node<T> nownode=first;
		int index=-1;
		for(int i=0;i<size;i++)
		{
			if(t.equals(nownode.key))
			{
				index=i;
				return index;
			}
			else
			{
				nownode=nownode.next;
			}
		}
		return -1;
	}
	public void print()
	{
		Node<T> nownode=first;
		for(int i=0;i<size;i++)
		{
			System.out.print(nownode.key+"  ");
			nownode=nownode.next;
		}
		System.out.println();
	}
}
  class Node<T>
{
	T key;
	Node<T> prev;
	Node<T> next;
	public Node(Node<T> prev,T t,Node<T> next)
	{
		this.prev=prev;
		this.key=t;
		this.next=next;
	}
}

