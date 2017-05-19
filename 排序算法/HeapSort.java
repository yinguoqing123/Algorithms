public class HeapSort
{
	public static void main(String[] args)
	{
		int[] arr={1,4,2,6,5,9,7};
		sort(arr);
		for(int i:arr)
		{
			System.out.print(i+" ");
		}
		
	}
	public static void maxHeapify(int[] arr,int i,int heapsize)
	{
		int l=0;
		l=2*i+1;
		int r=0;
		r=2*i+2;
		int largest=i;
		if(l<heapsize&&arr[l]>arr[i])
		{
			largest=l;
		}
		if(r<heapsize&&arr[r]>arr[largest])
			largest=r;
		if(largest!=i)
		{
			int temp=arr[largest];
			arr[largest]=arr[i];
			arr[i]=temp;
			maxHeapify(arr,largest,heapsize);
		}
	}
	public static void buildMaxHeap(int[] arr)
	{
		for(int i=arr.length/2;i>=0;i--)
		{
			maxHeapify(arr,i,arr.length);
		}
	}
	public static void sort(int[] arr)
	{
		buildMaxHeap(arr);
		for(int i=arr.length-1;i>=1;i--)
		{
			int temp;
			temp=arr[0];
			arr[0]=arr[i];
			arr[i]=temp;
			maxHeapify(arr,0,i);
		}
	}
}