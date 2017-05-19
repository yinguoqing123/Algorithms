
public class LinkedListInteger{
	public static void main(String[] args)
	{
		LinkedList1<Integer> lkl=new LinkedList1<>();
		lkl.add(1 );
		lkl.add(2 );
		lkl.add(3 );
		lkl.add(4 );
		lkl.add(5 );
		lkl.print();
		lkl.delete(3 );
		lkl.print();
		System.out.println(lkl.sercher(2));
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
