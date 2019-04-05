package skamila.calculator;

import java.math.BigDecimal;

import skamila.calculator.fragments.exceptions.DivByZeroException;
import skamila.calculator.fragments.exceptions.NegativeNumberException;

public class Calculator {

    private BigDecimal prevValue;
    private BigDecimal actualValue;
    private String actualOperation;

    public Calculator(){
        prevValue = BigDecimal.valueOf(0);
        actualValue = BigDecimal.valueOf(0);
        actualOperation = "";
    }

    public BigDecimal getPrevValue(){
        return prevValue;
    }

    public BigDecimal getActualValue(){
        return actualValue;
    }

    public String getActualOperation(){
        return actualOperation;
    }

    public void archiveValue(){
        this.prevValue = actualValue;
        actualValue = new BigDecimal(0);
    }

    public void setPrevValue(BigDecimal prevValue){
        this.prevValue = prevValue;
    }

    public void setActualValue(BigDecimal actualValue){
        this.actualValue = actualValue;
    }

    public void setActualOperation(String actualOperation){
        this.actualOperation = actualOperation;
    }

    public void doOperation() throws DivByZeroException, NegativeNumberException {

        switch (actualOperation) {
            case "+":
                add();
                break;
            case "-":
                sub();
                break;
            case "×":
                multi();
                break;
            case "÷":
                if(this.actualValue.doubleValue() != 0){
                    div();
                } else {
                    actualOperation = "";
                    actualValue = prevValue;
                    throw new DivByZeroException();
                }
                break;
            case "^":
                if(this.actualValue.doubleValue() >= 0){
                    powY();
                } else {
                    actualOperation = "";
                    throw new NegativeNumberException();
                }
                break;
            case "±":
                changeSign();
                return;
            case "√":
                if(this.actualValue.doubleValue() >= 0){
                    sqr();
                } else {
                    actualOperation = "";
                    throw new NegativeNumberException();
                }
                break;
            case "%":
                percent();
                break;
            case "sin":
                sin();
                break;
            case "cos":
                cos();
                break;
            case "tan":
                tan();
                break;
            case "^2":
                pow2();
                break;
            case "ln":
                if(this.actualValue.doubleValue() >= 0){
                    ln();
                } else {
                    actualOperation = "";
                    throw new NegativeNumberException();
                }
                break;
            case "log":
                if(this.actualValue.doubleValue() >= 0){
                    log();
                } else {
                    actualOperation = "";
                    throw new NegativeNumberException();
                }
                break;
        }

        actualOperation = "";
        //actualValue = actualValue.round(new MathContext(3, RoundingMode.HALF_UP));

    }

    public void clear(){
        actualValue = BigDecimal.valueOf(0);
    }

    public void clearAll(){
        prevValue = BigDecimal.valueOf(0);
        actualValue = BigDecimal.valueOf(0);
        actualOperation = "";
    }

    public boolean isActualOperation(){
        return !actualOperation.equals("");
    }

    private void add(){
        actualValue = prevValue.add(actualValue);
        prevValue = BigDecimal.valueOf(0);
    }

    private void sub(){
        actualValue = prevValue.subtract(actualValue);
        prevValue = BigDecimal.valueOf(0);
    }

    private void multi(){
        actualValue = prevValue.multiply(actualValue);
        prevValue = BigDecimal.valueOf(0);
    }

    private void div(){
        actualValue = prevValue.divide(actualValue);
        prevValue = BigDecimal.valueOf(0);
    }

    private void pow2(){
        actualValue = actualValue.pow(2);
    }

    private void powY(){
        actualValue = prevValue.pow(actualValue.intValue());
        prevValue = BigDecimal.valueOf(0);
    }

    private void changeSign(){
        actualValue = actualValue.multiply(BigDecimal.valueOf(-1));
    }

    private void sqr(){
        actualValue = BigDecimal.valueOf(Math.sqrt(actualValue.doubleValue()));;
    }

    private void percent(){
        actualValue = actualValue.divide(BigDecimal.valueOf(100));
    }

    private void sin(){
        actualValue = BigDecimal.valueOf(Math.sin(actualValue.doubleValue()/57.2957795));
    }

    private void cos(){
        actualValue = BigDecimal.valueOf(Math.cos(actualValue.doubleValue()/57.2957795));
    }

    private void tan(){
        actualValue = BigDecimal.valueOf(Math.tan(actualValue.doubleValue()/57.2957795));
    }

    private void ln(){
        actualValue = BigDecimal.valueOf(Math.log(actualValue.doubleValue()));
    }

    private void log(){
        actualValue = BigDecimal.valueOf(Math.log10(actualValue.doubleValue()));
    }

}
