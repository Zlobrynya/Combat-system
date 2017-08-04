package com.zlobrynya.game.classObject;

/**
 * Created by Nikita on 27.06.2017.
 */

public class Gladiator {
    private int strength; //сила
    private int stamina;  //выносливость
    private int protection; //защита
    private int accuracy; //точность
    private int blocking; //блокирование
    private int evasion;  //уклоненеи
    private int attackBonus;
    private int protectionBonus;


    public Gladiator(){
        strength = 0;
        stamina = 0;
        protection = 0;
        accuracy = 0;
        blocking = 0;
        evasion = 0;
        protectionBonus = 0;
        attackBonus = 0;
    }

    public void addAccuracy(int accuracy) {
        this.accuracy += accuracy;
    }

    public void addBlocking(int blocking) {
        this.blocking += blocking;
    }

    public void addEvasion(int evasion) {
        this.evasion += evasion;
    }

    public void addProtection(int protection) {
        this.protection += protection;
    }

    public void addStamina(int stamina) {
        this.stamina += stamina;
    }

    public void addStrength(int strength) {
        this.strength += strength;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getBlocking() {
        return blocking;
    }

    public int getEvasion() {
        return evasion;
    }

    public int getStamina() {
        return stamina;
    }

    public int getStrength() {
        return strength;
    }

    public int getSumTotalProtection(int value){
        return protection + value + protectionBonus;
    }

    public int getAttackBonus(){
        return attackBonus;
    }

    public void editStamina(int damage){
        stamina -= damage;
    }

    public int getProtection() {
        return protection;
    }
}
