package boardgame.logic.movement;

import boardgame.components.entity.Competitor;
import boardgame.logic.mediator.EntityMediator;

import java.util.Random;

public class Movement {

    private Competitor owner;
    private EntityMediator mediator;

    private int minSpeed;
    private int maxSpeed;
    private int lapsDone;
    private int maxLapsDone;

    public Movement(int minSpeed, int maxSpeed, Competitor owner) {
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.owner = owner;
        maxLapsDone = 0;
    }


    public MoveResult move(int steps, MovementType type) {
        MoveResult result = new MoveResult();
        result.setFromFieldId(owner.getFieldId());
        result.setType(type);
        int newField = calculateNewPosition(steps, result);
        result.saveDistance(newField);
        executeMove(result);
        return result;
    }

    private int calculateNewPosition(int steps, MoveResult result) {
        switch (result.getType()) {
            case EVENT:
                return result.getFromFieldId() + steps;
            case RANDOM:
                Random random = new Random();
                result.setRollResult(random.nextInt(maxSpeed - minSpeed +1) + minSpeed);
                return result.getFromFieldId() + result.getRollResult();
            default:
                return -1;
        }
    }

    private void executeMove(MoveResult result) {
        int stepsToMove = result.getDistance();
        int position = result.getFromFieldId();
        int boardSize = mediator.getBoardSize();

        while (stepsToMove != 0) {
            if (stepsToMove + position > boardSize-1) {
                position -= boardSize;
                lapsDone++;
                result.addLap();
            } else if(position+ stepsToMove<0){
                position += boardSize;
                lapsDone--;
                result.substractLap();
            } else {
                position += stepsToMove;
                stepsToMove = 0;
            }
        }

        owner.setFieldId(position);
        result.setToFieldId(position);
    }

    public int addLapsReward(int coinsPerLapReward) {
        int lapsDiference = lapsDone - maxLapsDone;
        int reward = 0;
        if (lapsDiference > 0) {
           addMaxLapsDone(lapsDiference);
            reward = coinsPerLapReward * lapsDiference;
//            System.out.println("Reached new max lap! " + reward + " coins got and adding");
          owner.addCoins(reward);
        }
        return reward;
    }



    public void changeMaxSpeed(int by) {
        maxSpeed += by;
        if (maxSpeed < minSpeed) {
            maxSpeed = minSpeed;
        }
    }

    public void changeMinSpeed(int by) {
        minSpeed += by;
        if (minSpeed > maxSpeed) {
            minSpeed = maxSpeed;
        }
        if (minSpeed < 1) {
            minSpeed = 1;
        }
    }

    public void addMaxLapsDone(int laps) {
        maxLapsDone += laps;
    }

    public int getLapsDone() {
        return lapsDone;
    }

    public void setLapsDone(int lapsDone) {
        this.lapsDone = lapsDone;
    }

    public void setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getMaxLapsDone() {
        return maxLapsDone;
    }

    public void setMediator(EntityMediator mediator) {
        this.mediator = mediator;
    }

    public boolean canPayInMaxSpeed(int quantity) {
        return maxSpeed-quantity >= minSpeed;
    }

    public boolean canPayInMinSpeed(int quantity) {
        return minSpeed-quantity >= 0;
    }
}
