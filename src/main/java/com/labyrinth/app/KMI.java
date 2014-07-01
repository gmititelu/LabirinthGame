package com.labyrinth.app;

import java.util.Arrays;

class KMI {
	 
	    
	    public static int solution(int[] A) {
	        Arrays.sort(A);
	        
	        int min = Integer.MAX_VALUE;
	        
	        for (int i = 0; i < A.length; i++) {
	        	int toSearch = -1 * A[i];
	        	int found = bSearch(A, toSearch);
	        	
	        	if (min > Math.abs(found + A[i])) {
	        		min = Math.abs(found + A[i]); 
	        	}	        	
	        }	       
	        return min;
	    }
	    
	    private static int bSearch(int[] A, int val) {
	    	int l = 0;
	    	int r = A.length - 1;
	    	
	    	while (l < r) {
	    		int mid = (l + r) / 2;
	    		if (A[mid] < val) {
	    			l = mid + 1;
	    		}
	    		else {
	    			r = mid;
	    		}
	    	}
	    	return A[l];
	    }

	public static void main(String args[]) {
		int A[] = {-8, 4, 5, -10, 3};
		
		System.out.println(solution(A));
	}
}
