using ImageCoffeeUtilsCrop.Crop.Adapter.In.Utils;
using Steeltoe.Extensions.Configuration.ConfigServer;

namespace ImageCoffeeUtilsCrop.Crop.Adapter.In.SpringCloud
{
    /// <summary>
    /// Spring Cloud Config client.
    /// </summary>
    public static class SpringConfigClient
    {
        /// <summary>
        /// Initialize Spring Cloud Config client.
        /// </summary>
        /// <param name="builder">Web application builder</param>
        public static void Init(WebApplicationBuilder builder)
        {
            ConfigurationUtils.UseEnvVariableOrDefault(
                "SPRING_CLOUD_CONFIG_URI",
                "spring:cloud:config:uri",
                builder.Configuration
            );
            builder.Configuration.AddConfigServer(builder.Environment);
        }
    }
}
