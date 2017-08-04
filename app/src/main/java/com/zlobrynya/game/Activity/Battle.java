package com.zlobrynya.game.Activity;


import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zlobrynya.game.AdapterBattleList;
import com.zlobrynya.game.R;
import com.zlobrynya.game.classObject.Gladiator;

import java.util.ArrayList;
import java.util.Random;

public class Battle extends AppCompatActivity {

    private final int FACE_DICE = 6;
    private int countDice = 4;
    private int walks = 0;
    private ArrayList<Gladiator> gladiators;
    private ArrayList<String> text;
    private AdapterBattleList adapterBattleList;
    private HelperText helperText;
    private ProgressBar enemyHP;
    private ProgressBar heroHP;
    private BattleGo battleGo;
    private ListView listBattleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        text = new ArrayList<>();

        adapterBattleList = new AdapterBattleList(this,R.layout.adapter_list_battle,text);
        listBattleText = (ListView) findViewById(R.id.listTextBattle);
        listBattleText.setAdapter(adapterBattleList);

        gladiators = new ArrayList<>();
        gladiators.add(new Gladiator());
        gladiators.add(new Gladiator());
        walks = getRandom(2);
        generationEnemyAttributes(gladiators.get(0));
        generationEnemyAttributes(gladiators.get(1));
        enemyHP = (ProgressBar) findViewById(R.id.progressHpEnemy);
        heroHP = (ProgressBar) findViewById(R.id.progressHpHero);
        enemyHP.setMax(gladiators.get(1).getStamina());
        enemyHP.setProgress(gladiators.get(1).getStamina());
        heroHP.setMax(gladiators.get(0).getStamina());
        heroHP.setProgress(gladiators.get(0).getStamina());

        helperText = new HelperText();
        battleGo = new BattleGo();
        battleGo.execute();
    }

    @Override
    protected void onDestroy() {
        battleGo.cancel(false);
        super.onDestroy();
    }

    private void setCharacteristics(Gladiator gladiator, String name){
        String[] strings = new String[6];
        strings[0] = "Сила: " + gladiator.getStrength();
        strings[1] = "Выносливость: " + gladiator.getStamina();
        strings[2] = "Защита: " + gladiator.getProtection();
        strings[3] = "Точность: " + gladiator.getAccuracy();
        strings[4] = "Блокирование: " + gladiator.getBlocking();
        strings[5] = "Уклонение: " + gladiator.getEvasion();

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Характиристики " + name);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dialog_item);
        arrayAdapter.addAll(strings);
        builderSingle.setAdapter(arrayAdapter,null);
        builderSingle.show();
    }

    public void clickEnemy(View view) {
        setCharacteristics(gladiators.get(1),"противника");
    }

    public void clickHero(View view) {
        setCharacteristics(gladiators.get(0),"героя");
    }

    private class HelperText{
        private void addText(String s){
            text.add(s);
            adapterBattleList.notifyDataSetChanged();
            listBattleText.setSelection(listBattleText.getCount() - 1);
        }

        void outputDamage(int damage){
            String whoAttack = "";
            String whoProtect = "";
            int hp = 0;
            if (walks == 0){
                whoAttack = "Герой нанес: ";
                whoProtect = "У противника осталось: ";
                hp = gladiators.get(1).getStamina();
                enemyHP.setProgress(hp);
            }else {
                whoAttack = "Противник нанес: ";
                whoProtect = "У героя осталось: ";
                hp = gladiators.get(0).getStamina();
                heroHP.setProgress(hp);
            }
            addText(whoAttack + damage + " урона." + whoProtect + hp + " здоровья.");
        }

        void outputMiss(){
            String textMiss;
            if (walks == 0){
                textMiss = "Герой промахнулся по противнику.";
            }else {
                textMiss = "Противник промахнулся по герою.";
            }
            addText(textMiss);
        }

        void outputBlock(){
            String textMiss;
            if (walks == 0){
                textMiss = "Противник смог заблокировать удар героя.";
            }else {
                textMiss = "Герой успешно заблокировал удар противника.";
            }
            addText(textMiss);
        }

        void outputEvasion(){
            String textMiss;
            if (walks == 0){
                textMiss = "Противник смог уклониться от удара героя.";
            }else {
                textMiss = "Герой успешно уклонился от удара противника.";
            }
            addText(textMiss);
        }

        void outputAttackResult(boolean dead){
            String textDead;
            if (dead){
                if (walks == 0){
                    textDead = "Противник погибает.";
                }else {
                    textDead = "Герой погибает.";
                }
            }else {
                if (walks == 0){
                    textDead = "Противник еще стоит на ногах.";
                }else {
                    textDead = "Герой еще стоит на ногах.";
                }
            }
            addText(textDead);
        }
    }

    private void generationEnemyAttributes(Gladiator enemy){
        enemy.addAccuracy(getRandom(2*FACE_DICE));
        enemy.addBlocking(getRandom(countDice*FACE_DICE));
        enemy.addEvasion(getRandom(countDice*FACE_DICE));
        enemy.addProtection(getRandom(15));
        enemy.addStamina(getRandom(countDice*FACE_DICE)+500);
        enemy.addStrength(getRandom(countDice*FACE_DICE));
    }

    private int getRandom(int max){
        Random rand = new Random();
        return rand.nextInt(max);
    }

    private int sumValueDice(int value){
        int sum = 0;
        for (int i = 0; i < value;i++){
            sum += getRandom(countDice*FACE_DICE);
        }
        return sum;
    }

    private class BattleGo extends AsyncTask<Void, String, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            boolean battle = true;
            while (battle){
                if (isCancelled())
                    break;
                Gladiator gg = gladiators.get(walks);
                Gladiator enemy = gladiators.get(1 - walks);
                if (getRollCheck(gg.getAccuracy())){
                    int totalProtected = 0;
                    int num = getRandom(2);
                    Log.i("Random", String.valueOf(num));
                    int valueProtected = 0;
                    if (walks == 0){
                        if (num == 0){
                            valueProtected = gg.getEvasion();
                        }else {
                            valueProtected = gg.getBlocking();
                        }
                    }else {
                        if (num == 0){
                            valueProtected = enemy.getEvasion();
                        }else {
                            valueProtected = enemy.getBlocking();
                        }
                    }
                    totalProtected = gg.getSumTotalProtection(valueProtected);
                    if (getRollCheck(totalProtected)){
                        int strength = gg.getStrength();
                        int countDice = strength/10;
                        int attack = strength % 10;
                        attack += gg.getAttackBonus();
                        attack += sumValueDice(countDice);
                        attack -= enemy.getProtection();
                        if (attack < 0){
                            attack = 0;
                        }
                        enemy.editStamina(attack);
                        publishProgress("Damage",String.valueOf(attack));
                        if (enemy.getStamina() <= 0){
                            battle = false;
                        }
                        publishProgress("Result", String.valueOf(!battle));
                    }else {
                        if (num == 0){
                            publishProgress("Evasion");
                        }else {
                            publishProgress("Block");
                        }
                    }
                }else {
                    publishProgress("Miss");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Передача хода другому персонажу
                walks = 1 - walks;
            }
            return null;
        }

        private boolean getRollCheck(int value){
            int sumDice = sumValueDice(countDice);
            return value <= sumDice;
        }

        protected void onProgressUpdate(String... progress) {
            String name = progress[0];
            if (name.contains("Damage")){
                helperText.outputDamage(Integer.parseInt(progress[1]));
            }else if (name.contains("Miss")){
                helperText.outputMiss();
            }else if (name.contains("Block")){
                helperText.outputBlock();
            }else if (name.contains("Result")){
                helperText.outputAttackResult(Boolean.parseBoolean(progress[1]));
            }else if (name.contains("Evasion")){
                helperText.outputBlock();
            }
        }
    }
}
