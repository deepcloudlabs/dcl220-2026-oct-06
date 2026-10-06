module com.example.catalog.provider {
    requires com.example.catalog.api;

    provides com.example.catalog.api.CatalogService
            with com.example.catalog.provider.InMemoryCatalog;
}
