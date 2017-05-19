public class MergeSort
{
	public static void main(String[] args)
	{	
		//�ݹ�����������Ҫ�ݹ麯��merge_sort
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
		// ��ԭַ���� 
		// ԭַ�����κ�ʱ����Ҫ�����������Ԫ�ش洢�ռ�洢��ʱ����
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
	//first last�ֱ�������Ŀ�ʼ�ͽ�������
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