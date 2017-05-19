import java.util.*;
public class GoodSelect
{
	public static void main(String[] args)
	{
		int[] arr={1,2,34,4,2,9,8,7,6,5,4,7,88,9};
		//分组取中位数的中位数
		System.out.println(good_select(arr,0,13,6));
		System.out.println(good_select(arr,0,13,2));
		System.out.println(good_select(arr,0,13,3));
		
	}
	//没有指针交换不方便 
	private static void  exchange(int[] arr,int a,int b)
	{
		int temp;
		temp=arr[a];
		arr[a]=arr[b];
		arr[b]=temp;
	}
	//k是好的划分所选择的privot点的值 即中位数的中位数 
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
		return j+1;//j+1 是数组中的索引
	}
	//好的选择  返回第i小的数
	private static int good_select(int[] arr,int low,int high,int i)
	{
		int k;
		//基准情形 防止无限递归调用
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
	//插入排序  返回第k小的数  
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
	//寻找中位数的中位数
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