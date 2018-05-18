package core.strategy;

import core.Message;

import java.util.List;

public class OscillatorStrategyImpl implements IOscillatorStrategy {

    public OscillatorStrategyImpl(){}

    @Override
    public boolean isOscillating(List<Message> l) {
        /* Detect consecutive oscillations not periodic ones */

        if(l.size() < 3){
            return false;
        }

        double val1 = l.get(0).getValue(), val2 = l.get(1).getValue();
        int marginOfError=10;
        for(int i = 2;i<l.size();i++){
            if(l.get(i).getValue()<=val1+marginOfError && l.get(i).getValue()>=val1-marginOfError){
                if(l.get(i).getValue()>val2+marginOfError || l.get(i).getValue()<val2-marginOfError){
                    val1=val2;
                    val2=l.get(i).getValue();
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }

        return true;
    }
}
