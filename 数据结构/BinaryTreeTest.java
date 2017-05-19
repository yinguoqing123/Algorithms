import java.util.*;
public class BinaryTreeTest
{
	public static void main(String[] args)
	{
		Node<String> node1=new Node<>(null,null,null,"a");
		Node<String> node2=new Node<>(null,null,null,"b");
		Node<String> node3=new Node<>(null,null,null,"c");
		Node<String> node4=new Node<>(null,null,null,"d");
		Node<String> node5=new Node<>(null,null,null,"e");
		Node<String> node6=new Node<>(null,null,null,"f");
		node1.leftchide=node2;
		node1.rightchide=node3;
		node2.parent=node1;
		node2.leftchide=node4;
		node2.rightchide=node5;
		node3.parent=node1;
		node3.rightchide=node6;
		node4.parent=node2;
		node5.parent=node2;
		BinaryTree tree=new BinaryTree(node1); 
		tree.preOrder(node1);
		tree.inOrder(node1);
		tree.postOrder(node1);
		tree.nonRecInOrder(node1);
		tree.nonRecPreOrder(node1);
	}
}
class BinaryTree<T>{
	private Node<T> root;
	public BinaryTree(){}
	public BinaryTree(Node<T> node)
	{
		root=node;
	}
	//�ݹ�ʱ��Ҫʹ��ջ�����ֳ� ���ƴ���Ч������
	public void preOrder(Node<T> node)
	{
		if(node==null)
			return;
		System.out.print(node.key+" ");
		preOrder(node.leftchide);
		preOrder(node.rightchide);

	}
	public void inOrder(Node<T> node)
	{
		if(node==null)
			return;
		inOrder(node.leftchide);
		System.out.print(node.key+" ");
		inOrder(node.rightchide);

	}
	public void postOrder(Node<T> node)
	{
		if(node==null)
			return;
		posOrder(node.leftchide);
		posOrder(node.rightchide);
		System.out.print(node.key+" ");

	}
	//ѭ������
	public void nonRecPreOrder(Node<T> node)
	{
		LinkedList<Node<T>> lkl=new LinkedList<>();
		while(node!=null||lkl.size()>0)
		{
			while(node!=null)
			{
				System.out.print(node.key+"  ");
				lkl.push(node);
				node=node.leftchide;
			}
			//node=node.parent.rightchide ����Ҫ����ջ ѭ������Ҫ�� ����
			node=lkl.pop();
			node=node.rightchide;
		}
	}
	public void nonRecInOrder(Node<T> node)
	{
		LinkedList<Node<T>> lkl=new LinkedList<>();
		while(node!=null||lkl.size()>0)
		{
			while(node!=null)
			{
				lkl.push(node);
				node=node.leftchide;
			}
			node=lkl.pop();
			System.out.print(node.key+" ");
			node=node.rightchide;
		}
	}
	/*public void nonRecPostOrder(Node<T> node)
	{
		LinkedList<Node<T>> lkl=new LinkedList<>();
		while(1)//��node=null ����lkl.size��Ϊ0ʱ����ѭ��
		{
			while(node!=null)
			{
				lkl.push(node);
				node=node.leftchide;
			}
			node=lkl.pop();
			lkl.push(node);
			node=node.rightchide;
			LinkedList<Node<T>> lkl1=new LinkedList<>();
			while(node!=null)
			{
				lkl1.push(node);
				node=node.leftchide;
			}
			System.out.print(lkl1.pop()+" ");
			node!=null||lkl.size>0
		}
	}*/
	
}
class Node<T>
{
	//��ʵ�����˸��ڵ� �ǵݹ����ʱ����Ҫջ
	public Node<T> parent;
	public Node<T> leftchide;
	public Node<T> rightchide;
	public T key;;
	//public Node<T>(){} �������в���д����
	public Node(){}
	public Node(Node<T> parent,Node<T> leftchide,Node<T> rightchide,T key)
	{
		this.parent=parent;
		this.leftchide=leftchide;
		this.rightchide=rightchide;
		this.key=key;
	}
}