package leetcode.test0201to0250;

import java.util.HashMap;
import java.util.Map;

public class Leetcode219 {
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        for(int i = 0;i<nums.length;i++) {
        	if(map.containsKey(nums[i])) {
        		if(i-map.get(nums[i])<=k) {
        			return true;
        		}
        		map.put(nums[i],i);
        	}else {
        		map.put(nums[i],i);
        	}
        }
        
        return false;
    }
}
