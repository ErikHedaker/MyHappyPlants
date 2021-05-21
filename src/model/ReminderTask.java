package model;

import controller.Controller;

import javax.management.timer.Timer;

public class ReminderTask implements Runnable {

    private Controller controllerRef;

    public ReminderTask(Controller controllerRef) {
        this.controllerRef = controllerRef;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            controllerRef.sendReminderMessage();
            try {
                Thread.sleep(Timer.ONE_MINUTE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
