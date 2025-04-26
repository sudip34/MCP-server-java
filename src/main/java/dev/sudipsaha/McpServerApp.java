package dev.sudipsaha;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class McpServerApp {

//    private final StableValue<Logger> logger = StableValue.of();
//    Logger getLogger() {
//        return logger.orElseSet(()->Logger.create(McpServerApp.class));
//    }

    public static final Logger log = LoggerFactory.getLogger(McpServerApp.class);
    private static PresentationTools presentationTools = new PresentationTools();
    public static void main(String[] args) {
        //Stdio server transport
        StdioServerTransportProvider transportProvider = new StdioServerTransportProvider(new ObjectMapper());

        // Sync Tool specification
        var synToolSpecification = getSyncToolSpecification();

        McpServer.sync(transportProvider)
                .serverInfo("mcpserver", "0.0.1")
                .capabilities(McpSchema.ServerCapabilities.builder()
                        .tools(true)
                        .logging()
                        .build());
        log.info("Starting server....");

    }

    private static McpServerFeatures.SyncToolSpecification getSyncToolSpecification() {
        // Sync tool specification
        var schema = """
            {
              "type" : "object",
              "id" : "urn:jsonschema:Operation",
              "properties" : {
                "operation" : {
                  "type" : "string"
                }
              }
            }
            """;
       return new McpServerFeatures.SyncToolSpecification(
                new McpSchema.Tool("get_presentation", "description_for_presentation", schema),
                (exchange, arguments)-> {
                    List<Presentation> presentations = presentationTools.getPresentations();
                    List<McpSchema.Content> contents = new ArrayList<>();
                    for (Presentation presentation : presentations) {
                        contents.add(new McpSchema.TextContent(presentation.toString()));
                    }
                    return new McpSchema.CallToolResult(contents, false);
                }
        );
    }

}
