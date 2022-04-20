using NewMail.Web.Services;

namespace NewMail.Web.AppInit
{
    public static class InitServices
    {
        public static void UseAppServices(this WebApplicationBuilder builder)
        {
            builder.Services.AddScoped<IJwtTokenService, JwtTokenService>();
        }
    }
}
