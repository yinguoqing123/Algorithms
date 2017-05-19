
public class LinkedListString{
	public static void main(String[] args)
	{
		LinkedList1<String> lkl=new LinkedList1<>();
		lkl.add("hello");
		lkl.add("ni");
		lkl.add("hao" );
		lkl.add("haha" );
		lkl.add("niyehao" );
		lkl.print();
		lkl.delete("hao");
		lkl.print();
		System.out.println(lkl.sercher("ni"));
	}	
}

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
	public void delete(Object t)
	{
		Node<T> nownode=first;
		for(int i=0;i<size;i++)
		{
			//注意多态  t运行时状态是Object子类
			if(!t.equals(nownode.key))
			{
				nownode=nownode.next;
				
			}
			else
			{
				nownode.prev.next=nownode.next;
				nownode.next.prev=nownode.prev;
				size--;
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
