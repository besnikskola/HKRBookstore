package sample;

import java.io.Serializable;

public class CartArrayList <T> implements Serializable {
    private T[] array;
    private int size = 0;

    public CartArrayList() {
        this.array = (T[]) new Object[10];
    }

    public void add(T value) {
        if (size == array.length) {
            increaseSize();
        }
        array[size] = value;
        size++;
    }

    private void increaseSize() {
        T[] newArray = (T[]) new Object[size+1];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public void remove(int index) {
        T[] newArray = (T[]) new Object[size-1];
        for (int i = 0, k = 0; i < size; i++) {
            if (i == index) {
                continue;
            }
            newArray[k++] = array[i];
        }
        array = newArray;
        size--;
    }

    public int size() { // Method simply returns the value of the size variable keeping track of stored objects in the array
        return size;
    }

    public T get(int index) {
        try {
            return array[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Index is out of bounds.");
        }
        return null;
    }

    public boolean isEmpty() {  // Method checks if the arraylist contains any objects
        return size == 0;
    }
}

