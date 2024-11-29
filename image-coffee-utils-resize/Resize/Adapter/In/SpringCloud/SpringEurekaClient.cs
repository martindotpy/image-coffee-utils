using ImageCoffeeUtilsCrop.Crop.Adapter.In.Utils;
using Steeltoe.Discovery.Client;

namespace ImageCoffeeUtilsCrop.Crop.Adapter.In.SpringCloud
{
    /// <summary>
    /// Spring Cloud Eureka client.
    /// </summary>
    public static class SpringEurekaClient
    {
        /// <summary>
        /// Initialize Spring Cloud Eureka client.
        /// </summary>
        /// <param name="builder">Web application builder</param>
        public static void Init(WebApplicationBuilder builder)
        {
            ConfigurationUtils.UseEnvVariableOrDefault(
                "SPRING_CLOUD_EUREKA_HOSTNAME",
                "eureka:instance:hostname",
                builder.Configuration
            );
            string eurekaUrl =
                builder.Configuration.GetValue<string>("eureka:client:serviceUrl")
                ?? "http://localhost:8761/eureka";
            string eurekaHostName =
                builder.Configuration.GetValue<string>("eureka:instance:hostname") ?? "localhost";
            eurekaUrl = eurekaUrl.Replace("${eureka.instance.hostname}", eurekaHostName);
            builder.Configuration["eureka:client:serviceUrl"] = eurekaUrl;

            builder.Services.AddDiscoveryClient(builder.Configuration);
            builder.Services.AddConfigurationDiscoveryClient(builder.Configuration);
        }
    }
}
