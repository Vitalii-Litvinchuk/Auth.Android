using Microsoft.AspNetCore.Identity;
using NewMail.Web.Data.Entities.Identity;
using NewMail.Web.Root;

namespace NewMail.Web.Data
{
    public static class SeederDB
    {
        public static void SeedData(this IApplicationBuilder app)
        {
            using (var scope =
                app.ApplicationServices.GetRequiredService<IServiceScopeFactory>().CreateScope())
            {
                try
                {
                    InitRoleAndUsers(scope);
                }
                catch (Exception ex)
                {
                    var logger = scope.ServiceProvider.GetRequiredService<ILogger<Program>>();
                    logger.LogError("Problem seed database " + ex.Message);

                }
            }
        }

        private static void InitRoleAndUsers(IServiceScope scope)
        {
            var roleManager = scope.ServiceProvider.GetRequiredService<RoleManager<AppRole>>();
            var userManager = scope.ServiceProvider.GetRequiredService<UserManager<AppUser>>();
            if (!roleManager.Roles.Any())
            {
                var logger = scope.ServiceProvider.GetRequiredService<ILogger<Program>>();
                var result = roleManager.CreateAsync(new AppRole
                {
                    Name = Roles.Admin
                }).Result;
                if (result.Succeeded)
                    logger.LogWarning($"Create role {Roles.Admin}");
                else
                    logger.LogError($"Problem crate role {Roles.Admin}");
                result = roleManager.CreateAsync(new AppRole
                {
                    Name = Roles.User
                }).Result;
            }
        }
    }
}
