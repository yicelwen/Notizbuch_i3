package w0146_LRU_Cache;

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    class Node {
        int key, value;
        Node pre, next;
        Node (int k, int v) {
            this.key = k;
            this.value = v;
        }
    }
    // Dummy nodes
    Node head, tail;

    // Add the node to the head of the list
    private void add(Node node) {
        node.pre = head;
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
    }
    
    // Remove the node from the current position  [3]->[2]->[1]
    private void remove(Node node){
        node.pre.next = node.next; // 3的next指針不指向2,改指向2的下一個1
        node.next.pre = node.pre;  // 1的pre指針不指向2,改指向3
    }
    
    // 1. 從當前位置刪除 2.加到頭部
    private void update(Node node) {
        remove(node);
        add(node);
    }
    
    
                //當前 cache set , 最大容量
    private int count, capacity;
    private Map<Integer, Node> map; // 存取 key 和 node 之間的對應關係
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        count = 0;
        map = new HashMap<>();
        head = new Node (0,0);
        tail = new Node(0,0);
        head.next = tail;  // 初始化此cache空間(dummy head-tail)
        tail.next = head;
    }
    
    public int get(int key) {
        Node node = map.get(key); 
        // Not in cache
        if (node == null) return -1;
        // Update the node
        // remove and add to the head
        update(node); // remove and then add to list head
        return node.value;
    }
    
    public void put(int key, int value) {
        Node node = map.get(key);
        // Not in cache
        if(node == null) {
            // Create the node
            node = new Node(key, value);
            add(node);
            map.put(key, node);
            count++;
        }else{
            // Already in cache, just update
            node.value = value;
            update(node);
        }
        
        // Reach to the capacity, remove from tail
        if(count > capacity) {
            Node toDelete = tail.pre;
            remove(toDelete);         // 刪除節點的同時...
            map.remove(toDelete.key); // 也要從 map 中刪除
            count--;
        }
    }
    
}


/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */ 



/*
 Cache 
1. 離CPU近，比較快
2. 小，易填滿。如果有新的數據，需要先把舊的踢出去

Cache Replacement Algorithm
Full -> Evict some items. Which one? 要剔除哪些數據?

LRU (least recently used)
Cache: items might be used in the future 存放即將會用到的數據

+ Evict: item may NOT be used for the longest period of time 很長一段時間都不會用到它

+ Item that is least recently used
  1. Fact: the last time (long time ago)
  2. Prediction: the next time (not any time soon)

1. 初始化接受參數  LRUCache(int capacity)
2. int get(int key) 通過 key 在 cache 中找出相對應的 value
    + Exist -> return value
    + Not exit -> return -1
3. void put(int key, int value) 把 key-value 放入到 cache 中
    + Exist -> Update
    + Not exist -> Add to cache
    + Reach capacity -> Evict the least recently used item
4. time complexity
    + get & put method 都要為 O(1) 的操作

#2 Algorithm
1. Time
2. Position: 使用相對位置表示被access相應時間 左(近) <---> 右(遠)
3. How to mark the LRU item? LinkedList.
    + Add
      1. Create the node 建一個node放到頭部
      2. Add to head
    + Access 存取
      1. Remove from current position 從當前位置移除,加到頭部
      2. Add to head
    + Capacity reach 去除掉尾部的 node
      1. Remove from tail
4. How to achieve O(1) for get(int key) method? HashMap<key, Node>
    + Key 數據對應的key
      + The key we put in
    + Value
      + The node of linked list
        + Key, Value, Next 對應到數據KV 以及指針
5. How to achieve O(1) for put(int key, int value)?
    + Linked List
        + Add is O(1)
        + Remove is O(n) - traverse to the node 要從頭一直走到被刪除的點才能刪它
    + Double linked list
        + Remove is O(1) 
        + Key, Value, Next, Prev 
6. Any other details?
    + For boundary nodes, have to do null check while adding and removing
    + To avoid null check, we can use dummy head and tail.
      (head)->(F1)->(F2)->(F3)->(F4)->(tail)
 */

