import java.util.Scanner;
//除了树形结构外 还可以用链表表示  数组表示
public class FindUnion<T>
{
	private static class Node<T>
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
	public static void main(String[] args)
	{
		FindUnion<String> fu=new FindUnion<>();
		System.out.printf("input string number:");
		int n=fu.readInt();
		Node[] node=new Node[n];
		for(int i=0;i<n;i++)
		{
			System.out.printf("input number(%d)",i);
			System.out.println();
			System.out.printf("input string:");
			String str=fu.read();
			node[i]=new Node<>(str);
			fu.set(node[i]);
		}
		System.out.printf("合并次数:");
		int t=fu.readInt();
		for(int i=0;i<t;i++)
		{
			System.out.printf("输入要合并的对象序号:");
			int p=fu.readInt();
			int q=fu.readInt();
			fu.union(node[p],node[q]);
		}
		for(int i=0;i<n;i++)
		{
			System.out.println(node[i].t+"的老大是"+fu.find(node[i]).t);
		}
	}
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
	private String read()
	{
		Scanner sc=new Scanner(System.in);
		String str=(String)sc.next();
		return str;
	}
	private int readInt()
	{
		Scanner sc=new Scanner(System.in);
		 return sc.nextInt();
		
	}
}