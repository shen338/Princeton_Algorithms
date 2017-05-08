public class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        Stack<ListNode> stack = new Stack<>();
        for(int i = 0;i<k;i++){
            stack.push(head);
            head = head.next;
        }

        ListNode temp = head;
        while(temp.next != null){
            temp = temp.next;
        }

        for(int j = 0;j<k;j++){
            temp.next = stack.pop();
            temp = temp.next;
        }

        return head;
    }
    public class ListNode{
        int val;
        ListNode next;
        ListNode(int x) { val = x;}
    }
    public static void main(String[] args) {
    }

}