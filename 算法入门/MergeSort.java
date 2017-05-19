public class MergeSort
{
	public static void main(String[] args)
	{	
		//递归排序，首先需要递归函数merge_sort
		int[] arr=new int[] {7,6,5,4,3,2,1};
		sort(arr,3,6);	
		for(int i:arr)
		{
			System.out.println(i);
		}
	}
	public static void merge(int[] arr,int low1,int low2,int high2)
	{
		final int p=low1,q=low2-1,len=high2-low1+1;
		// 非原址排序 
		// 原址排序：任何时候需要常数个额外的元素存储空间存储临时数据
		int[] arr3=new int[len];
		for(int k=0;k<len;)
		{
			if((low1<=q&&low2<=high2&&arr[low1]<=arr[low2])||low2>high2)
			{
					arr3[k++]=arr[low1++];
			}
			else
			{
					arr3[k++]=arr[low2++];
			}
		}
		System.arraycopy(arr3,0,arr,p,len);
	}
	//first last分别是排序的开始和结束索引
	public static void sort(int[] arr,int first,int last)
	{
		if(first<last)
		{
			int middle=(first+last)/2;
			sort(arr,first,middle);
			sort(arr,middle+1,last);
			merge(arr,first,middle+1,last);
		}
	}
}