package com.nastra.algorithms.permutation;

import java.util.ArrayList;
import java.util.List;

public class HeapPermute {

	private List<String>permutations= new ArrayList<String>();
	
	public List<String> getPermutations(){
		return permutations;
	}
	public void permute(String input ,int n){
		if(n==1){
			permutations.add(input);
		}
		else {
			for(int i=0;i<n ;i++){
				permute(input ,n-1);
				if(n %2==1){
					swap(input.toCharArray(),0,n-1);
					
			}
				else {
					swap(input.toCharArray(), i, n - 1);
					
				}
		}
		}
		
	}
	private void swap(char[] charArray, int i, int j) {
		char t= charArray[i];
		charArray[i]= charArray[j];
		charArray[j]= t;
		
		
	}
	public void permute(String input){
		if(null==input && "".equals(input)){
			return;
		}
		permutations.clear();
		permute(input,input.length());
	}
	
	
}
