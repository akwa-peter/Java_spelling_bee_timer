package PKGTimer;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.TimerTask;

public class Timer {
    private JLabel TFSeconds;
    private JPanel mainWindow;
    private JButton startTimerButton;
    private JLabel timeUpMessage;

    private static final String startTime = "Start Timer";
    private static final String stopTime = "Stop Timer";

    public Timer() {
        startTimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (startTimerButton.getText().equals(startTime)) {
                    timeUpMessage.setVisible(false);
                    startTimerButton.setText(stopTime);
                    toolkit = Toolkit.getDefaultToolkit();
                    timer = new java.util.Timer();
                    timer.schedule(new RemindTask(),
                            0,        //initial delay
                            1 * 1000);  //subsequent rate
                }
                else if (startTimerButton.getText().equals(stopTime)){
                    timer.cancel();
                    startTimerButton.setText(startTime);
                    TFSeconds.setText(""+0);
                }
            }
        });
    }

    public static void main(String[] args){
        JFrame jFrame = new JFrame("TimerApp");
        jFrame.setContentPane(new Timer().mainWindow);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.isAlwaysOnTop();
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setSize(600, 500);
    }

    java.util.Timer timer;
    Toolkit toolkit;

    class RemindTask extends TimerTask {
        int seconds = 0;

        public void run() {
            if (seconds <= 40) {
                toolkit.beep();
                TFSeconds.setText(""+seconds);
                seconds++;
            } else {
                toolkit.beep();
                timer.cancel();
                timeUpMessage.setVisible(true);
                File clap = new File("/home/akwa/IdeaProjects/SpellingBeeTimer/src/alarm-clock.wav");
                playSound(clap);
                startTimerButton.setText(startTime);
            }
        }
    }

    private void playSound(File sound) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.open();
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
