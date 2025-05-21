package wood.mike.datastructures;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * A LinkedList is a linear data structure where elements (called nodes) are stored in separate objects, and each node points to the next (and sometimes previous) one.
 * Java's LinkedList is a doubly linked list, meaning each node has pointers to both previous and next elements.
 * Use LinkedList when you:
 *  Add/remove elements frequently at the beginning or end
 *  Don't need fast random access
 */
public class LinkedListStructure {
    public static void main(String[] args) {
        LinkedList<Integer> numbers = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5));
        LinkedList<Integer> reversed = reverse(numbers);
        System.out.println(reversed); // should print [5, 4, 3, 2, 1]

        System.out.println(isPalindrome(new LinkedList<>(Arrays.asList('r','a','c','e','c','a','r')))); // true
        System.out.println(isPalindrome(new LinkedList<>(Arrays.asList('h','e','l','l','o'))));         // false
        System.out.println(isPalindrome(new LinkedList<>(Arrays.asList('h','a','n','n','a','h'))));
    }

    public static LinkedList<Integer> reverse(LinkedList<Integer> list) {
        LinkedList<Integer> reversed = new LinkedList<>();
        ListIterator<Integer> iterator = list.listIterator(list.size());

        while (iterator.hasPrevious()) {
            reversed.add(iterator.previous());
        }

        return reversed;
    }

    /**
     * Time Complexity: O(n) ✅
     * Space Complexity: O(1) ✅ (no extra data structures used)
     */
    public static boolean isPalindrome(LinkedList<Character> list) {
        ListIterator<Character> forward = list.listIterator();
        ListIterator<Character> backward = list.listIterator(list.size());

        int half = list.size() / 2;
        for (int i = 0; i < half; i++) {
            Character front = forward.next();
            Character back = backward.previous();
            if (!front.equals(back)) {
                return false;
            }
        }
        return true;
    }

}
