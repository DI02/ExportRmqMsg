import java.util.Map;

/**
 * Created by D.I. on 07.12.2016.
 */
class SimpleThread implements Runnable {

    private Map<String, Object> con_paramss;
    private String rmq_queue;

    public SimpleThread(Map<String,Object> con_params, String rmq_queue ) {
        this.con_paramss  = con_params;
        this.rmq_queue = rmq_queue;
    }

    @Override
    public void run() {
        System.out.println(" [*] New thread started. ");
        RabbitConsumer.StartConsume(con_paramss,rmq_queue);
    }
}
