package model.api;

import java.util.LinkedList;

/** Used for Buffer Queue, Synchronized method.
        * @Author Viktor Johansson
*/

public class Buffer<T> {
    private LinkedList<T> buffer = new LinkedList<T>();

    public synchronized void put(T obj) {
        buffer.addLast(obj);
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while(buffer.isEmpty()) {
            wait();
        }
        return buffer.removeFirst();
    }

    /** Method not used, but is kept due to possibility of further development.
     * @return size
     */
    public int size() {
        return buffer.size();
    }
}
