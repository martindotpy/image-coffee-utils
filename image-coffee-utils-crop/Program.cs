using System.Net;
using System.Net.Sockets;
using ImageCoffeeUtilsCrop.Crop.Adapter.In.SpringCloud;
using ImageCoffeeUtilsCrop.Crop.Adapter.In.Swagger;
using ImageCoffeeUtilsCrop.Crop.Application.Port.In;
using ImageCoffeeUtilsCrop.Crop.Application.UseCase;

string banner = File.ReadAllText("./banner.txt");
Console.WriteLine(banner);

var builder = WebApplication.CreateBuilder(args);

// Config server
SpringConfigClient.Init(builder);

// Server
IPHostEntry host = Dns.GetHostEntry(Dns.GetHostName());
string localIp =
    host.AddressList.FirstOrDefault(ip => ip.AddressFamily == AddressFamily.InterNetwork)
        ?.ToString() ?? "127.0.0.1";
int port = builder.Configuration.GetValue<int>("server:port");
string basePath =
    builder.Configuration.GetValue<string>("server:servlet:context-path") ?? string.Empty;
basePath = basePath.StartsWith('/') ? basePath[1..] : basePath;

builder.WebHost.UseUrls($"http://{localIp}:{port}");

// Eureka client
SpringEurekaClient.Init(builder);

// Services
builder.Services.AddSingleton<ICropImagePort, CropImageUseCase>();

// Swagger and controllers
builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
SwaggerConfigurer.Configure(builder);

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

SwaggerConfigurer.Use(app, basePath);
app.Map(
    $"/{basePath}",
    appBuilder =>
    {
        appBuilder.UseRouting();
        appBuilder.UseEndpoints(endpoints =>
        {
            endpoints.MapControllers();
        });
    }
);
app.UseRouting();

app.Run();
