using System.Reflection;
using Microsoft.OpenApi.Models;

namespace ImageCoffeeUtilsCrop.Crop.Adapter.In.Swagger
{
    /// <summary>
    /// Filter to add descriptions to tags
    /// </summary>
    public static class SwaggerConfigurer
    {
        /// <summary>
        /// Configure the Swagger.
        /// </summary>
        /// <param name="builder">Web application builder</param>
        public static void Configure(WebApplicationBuilder builder)
        {
            builder.Services.AddSwaggerGen(options =>
            {
                options.SwaggerDoc(
                    "v0",
                    new OpenApiInfo
                    {
                        Title = "Image Coffee Utils Crop API",
                        Version = "v0.0.1",
                        Description = "API for cropping images",
                    }
                );

                var xmlFile = $"{Assembly.GetExecutingAssembly().GetName().Name}.xml";
                var xmlPath = Path.Combine(AppContext.BaseDirectory, xmlFile);
                options.IncludeXmlComments(xmlPath);

                options.DocumentFilter<TagDescriptionsDocumentFilter>();
            });
        }

        /// <summary>
        /// Use the Swagger.
        /// </summary>
        /// <param name="app">Web application</param>
        /// <param name="basePath">Application base path</param>
        public static void Use(WebApplication app, string basePath)
        {
            app.UseSwagger(options =>
            {
                options.RouteTemplate = $"{basePath}/docs/{{documentName}}/swagger.json";
                options.PreSerializeFilters.Add(
                    (swaggerDoc, httpReq) =>
                    {
                        var paths = new OpenApiPaths();

                        foreach (var path in swaggerDoc.Paths)
                        {
                            paths.Add($"/{basePath}{path.Key}", path.Value);
                        }

                        swaggerDoc.Paths = paths;
                    }
                );
            });
            app.UseSwaggerUI(options =>
            {
                options.RoutePrefix = $"{basePath}/docs";
                options.SwaggerEndpoint($"/{basePath}/docs/v0/swagger.json", "api v0");
            });
        }
    }
}
