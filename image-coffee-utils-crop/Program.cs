using System.Net;
using image_coffee_utils_crop.Utils;
using Steeltoe.Discovery.Client;
using Steeltoe.Extensions.Configuration.ConfigServer;

var builder = WebApplication.CreateBuilder(args);

// Config server
ConfigurationUtils.UseEnvVariableOrDefault("SPRING_CLOUD_CONFIG_URI", "spring:cloud:config:uri", builder.Configuration);
builder.Configuration.AddConfigServer(builder.Environment);

// Server
IPHostEntry host = System.Net.Dns.GetHostEntry(System.Net.Dns.GetHostName());
string localIp = host.AddressList.FirstOrDefault(ip => ip.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork)?.ToString() ?? "127.0.0.1";
int port = builder.Configuration.GetValue<int>("server:port");
string path = builder.Configuration.GetValue<string>("server:servlet:context-path") ?? string.Empty;
path = path.StartsWith('/') ? path[1..] : path;

builder.WebHost.UseUrls($"http://{localIp}:{port}");

// Eureka client
ConfigurationUtils.UseEnvVariableOrDefault("SPRING_CLOUD_EUREKA_HOSTNAME", "eureka:instance:hostname", builder.Configuration);
string eurekaUrl = builder.Configuration.GetValue<string>("eureka:client:serviceUrl") ?? "http://localhost:8761/eureka";
string eurekaHostName = builder.Configuration.GetValue<string>("eureka:instance:hostname") ?? "localhost";
eurekaUrl = eurekaUrl.Replace("${eureka.instance.hostname}", eurekaHostName);
builder.Configuration["eureka:client:serviceUrl"] = eurekaUrl;

builder.Services.AddDiscoveryClient(builder.Configuration);
builder.Services.AddConfigurationDiscoveryClient(builder.Configuration);

// Swagger and controllers
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(options =>
{
    options.SwaggerDoc("v0", new Microsoft.OpenApi.Models.OpenApiInfo
    {
        Title = "Image Coffee Utils Crop API",
        Version = "v0.0.1",
        Description = "API for cropping images",
    });
});

var app = builder.Build();

app.Map("/info", appBuilder =>
{
    appBuilder.Run(async context =>
    {
        context.Response.StatusCode = 200;
        context.Response.ContentType = "application/json";

        await context.Response.WriteAsync("{\"status\":\"UP\"}");
    });
});

app.UseSwagger(options =>
{
    options.RouteTemplate = $"{path}/docs/{{documentName}}/swagger.json";
});
app.UseSwaggerUI(options =>
{
    options.RoutePrefix = $"{path}/docs";
    options.SwaggerEndpoint($"/{path}/docs/v0/swagger.json", "API V1");
});

app.UsePathBase($"/{path}");
app.UseRouting();

app.MapControllers();

string banner = System.IO.File.ReadAllText("./banner.txt");
Console.WriteLine(banner);

app.Run();
