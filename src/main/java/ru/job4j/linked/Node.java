package ru.job4j.linked;

public final class Node<T> {
    final private Node<T> next;
    final private T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }

}