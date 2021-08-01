package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    final private List<User> users = new ArrayList<>();

    public synchronized boolean add(User user) {
        User currentUser = findById(user.getId());
        if (currentUser != null) {
            return false;
        }
        return users.add(new User(user.getId(), user.getAmount()));
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        for (User currentUser : users) {
            if (currentUser.getId() == user.getId()) {
                currentUser.setAmount(user.getAmount());
                rsl = true;
                break;
           }
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        return users.remove(user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
            User src = findById(fromId);
            User dst = findById(toId);
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

    private synchronized User findById(int id) {
        User rsl = null;
        for (User current : users) {
            if (current.getId() == id) {
                rsl = current;
                break;
            }
        }
        return rsl;
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

        storage.transfer(1, 2, 100);

        System.out.println(storage);

        storage.delete(new User(1, 100));
        storage.delete(new User(2, 500));
        System.out.println(storage);

    }
}