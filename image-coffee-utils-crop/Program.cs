using Microsoft.Extensions.Options;
using Steeltoe.Discovery.Client;
using Steeltoe.Extensions.Configuration.ConfigServer;

var builder = WebApplication.CreateBuilder(args);

// Use SPRING_CLOUD_CONFIG_URI if is set
var defaultLogLevel = builder.Configuration["spring:cloud:config:uri"];
var envLogLevel = Environment.GetEnvironmentVariable("SPRING_CLOUD_CONFIG_URI");
if (!string.IsNullOrEmpty(envLogLevel))
{
    builder.Configuration.AddInMemoryCollection(
        initialData: new Dictionary<string, string?> { { "spring:cloud:config:uri", envLogLevel } }
    );
}
else
{
    builder.Configuration.AddInMemoryCollection(
        new Dictionary<string, string?> { { "spring:cloud:config:uri", defaultLogLevel } }
    );
}

// Config server
builder.Configuration.AddConfigServer(builder.Environment);

var host = System.Net.Dns.GetHostEntry(System.Net.Dns.GetHostName());
var localIp = host.AddressList.FirstOrDefault(ip => ip.AddressFamily == System.Net.Sockets.AddressFamily.InterNetwork)?.ToString() ?? "127.0.0.1";
var port = builder.Configuration.GetValue<int>("server:port");
string path = builder.Configuration.GetValue<string>("server:servlet:context-path") ?? string.Empty;
path = path.StartsWith('/') ? path[1..] : path;

builder.WebHost.UseUrls($"http://{localIp}:{port}");


// Eureka server
builder.Services.AddDiscoveryClient(builder.Configuration);
builder.Services.AddConfigurationDiscoveryClient(builder.Configuration);

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

// Configure the HTTP request pipeline
if (app.Environment.IsDevelopment())
{
    app.UseSwagger(options =>
    {
        options.RouteTemplate = $"{path}/docs/{{documentName}}/swagger.json";
    });
    app.UseSwaggerUI(options =>
    {
        options.RoutePrefix = $"{path}/docs";
        options.SwaggerEndpoint($"/{path}/docs/v0/swagger.json", "API V1");
    });
}

app.UsePathBase($"/{path}");
app.UseRouting();

app.UseHttpsRedirection();
app.UseAuthorization();

app.MapControllers();

var banner = System.IO.File.ReadAllText("banner.txt");
Console.WriteLine(banner);

app.Run();
