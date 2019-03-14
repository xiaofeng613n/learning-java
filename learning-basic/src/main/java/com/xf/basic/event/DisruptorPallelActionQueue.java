package com.xf.basic.event;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.LinkedList;
import java.util.Queue;

public class DisruptorPallelActionQueue extends AbstractActionQueue{

    private Disruptor<ActionEvent> disruptor;

    private ActionEventTranslator translator = new ActionEventTranslator();

    public DisruptorPallelActionQueue(Disruptor<ActionEvent> disruptor) {
        super(new LinkedList<Action>());
        this.disruptor = disruptor;
    }

    @Override
    public void doExecutor(Action action) {
        disruptor.publishEvent(translator, action);
    }

    public static class ActionEvent{

        public Action getValue() {
            return value;
        }

        public void setValue(Action value) {
            this.value = value;
        }

        private Action value;


    }

    public static class ActionEventTranslator implements EventTranslatorOneArg<ActionEvent, Action> {

        @Override
        public void translateTo(ActionEvent event, long sequence, Action value) {
            event.setValue(value);
        }
    }

}
