public class HashTable
{
	public static void main(String[] args)
	{
		HashTable1<Integer,String> hst=new HashTable1<>();
		Node<Integer,String> node1=new Node<>(1,"hello");
		Node<Integer,String> node2=new Node<>(2,"I");
		Node<Integer,String> node3=new Node<>(3,"am");
		Node<Integer,String> node4=new Node<>(4,"from");
		Node<Integer,String> node5=new Node<>(4,"qushi");
		Node<Integer,String> node6=new Node<>(4,"the");
		Node<Integer,String> node7=new Node<>(4,"out");
		Node<Integer,String> node8=new Node<>(7,"side");
		Node<Integer,String> node9=new Node<>(8,"we");
		Node<Integer,String> node10=new Node<>(8,"dont");
		hst.insert(node1);
		hst.insert(node2);
		hst.insert(node3);
		hst.insert(node4);
		hst.insert(node5);
		hst.insert(node6);
		hst.insert(node7);
		hst.insert(node8);
		hst.insert(node9);
		hst.insert(node10);
		//�����ظ�keyֵ��Ԫ�ػ����NULLPOINTEXCEPTION
		hst.delete(node4);
		hst.delete(node1);
		System.out.println(hst.search(node5));
		System.out.println(hst.search(node4));
		hst.print();
	}
}
class HashTable1<K,V>
{
	int length=15;//Ϊ�˾��������ͻ lengthֵ��Ҫ��lengthֵ��index�����йأ�
	//����ʱ���о��� �����ֲ�֪�����������������Ҫ��������ʱ��ô��
	@SuppressWarnings(value="unchecked")
	private Node<K,V>[]  nodeArr=(Node<K,V>[])new  Node[length];
	public HashTable1(){}
	public void insert(Node<K,V> node)
	{
		int index=computeIndex(node.key);
		if(nodeArr[index]==null)
			nodeArr[index]=node;
		//��������ͻ
		else
		{
			Node<K,V> tempnode=nodeArr[index];
			node.next=tempnode;
			nodeArr[index]=node;
			tempnode.prev=node;
		}	
	}
	public V search(Node<K,V> node)
	{
		
		int index=computeIndex(node.key);
		Node<K,V> tempnode=nodeArr[index];	
		while(tempnode!=null)
		{
			if(tempnode.value.equals(node.value))
				return tempnode.value;
			tempnode=tempnode.next;
		}
		return null;
	}
	public void delete(Node<K,V> node)
	{
		
		int index=computeIndex(node.key);
		Node<K,V> tempnode=nodeArr[index];
		//����NullPointerException
		/*
		while(tempnode!=null)
		{
			if(node.value.equals(tempnode.value))
				{
					tempnode.prev.next=tempnode.next;
					break;
				}
			tempnode=tempnode.next;
		}
		*/
		while(tempnode!=null)
		{
			if(node.value.equals(tempnode.value))
				{
				//��������� ��ֹ���ֿ�ָ���쳣
				Node<K,V> pre=tempnode.prev;
				Node<K,V> nex=tempnode.next;
					if(pre==null&&nex==null)
						nodeArr[index]=null;
					else
					{
						if(pre==null)
						{
							nodeArr[index]=tempnode.next;
							nodeArr[index].prev=null;
						}
						else if(nex==null)
						{
							tempnode.prev=tempnode;
						}
						else
							tempnode.prev.next=tempnode.next;
					}
					break;
				}
			tempnode=tempnode.next;
		}	
	
	}
	public void print()
	{
		for(int i=0;i<length;i++)
		{
			Node<K,V> tempnode=nodeArr[i];
			while(tempnode!=null)
			{
				System.out.println(tempnode.key+" "
						+tempnode.value);
				tempnode=tempnode.next;
			}
		}

	}
	//�����������е�����
	public int computeIndex(K key)
	{
		//ɢ�к�������
		//return key.hashCode()&(length-1);
		return (key.hashCode()*11)%length;
	}
}
 class Node<K,V>
{
	final K key;
	V value;
	Node<K,V> prev;
	Node<K,V> next;
	public Node(K key, V value)
	{
		this.key=key;
		this.value=value;
	}
}
 
