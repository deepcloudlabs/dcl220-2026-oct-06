module com.example.catalog.model {
    requires static java.compiler;
    exports com.example.catalog.model.internal to com.example.catalog.provider;
    exports com.example.catalog.model;
    opens com.example.catalog.model.diagnostics to com.example.catalog.app;
}
