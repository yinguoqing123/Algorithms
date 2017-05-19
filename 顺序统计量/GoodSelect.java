import java.util.*;
public class GoodSelect
{
	public static void main(String[] args)
	{
		int[] arr={1,2,34,4,2,9,8,7,6,5,4,7,88,9};
		//����ȡ��λ������λ��
		System.out.println(good_select(arr,0,13,6));
		System.out.println(good_select(arr,0,13,2));
		System.out.println(good_select(arr,0,13,3));
		
	}
	//û��ָ�뽻�������� 
	private static void  exchange(int[] arr,int a,int b)
	{
		int temp;
		temp=arr[a];
		arr[a]=arr[b];
		arr[b]=temp;
	}
	//k�ǺõĻ�����ѡ���privot���ֵ ����λ������λ�� 
	private static int good_partition(int[] arr,int low,int high,int key)
	{
		int i=low;
		while(arr[i]!=key)
			i++;
		exchange(arr,i,high);
		int j=low-1;
		for(int k=low;k<high;k++)
		{
			if(arr[k]<arr[high])
			{
				j++;
				exchange(arr,j,k);
			}
		}
		exchange(arr,j+1,high);
		return j+1;//j+1 �������е�����
	}
	//�õ�ѡ��  ���ص�iС����
	private static int good_select(int[] arr,int low,int high,int i)
	{
		int k;
		//��׼���� ��ֹ���޵ݹ����
		if(high-low+1<=5)
		{
			k=insert_Sort(arr,low,high,i);
			return k;
		}
		else 
		{
			k=find_Median(arr,low,high);
			int q=good_partition(arr,low,high,k);
			int p=q-low+1;
			if(p==i)
				return arr[q];
			else if(p<i)
				return good_select(arr,q+1,high,i-p);
			else
				return good_select(arr,low,q-1,i);
		}
	}
	//��������  ���ص�kС����  
	private static int insert_Sort(int[] arr,int low,int high,int k)
	{
		for(int i=low+1;i<=high;i++)
		{
			int j=i-1;
			int key=arr[i];
			while(j>=low&&arr[j]>key)
			{
				arr[j+1]=arr[j];
				j--;
			}
			arr[j+1]=key;
		}
		return arr[low+k-1];
	}
	//Ѱ����λ������λ��
	private static int find_Median(int[] arr,int low,int high)
	{
		int begin=-1;
		int end;
		int j=0;
		int ret;
		int[] arr1=new int[(high-low+1)/5+1];
		for(int i=low;i<=high;i++)
		{
			if((i-low)%5==0)
				begin=i;
			else if((i-low)%5==4||i==high)
			{
				end=i;
				arr1[j++]=insert_Sort(arr,begin,end,(end-begin+2)/2);
			}
		}
		ret=good_select(arr1,0,j-1,(j+1)/2);
		return ret;
	}
}