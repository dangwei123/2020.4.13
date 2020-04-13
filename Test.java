给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。
class Solution {
    public int findDuplicate(int[] nums) {
        int fast=nums[0];
        int slow=nums[0];
        do{
            fast=nums[fast];
            fast=nums[fast];
            slow=nums[slow];
        }while(fast!=slow);
        fast=nums[0];
        while(fast!=slow){
            fast=nums[fast];
            slow=nums[slow];
        }
        return fast;
    }
}

给定一个未排序的整数数组，找出最长连续序列的长度。

要求算法的时间复杂度为 O(n)。
class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set=new HashSet<>();
        for(int num:nums){
            set.add(num);
        }
        int res=0;
        for(int num:nums){
            int cur=num-1;
            int count=1;
            while(set.contains(cur)){
                count++;
                cur--;
            }
            cur=num+1;
            while(set.contains(cur)){
                cur++;
                count++;
            }
            res=Math.max(res,count);
        }
        return res;
    }
}

设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近十条推文。你的设计需要支持以下的几个功能：

postTweet(userId, tweetId): 创建一条新的推文
getNewsFeed(userId): 检索最近的十条推文。每个推文都必须是由此用户关注的人或者是用户自己发出的。推文必须按照时间顺序由最近的开始排序。
follow(followerId, followeeId): 关注一个用户
unfollow(followerId, followeeId): 取消关注一个用户

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/design-twitter
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
class Twitter {
    private static int timestamp=0;
    private static class Passage{
        private int id;
        private int time;
        private Passage next;
        public Passage(int id,int time){
            this.id=id;
            this.time=time;
        }
    }

    private static class User{
        private int id;
        private Set<Integer> followed;
        private Passage head;
        public User(int id){
            this.id=id;
            followed=new HashSet<>();
            head=null;
            followed.add(id);
        }

        private void follow(int followId){
            followed.add(followId);
        }
        private void unfollow(int followedId){
            if(followedId!=this.id){
                followed.remove(followedId);
            }
        }
        private void post(int tweID){
            Passage twe=new Passage(tweID,timestamp);
            timestamp++;
            twe.next=head;
            head=twe;
        }
    }
    /** Initialize your data structure here. */
    private Map<Integer,User> map=new HashMap<>();
    public Twitter() {

    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId) {
        if(!map.containsKey(userId)){
            map.put(userId,new User(userId));
        }
        map.get(userId).post(tweetId);
    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res=new ArrayList<>();
        if(!map.containsKey(userId)) return res;
        Set<Integer> set=map.get(userId).followed;
        PriorityQueue<Passage> queue=new PriorityQueue<>(set.size(),(a,b)->(b.time-a.time));
        for(int i:set){
            Passage p=map.get(i).head;
            if(p==null) continue;
            queue.offer(p);
        }
        while(!queue.isEmpty()){
            if(res.size()==10){
                break;
            }
            Passage p=queue.poll();
            res.add(p.id);
            if(p.next!=null){
                queue.offer(p.next);
            }
        }
        return res;
    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!map.containsKey(followerId)){
            map.put(followerId,new User(followerId));
        }
        if(!map.containsKey(followeeId)){
            map.put(followeeId,new User(followeeId));
        }
        map.get(followerId).follow(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(map.containsKey(followerId)){
            map.get(followerId).unfollow(followeeId);
        }
    }

    
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
 
 