package be.uclouvain.gepiciad.benchmark.beam.nexmark;

import org.apache.beam.sdk.extensions.sql.BeamSqlCli;
import org.apache.beam.sdk.extensions.sql.meta.provider.TableProvider;
import org.apache.beam.sdk.extensions.sql.meta.provider.kafka.KafkaTableProvider;
import org.apache.beam.sdk.extensions.sql.meta.provider.text.TextTableProvider;
import org.apache.beam.sdk.extensions.sql.meta.store.InMemoryMetaStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class BeamSqlEnvKafkaGenerator {
    

    public static void main(String[] args) {   
        final Logger log = LoggerFactory.getLogger(BeamSqlEnvKafkaGenerator.class);
        InMemoryMetaStore metaStore = new InMemoryMetaStore();

        TableProvider kafkaTableProvider = new KafkaTableProvider();        
        TableProvider textProvider = new TextTableProvider();
        metaStore.registerProvider(kafkaTableProvider);
        metaStore.registerProvider(textProvider);

        BeamSqlCli cli = new BeamSqlCli().metaStore(metaStore);
        cli.execute("SET jobName='test'");
        cli.execute("SET appName='test'");
        cli.execute("SET runner = FlinkRunner");
        //cli.execute("SET flinkMaster = 'localhost:8081'"); // Use mini cluster for topic initialization
        cli.execute("SET operatorChaining = 'false'");
        cli.execute(
            "CREATE EXTERNAL TABLE test_table (\n"
                + "id INT, \n"
                + "label VARCHAR, \n"
                + "ts TIMESTAMP"
                + ") \n"
                + "TYPE kafka \n"
                + "LOCATION 'localhost:9094/test' \n" // Use Kafka cluster from outside Compose network
//                + "LOCATION 'kafka-edge1:9092/test' \n" 
                + "TBLPROPERTIES '{"
                + "    \"format\": \"json\" "
                + "}'"
                );
        cli.execute(
            "CREATE EXTERNAL TABLE csv_table (\n"
                + "id INT, \n"
                + "label VARCHAR, \n"
                + "ts TIMESTAMP"
                + ") \n"
                + "TYPE text \n"
                + "LOCATION '/home/guillaume/research/gepiciad/nexmark-beam/test.csv'"
                );

        cli.execute("INSERT INTO test_table SELECT * FROM csv_table");
    }
}
