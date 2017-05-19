public class SearchTree
{
	public static void main(String[] args)
	{
		SearchTree1<String> tree=new SearchTree1<>();
		Node<String> node1=new Node<>(5,"wo");
		Node<String> node2=new Node<>(4,"shi");
		Node<String> node3=new Node<>(6,"da");
		Node<String> node4=new Node<>(3,"yi");
		Node<String> node5=new Node<>(2,"ba");
		Node<String> node6=new Node<>(7,"lang");
		Node<String> node7=new Node<>(8,"dai");
		Node<String> node8=new Node<>(1,"shui");
		Node<String> node9=new Node<>(9,"chi");
		Node<String> node10=new Node<>(10,"shi");
		tree.insert(node1);
		tree.insert(node2);
		tree.insert(node3);
		tree.insert(node4);
		tree.insert(node5);
		tree.insert(node6);
		tree.insert(node7);
		tree.insert(node8);
		tree.insert(node9);
		tree.insert(node10);
		tree.inOrder(node1);
		System.out.println();
		System.out.println(tree.preducessor(node3));
		System.out.println(tree.successor(node3));
		System.out.println(tree.max(node1));
		System.out.println(tree.min(node1));
		System.out.println(tree.search(node2));
		tree.delete(node5);
		tree.inOrder(node1);
	}
}
class SearchTree1<T>
{
	private Node<T> root;
	public SearchTree1(){}
	public Node<T> search(Node<T> node)
	{
		Node<T> tempnode=root;
		while(node!=null)
		{
			if(tempnode.key<node.key)
				tempnode=tempnode.r;
			else if(tempnode.key>node.key)
				tempnode=tempnode.l;
			else
				return tempnode;
		}
		return null;
	}	
	public void insert(Node<T> node)
	{
		Node<T> tempnode=root;
		boolean flag=false;
		boolean flag1=false;
		Node<T> tp=null;
		while(tempnode!=null)
		{
			//Node<T> tp=tempnode; 错误  tp值只在循环内有效
			tp=tempnode;
			if(tempnode.key==node.key)
			{
				if(flag)
					{
						tempnode=tempnode.l;
						flag=false;
					}
				else
					{
						tempnode=tempnode.r;
						flag=true;
					}
			}
			else if(tempnode.key>node.key)
				tempnode=tempnode.l;
			else
				tempnode=tempnode.r;
		}
		node.p=tp;
		if(tp==null)
		{
			root=node;
		}
		else if(tp.key==node.key)
		{
			if(flag1)
			{
				tp.l=node;
				flag1=false;
			}
			else
			{
				tp.r=node;
				flag1=true;
			}
		}
		else if(tp.key>node.key)
			tp.l=node;
		else 
			tp.r=node;	
			
	}
	public void delete(Node<T> node)
	{
		if(node.l==null)
			transplant(node,node.r);
		else if(node.r==null)
			transplant(node,node.l);
		else
		{
			Node<T> tempnode=min(node.r);
			if(tempnode.p!=node)
			{
				transplant(tempnode,tempnode.r);
				tempnode.r=node.r;
				tempnode.r.p=tempnode;
			}
			transplant(node,tempnode);
			tempnode.l=node.l;
			tempnode.l.p=tempnode;
		}
	}
	public Node<T> max(Node<T> node)
	{
		Node<T> tempnode=node;
		Node<T> max=null;
		while(tempnode!=null)
		{
			max=tempnode;
			tempnode=tempnode.r;
		}
		return max;
			
	}
	public Node<T> min(Node<T> node)
	{
		Node<T> tempnode=node;
		Node<T> min=null;
		while(tempnode!=null)
		{
			min=tempnode;
			tempnode=tempnode.l;
		}
		return min;
	}
	public Node<T> preducessor(Node<T> node)
	{
		if(node.l!=null)
			return max(node.l);
		else
		{
			Node<T> tp=node.p;
			while(tp!=null&&node==tp.l)
			{
				node=tp;
				tp=node.p;
			}
			return tp;
		}
	}
	public Node<T> successor(Node<T> node)
	{
		if(node.r!=null)
			return min(node.r);
		else
		{
			Node<T> tp=node.p;
			while(tp!=null&&node==tp.r)
			{
				node=tp;
				tp=node.p;
			}
			return tp;
		}
		
	}
	public void transplant(Node<T> u,Node<T> v)
	{
		if(u.p==null)
			u=v;
		else if(u==u.p.l)
			u.p.l=v;
		else
			u.p.r=v;
		if(v!=null)
			v.p=u.p;
	}
	public void inOrder(Node<T> node)
	{
		
		if(node!=null)
		{
			inOrder(node.l);
			System.out.print(node+"  ");
			inOrder(node.r);
		}
	}
}
class Node<T> 
{
	int key;
	T t;
	Node<T> p;
	Node<T>	l;
	Node<T> r;
	public Node(){}
	public Node(int key,T t)
	{
		this.key=key;
		this.t=t;
	}
	public String toString()
	{
		return key+" "+t;
	}
}

