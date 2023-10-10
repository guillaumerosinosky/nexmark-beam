package be.uclouvain.gepiciad.benchmark.beam.nexmark;

import org.apache.beam.sdk.extensions.sql.BeamSqlCli;
import org.apache.beam.sdk.extensions.sql.meta.provider.TableProvider;
import org.apache.beam.sdk.extensions.sql.meta.provider.kafka.KafkaTableProvider;
import org.apache.beam.sdk.extensions.sql.meta.provider.text.TextTableProvider;
import org.apache.beam.sdk.extensions.sql.meta.store.InMemoryMetaStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class BeamSqlEnvKafkaQueryBerserker {
    

    public static void main(String[] args) {   
        final Logger log = LoggerFactory.getLogger(BeamSqlEnvKafkaQueryBerserker.class);
        InMemoryMetaStore metaStore = new InMemoryMetaStore();

        TableProvider kafkaTableProvider = new KafkaTableProvider();        
        TableProvider textProvider = new TextTableProvider();
        metaStore.registerProvider(kafkaTableProvider);
        metaStore.registerProvider(textProvider);

        BeamSqlCli cli = new BeamSqlCli().metaStore(metaStore);
        cli.execute("SET jobName='test'");
        cli.execute("SET appName='test'");
        cli.execute("SET runner = FlinkRunner");
        cli.execute("SET parallelism = '4'");
        cli.execute("SET flinkMaster = 'localhost:8081'");  // remote cluster from compose
        cli.execute("SET operatorChaining = 'false'");
        cli.execute("SET streaming = 'false'");
        cli.execute(
            "CREATE EXTERNAL TABLE test_table (\n"
                + "id VARCHAR, \n"
                + "label VARCHAR, \n"
                + "quantity INT, \n"
                + "ts TIMESTAMP"
                + ") \n"
                + "TYPE kafka \n"
                + "LOCATION 'kafka-edge1:9092/berserker_test' \n" // remote cluster from inside compose deployment
                + "TBLPROPERTIES '{"
                + " \"bootstrap_servers\": [\"localhost:9094\"], \n"
                + " \"format\": \"json\" "
                + "}'"
                );
        cli.execute(
            "CREATE EXTERNAL TABLE test_table2 (\n"
                + "label VARCHAR, \n"
                + "quantity INT"
                + ") \n"
                + "TYPE kafka \n"
                + "LOCATION 'kafka-edge1:9092/berserker_test2' \n" // remote cluster from inside compose deployment
                + "TBLPROPERTIES '{"
                + "    \"format\": \"json\" "
                + "}'"
                );                
        cli.execute(
              "INSERT INTO test_table2 \n"
            + "SELECT label, COUNT(*) \n" 
            + "FROM test_table \n"
            + "WHERE label <> 'tomato' \n"
            + "GROUP BY label, TUMBLE(ts, INTERVAL '1' MINUTE) ");

    }
}
