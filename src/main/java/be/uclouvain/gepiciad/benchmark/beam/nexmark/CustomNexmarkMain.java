package be.uclouvain.gepiciad.benchmark.beam.nexmark;

import org.apache.beam.sdk.nexmark.Main;
import org.apache.beam.sdk.nexmark.NexmarkConfiguration;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.nexmark.NexmarkOptions;
import java.io.IOException;
import org.apache.beam.runners.flink.FlinkRunner;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.nexmark.NexmarkConfiguration;
import org.apache.beam.sdk.nexmark.NexmarkLauncher;

public class CustomNexmarkMain extends Main {

  public static void main(String[] args) {
    //TODO: make it customizable
    /*
    NexmarkConfiguration configuration = NexmarkConfiguration.DEFAULT;
    PipelineOptionsFactory.register(NexmarkOptions.class);
    NexmarkOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(NexmarkOptions.class);
    options.setRunner(FlinkRunner.class);

    NexmarkLauncher<NexmarkOptions> nexmarkLauncher = new NexmarkLauncher<NexmarkOptions>(options, configuration);
    
    try {    
      nexmarkLauncher.run();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    // You can add custom logic here, such as modifying the configuration or adding new functionality.
  */
    try {
        Main.main(args);
    } catch (IOException e) {
        e.printStackTrace();
    }

  }
}