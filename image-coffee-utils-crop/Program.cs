using System.Net;
using System.Reflection;
using ImageCoffeeUtilsCrop.Crop.Adapter.In.Swagger;
using ImageCoffeeUtilsCrop.Crop.Adapter.In.Utils;
using ImageCoffeeUtilsCrop.Crop.Application.Port.In;
using ImageCoffeeUtilsCrop.Crop.Application.UseCase;
using Microsoft.OpenApi.Models;
using Steeltoe.Discovery.Client;
using Steeltoe.Extensions.Configuration.ConfigServer;

var builder = WebApplication.CreateBuilder(args);

// Config server
ConfigurationUtils.UseEnvVariableOrDefault(
    "SPRING_CLOUD_CONFIG_URI",
    "spring:cloud:config:uri",
    builder.Configuration
);
builder.Configuration.AddConfigServer(builder.Environment);

// Server
IPHostEntry host = System.Net.Dns.GetHostEntry(System.Net.Dns.GetHostName());
string localIp =
    host.AddressList.FirstOrDefault(ip =>
            ip.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork
        )
        ?.ToString() ?? "127.0.0.1";
int port = builder.Configuration.GetValue<int>("server:port");
string basePath =
    builder.Configuration.GetValue<string>("server:servlet:context-path") ?? string.Empty;
basePath = basePath.StartsWith('/') ? basePath[1..] : basePath;

builder.WebHost.UseUrls($"http://{localIp}:{port}");

// Eureka client
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

// Services
builder.Services.AddSingleton<ICropImagePort, CropImageUseCase>();

// Swagger and controllers
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
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

var app = builder.Build();

app.Map(
    "/info",
    appBuilder =>
    {
        appBuilder.Run(async context =>
        {
            context.Response.StatusCode = 200;
            context.Response.ContentType = "application/json";

            await context.Response.WriteAsync("{\"status\":\"UP\"}");
        });
    }
);

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
    options.SwaggerEndpoint($"/{basePath}/docs/v0/swagger.json", "API V1");
});

app.UsePathBase($"/{basePath}");
app.UseRouting();

app.MapControllers();

string banner = File.ReadAllText("./banner.txt");
Console.WriteLine(banner);

app.Run();
