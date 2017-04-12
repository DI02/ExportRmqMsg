
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by D.I. on 07.11.2016.
 */
public class ExportRmqMsg {

    public static void main(String[] args) throws Exception {

        org.apache.commons.cli.Options options = new org.apache.commons.cli.Options();

        org.apache.commons.cli.Option con_string = new org.apache.commons.cli.Option("c", "connection", true, "connection to RMQ: user:password@host:port");
        con_string.setRequired(true);
        options.addOption(con_string);

        org.apache.commons.cli.Option queues = new org.apache.commons.cli.Option("q", "queues", true, "Queues to read");
        queues.setRequired(true);
        options.addOption(queues);

        options.addOption("p","prefetch",true,"Prefetch count");


        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        org.apache.commons.cli.CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (org.apache.commons.cli.ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Utility to read massages from RMQ queue and save them to file.", options);

            System.exit(1);
            return;
        }

        AmqpConnectionParameters conn_string =
                new AmqpConnectionParameters(
                        "amqp://"+cmd.getOptionValue("connection"));

        Map<String,Object> con_params = new HashMap<String, Object>();
        con_params.put("User",conn_string.getUsername());
        con_params.put("Password",conn_string.getPassword());
        con_params.put("Hostname",conn_string.getHost());
        con_params.put("Port",conn_string.getPort());
        con_params.put("VirtHost",conn_string.getVHost());

        if (cmd.getOptionValue("prefetch") == null) {
            con_params.put("Prefetch", "1000");
        }
        else {
            con_params.put("Prefetch",cmd.getOptionValue("prefetch") );
        }
        String values[] = cmd.getOptionValues("queues");

        System.out.println(" [*] Starting...");

        for(int i=0; i<values.length; i++){

            System.out.println(" [*] Queue name: " + values[i] +". Thread open.");
            Runnable r = new SimpleThread(con_params,values[i]);
            new Thread(r).start();

        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    }




}
