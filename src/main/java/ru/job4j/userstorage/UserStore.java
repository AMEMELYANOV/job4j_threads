package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    final private Map<Integer, User> users = new HashMap<>();

    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), new User(user.getId(), user.getAmount())) == null;
    }

    public synchronized boolean update(User user) {
        return users.replace(user.getId(), new User(user.getId(), user.getAmount())) != null;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User src = users.get(fromId);
        User dst = users.get(toId);
        if (src == null || dst == null || src.getAmount() < amount) {
            System.out.println("Check source, destination or amount");
        } else {
            src.setAmount(src.getAmount() - amount);
            dst.setAmount(dst.getAmount() + amount);
        }
    }

    @Override
    public synchronized String toString() {
        return "UserStore{"
                + "users="
                + users
                + '}';
    }
}

class Test {
    public static void main(String[] args) {
        UserStore storage = new UserStore();
        User user = new User(1, 100);
        System.out.println(storage.add(user));
        System.out.println(storage.add(new User(2, 200)));
        System.out.println(storage.add(new User(2, 200)));
        user.setAmount(1000);
        System.out.println(storage);

        User replaceUser = new User(2, 400);
        System.out.println(storage.update(new User(1, 200)));
        System.out.println(storage.update(replaceUser));
        System.out.println(storage.update(new User(3, 400)));
        replaceUser.setAmount(2000);
        System.out.println(storage);

        storage.transfer(1, 2, 100);
        System.out.println(storage);
        storage.transfer(1, 2, 500);
        storage.transfer(3, 2, 100);
        storage.transfer(2, 3, 100);

        storage.delete(new User(1, 100));
        storage.delete(new User(2, 500));
        System.out.println(storage);
    }
}