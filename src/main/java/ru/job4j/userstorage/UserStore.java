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
        boolean rsl = false;
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), new User(user.getId(), user.getAmount()));
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (users.containsKey(user.getId())) {
            users.replace(user.getId(), new User(user.getId(), user.getAmount()));
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
            User src = users.get(fromId);
            User dst = users.get(toId);
            if (src == null) {
                System.out.println("Source of transfer not found");
            } else if (dst == null) {
                System.out.println("Destination of transfer not found");
            } else if (src.getAmount() < amount) {
                System.out.println("Not enough money for transfer");
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
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));

        storage.update(new User(1, 200));
        storage.update(new User(2, 400));
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