//Partition还有一种变形 可以使相同元素过多时，简化运行时间
//将Partition分为小于 等于  大于 三部分
public class QuickSort 
{
	public static int partition(int[] arr,int p,int r)
	{
		int x=arr[r];
		int i=p-1;
		for(int j=p;j<r;j++)
		{
			if(arr[j]<x)
			{
				i++;
				int temp=arr[j];
				arr[j]=arr[i];
				arr[i]=temp;
			}
		}
		int temp=arr[i+1];
		arr[i+1]=arr[r];
		arr[r]=temp;
		return i+1;
	}
	public static void quicksort(int[] arr,int p,int r,int k)
	{
		if(r-p>=k)
		{
			int q=partition(arr,p,r);
			quicksort(arr,p,q-1,k);
			quicksort(arr,q+1,r,k);
		}
		else
			insertsort(arr,p,r);
	}
	public static void insertsort(int[] arr,int p,int r)
	{
		for(int i=p+1;i<=r;i++)
		{
			int key=arr[i];
			int j=i-1;
			while(j>=0&&key<arr[j])
			{
				arr[j+1]=arr[j];
				j--;
			}
			arr[j+1]=key;
		}
	}
	public static void main(String[] args)
	{
		int[] arr={1,5,10,8,9,4,6,2,7,3,14,20,35,48,25,34};
		quicksort(arr,0,15,4);
		for(int i:arr)
		{
			System.out.print(i+" ");
		}
	}
}