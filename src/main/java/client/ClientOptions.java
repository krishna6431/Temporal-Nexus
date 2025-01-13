// Package declaration for client-related classes
package client;

// Import statements for required dependencies
import io.grpc.Metadata; // For gRPC metadata handling
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts; // For gRPC SSL context configuration
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder; // For building SSL contexts
import io.grpc.netty.shaded.io.netty.handler.ssl.util.InsecureTrustManagerFactory; // For insecure
                                                                                   // SSL testing
import io.grpc.stub.MetadataUtils; // For gRPC metadata utilities
import io.temporal.client.WorkflowClient; // Temporal workflow client
import io.temporal.client.WorkflowClientOptions; // Options for workflow client
import io.temporal.serviceclient.WorkflowServiceStubs; // Temporal service stubs
import io.temporal.serviceclient.WorkflowServiceStubsOptions; // Options for service stubs
import java.io.FileInputStream; // File input handling
import java.io.FileNotFoundException; // File not found exception
import javax.net.ssl.SSLException; // SSL-related exceptions
import org.apache.commons.cli.*; // Command line argument parsing

// Class author documentation
/**
 * @author krishna
 */

// Main class for handling client configuration options
public class ClientOptions {
  // Static method to create and configure a WorkflowClient based on command line arguments
  public static WorkflowClient getWorkflowClient(String[] args) {
    // Get command line options configuration
    Options options = getOptions();

    // Create command line parser and formatter
    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd = null;

    // Try to parse command line arguments
    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      // Print error message and help information if parsing fails
      System.out.println(e.getMessage());
      formatter.printHelp("utility-name", options);
      System.exit(1);
    }

    // Extract command line option values with defaults
    String targetHost = cmd.getOptionValue("target-host", "localhost:7233");
    String namespace = cmd.getOptionValue("namespace", "default");
    String serverRootCaCert = cmd.getOptionValue("server-root-ca-cert", "");
    String clientCert = cmd.getOptionValue("client-cert", "");
    String clientKey = cmd.getOptionValue("client-key", "");
    String serverName = cmd.getOptionValue("server-name", "");
    boolean insecureSkipVerify = cmd.hasOption("insecure-skip-verify");
    String apiKey = cmd.getOptionValue("api-key", "");

    // Validate that API key and client certificates are not both provided
    if (!apiKey.isEmpty() && (!clientCert.isEmpty() || !clientKey.isEmpty())) {
      throw new IllegalArgumentException("API key and client cert/key are mutually exclusive");
    }

    // Create builder for service stub options with target host
    WorkflowServiceStubsOptions.Builder serviceStubOptionsBuilder =
        WorkflowServiceStubsOptions.newBuilder().setTarget(targetHost);

    // Configure TLS if client certificates are provided
    if (!clientCert.isEmpty() || !clientKey.isEmpty()) {
      // Validate both cert and key are provided
      if (clientCert.isEmpty() || clientKey.isEmpty()) {
        throw new IllegalArgumentException("Both client-cert and client-key must be provided");
      }
      try {
        // Build SSL context with client certificates
        SslContextBuilder sslContext =
            SslContextBuilder.forClient()
                .keyManager(new FileInputStream(clientCert), new FileInputStream(clientKey));

        // Add server root CA if provided
        if (serverRootCaCert != null && !serverRootCaCert.isEmpty()) {
          sslContext.trustManager(new FileInputStream(serverRootCaCert));
        }

        // Configure insecure trust manager if verification should be skipped
        if (insecureSkipVerify) {
          sslContext.trustManager(InsecureTrustManagerFactory.INSTANCE);
        }

        // Set SSL context in options builder
        serviceStubOptionsBuilder.setSslContext(GrpcSslContexts.configure(sslContext).build());
      } catch (SSLException | FileNotFoundException e) {
        throw new RuntimeException(e);
      }

      // Set server name if provided
      if (serverName != null && !serverName.isEmpty()) {
        serviceStubOptionsBuilder.setChannelInitializer(c -> c.overrideAuthority(serverName));
      }
    }

    // Configure API key authentication if provided
    if (!apiKey.isEmpty()) {
      serviceStubOptionsBuilder.setEnableHttps(true);
      serviceStubOptionsBuilder.addApiKey(() -> apiKey);

      // Create and configure metadata for namespace
      Metadata.Key<String> TEMPORAL_NAMESPACE_HEADER_KEY =
          Metadata.Key.of("temporal-namespace", Metadata.ASCII_STRING_MARSHALLER);
      Metadata metadata = new Metadata();
      metadata.put(TEMPORAL_NAMESPACE_HEADER_KEY, namespace);

      // Set channel interceptor for metadata
      serviceStubOptionsBuilder.setChannelInitializer(
          (channel) -> {
            channel.intercept(MetadataUtils.newAttachHeadersInterceptor(metadata));
          });
    }

    // Create service stubs with configured options
    WorkflowServiceStubs service =
        WorkflowServiceStubs.newServiceStubs(serviceStubOptionsBuilder.build());

    // Create and return workflow client with namespace configuration
    return WorkflowClient.newInstance(
        service, WorkflowClientOptions.newBuilder().setNamespace(namespace).build());
  }

  // Private method to define command line options
  private static Options getOptions() {
    Options options = new Options();

    // Add target host option
    Option targetHostOption = new Option("target-host", true, "Host:port for the Temporal service");
    targetHostOption.setRequired(false);
    options.addOption(targetHostOption);

    // Add namespace option
    Option namespaceOption = new Option("namespace", true, "Namespace to connect to");
    namespaceOption.setRequired(false);
    options.addOption(namespaceOption);

    // Add server root CA certificate option
    Option serverRootCaOption =
        new Option("server-root-ca-cert", true, "Optional path to root server CA cert");
    serverRootCaOption.setRequired(false);
    options.addOption(serverRootCaOption);

    // Add client certificate option
    Option clientCertOption =
        new Option(
            "client-cert", true, "Optional path to client cert, mutually exclusive with API key");
    clientCertOption.setRequired(false);
    options.addOption(clientCertOption);

    // Add client key option
    Option clientKeyOption =
        new Option(
            "client-key", true, "Optional path to client key, mutually exclusive with API key");
    clientKeyOption.setRequired(false);
    options.addOption(clientKeyOption);

    // Add API key option
    Option apiKeyOption =
        new Option("api-key", true, "Optional API key, mutually exclusive with cert/key");
    apiKeyOption.setRequired(false);
    options.addOption(apiKeyOption);

    // Add server name option
    Option serverNameOption =
        new Option(
            "server-name", true, "Server name to use for verifying the server's certificate");
    serverNameOption.setRequired(false);
    options.addOption(serverNameOption);

    // Add insecure skip verify option
    Option insercureSkipVerifyOption =
        new Option(
            "insecure-skip-verify",
            false,
            "Skip verification of the server's certificate and host name");
    insercureSkipVerifyOption.setRequired(false);
    options.addOption(insercureSkipVerifyOption);

    // Return configured options
    return options;
  }
}
