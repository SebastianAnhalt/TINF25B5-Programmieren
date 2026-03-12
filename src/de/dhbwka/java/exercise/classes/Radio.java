package de.dhbwka.java.exercise.classes;

/**
 * @author DHBW lecturer
 * @version 1.0
 *
 * Part of lectures on 'Programming in Java'. Baden-Wuerttemberg
 * Cooperative State University.
 *
 * (C) 2018 by J. Sidler, T. Schlachter, C. Schmitt, W. Süß
 */
public class Radio {

    boolean on;
    int volume;
    double frequency;

    public Radio() {
        this(true, 5, 98.4);
    }

    public Radio(boolean on, int volume, double frequency) {
        this.on = on;
        if (volume < 0) {
            this.volume = 0;
        } else if (volume > 10) {
            this.volume = 10;
        } else {
            this.volume = volume;
        }
        this.setFrequency(frequency);
    }

    public void turnOn() {
        this.on = true;
    }

    public void turnOff() {
        this.on = false;
    }

    public void incVolume() {
        if (on && volume<10)
            volume++;
    }

    public void decVolume() {
        if (on && volume>0)
            volume--;
    }

    public void setFrequency(double freq) {
        if (freq >= 85.0 && freq < 110.0)
            this.frequency = freq;
        else
            this.frequency = 99.9; // standard freq.
    }

    @Override
    public String toString() {
        String an_aus = this.on ? "an" : "aus";
        return "Radio " + an_aus + "; Lautstärke " + this.volume + "; Frequenz " + this.frequency + " MHz";
    }

    public static void main(String[] args) {
        Radio radio = new Radio(false, 7, 93.5);
        System.out.println(radio);
        radio.turnOn();
        System.out.println(radio);
        radio.incVolume();
        radio.incVolume();
        System.out.println(radio);
        radio.incVolume();
        radio.incVolume();
        System.out.println(radio);
        radio.decVolume();
        System.out.println(radio);
        radio.setFrequency(97.8);
        System.out.println(radio);
        radio.setFrequency(112.7);
        System.out.println(radio);
        radio.turnOff();
        System.out.println(radio);
    }

}
