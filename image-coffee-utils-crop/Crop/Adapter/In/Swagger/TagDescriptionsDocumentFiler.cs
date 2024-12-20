using Microsoft.OpenApi.Models;
using Swashbuckle.AspNetCore.SwaggerGen;

namespace ImageCoffeeUtilsCrop.Crop.Adapter.In.Swagger
{
    /// <summary>
    /// Filter to add descriptions to tags
    /// </summary>
    public class TagDescriptionsDocumentFilter : IDocumentFilter
    {
        /// <inheritdoc/>
        public void Apply(OpenApiDocument swaggerDoc, DocumentFilterContext context)
        {
            swaggerDoc.Tags = [new OpenApiTag { Name = "Crop", Description = "Image cropping" }];
        }
    }
}
