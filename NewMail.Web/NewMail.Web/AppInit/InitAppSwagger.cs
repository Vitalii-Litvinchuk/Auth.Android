using Microsoft.OpenApi.Models;
using System.Reflection;

namespace NewMail.Web.AppInit
{
    public static class InitAppSwagger
    {
        public static void UseAppSwaggerGen(this WebApplicationBuilder builder)
        {
            var assemblyName = Assembly.GetExecutingAssembly().GetName().Name;
            builder.Services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo { Title = assemblyName, Version = "v1" });
                c.AddSecurityDefinition("Bearer",
                    new OpenApiSecurityScheme
                    {
                        Description = "JWT Authorization header using the Bearer scheme.",
                        Type = SecuritySchemeType.Http,
                        Scheme = "bearer"
                    });
                c.AddSecurityRequirement(new OpenApiSecurityRequirement{
                    {
                        new OpenApiSecurityScheme{
                            Reference = new OpenApiReference{
                                Id = "Bearer",
                                Type = ReferenceType.SecurityScheme
                            }
                        },new List<string>()
                    }
                });
                var fileDoc = Path.Combine(AppContext.BaseDirectory, $"{assemblyName}.xml");
                c.IncludeXmlComments(fileDoc);
            });
        }
    }
}
