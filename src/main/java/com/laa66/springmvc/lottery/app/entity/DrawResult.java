package com.laa66.springmvc.lottery.app.entity;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 *
 *  entity class
 *  these instances are only created in lotteryService
 *  they hold generated numbers in lottery
 *
 */
public class DrawResult {
    private int id;
    private Set<Integer> numbers;
    private LocalDateTime date;

    public DrawResult() {

    }

    // populate inner data structure with 6 generated numbers
    public void draw() {
        numbers = new HashSet<>(6, 1);
        SecureRandom secureRandom = new SecureRandom();
        while (numbers.size() < 6) numbers.add(secureRandom.nextInt(100));
        date = LocalDateTime.now();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(Set<Integer> numbers) {
        this.numbers = numbers;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DrawResult{" +
                "id=" + id +
                ", numbers=" + numbers +
                ", date=" + date +
                '}';
    }
}
