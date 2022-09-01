package com.pomodoro;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class PomodoroTimer {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Введите время работы, время отдыха, количество циклов, множитель времени работы:");
        var cmd = new Scanner(System.in).nextLine().split(" ");

        int workTime = 1;
        int breakTime=1;
        int iteration = 1;
        int mult = 1;
        boolean isHelp = false;

        for (int i = 0; i < cmd.length; i++) {
            switch (cmd[i]){
                case "-w" -> workTime = Integer.parseInt(cmd[++i]);
                case "-b" -> breakTime = Integer.parseInt(cmd[++i]);
                case "-count" -> iteration = Integer.parseInt(cmd[++i]);
                case "-m" -> mult = Integer.parseInt(cmd[++i]);
                case "--help" -> {
                    System.out.println("""
                        \nPomodoro - это приложение для улучшения личной эффективности
                        -w - сколько работать
                        -b - сколько отдыхать
                        -count - количество циклов
                        -m - множитель для времени работы
                        --help - вызвать помощь
                        """);
                    isHelp = true;
                }
            }
        }
        if (isHelp) return;
        long startTimer = System.currentTimeMillis();
        progression(workTime, breakTime, iteration, mult);
        long endTime = System.currentTimeMillis();

        System.out.println("Таймер работал " + (endTime-startTimer)/(1000*60) + " min.");
    }

    /**********************************************************************/
    public static void timer(int workTime, int breakTime) throws InterruptedException {
        printProgress("Work progress bar ", workTime, 30);
        printProgress("Break progress bar ", breakTime, 30);
    }

    /**********************************************************************/
    private static void printProgress(String process, int time, int size) throws InterruptedException {
        int length;
        int rep;
        length = 60 * time / size;
        rep = 60 * time / length;
        int stretchb = size / (3 * time);
        for (int i = 1; i <= rep; i++) {
            double x = i;
            x = 1.0 / 3.0 * x;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            double w = time * stretchb;
            double percent = (x / w) * 1000;
            x /= stretchb;
            x *= 10;
            x = Math.round(x);
            x /= 10;
            percent = Math.round(percent);
            percent /= 10;
            System.out.print(process + percent + "% " + (" ").repeat(5 - (String.valueOf(percent).length())) + "[" + ("#").repeat(i) + ("-").repeat(rep - i) + "]    ( " + x + "min / " + time + "min )" + "\r");
            if (true) {
                TimeUnit.SECONDS.sleep(length);
            }
        }
    }
/**********************************************************************/
    private static void progression(int workTime, int breakTime, int iteration, int mult) throws InterruptedException {
        timer(workTime, breakTime);
        for (int i = 2; i <= iteration; i++) {
            timer(workTime *= mult, breakTime);

        }
    }
}
