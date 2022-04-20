using Microsoft.Extensions.FileProviders;

namespace NewMail.Web.Helpers
{
    public static class AppStaticFiles
    {
        public static void UseAppStaticFiles(this WebApplication app)
        {
            var dir = Path.Combine(Directory.GetCurrentDirectory(), "uploads");
            if (!Directory.Exists(dir))
            {
                Directory.CreateDirectory(dir);
            }
            app.UseStaticFiles(new StaticFileOptions
            {
                FileProvider = new PhysicalFileProvider(dir),
                RequestPath = "/images"
            });
        }
    }   
}
